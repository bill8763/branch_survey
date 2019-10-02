package com.allianz.sd.core.service;

import com.allianz.sd.core.jpa.bean.DataCategory;
import com.allianz.sd.core.jpa.model.GoalSettingVersion;
import com.allianz.sd.core.jpa.model.LanguageTextMapping;
import com.allianz.sd.core.jpa.model.Message;
import com.allianz.sd.core.jpa.repository.LanguageTextMappingRepository;
import com.allianz.sd.core.jpa.repository.MessageGoalStatusRepository;
import com.allianz.sd.core.jpa.repository.MessageRepository;
import com.allianz.sd.core.service.bean.MessageGoalStatus;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/4/14
 * Time: 下午 2:32
 * To change this template use File | Settings | File Templates.
 */
@Service
public class MessageService {

    private Logger logger = LoggerFactory.getLogger(MessageService.class);

    @Value("${SND.OrganizationalUnit}")
    private String organizationalUnit;

    @Value("${SND.Testing.DefaultPopupStatus:}")
    private String defaultPopupStatus;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private DateService dateService;

    @Autowired
    private AgentUpdateVersionService agentUpdateVersionService = null;

    @Autowired
    private MessageGoalStatusRepository messageGoalStatusRepository;

    public void updateMessage(Message message) {

        logger.trace("updateMessage = " + message);

        //update message
        messageRepository.save(message);

        agentUpdateVersionService.updateAgentLastTme(dateService.getTodayDate(), message.getAgentID(), DataCategory.MESSAGE, message.getMessageID());
    }

    public void addMessage(Message message) {
        addMessage(message,true);
    }

    public void addMessage(Message message,boolean isAppend) {

        if(!isAppend) {
            if(messageRepository.getUnReadMessage(message.getMessageCategory(),message.getMessageType(),message.getAgentID()) != 0) {
                return;
            }
        }


        message.setOrganizationalUnit(organizationalUnit);
        message.setCreateBy(message.getAgentID() + "");
        message.setMessageTime(new Date());
        message.setStatus("UnRead");
        message.setDataTime(dateService.getTodayDate());

        if(StringUtils.isNotEmpty(defaultPopupStatus)) message.setIsPopup(defaultPopupStatus);
        else message.setIsPopup(message.getIsPopup()!=null?message.getIsPopup():"Y");

        message.setIsPopup(message.getIsPopup()!=null?message.getIsPopup():defaultPopupStatus);
        message.setIsShow(message.getIsShow()!=null?message.getIsShow():"Y");
        message.setIsClick(message.getIsClick()!=null?message.getIsClick():"Y");

        logger.trace("addMessage = " + message);

        //add message
        messageRepository.save(message);

        //add customer update record to data sync
        agentUpdateVersionService.insertAgentLastTme(dateService.getTodayDate(), message.getAgentID(), DataCategory.MESSAGE, message.getMessageID());

    }

    public Message getMessageByGoal(int dataYear , String agentID) {
        return messageRepository.getMessageByGoal(dataYear,agentID);
    }

    public Message getMessageByGoalSettingSeq(int goalSettingSeq) {
        return messageRepository.getMessageByGoalSettingSeq(goalSettingSeq);
    }

    public void updateMessageGoalStatusToDone(int goalSettingSeq , String signingSupervisor) {

        //Update Message can't click
        Message message = getMessageByGoalSettingSeq(goalSettingSeq);
//        updateMessageIsClickStatus(message,false);


        //update message goal status
        logger.trace("update MessageGoalStatus");
        com.allianz.sd.core.jpa.model.MessageGoalStatus goalStatus = messageGoalStatusRepository.findByGoalSettingSeq(goalSettingSeq);

        if(goalStatus != null) {
            goalStatus.setStatus(MessageGoalStatus.Done.toString());
            messageGoalStatusRepository.save(goalStatus);
        }

        updateMessage(message);

    }

    public void updateGoalStatusToApprove(int messageID , Integer goalSettingSeq) {

        //save to message goal status
        com.allianz.sd.core.jpa.model.MessageGoalStatus goalStatus = new com.allianz.sd.core.jpa.model.MessageGoalStatus();
        goalStatus.setMessageID(messageID);
        goalStatus.setGoalSettingSeq(goalSettingSeq);
        goalStatus.setStatus(MessageGoalStatus.Approve.toString());
        messageGoalStatusRepository.save(goalStatus);


    }
}
