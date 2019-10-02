package com.allianz.sd.core.schedule.job;

import com.allianz.sd.core.jpa.bean.DataCategory;
import com.allianz.sd.core.jpa.model.Code;
import com.allianz.sd.core.jpa.model.Customer;
import com.allianz.sd.core.jpa.repository.CustomerRepository;
import com.allianz.sd.core.service.AgentUpdateVersionService;
import com.allianz.sd.core.service.CodeService;
import com.allianz.sd.core.service.DateService;
import com.allianz.sd.core.service.bean.DateType;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 2:50
 * To change this template use File | Settings | File Templates.
 */

/**
 * check calendar not all day event reminder
 */
@Component
public class CustomerCountAge extends QuartzJobBean {

    private static final Logger logger = LoggerFactory.getLogger(CustomerCountAge.class);

    @Autowired
    private AgentUpdateVersionService agentUpdateVersionService = null;

    @Autowired
    private CodeService codeService = null;

    @Autowired
    private DateService dateService = null;

    @Autowired
    private CustomerRepository customerRepository = null;


    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        String today = dateService.getTodayString();
        Date todayDate = dateService.getTodayDate();
        logger.trace("today = " + today);

        //query today is birthday customer
        List<Customer> customerList = customerRepository.findByBirthdayMonthAndBirthdayDate(today.substring(4, 6), today.substring(6, 8));

        logger.trace("today birthday customer size = " + customerList.size());

        for(Customer customer : customerList) {

            logger.trace("customerID = " + customer.getCustomerID() + ",ageRange = " + customer.getAgeRange());

            String birthdayYear = customer.getBirthdayYear();
            String birthdayMonth = customer.getBirthdayMonth();
            String birthdayDate = customer.getBirthdayDate();

            birthdayMonth = StringUtils.leftPad(birthdayMonth,2,"0");
            birthdayDate = StringUtils.leftPad(birthdayDate,2,"0");

            Date birthday = dateService.parseDateString2Date(birthdayYear + birthdayMonth + birthdayDate + "000000");
            int day = dateService.getBetweenTime(birthday,todayDate, DateType.DAY);
            int age = day / 365;

            logger.trace("birthday = " + birthday + ",day is " + day + ",age is " + age);

            Map<String,Code> codeMap = codeService.getCodeMap("Customer_Age");
            for(String codeId : codeMap.keySet()) {
                Code code = codeMap.get(codeId);
                String args = code.getArguments();
                JSONObject jsonObject = new JSONObject(args);

                //check age
                if (age >= jsonObject.getInt("start") && age <= jsonObject.getInt("end")) {

                    customer.setAge(age);
                    customer.setAgeRange(codeId);
                    customer.setDataTime(todayDate);
                    customer.setUpdateBy("SD");

                    //update customer
                    customerRepository.save(customer);

                    //add customer update record to data sync
                    agentUpdateVersionService.updateAgentLastTme(todayDate,customer.getAgentId(), DataCategory.CUSTOMER,customer.getCustomerID());
                }
            }


        }
    }
}
