package com.allianz.sd.core.goalsetting.impl;

import com.allianz.sd.core.goalsetting.GoalStatusChangeHandler;
import com.allianz.sd.core.goalsetting.bean.GoalStatusSubject;
import com.allianz.sd.core.jpa.bean.DataCategory;
import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.jpa.model.Message;
import com.allianz.sd.core.notification.Notification;
import com.allianz.sd.core.service.DateService;
import com.allianz.sd.core.service.MessageService;
import com.allianz.sd.core.service.NotificationsService;
import com.allianz.sd.core.service.bean.MessageType;
import com.allianz.sd.core.service.bean.NotificationCode;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
public class SendOverdueGoalNotification implements GoalStatusChangeHandler {

    private Logger logger = LoggerFactory.getLogger(SendOverdueGoalNotification.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private DateService dateService;

    @Autowired
    private NotificationsService notificationsService;

    @Override
    @Transactional
    public void onStatusChange(GoalStatusSubject goalStatusSubject) throws Exception {

        AgentYearData agentYearData = goalStatusSubject.getAgentYearData();
        int dataYear = agentYearData.getIdentity().getDataYear();
        String agentID = agentYearData.getIdentity().getAgentID();

        JSONObject arg = new JSONObject();

        arg.put("year",dataYear);

        Message message = new Message();
        message.setMessageCategory(DataCategory.GOALSETTING);
        message.setMessageType(MessageType.GoalAutoReject.toString());
        message.setAgentID(agentID);
        message.setTitle(NotificationCode.Goal_Auto_Reject_Message_Title.toString());
        message.setDescription(NotificationCode.Goal_Auto_Reject_Message_Description.toString());
        message.setArguments(arg.toString());
        message.setIsPopup("Y");
        messageService.addMessage(message);

        //6 -發送推播給當下業務員
        Notification notification = new Notification();
        notification.setAgentID(agentID);
        notification.setImmediately(true);

        notification.setTitle(NotificationCode.Goal_Auto_Reject_Message_Title.toString());
        notification.setBody(NotificationCode.Goal_Auto_Reject_Message_Description.toString());
        notification.setPushType(Notification.NotificationType.SINGLE);

        notification.setMessage(message);

        try {
            notificationsService.push(notification);
        } catch (Exception e) {
            logger.trace(e.getMessage());
        }
    }
}
