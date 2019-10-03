package com.allianz.sd.core.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.allianz.sd.core.InstanceCode;
import com.allianz.sd.core.jpa.model.AgentDevice;
import com.allianz.sd.core.jpa.model.Message;
import com.allianz.sd.core.notification.PushNotification;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.allianz.sd.core.notification.Notification;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/1/23
 * Time: 下午 3:59
 * To change this template use File | Settings | File Templates.
 */
@Service
public class NotificationsService {

	private Logger logger = LoggerFactory.getLogger(NotificationsService.class);

	@Autowired
	private BeanFactory beanFactory;

	@Autowired
	private LanguageService languageService;

	@Autowired
	private AgentDeviceService agentDeviceService;


	@Async
    public void push(Notification notification) {

		String agentID = notification.getAgentID();

		//convert language
		Map<String,String> paramMap = notification.getLanguageTextMap();
		notification.setTitle(languageService.convertText(notification.getTitle(),paramMap));
		notification.setBody(languageService.convertText(notification.getBody(),paramMap));

		//set message & Customize JSONData
		Message message = notification.getMessage();
		JSONObject dataObj = notification.getData();

		logger.info("message = " + message);
		if(message != null) {
			dataObj.put("category", message.getMessageCategory() );
			dataObj.put("sndMessageType", message.getMessageType() );

			Integer messageId = message.getMessageID();

			if(messageId != null) dataObj.put("messageID", messageId );

			if(StringUtils.isNotEmpty(message.getArguments())) {
				JSONObject messageArg = new JSONObject(message.getArguments());
				Set<String> keys = messageArg.keySet();
				for(String key : keys) {
					dataObj.put(key,messageArg.get(key));
				}
			}
		}

		if(notification.getPushType() == Notification.NotificationType.SINGLE) {
			List<AgentDevice> agentDevices = agentDeviceService.getAgentDevice(agentID);

			logger.trace("agentID["+agentID+"],agentDevices size = " + agentDevices.size());

			for(AgentDevice agentDevice:agentDevices) {
				notification.addPushID(agentDevice.getPushId());
			}

		}
		else if(notification.getPushType() == Notification.NotificationType.TOPIC) {
			//maybe in the future have TOPIC PUSH
		}

		//get PushNotification implements
		PushNotification pushNotification = (PushNotification) beanFactory.getBean(InstanceCode.PushNotification);
		pushNotification.push(notification);

    }
}
