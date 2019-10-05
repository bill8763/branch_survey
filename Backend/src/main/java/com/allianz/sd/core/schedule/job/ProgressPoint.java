package com.allianz.sd.core.schedule.job;

import java.util.Date;
import java.util.List;

import com.allianz.sd.core.InstanceCode;
import com.allianz.sd.core.service.*;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.allianz.sd.core.jpa.bean.DataCategory;
import com.allianz.sd.core.jpa.model.Message;
import com.allianz.sd.core.notification.Notification;
import com.allianz.sd.core.progress.ProgressActivity;
import com.allianz.sd.core.service.bean.MessageType;
import com.allianz.sd.core.service.bean.NotificationCode;

@Component
public class ProgressPoint extends QuartzJobBean{

	private static final Logger logger = LoggerFactory.getLogger(ProgressPoint.class);

	@Autowired
	private DateService dateService;

	@Autowired
	private ProgressService progressService;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private BeanFactory beanFactory;

	@Autowired
	private MessageService messageService;

	@Autowired
	private NotificationsService notificationsService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.trace("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<QuartzJob ProgressPoint Start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		//get implements class
		ProgressActivity progressActivity = (ProgressActivity) beanFactory.getBean(InstanceCode.ProgressActivity);

		//取得MiniPoint
		int lessPoint = progressActivity.getMiniPoint();

		String organizationalUnit = organizationService.getOrganizationalUnit();
		Date date = dateService.getTodayDate();

		//Get all agentIDs less than 20 points on the day
		List<String> agentIdList = progressService.getLowMiniPointAgents(organizationalUnit, date, lessPoint);

		logger.debug("agentIdList = " + agentIdList.size());

		for(String agentID :agentIdList) {

			logger.debug("agentID = " + agentID);

			//寫入訊息夾
			Message message = new Message();
			message.setMessageCategory(DataCategory.PROGRESS);
			message.setMessageType(MessageType.ActivityLessThanTwentyPoint.toString());
			message.setAgentID(agentID);
			message.setTitle(NotificationCode.Activity_Mini_Point_Title.toString());
			message.setDescription(NotificationCode.Activity_Mini_Point_Body.toString());
			message.setIsPopup("Y");
			message.setIsShow("N");

			messageService.addMessage(message,false);
			
			//發送推播
			Notification notification = new Notification();
			notification.setAgentID(agentID);
			notification.setTitle(NotificationCode.Activity_Mini_Point_Title.toString());
			notification.setBody(NotificationCode.Activity_Mini_Point_Body.toString());
			notification.setImmediately(true);
			notification.setPushType(Notification.NotificationType.SINGLE);
            notification.setMessage(message);

			notificationsService.push(notification);

		}

		logger.trace("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< QuartzJob ProgressPoint Finish >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}



}
