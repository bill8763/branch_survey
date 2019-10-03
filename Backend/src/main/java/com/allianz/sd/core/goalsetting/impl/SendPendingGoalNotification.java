package com.allianz.sd.core.goalsetting.impl;

import com.allianz.sd.core.goalsetting.GoalStatusChangeHandler;
import com.allianz.sd.core.goalsetting.bean.GoalStatusSubject;
import com.allianz.sd.core.jpa.bean.DataCategory;
import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.jpa.model.Message;
import com.allianz.sd.core.service.AgentUpdateVersionService;
import com.allianz.sd.core.service.DateService;
import com.allianz.sd.core.service.MessageService;
import com.allianz.sd.core.service.bean.MessageType;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SendPendingGoalNotification implements GoalStatusChangeHandler {

    private Logger logger = LoggerFactory.getLogger(SendPendingGoalNotification.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private DateService dateService;

    @Override
    @Transactional
    public void onStatusChange(GoalStatusSubject goalStatusSubject) throws Exception {

        AgentYearData agentYearData = goalStatusSubject.getAgentYearData();
        int dataYear = agentYearData.getIdentity().getDataYear();
        String agentID = agentYearData.getIdentity().getAgentID();
        String agentName = agentYearData.getAgentData().getAgentName();
        String goalSigningSupervisor = agentYearData.getGoalSigningSupervisor();

        JSONObject arguments = new JSONObject();
        arguments.put("agentID",agentID);
        arguments.put("agentName",agentYearData.getAgentData().getAgentName());
        arguments.put("DataYear",dataYear);
        arguments.put("AppUseMode",agentYearData.getAppUseMode());

        int goalSettingSeq = goalStatusSubject.getAfterGoal().getGoalSettingVersion().getGoalSettingSeq();

        //Message
        Message message = messageService.getMessageByGoal(dataYear,agentID);
        if(message == null) {
            message = new Message();
            message.setMessageCategory(DataCategory.GOALSETTING);
            message.setMessageType(MessageType.Need_Goal_Approve.toString());
            message.setAgentID(goalSigningSupervisor);
            message.setTitle(agentName);
            message.setDescription("Goal _Setting_Approval_notification");
            message.setArguments(arguments.toString());
            message.setIsPopup("N");
            messageService.addMessage(message);
        }
        else {
            message.setStatus("UnRead");
            message.setMessageTime(dateService.getTodayDate());
            message.setDataTime(dateService.getTodayDate());
            messageService.updateMessage(message);
        }


        //save to message goal status
        messageService.updateGoalStatusToApprove(message.getMessageID(),goalSettingSeq);
    }
}
