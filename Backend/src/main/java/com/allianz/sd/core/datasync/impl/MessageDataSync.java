package com.allianz.sd.core.datasync.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.allianz.sd.core.api.model.SynchDetail;
import com.allianz.sd.core.datasync.DataMatch;
import com.allianz.sd.core.datasync.DataSync;
import com.allianz.sd.core.jpa.model.Message;
import com.allianz.sd.core.jpa.model.MessageGoalStatus;
import com.allianz.sd.core.jpa.repository.MessageGoalStatusRepository;
import com.allianz.sd.core.jpa.repository.MessageRepository;
import com.allianz.sd.core.service.DateService;
import com.allianz.sd.core.service.bean.MessageType;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/4/18
 * Time: 下午 5:47
 * To change this template use File | Settings | File Templates.
 */
@Component
@Scope("prototype")
public class MessageDataSync implements DataSync , DataMatch {

    private Logger logger = LoggerFactory.getLogger(MessageDataSync.class);

    private String agentID = null;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageGoalStatusRepository messageGoalStatusRepository;

    @Autowired
    private DateService dateService;

    @Override
    public boolean isDifference(Object dbData,Object pushData) {
//        Message message = (Message) dbData;
//        com.allianz.sd.core.api.model.Message apiMessage = (com.allianz.sd.core.api.model.Message) pushData;
//
//        return message.getDataTime().getTime() != dateService.toDateTimeFormatDate(apiMessage.getSynchDetail().getLastUpdateDateTimeBackend()).getTime();

        return false;
    }

    @Override
    public Map findPullData(Date appSyncDate) {
        Map<Integer,Message> map = new HashMap<Integer,Message>();

        logger.trace("findPullData agentID = ["+agentID+"] , appSyncDate = ["+appSyncDate+"]");

        List<Message> messages = this.messageRepository.findPullData(appSyncDate,agentID);
        for(Message message : messages) {

            map.put(message.getMessageID(),message);
        }

        return map;
    }

    @Override
    public Object findPushData(Object data) {
        Optional<Message> messageOpt = messageRepository.findById(Integer.parseInt(String.valueOf(data)));
        return messageOpt.orElse(null);

    }

    @Override
    public Object data2SyncObj(Object data) {
        Message message = (Message) data;

        SynchDetail synchDetail = new SynchDetail();
        synchDetail.setLastUpdateDateTimeBackend(dateService.toDateTimeFormatString(message.getDataTime()));
        synchDetail.setToDelete(false);

        com.allianz.sd.core.api.model.Message apiMessage = new com.allianz.sd.core.api.model.Message();
        apiMessage.setMessageID(new BigDecimal(message.getMessageID()));
        apiMessage.setMessageCategory(message.getMessageCategory());
        apiMessage.setMessageType(message.getMessageType());
        apiMessage.setMessageTime(dateService.toDateTimeFormatString(message.getMessageTime()));
        apiMessage.setArguments(message.getArguments());
        apiMessage.setTitle(message.getTitle());
        apiMessage.setDescription(message.getDescription());
        apiMessage.setStatus(message.getStatus());
        apiMessage.setIsClick("Y".equals(message.getIsClick()));
        apiMessage.setIsPopup("Y".equalsIgnoreCase(message.getIsPopup()) );
        apiMessage.setIsShow("Y".equalsIgnoreCase(message.getIsShow()) );
        apiMessage.setSynchDetail(synchDetail);
        
        //need goal approve message set status
        String status = "";
        if(MessageType.Need_Goal_Approve.toString().equalsIgnoreCase(message.getMessageType())) {
        	MessageGoalStatus goalStatus = messageGoalStatusRepository.findById(message.getMessageID()).orElse(null);
        	if(goalStatus!=null) {
        		status = goalStatus.getStatus();
        	}
        }
        apiMessage.setLinkStatus(status);

        return apiMessage;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Integer getIdentityID(Object data) {
        if(data instanceof Message) return ((Message) data).getMessageID();
        else return (Integer)data;
    }

    @Override
    public boolean isDeleteData(Object data) {
        return false;
    }

    @Override
    public void onDeleteData(Object data) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object onSaveData(Object dbData, Object data) {
        Integer messageID = new Integer(String.valueOf(data));

        messageRepository.updateMessageReading(messageID);

        return messageID;
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
