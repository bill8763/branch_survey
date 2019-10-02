package com.allianz.sd.core.schedule.job;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.allianz.sd.core.jpa.bean.DataCategory;
import com.allianz.sd.core.jpa.model.Message;
import com.allianz.sd.core.service.*;
import com.allianz.sd.core.service.bean.MessageType;
import com.allianz.sd.core.service.bean.NeedSetting;
import org.json.JSONObject;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.allianz.sd.core.exception.NotFoundSysYearDataException;
import com.allianz.sd.core.notification.Notification;
import com.allianz.sd.core.service.bean.NotificationCode;

@Component
public class NeedGoalSetting extends QuartzJobBean{

	private static final Logger logger = LoggerFactory.getLogger(NeedGoalSetting.class);

	@Autowired
	private DateService dateService;

	@Autowired
	private GoalService goalService;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private SysDataService sysDataService;

	@Autowired
	private NotificationsService notificationsService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.trace("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< QuartzJob NeedGoalSetting Start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		String organizationalUnit = organizationService.getOrganizationalUnit();
		Date date = dateService.getTodayDate();

		//查年份
		Set<Integer> years = sysDataService.getAPPDisplayYears(date,organizationalUnit);
		logger.trace("years size = "+years.size());

		if(years.size() != 0) {
			for(Integer  year : years) {

				//查有哪些Agent需要GoalSetting
				List<NeedSetting> needSettings = goalService.getNeedSetting(organizationalUnit,year,date);

				for(NeedSetting needSetting :needSettings) {
					int days = needSetting.getDays();
					String agentID = needSetting.getAgentID();

					JSONObject jsonObj = new JSONObject();
					jsonObj.put("days", days);
					jsonObj.put("year", year);
					jsonObj.put("isPromo",needSetting.isPromo());

					Message message = new Message();
					message.setMessageCategory(DataCategory.GOALSETTING);
					message.setMessageType(MessageType.GoalPromoSetting.toString());
					message.setArguments(jsonObj.toString());


					//發送推播
					Notification notification = new Notification();
					notification.setAgentID(agentID);

					notification.setTitle(NotificationCode.Need_To_Goal_Setting_Title.toString());
					notification.setBody(NotificationCode.Need_To_Goal_Setting_Body.toString());
					notification.setImmediately(true);
					notification.setPushType(Notification.NotificationType.SINGLE);
					notification.setMessage(message);

					//加參數
					notification.addLanguageText("days", String.valueOf(days));
					notification.addLanguageText("year", String.valueOf(year));

					notificationsService.push(notification);
				}

			}
		}

		logger.trace("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< QuartzJob NeedGoalSetting Finish >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}



}
