package com.allianz.sd.core.goalsetting.impl;

import com.allianz.sd.core.goalsetting.GoalStatusChangeHandler;
import com.allianz.sd.core.goalsetting.bean.GoalStatusSubject;
import com.allianz.sd.core.jpa.bean.DataCategory;
import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.jpa.model.GoalSettingVersion;
import com.allianz.sd.core.jpa.model.Message;
import com.allianz.sd.core.notification.Notification;
import com.allianz.sd.core.service.DateService;
import com.allianz.sd.core.service.MessageService;
import com.allianz.sd.core.service.NotificationsService;
import com.allianz.sd.core.service.bean.GoalSettingStatus;
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
public class SendApproveOrRejectGoalNotification implements GoalStatusChangeHandler {

    private Logger logger = LoggerFactory.getLogger(SendApproveOrRejectGoalNotification.class);

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

        GoalSettingVersion goalSettingVersion = goalStatusSubject.getAfterGoal().getGoalSettingVersion();
        int goalSettingSeq = goalSettingVersion.getGoalSettingSeq();
        String goalSigningSupervisor = goalSettingVersion.getSigningSupervisor();
        String status = goalSettingVersion.getStatus();
        boolean isApprove = GoalSettingStatus.APPROVED.equals(GoalSettingStatus.valueOf(status));

        //Message
        String title = isApprove?
                NotificationCode.Approve_Goal_Is_Approved_Title.toString() :
                NotificationCode.Approve_Goal_Is_Reject_Title.toString();
        String description = isApprove?
                NotificationCode.Approve_Goal_Is_Approved_Body.toString() :
                NotificationCode.Approve_Goal_Is_Reject_Body.toString();
        String messageType = isApprove?
                MessageType.ApproveGoalIsApprove.toString() :
                MessageType.ApproveGoalIsReject.toString();

        JSONObject arg = new JSONObject();
        arg.put("year",dataYear);

        Message message = new Message();
        message.setMessageCategory(DataCategory.GOALSETTING);
        message.setMessageType(messageType);
        message.setAgentID(agentID);
        message.setTitle(title);
        message.setDescription(description);
        message.setArguments(arg.toString());
        messageService.addMessage(message);

        //notification + message to agent( no push during 10:00 P.M. ~ 9:00 A.M. )
        Date todayDate = dateService.getTodayDate();
        logger.trace("IsNeedSend Approve Result Notification , hours = ["+todayDate.getHours()+"] , result =" + (todayDate.getHours() >= 9 && todayDate.getHours() < 22));
        if(todayDate.getHours() >= 9 && todayDate.getHours() < 22) {

            //pushNotification
            Notification notification = new Notification();
            notification.setAgentID(agentID);
            notification.setImmediately(true);
            notification.setTitle(title);
            notification.setBody(description);
            notification.setPushType(Notification.NotificationType.SINGLE);
            notification.setMessage(message);

            notificationsService.push(notification);
        }

        messageService.updateMessageGoalStatusToDone(goalSettingSeq,goalSigningSupervisor);
    }
}
