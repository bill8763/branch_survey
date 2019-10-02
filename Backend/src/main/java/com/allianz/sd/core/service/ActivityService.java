package com.allianz.sd.core.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import com.allianz.sd.core.service.bean.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allianz.sd.core.exception.NotFoundAgentYearDataException;
import com.allianz.sd.core.exception.NotFoundPerformanceTableMonthException;
import com.allianz.sd.core.exception.NotFoundSysYearDataException;
import com.allianz.sd.core.jpa.bean.DataCategory;
import com.allianz.sd.core.jpa.model.AgentDailyAppData;
import com.allianz.sd.core.jpa.model.AgentDailyAppDataIdentity;
import com.allianz.sd.core.jpa.model.AgentDailyData;
import com.allianz.sd.core.jpa.model.AgentDailyDataIdentity;
import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.jpa.model.AgentYearDataIdentity;
import com.allianz.sd.core.jpa.model.Message;
import com.allianz.sd.core.jpa.model.PerformanceTableMonth;
import com.allianz.sd.core.jpa.model.PerformanceTableMonthIdentity;
import com.allianz.sd.core.jpa.model.PersonalProgress;
import com.allianz.sd.core.jpa.model.ProgressIdentity;
import com.allianz.sd.core.jpa.model.SysYearData;
import com.allianz.sd.core.jpa.model.SysYearDataIdentity;
import com.allianz.sd.core.jpa.repository.AgentDailyAppDataRepository;
import com.allianz.sd.core.jpa.repository.AgentDailyDataRepository;
import com.allianz.sd.core.jpa.repository.AgentYearDataRepository;
import com.allianz.sd.core.jpa.repository.PerformanceTableMonthRepository;
import com.allianz.sd.core.jpa.repository.PersonalProgressRepository;
import com.allianz.sd.core.jpa.repository.SysYearDataRepository;
import com.allianz.sd.core.notification.Notification;
import com.allianz.sd.core.progress.ProgressActivity;
import com.allianz.sd.core.progress.CalcAcvitity;

@Service
public class ActivityService {

	private static final Logger log = LoggerFactory.getLogger(ActivityService.class);

	@Autowired
	private AgentDailyAppDataRepository agentDailyAppDataRepository;

	@Autowired
	private AgentDailyDataRepository agentDailyDataRepository;

	@Autowired
	private DateService dateService;

	@Autowired
	private ProgressService progressService;

	@Autowired
	private ProgressActivity progressActivity;

	@Autowired
	private NotificationsService notificationsService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private PersonalProgressRepository personalProgressRepository;

	@Autowired
	private SysDataService sysDataService;

	@Autowired
	private AgentDataService agentDataService;

	public void calcPushProgressActivityMessage(String agentID , Date date) {

		int dataYear = dateService.getFullYear(date);

		AgentYearData agentYearData = agentDataService.getAgentYearData(agentID,dataYear);

		calcPushProgressActivityMessage(agentYearData,date);
	}

	public void calcPushProgressActivityMessage(AgentYearData agentYearData , Date date) {

		String agentID = agentYearData.getIdentity().getAgentID();
		String appUseMode = agentYearData.getAppUseMode();
		if(AppUseMode.LEADER.equalsIgnoreCase(appUseMode) || AppUseMode.AGENT.equalsIgnoreCase(appUseMode)) {
			// Calculate the number of points on the day，Set push
			int point = progressService.getActivityPointByDate(agentID, date);

			log.trace("ActivityService Point = " + point);
			Notification notification = progressActivity.getActivityNotification(point, agentID, date);
			log.trace("notification = " + notification);
			if (notification != null) {
				// 寫入Message
				Message message = new Message();
				message.setMessageCategory(DataCategory.PROGRESS);
				message.setMessageType(MessageType
						.getMessageType(NotificationCode.valueOf(notification.getTitle())).toString());
				message.setIsPopup("Y");
				message.setAgentID(agentID);
				message.setTitle(notification.getTitle());
				message.setDescription(notification.getBody());
				message.setIsShow("N");

				messageService.addMessage(message);

				// notification
				notification.setAgentID(agentID);
				notification.setImmediately(true);
				notification.setPushType(Notification.NotificationType.SINGLE);
				notification.setMessage(message);
				notificationsService.push(notification);

			}
		}
	}

