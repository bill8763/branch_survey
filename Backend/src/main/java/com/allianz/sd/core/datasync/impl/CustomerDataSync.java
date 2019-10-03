package com.allianz.sd.core.datasync.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.allianz.sd.core.InstanceCode;
import com.allianz.sd.core.service.*;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.allianz.sd.core.api.model.Address;
import com.allianz.sd.core.api.model.Email;
import com.allianz.sd.core.api.model.Person;
import com.allianz.sd.core.api.model.PhoneChannel;
import com.allianz.sd.core.api.model.SynchDetail;
import com.allianz.sd.core.datasync.CustomerDataSyncListener;
import com.allianz.sd.core.datasync.DataMatch;
import com.allianz.sd.core.datasync.DataSync;
import com.allianz.sd.core.jpa.bean.DataCategory;
import com.allianz.sd.core.jpa.model.Customer;
import com.allianz.sd.core.jpa.repository.CustomerRepository;
import com.allianz.sd.core.service.bean.AgentDailyAppDateType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class CustomerDataSync implements DataSync , DataMatch {

    private Logger logger = LoggerFactory.getLogger(CustomerDataSync.class);
    private String agentID = null;

    @Autowired
    private CustomerRepository customerRepository = null;

    @Autowired
    private AgentUpdateVersionService agentUpdateVersionService= null;

    @Autowired
    private DateService dateService = null;

    @Autowired
    private OrganizationService organizationService = null;

    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private UtilsService utilsService;
    
    @Override
    public boolean isDifference(Object dbData, Object pushData) {
        Customer customer = (Customer) dbData;
        Person person = (Person) pushData;

        Date pushDate = dateService.toDateTimeFormatDate(person.getSynchDetail().getLastUpdateDateTimeBackend());
        return customer.getDataTime().getTime() != pushDate.getTime();
    }

    @Override
    public Map findPullData(Date appSyncDate) {
        Map<Integer, Customer> map = new HashedMap<>();

        List<Integer> idList = agentUpdateVersionService.getAgentLastSyncData(agentID, DataCategory.CUSTOMER,appSyncDate);

        for(Integer customerId : idList) {

            Optional<Customer> customerOptional = customerRepository.findById(customerId);
            com.allianz.sd.core.jpa.model.Customer customer = customerOptional.orElse(null);

            map.put(customerId,customer);
        }

        return map;
    }

    @Override
    public Object findPushData(Object data) {
        Person person = (Person) data;
        Optional<Customer> customerOptional = customerRepository.findById(person.getCustomerID().intValue());

        return customerOptional.orElse(null);
    }

    @Override
    public Object data2SyncObj(Object data) {
        Customer customer = (Customer) data;

        Float completeness = customer.getCompleteness();

        SynchDetail synchDetail = new SynchDetail();
        synchDetail.setToDelete(false);
        synchDetail.setLastUpdateDateTimeBackend(dateService.toDateTimeFormatString(customer.getDataTime()));

        Person person = new Person();
        person.setCustomerID(new BigDecimal(customer.getCustomerID()));
        person.setLastName(customer.getLastName());
        person.setFirstName(customer.getFirstName());
        person.setMiddleName(customer.getMiddleName());

        person.setAgeRange(customer.getAgeRange());
        person.setAlternateName("");
        person.setAnnualIncomeRange(customer.getIncome());
        person.setBirthDate(customer.getBirthdayYear() + "-" + customer.getBirthdayMonth() + "-" + customer.getBirthdayDate());
        person.setContactLink(customer.getSource());
        person.setEmployer(customer.getCompany());
        person.setFamiliarity(customer.getFamiliarity());
        person.setGender(customer.getGender());
        person.setIsChangeable(!"OPUS".equals(customer.getDataSource()));
        person.setIsFollowed("Y".equals(customer.getIsFollow()));
        person.setIsOverTimeAlert(customer.getOverTimeAlertTime() != null);
        person.setLeadProbability(customer.getPossibility());
        person.setMarritalStatus(customer.getMarriage());
        person.setNumberOfChildren(StringUtils.isNotEmpty(customer.getChildren()) && !"null".equalsIgnoreCase(customer.getChildren()) ? new BigDecimal(customer.getChildren()) : null);
        person.setOccupation(customer.getOccupation());
        person.setTouchPointFrequency(customer.getContactFrequancy());
        person.setProfileCompletion(new BigDecimal(completeness == null ? 0 : completeness));
        person.setSynchDetail(synchDetail);

        person.setAddresses(toSyncAddress(customer));
        person.setPhoneChannels(toSyncTel(customer));
        person.setEmailContacts(toSyncEmail(customer));

        Object listenerBean = beanFactory.getBean(InstanceCode.CustomerExtension);
        if(utilsService.isNotEmptyBean(listenerBean)) {
            CustomerDataSyncListener listener = (CustomerDataSyncListener) listenerBean;
            listener.onPullData(customer,person);
        }

        return person;
    }

    @Override
    public Integer getIdentityID(Object data) {
        Customer customer = (Customer) data;
        return customer.getCustomerID();
    }

    @Override
    public boolean isDeleteData(Object data) {
        Person person = (Person) data;
        return person.getSynchDetail().isToDelete();
    }

    @Override
    public void onDeleteData(Object data) {
        Customer customer = (Customer) data;
        this.customerRepository.deleteExtensionData(customer.getCustomerID(),customer.getOrganizationalUnit());
        this.customerRepository.delete(customer);

    }

    @Override
    public Object onSaveData(Object dbData , Object data) {
        Customer customer = (Customer) dbData;
        boolean exist = customer != null;
        if(!exist) {
            customer = new Customer();
            customer.setCreateBy(agentID + "");
        }

        Person person = (Person) data;

        setCustomerProfile(agentID,customer,person);

        setCustomerOtherInfo(customer,person);
        this.customerRepository.save(customer);

        if(!exist) {

            String clientTime = person.getSynchDetail().getClientTime();
        	String agentID = customer.getAgentId();
        	String organizationalUnit = customer.getOrganizationalUnit();
            customerRepository.saveExtension(customer.getCustomerID(),organizationalUnit);

            //add to Agent_Daily_App_Data
            activityService.UpdateFindSchedule(agentID, organizationalUnit, AgentDailyAppDateType.FIND, 1,clientTime, false);
            
            //add to PersonalProgress
            activityService.updatePersonalProgress(agentID, organizationalUnit, 1, AgentDailyAppDateType.FIND,clientTime);
        }

        Object listenerBean = beanFactory.getBean(InstanceCode.CustomerExtension);
        if(utilsService.isNotEmptyBean(listenerBean)) {
            CustomerDataSyncListener listener = (CustomerDataSyncListener) listenerBean;
            listener.postSave(customer,person);
        }

        return customer;
    }

    @Override
    public DataMatch getDataMatchPolicy() {
        return this;
    }

    private void setCustomerProfile(String agentID, Customer customer , Person person) {
        if(StringUtils.isNotEmpty(person.getBirthDate())) {
            Date birthday = this.dateService.getDate(person.getBirthDate());

            Calendar calendar = new GregorianCalendar();
            calendar.setTime(birthday);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            customer.setBirthdayYear(String.valueOf(year));
            customer.setBirthdayMonth(StringUtils.leftPad(String.valueOf(month),2,"0"));
            customer.setBirthdayDate(StringUtils.leftPad(String.valueOf(day),2,"0"));
        }

        customer.setCustomerID(person.getCustomerID().intValue());
        customer.setDataSource(person.isIsChangeable() ? "APP" : "OPUS");
        customer.setOrganizationalUnit(organizationService.getOrganizationalUnit());
        customer.setAgentId(agentID);
        customer.setOccupation(person.getOccupation());
        customer.setCompany(person.getEmployer());

        customer.setAgeRange(person.getAgeRange());

        customer.setIncome(person.getAnnualIncomeRange());
        customer.setSource(person.getContactLink());
        customer.setMarriage(person.getMarritalStatus() != null ? person.getMarritalStatus().toString() : null);
        customer.setChildren(String.valueOf(person.getNumberOfChildren()));
        customer.setFamiliarity(person.getFamiliarity());
        customer.setContactFrequancy(person.getTouchPointFrequency());
        customer.setPossibility(person.getLeadProbability());
        customer.setFollow(person.isIsFollowed() ? "Y" : "N");
        customer.setCompleteness(person.getProfileCompletion().floatValue());
        customer.setOverTimeAlertTime(null);

        //check datasource is OPUS
        if(person.isIsChangeable()) {
            customer.setFirstName(person.getFirstName());
            customer.setLastName(person.getLastName());
            customer.setMiddleName(person.getMiddleName());

            customer.setGender(person.getGender() != null ? person.getGender().toString() : null);
        }

        customer.setUpdateBy(agentID + "");
        customer.setDataTime(dateService.getTodayDate());
        customer.setAppUpdateTime(new Date());

    }

    private void setCustomerOtherInfo(Customer customer,Person person) {

        //keep OPUS data , clear APP data
        Set<com.allianz.sd.core.jpa.model.CustomerTel> telTemp = new HashSet<>();
        Set<com.allianz.sd.core.jpa.model.CustomerEmail> emailTemp = new HashSet<>();
        Set<com.allianz.sd.core.jpa.model.CustomerAddress> addressTemp = new HashSet<>();

        //keep OPUS data
        for(PhoneChannel phoneChannel : person.getPhoneChannels()) {
        	if(phoneChannel.isIsChangeable() != null) {
	            if(phoneChannel != null && !phoneChannel.isIsChangeable() ) telTemp.add(apiTel2JpaTel(phoneChannel));
        	}
        }
        for(Email email : person.getEmailContacts()) {
        	if(email.isIsChangeable() != null) {
        		if(email != null && email.isIsChangeable() != null && !email.isIsChangeable() ) emailTemp.add(apiEmail2JpaEmail(email));
        	}
        }
        for(Address address : person.getAddresses()) {
        	if(address.isIsChangeable() != null) {
               if(address != null && address.isIsChangeable() != null && !address.isIsChangeable() ) addressTemp.add(apiAddress2JpaAddress(address));
        	}
        }
        
        //add APP data
        for(PhoneChannel phoneChannel : person.getPhoneChannels()) {
        	if(phoneChannel.isIsChangeable() != null) {
	            if(phoneChannel.isIsChangeable()) {
	                com.allianz.sd.core.jpa.model.CustomerTel customerTelModel = apiTel2JpaTel(phoneChannel);
	
	                customerTelModel.setCreateBy(customer.getAgentId() + "");
	                customerTelModel.setUpdateBy(customer.getAgentId() + "");
	
	                telTemp.add(customerTelModel);
	            }
        	}

        }

        for(Email email : person.getEmailContacts()) {
        	if(email.isIsChangeable() != null) {
	            if(email.isIsChangeable()) {
	                com.allianz.sd.core.jpa.model.CustomerEmail customerEmailModel = apiEmail2JpaEmail(email);
	
	                customerEmailModel.setCreateBy(customer.getAgentId() + "");
	                customerEmailModel.setUpdateBy(customer.getAgentId() + "");
	
	                emailTemp.add(customerEmailModel);
	            }
        	}
        }

        for(Address address : person.getAddresses()) {
        	if(address.isIsChangeable() != null) {
	            if(address.isIsChangeable()) {
	                com.allianz.sd.core.jpa.model.CustomerAddress customerAddressModel = apiAddress2JpaAddress(address);
	
	                customerAddressModel.setCreateBy(customer.getAgentId() + "");
	                customerAddressModel.setUpdateBy(customer.getAgentId() + "");
	
	                addressTemp.add(customerAddressModel);
	            }
        	}

        }

        customer.getTels().clear();
        customer.getEmails().clear();
        customer.getAddresses().clear();

        customer.getTels().addAll(telTemp);
        customer.getEmails().addAll(emailTemp);
        customer.getAddresses().addAll(addressTemp);
    }

    private List<PhoneChannel> toSyncTel(com.allianz.sd.core.jpa.model.Customer customer) {
        List<PhoneChannel> tels = new ArrayList<>();

        if(customer.getTels() != null) {
            for(com.allianz.sd.core.jpa.model.CustomerTel tel : customer.getTels()) {
                PhoneChannel phoneChannel = new PhoneChannel();
                phoneChannel.setPhoneID(new BigDecimal(tel.getTelID()));
                phoneChannel.setUsageType(tel.getTelType());
                phoneChannel.setPhoneNumber(tel.getTel());
                phoneChannel.setIsChangeable(!"OPUS".equals(tel.getDataSource()));
                tels.add(phoneChannel);
            }
        }

        return tels;
    }

    private List<Email> toSyncEmail(com.allianz.sd.core.jpa.model.Customer customer) {
        List<Email> emails = new ArrayList<>();

        if(customer.getTels() != null) {
            for(com.allianz.sd.core.jpa.model.CustomerEmail email : customer.getEmails()) {
                Email customerEmail = new Email();
                customerEmail.setEmailType(email.getEmailType());
                customerEmail.setEmail(email.getEmail());
                customerEmail.setIsChangeable(true);
                customerEmail.setEmailID(new BigDecimal(email.getMailID()));
                emails.add(customerEmail);
            }
        }

        return emails;
    }

    private List<Address> toSyncAddress(com.allianz.sd.core.jpa.model.Customer customer) {
        List<Address> addresses = new ArrayList<>();

        if(customer.getTels() != null) {
            for(com.allianz.sd.core.jpa.model.CustomerAddress address : customer.getAddresses()) {
                Address customerAddress = new Address();
                customerAddress.setIsChangeable(true);
                customerAddress.setLine1(address.getAddress1());
                customerAddress.setAddressType(address.getAddressType());
                customerAddress.setAddressId(new BigDecimal(address.getAddrID()));
                customerAddress.setArea(address.getArea());
                customerAddress.setCity(address.getCity());
                customerAddress.setCountryCode(address.getCountry());
                customerAddress.setPostCode(address.getZipcode());

                addresses.add(customerAddress);
            }
        }

        return addresses;
    }

    private com.allianz.sd.core.jpa.model.CustomerTel apiTel2JpaTel(PhoneChannel phoneChannel) {
        com.allianz.sd.core.jpa.model.CustomerTel customerTelModel = new com.allianz.sd.core.jpa.model.CustomerTel();
        customerTelModel.setTel(phoneChannel.getPhoneNumber());
        customerTelModel.setTelType(phoneChannel.getUsageType());
        customerTelModel.setDataSource(phoneChannel.isIsChangeable() ? "APP" : "OPUS");
        customerTelModel.setOrganizationalUnit(organizationService.getOrganizationalUnit());

        return customerTelModel;
    }

    private com.allianz.sd.core.jpa.model.CustomerEmail apiEmail2JpaEmail(Email email) {
        com.allianz.sd.core.jpa.model.CustomerEmail customerEmailModel = new com.allianz.sd.core.jpa.model.CustomerEmail();
        customerEmailModel.setEmail(email.getEmail());
        customerEmailModel.setEmailType(email.getEmailType());

        customerEmailModel.setOrganizationalUnit(organizationService.getOrganizationalUnit());

        return customerEmailModel;
    }

    private com.allianz.sd.core.jpa.model.CustomerAddress apiAddress2JpaAddress(Address address) {
        com.allianz.sd.core.jpa.model.CustomerAddress customerAddressModel = new com.allianz.sd.core.jpa.model.CustomerAddress();
        customerAddressModel.setAddress1(address.getLine1());
        customerAddressModel.setAddressType(address.getAddressType());
        customerAddressModel.setArea(address.getArea());
        customerAddressModel.setCity(address.getCity());
        customerAddressModel.setCountry(address.getCountryCode());
        customerAddressModel.setZipcode(address.getPostCode());

        customerAddressModel.setOrganizationalUnit(organizationService.getOrganizationalUnit());

        return customerAddressModel;
    }

    @Override
	public void setAgentID(String agentID) {
		this.agentID = agentID;
	}
}
