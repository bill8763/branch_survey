package com.allianz.sd.core.schedule.job;

import com.allianz.sd.core.jpa.bean.DataCategory;
import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.jpa.model.Customer;
import com.allianz.sd.core.jpa.model.Message;
import com.allianz.sd.core.jpa.model.SysData;
import com.allianz.sd.core.jpa.repository.CustomerRepository;
import com.allianz.sd.core.notification.impl.IDNameBuilder;
import com.allianz.sd.core.notification.impl.IDNameItem;
import com.allianz.sd.core.notification.Notification;
import com.allianz.sd.core.service.*;
import com.allianz.sd.core.service.bean.MessageType;
import com.allianz.sd.core.service.bean.NotificationCode;
import org.json.JSONArray;
import org.json.JSONObject;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.*;

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
public class CustomerCheckMaintainTime extends QuartzJobBean {

    private static final Logger logger = LoggerFactory.getLogger(CustomerCheckMaintainTime.class);

    @Autowired
    private DateService dateService = null;

    @Autowired
    private CustomerRepository customerRepository = null;

    @Autowired
    private MessageService messageService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private NotificationsService notificationsService;

    @Autowired
    private SysDataService sysDataService;

    @Autowired
    private IDNameBuilder agentListBuilder;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        String today = dateService.getTodayString();
        Date todayDate = dateService.getTodayDate();
        logger.trace("today = " + today);

        List<SysData> sysDatas = sysDataService.getAllSysData();
        for(SysData sysData : sysDatas) {

            Integer overTimeMonthAlert = sysData.getCustomerDataTrackingLimit();

            Date queryDate = dateService.addDate(todayDate, Calendar.MONTH,overTimeMonthAlert * -1);

            logger.trace("queryDate = " + queryDate);

            List<Customer> customerList = customerRepository.findByAPPUpdateTimeLessThanEqual(queryDate);

            logger.trace("long time no maintain customer size = " + customerList.size());

            int dataYear = dateService.getCurrentYear();

            //prepared to add to message-box
            Map<String,JSONArray> agentMap = new HashMap<String,JSONArray>();
            for(Customer customer : customerList) {

                String agentID = customer.getAgentId();
                String customerName = customerService.convertFullName(customer);
                int customerId = customer.getCustomerID();
                logger.trace("customerID = " + customerId + ",updateTime = " + customer.getUpdateTime());

                customer.setOverTimeAlertTime(todayDate);
                customer.setUpdateBy("SD");

                //update customer
                customerRepository.save(customer);



                //get Message JSON
                JSONArray datas = agentMap.getOrDefault(agentID,new JSONArray());

                JSONObject tmp = new JSONObject();
                tmp.put("customerID",customerId);
                tmp.put("customerName",customerName);
                datas.put(tmp);

                agentMap.put(agentID,datas);

                //get PushNotification Item
                agentListBuilder.addItem(new IDNameItem(String.valueOf(customerId),customerName));

            }

            //if has customer data , add message box
            if(agentMap.size() != 0) {

                for(String agentID : agentMap.keySet()) {
                    JSONObject arg = new JSONObject();
                    arg.put("customers",agentMap.get(agentID));

                    Message message = new Message();
                    message.setIsShow("N");
                    message.setMessageCategory(DataCategory.CUSTOMER);
                    message.setMessageType(MessageType.Overtime.toString());
                    message.setAgentID(agentID);
                    message.setTitle(NotificationCode.Customer_Overtime_Title.toString());
                    message.setDescription(NotificationCode.Customer_Overtime_Body.toString());
                    message.setArguments(arg.toString());

                    messageService.addMessage(message);

                    //發送推播
                    Notification notification = new Notification();
                    notification.setAgentID(agentID);
                    notification.setTitle(NotificationCode.Customer_Overtime_Title.toString());
                    notification.setBody(NotificationCode.Customer_Overtime_Body.toString());
                    notification.setImmediately(true);
                    notification.setPushType(Notification.NotificationType.SINGLE);
                    notification.setMessage(message);

                    //加入客戶清單
                    notification.addLanguageText("customers",agentListBuilder.toText());

                    notificationsService.push(notification);
                }

            }
        }


    }
}