	public void UpdateFindSchedule(String agentID, String organizationalUnit, AgentDailyAppDateType type, int value,
								   String dataDate, boolean isReplace) {
		log.trace("UpdateFindSchedule  START >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		log.trace("agentID : " + agentID + " organizationalUnit : " + organizationalUnit + " type : " + type
				+ " Value : " + value);

		Date date = dateService.getDate(dataDate);
		int dataYear = dateService.getFullYear(date);

		AgentYearData agentYearData = agentDataService.getAgentYearData(agentID,dataYear);

		// step2. get AppSysCtrl & PerformanceTable value
		if (agentYearData != null) {

			String appSysCtrl = agentYearData.getAppSysCtrl();
			String performanceTable = agentYearData.getPerformanceTable();

			// step3. query point rate from sysYearData
			SysYearData sysYearDataQuery = sysDataService.getSysYearDataBySysCtrl(organizationalUnit,appSysCtrl,dataYear);

			// step4. query Year/Quarter/Month
			PerformanceTime performanceTime = progressService.getPerformanceTime(date,organizationalUnit,performanceTable);

			if (sysYearDataQuery != null && performanceTime != null) {

				double findConvertPointBase = sysYearDataQuery.getFindConvertPointBase();
				double scheduleConvertPointBase = sysYearDataQuery.getScheduleConvertPointBase();
				double meetConvertPointBase = sysYearDataQuery.getMeetConvertPointBase();
				double submitConvertPointBase = sysYearDataQuery.getSubmitConvertPointBase();
				double inforceConvertPointBase = sysYearDataQuery.getInforceConvertPointBase();

				int performanceYear = performanceTime.getYear();
				int performanceQuarter = performanceTime.getQuarter();
				int performanceMonth = performanceTime.getMonth();

				// FIND OR SCHEDULE
				if (type == AgentDailyAppDateType.FIND || type == AgentDailyAppDateType.SCHEDULE) {

					// step5. Query Agent_Daily_App_Data
					AgentDailyAppDataIdentity agentDailyAppDataIdentity = new AgentDailyAppDataIdentity();
					agentDailyAppDataIdentity.setAgentID(agentID);
					agentDailyAppDataIdentity.setOrganizationalUnit(organizationalUnit);
					agentDailyAppDataIdentity.setDataDate(date);
					AgentDailyAppData agentDailyAppData = agentDailyAppDataRepository.findData(agentID,
							organizationalUnit, date);

					if (agentDailyAppData == null) {
						agentDailyAppData = new AgentDailyAppData();
						agentDailyAppData.setIdentity(agentDailyAppDataIdentity);
					}

					// calc
					CalcAcvitity calcAcvitity = (CalcAcvitity) agentDailyAppData;
					double pointBase = type == AgentDailyAppDateType.FIND ? findConvertPointBase
							: scheduleConvertPointBase;

					calcAcvitity.calc(value, pointBase, type, isReplace);

					// save data
					agentDailyAppData.setDataMonth(performanceMonth);
					agentDailyAppData.setDataYear(performanceYear);
					agentDailyAppData.setDataQuarter(performanceQuarter);
					agentDailyAppData.setDataTime(date);
					agentDailyAppData.setCreateBy(agentID);
					agentDailyAppData.setUpdateBy(agentID);

					agentDailyAppDataRepository.save(agentDailyAppData);

				}
				// Meet OR Submit OR Inforce
				else {
					// step5. Query Agent_Daily_Data
					AgentDailyDataIdentity agentDailyDataIdentity = new AgentDailyDataIdentity();
					agentDailyDataIdentity.setAgentID(agentID);
					agentDailyDataIdentity.setOrganizationalUnit(organizationalUnit);
					agentDailyDataIdentity.setDataDate(date);

					AgentDailyData agentDailyData = agentDailyDataRepository.findData(agentID, organizationalUnit,
							date);
					if (agentDailyData == null) {
						agentDailyData = new AgentDailyData();
						agentDailyData.setIdentity(agentDailyDataIdentity);
					}


					// calc
					double base = 0;
					CalcAcvitity calcAcvitity = (CalcAcvitity) agentDailyData;
					if (type == AgentDailyAppDateType.MEET) {
						base = meetConvertPointBase;
					} else if (type == AgentDailyAppDateType.SUBMIT) {
						base = submitConvertPointBase;
					} else {
						base = inforceConvertPointBase;
					}

					calcAcvitity.calc(value, base, type, isReplace);

					// Step7. save data
					agentDailyData.setDataMonth(performanceMonth);
					agentDailyData.setDataYear(performanceYear);
					agentDailyData.setDataQuarter(performanceQuarter);
					agentDailyData.setDataTime(date);
					agentDailyData.setCreateBy(agentID);
					agentDailyData.setUpdateBy(agentID);

					agentDailyDataRepository.save(agentDailyData);
				}




			} else {
				log.error("Can't find SysYearData or PerformanceTable");
			}
		} else {
			throw new NotFoundAgentYearDataException(organizationalUnit,agentID,dataYear);
		}

	}

