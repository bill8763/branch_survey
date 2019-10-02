package com.allianz.sd.core.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.allianz.sd.core.jpa.bean.DataCategory;
import com.allianz.sd.core.jpa.model.Message;
import com.allianz.sd.core.jpa.repository.MessageRepository;
import com.allianz.sd.core.notification.Notification;
import com.allianz.sd.core.progress.ProgressActivity;
import com.allianz.sd.core.service.bean.MessageType;
import com.allianz.sd.core.service.bean.NotificationCode;

public class DefaultProgressActivity implements ProgressActivity {

	private static final Logger log = LoggerFactory.getLogger(DefaultProgressActivity.class);
	
	@Autowired
	private MessageRepository messageRepository;
	
	@Override
	public Notification getActivityNotification(int point , String agentID,Date date) {
		String dateformat = new SimpleDateFormat("yyyy-MM-dd").format(date);
		
		log.trace("ProgressActivity point = "+point);
		Notification notification = null;

		String messageType = null;
		String messageTitle = null;
		String messageDescription = null;

		if(point >= 10 && point < 20) {
			log.trace("point >= 10 && point < 20");
			//設定 今日活動點數已達10點  !
			messageType = MessageType.ActivityArriveTenPoint.toString();
			messageTitle = NotificationCode.Activity_Ten_Point_Title.toString();
			messageDescription = NotificationCode.Activity_Ten_Point_Body.toString();
		}else if(point >= 20) {
			log.trace("point >= 20");
			//設定 今日活動點數已達20點  !
			messageType = MessageType.ActivityArriveTwentyPoint.toString();
			messageTitle = NotificationCode.Activity_Twenty_Point_Title.toString();
			messageDescription = NotificationCode.Activity_Twenty_Point_Body.toString();
		}

		if(StringUtils.isNotEmpty(messageType)) {
			//判斷當天是否發送過推播
			Message message = messageRepository.getNotification(DataCategory.PROGRESS, messageType, dateformat, agentID);
			log.trace("message = "+ message);
			if(message == null) {
				notification = new Notification();
				notification.setTitle(messageTitle);
				notification.setBody(messageDescription);
				notification.setMessage(message);
			}
		}
		return notification;
	}

	@Override
	public int getMiniPoint() {
		return 20;
	}


}
