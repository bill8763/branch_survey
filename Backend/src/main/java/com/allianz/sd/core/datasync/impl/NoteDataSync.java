package com.allianz.sd.core.datasync.impl;

import com.allianz.sd.core.InstanceCode;
import com.allianz.sd.core.api.model.*;
import com.allianz.sd.core.datasync.CustomerContactDataSyncListener;
import com.allianz.sd.core.datasync.DataMatch;
import com.allianz.sd.core.datasync.DataSync;
import com.allianz.sd.core.jpa.bean.DataCategory;
import com.allianz.sd.core.jpa.model.CustomerContact;
import com.allianz.sd.core.jpa.repository.CustomerContactRepository;
import com.allianz.sd.core.jpa.repository.CustomerRepository;
import com.allianz.sd.core.service.AgentUpdateVersionService;
import com.allianz.sd.core.service.DateService;
import com.allianz.sd.core.service.OrganizationService;
import com.allianz.sd.core.service.UtilsService;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
@Scope("prototype")
public class NoteDataSync implements DataSync , DataMatch {

    private Logger logger = LoggerFactory.getLogger(NoteDataSync.class);
    private String agentID = null;


    @Autowired
    private CustomerRepository customerRepository = null;

    @Autowired
    private CustomerContactRepository customerContactRepository = null;

    @Autowired
    private AgentUpdateVersionService agentUpdateVersionService = null;

    @Autowired
    private DateService dateService = null;

    @Autowired
    private OrganizationService organizationService = null;

    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private UtilsService utilsService;

    @Override
    public boolean isDifference(Object dbData, Object pushData) {
        CustomerContact customer = (CustomerContact) dbData;
        Note note = (Note) pushData;

        Date pushDate = dateService.toDateTimeFormatDate(note.getSynchDetail().getLastUpdateDateTimeBackend());
        return customer.getDataTime().getTime() != pushDate.getTime();
    }

    @Override
    public Map findPullData(Date appSyncDate) {
        Map<Integer, CustomerContact> map = new HashedMap<>();

        List<Integer> idList = agentUpdateVersionService.getAgentLastSyncData(agentID, DataCategory.CUSTOMERCONTACT,appSyncDate);

        for(Integer contactId : idList) {

            Optional<CustomerContact> customerContactOptional = customerContactRepository.findById(contactId);
            CustomerContact contact = customerContactOptional.orElse(null);

            map.put(contactId,contact);
        }

        return map;
    }

    @Override
    public Object findPushData(Object data) {
        Note note = (Note) data;
        Optional<CustomerContact> customerContactOptional = customerContactRepository.findById(note.getNoteId().intValue());

        return customerContactOptional.orElse(null);
    }

    @Override
    public Object data2SyncObj(Object data) {
        CustomerContact customerContact = (CustomerContact) data;

        SynchDetail synchDetail = new SynchDetail();
        synchDetail.setToDelete(false);
        synchDetail.setLastUpdateDateTimeBackend(dateService.toDateTimeFormatString(customerContact.getDataTime()));

        Note note = new Note();
        note.setNoteId(new BigDecimal(customerContact.getContactID()));
        note.setPersonId(new BigDecimal(customerContact.getCustomerID()));
        note.setText(customerContact.getNote());
        note.setOrigin("");
        note.setCreationDateTime(dateService.toDateTimeFormatString(customerContact.getNoteTime()));
        note.setSynchDetail(synchDetail);

        Object listenerBean = beanFactory.getBean(InstanceCode.CustomerContactExtension);
        if(utilsService.isNotEmptyBean(listenerBean)) {
            CustomerContactDataSyncListener listener = (CustomerContactDataSyncListener) listenerBean;
            listener.onPullData(customerContact,note);
        }

        return note;
    }

    @Override
    public Integer getIdentityID(Object data) {
        CustomerContact customerContact = (CustomerContact) data;
        return customerContact.getContactID();
    }

    @Override
    public boolean isDeleteData(Object data) {
        Note note = (Note) data;
        return note.getSynchDetail().isToDelete();
    }

    @Override
    public void onDeleteData(Object data) {
        CustomerContact customerContact = (CustomerContact) data;
        this.customerContactRepository.deleteExtensionData(customerContact.getContactID(),customerContact.getOrganizationalUnit());
        this.customerContactRepository.delete(customerContact);

    }

    @Override
    public Object onSaveData(Object dbData , Object data) {
        CustomerContact customerContact = (CustomerContact) dbData;
        boolean exist = customerContact != null;
        if(!exist) {
            customerContact = new CustomerContact();
            customerContact.setCreateBy(agentID + "");
        }

        Note note = (Note) data;

        Integer customerID = note.getPersonId().intValue();
        String noteText = note.getText();

        Optional<com.allianz.sd.core.jpa.model.Customer> customerOptional = this.customerRepository.findById(customerID);
        com.allianz.sd.core.jpa.model.Customer customer = customerOptional.orElse(null);

        if(customer != null && StringUtils.isNotEmpty(noteText)) {
            customerContact.setOrganizationalUnit(organizationService.getOrganizationalUnit());
            customerContact.setContactID(note.getNoteId().intValue());
            customerContact.setCustomerID(customerID);
            customerContact.setNote(noteText);
            customerContact.setNoteTime(dateService.toDateTimeFormatDate(note.getCreationDateTime()));
            customerContact.setDataTime(dateService.getTodayDate());
            customerContact.setUpdateBy(agentID + "");

            customer.setAppUpdateTime(new Date());

            customerContactRepository.save(customerContact);
            customerRepository.save(customer);

            if(!exist) {
                this.customerContactRepository.saveExtension(customerContact.getContactID(), customerContact.getOrganizationalUnit());
            }

            Object listenerBean = beanFactory.getBean(InstanceCode.CustomerContactExtension);
            if(utilsService.isNotEmptyBean(listenerBean)) {
                CustomerContactDataSyncListener listener = (CustomerContactDataSyncListener) listenerBean;
                listener.postSave(customerContact,note);
            }
        }

        return customerContact;
    }

    @Override
    public DataMatch getDataMatchPolicy() {
        return this;
    }

    @Override
    public void setAgentID(String agentID) {
        this.agentID = agentID;
    }

}
