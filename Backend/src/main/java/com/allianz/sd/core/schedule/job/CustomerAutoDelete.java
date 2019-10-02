package com.allianz.sd.core.schedule.job;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.allianz.sd.core.jpa.model.SysData;
import com.allianz.sd.core.notification.impl.IDNameBuilder;
import com.allianz.sd.core.notification.impl.IDNameItem;
import com.allianz.sd.core.service.*;
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

import com.allianz.sd.core.jpa.bean.DataCategory;
import com.allianz.sd.core.jpa.model.Customer;
import com.allianz.sd.core.jpa.model.Message;
import com.allianz.sd.core.jpa.model.OPUS;
import com.allianz.sd.core.jpa.repository.CustomerRepository;
import com.allianz.sd.core.jpa.repository.OPUSRepository;
import com.allianz.sd.core.notification.Notification;
import com.allianz.sd.core.service.bean.MessageType;
import com.allianz.sd.core.service.bean.NotificationCode;

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
public class CustomerAutoDelete extends QuartzJobBean {

    private static final Logger logger = LoggerFactory.getLogger(CustomerAutoDelete.class);

    @Autowired
    private AgentUpdateVersionService agentUpdateVersionService;

    @Autowired
    private DateService dateService = null;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MessageService messageService;

    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private OPUSRepository oPUSRepository;

    @Autowired
    private IDNameBuilder agentListBuilder;

    @Autowired
    private NotificationsService notificationsService;

    @Autowired
    private SysDataService sysDataService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        String today = dateService.getTodayString();
        Date todayDate = dateService.getTodayDate();
        logger.trace("today = " + today);

        List<SysData> sysDatas = sysDataService.getAllSysData();
        for(SysData sysData : sysDatas) {
            Integer overTimeDayAutoDelete = sysData.getCustomerDataDeleteLimit();

            Date overtime = dateService.addDate(todayDate, java.util.Calendar.DATE,overTimeDayAutoDelete * -1);
            String dateformat = new SimpleDateFormat("yyyy-MM-dd").format(overtime);
            logger.trace("findByOverTimeAlertTime queryDate = " + dateformat);

            //get overtime maintain customer
            List<Customer> customerList = customerRepository.findByOverTimeAlertTime(dateformat);

            logger.trace("long time no maintain will be deleted customer size = " + customerList.size());

            Map<String,JSONArray> agentMap = new HashMap<String,JSONArray>();
            for(Customer customer : customerList) {

                int customerID = customer.getCustomerID();
                String agentID = customer.getAgentId();
                String customerName = customerService.convertFullName(customer);

                logger.trace("customerID = " + customerID + ",overtimeAlertTime = " + customer.getOverTimeAlertTime());

                //delete customer
                customerRepository.delete(customer);

                //add customer update record to data sync
                agentUpdateVersionService.updateAgentLastTme(todayDate, customer.getAgentId(), DataCategory.CUSTOMER, customerID);

                JSONArray datas = agentMap.getOrDefault(agentID,new JSONArray());

                JSONObject tmp = new JSONObject();
                tmp.put("customerID",customerID);
                tmp.put("customerName",customerName);
                datas.put(tmp);

                agentMap.put(agentID,datas);

                //get PushNotification Item
                agentListBuilder.addItem(new IDNameItem(String.valueOf(customerID),customerName));
            }

            //check OPUS delete
            logger.trace("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< OPUS START >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            //query All OPUS
            List<OPUS> opusAll = oPUSRepository.findAll();
            for(OPUS opusData : opusAll) {
                int customerID = opusData.getIdentity().getCustomerID();
                String customerName = opusData.getCustomerName();
                String agentID = opusData.getIdentity().getAgentID();

                JSONArray datas = agentMap.getOrDefault(agentID,new JSONArray());

                JSONObject tmp = new JSONObject();
                tmp.put("customerID",customerID);
                tmp.put("customerName",customerName);
                datas.put(tmp);

                agentMap.put(agentID,datas);

                //get PushNotification Item
                agentListBuilder.addItem(new IDNameItem(String.valueOf(customerID),customerName));
            }


            oPUSRepository.deleteAll();
            logger.trace("<<<<<<<<<<<<<< OPUS END >>>>>>>>>>>>>>");

            //if has customer data , add message box
            if(agentMap.size() != 0) {

                for(String agentID : agentMap.keySet()) {
                    JSONObject arg = new JSONObject();
                    arg.put("customers",agentMap.get(agentID));

                    Message message = new Message();
                    message.setIsShow("N");
                    message.setMessageCategory(DataCategory.CUSTOMER);
                    message.setMessageType(MessageType.AutoDelete.toString());
                    message.setAgentID(agentID);
                    message.setTitle(NotificationCode.Customer_Auto_Delete_Title.toString());
                    message.setDescription(NotificationCode.Customer_Auto_Delete_Body.toString());
                    message.setArguments(arg.toString());

                    messageService.addMessage(message);


                    //發送推播
                    Notification notification = new Notification();
                    notification.setAgentID(agentID);
                    notification.setTitle(NotificationCode.Customer_Auto_Delete_Title.toString());
                    notification.setBody(NotificationCode.Customer_Auto_Delete_Body.toString());
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
