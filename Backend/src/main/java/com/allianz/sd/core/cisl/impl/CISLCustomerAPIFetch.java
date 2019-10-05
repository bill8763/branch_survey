package com.allianz.sd.core.cisl.impl;

import com.allianz.sd.core.cisl.bean.CustomerDataCISL;
import com.allianz.sd.core.cisl.bean.ProcessData;
import com.allianz.sd.core.cisl.bean.SalesDataCISL;
import com.allianz.sd.core.jpa.model.AgentSalesDataIdentity;
import com.allianz.sd.core.jpa.model.CISLAgentSalesData;
import com.allianz.sd.core.jpa.model.Customer;
import com.allianz.sd.core.jpa.model.CustomerTel;
import com.allianz.sd.core.jpa.repository.CISLAgentSalesDataRepository;
import com.allianz.sd.core.jpa.repository.CustomerRepository;
import com.allianz.sd.core.service.CISLService;
import com.allianz.sd.core.service.DateService;
import com.allianz.sd.core.service.SequenceIDService;
import org.apache.poi.ss.formula.functions.T;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.*;

@Component
public class CISLCustomerAPIFetch extends CISLUsageModelAPIFetch {

    @Value("${CISL.API.CustomerURL}")
    private String url;

    @Autowired
    private DateService dateService;

    @Autowired
    private CustomerRepository<T, Set<T>> customerRepository;

    @Autowired
    private SequenceIDService sequenceIDService ;

    @Override
    protected String getCISLAPIURL() {
        return url;
    }

    @Override
    protected String getUsageModel() {
        return "Customer";
    }

    @Override
    public JSONObject fetchByAgent(String agentID, int pageNo) throws Exception {
        throw new Exception("Not Support API by Agent Query");
    }

    @Override
    public ProcessData parse2ProcessData(JSONObject content) throws Exception {

        CustomerDataCISL customerData = new CustomerDataCISL();

        //get _refsObject
        JSONObject refsObject = content.getJSONObject("_refs");

        int year = dateService.getCurrentYear();
        String organizationalUnit = refsObject.getJSONObject("OrganizationalUnit").getString("locationName");
        String agentID = refsObject.getJSONObject("AccountManager").getString("agentNumber");
        String name = content.getString("name");

        //get Person Object
        JSONObject personObject = refsObject.getJSONObject("Person");
        Date birthday = dateService.parseDateString2Date(personObject.getString("birthDate"));
        String gender = content.getString("genderIdentity");

        //get person Object >> extEntity Object
        JSONObject personExtEntityObject = personObject.getJSONObject("extEntity");
        String dataSource = personExtEntityObject.getString("dataSource");
        int partID = personObject.getInt("customerNumber");

        //get PhoneChannel Object
        JSONObject phoneChannelObject = refsObject.getJSONObject("PhoneChannel");
        String telType = phoneChannelObject.getString("usageType");
        System.out.println("phoneNumber = " + phoneChannelObject.has("phoneNumber"));
        String tel = phoneChannelObject.has("phoneNumber") ? phoneChannelObject.getString("phoneNumber") : "";
        int addID = phoneChannelObject.getInt("source");
        int contractID = phoneChannelObject.getInt("dataSource");

        //set Bean
        customerData.setOrganizationalUnit(organizationalUnit);
        customerData.setAgentID(agentID);
        customerData.setDataYear(year);
        customerData.setName(name);
        customerData.setGender(gender);
        customerData.setAddID(addID);
        customerData.setBirthday(birthday);
        customerData.setDataSource(dataSource);
        customerData.setPartID(partID);
        customerData.setTel(tel);
        customerData.setTelType(telType);
        customerData.setContractID(contractID);

        return customerData;
    }

    @Override
    public void saveToSND(ProcessData processData) throws Exception{

        CustomerDataCISL customerData1 = (CustomerDataCISL) processData;

        String organizationalUnit = customerData1.getOrganizationalUnit();
        String agentID = customerData1.getAgentID();
        int year = customerData1.getDataYear();
        String name = customerData1.getName();
        Date birthday = customerData1.getBirthday();
        String gender = customerData1.getGender();
        String dataSource = customerData1.getDataSource();
        int partID = customerData1.getPartID();
        String telType = customerData1.getTelType();
        String tel = customerData1.getTel();
        Integer addID = customerData1.getAddID();
        Integer contractID = customerData1.getContractID();

        //to get 1 CustomerID
        List<BigDecimal> customerID = sequenceIDService.getId("Customer", new BigDecimal(1));

        //customerTel
        CustomerTel customerTel = new CustomerTel();
        customerTel.setOrganizationalUnit(organizationalUnit);
        customerTel.setCustomerID(customerID.get(0).intValueExact());
        customerTel.setTelType(telType);
        customerTel.setAddID(addID);
        customerTel.setContractID(contractID);
        customerTel.setTel(tel);
        customerTel.setDataSource(dataSource);
        customerTel.setCreateBy("SD");
        customerTel.setUpdateBy("SD");
        Set<CustomerTel> set = new HashSet<CustomerTel>();
        set.add(customerTel);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(birthday);

        Customer customer = new Customer();
        customer.setCustomerID(customerTel.getCustomerID());
        customer.setOrganizationalUnit(organizationalUnit);
        customer.setAgentId(agentID);
        customer.setBirthdayYear(String.valueOf(calendar.get(Calendar.YEAR)));
        customer.setBirthdayMonth(String.valueOf(calendar.get(Calendar.MONTH) + 1));
        customer.setBirthdayDate(String.valueOf(calendar.get(Calendar.DATE)));
        customer.setGender(gender);
        customer.setFirstName(name);
        customer.setDataSource(dataSource);
        customer.setPartID(partID);
        customer.setTels(set);
        customer.setCreateBy("SD");
        customer.setUpdateBy("SD");

        customerRepository.save(customer);

    }
}