	public void updatePersonalProgress(String agentID, String organizationalUnit, int value,
									   AgentDailyAppDateType type , String dataDate) {
		log.trace("<<<<<<<<<<<<<<<<<<<<updatePersonalProgress  START>>>>>>>>>>>>>>>>>>>");
		log.trace("AgentDailyAppDataType = "+type.toString());

		Date date = dateService.getDate(dataDate);
		AgentYearData agentYearData = agentDataService.getAgentYearData(agentID,dateService.getFullYear(date));
		if(agentYearData != null) {
			PerformanceTime performanceTime = progressService.getPerformanceTime(date,organizationalUnit,agentYearData.getPerformanceTable());

			if(performanceTime != null) {
				int dataYear = performanceTime.getYear();

				// 先查personalProgress 那筆資料 做數值比對
				ProgressIdentity identity = new ProgressIdentity();
				identity.setAgentID(agentID);
				identity.setOrganizationalUnit(organizationalUnit);
				identity.setDataType("Actual");
				identity.setDataYear(dataYear);
				identity.setTimeBase(ProgressTimeField.Day.toString());

				Optional<PersonalProgress> personalProgressOption = personalProgressRepository.findById(identity);
				PersonalProgress personalProgress = personalProgressOption.orElse(null);
				log.trace("personalProgress = " + personalProgress);
				if (personalProgress != null) {
					int originalvalueFind = Integer.parseInt(String.valueOf(personalProgress.getFind()));
					int originalvalueSchedule = Integer.parseInt(String.valueOf(personalProgress.getSchedule()));
					int originalvalueMeet = Integer.parseInt(String.valueOf(personalProgress.getMeet()));
					int originalvalueSubmit = Integer.parseInt(String.valueOf(personalProgress.getSubmit()));
					int originalvalueInforce = Integer.parseInt(String.valueOf(personalProgress.getInforce()));

					int updateValue = 1;
					switch (type) {
						case FIND: {
							if(value != 1) updateValue = value - originalvalueFind;
							log.trace("FIND updateValue = " + updateValue);
							personalProgressRepository.updateFind(new BigDecimal(updateValue), agentID, organizationalUnit, dataYear);
							break;
						}
						case SCHEDULE: {
							if (value != 1) updateValue = value - originalvalueSchedule;
							log.trace("SCHEDULE updateValue = " + updateValue);
							personalProgressRepository.updateSchedule(new BigDecimal(updateValue), agentID, organizationalUnit, dataYear);
							break;
						}
						case MEET: {
							if (value != 1) updateValue = value - originalvalueMeet;
							log.trace("MEET updateValue = " + updateValue);
							personalProgressRepository.updateMeet(new BigDecimal(updateValue), agentID, organizationalUnit, dataYear);
							break;
						}
						case SUBMIT: {
							if (value != 1) updateValue = value - originalvalueSubmit;
							log.trace("SUBMIT updateValue = " + updateValue);
							personalProgressRepository.updateSubmit(new BigDecimal(updateValue), agentID, organizationalUnit, dataYear);
							break;
						}
						case INFORCE: {
							if (value != 1) updateValue = value - originalvalueInforce;
							log.trace("INFORCE updateValue = " + updateValue);
							personalProgressRepository.updateInforce(new BigDecimal(updateValue), agentID, organizationalUnit, dataYear);
							break;
						}
					}
				}
			}

		}

	}

}
