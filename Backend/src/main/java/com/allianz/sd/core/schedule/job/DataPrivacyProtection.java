package com.allianz.sd.core.schedule.job;

import java.util.List;

import com.allianz.sd.core.jpa.model.AgentData;
import com.allianz.sd.core.service.AgentDataService;
import com.allianz.sd.core.service.NotificationsService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.allianz.sd.core.jpa.bean.DataCategory;
import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.jpa.model.Message;
import com.allianz.sd.core.notification.Notification;
import com.allianz.sd.core.service.DateService;
import com.allianz.sd.core.service.OrganizationService;
import com.allianz.sd.core.service.bean.MessageType;
import com.allianz.sd.core.service.bean.NotificationCode;

/**
 * @author bill8
 *
 */
@Component
public class DataPrivacyProtection extends QuartzJobBean{

	private static final Logger logger = LoggerFactory.getLogger(DataPrivacyProtection.class);

	@Autowired
	private DateService dateService;

	@Autowired
	private AgentDataService agentDataService;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private NotificationsService notificationsService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.trace("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< QuartzJob DataPrivacyProtection Start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		String organizationalUnit = organizationService.getOrganizationalUnit();
		
		int dataYear = dateService.getCurrentYear();

		List<AgentYearData> agentYearDatas = agentDataService.getAllAgentYearData(dataYear, organizationalUnit);
		
		for(AgentYearData  agentYearData : agentYearDatas) {

			String agentID = agentYearData.getIdentity().getAgentID();

			Message message = new Message();
			message.setMessageCategory(DataCategory.CUSTOMER);
			message.setMessageType(MessageType.DataPrivacyProtection.toString());

			//發送推播
			Notification notification = new Notification();
			notification.setAgentID(agentID);

			notification.setTitle(NotificationCode.Data_Privacy_Protection_Title.toString());
			notification.setBody(NotificationCode.Data_Privacy_Protection_Body.toString());
			notification.setImmediately(true);
			notification.setPushType(Notification.NotificationType.SINGLE);
			notification.setMessage(message);

			notificationsService.push(notification);
		}

		logger.trace("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< QuartzJob DataPrivacyProtection Finish >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}



}
