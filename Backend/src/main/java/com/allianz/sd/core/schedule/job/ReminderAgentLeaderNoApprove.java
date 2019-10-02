package com.allianz.sd.core.schedule.job;

import com.allianz.sd.core.goalsetting.GoalStatusListener;
import com.allianz.sd.core.goalsetting.bean.GoalStatusSubject;
import com.allianz.sd.core.jpa.bean.DataCategory;
import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.jpa.model.GoalSettingVersion;
import com.allianz.sd.core.jpa.model.Message;
import com.allianz.sd.core.jpa.model.SysData;
import com.allianz.sd.core.jpa.repository.GoalSettingVersionRepository;
import com.allianz.sd.core.jpa.repository.SysDataRepository;
import com.allianz.sd.core.notification.Notification;
import com.allianz.sd.core.notification.impl.IDNameBuilder;
import com.allianz.sd.core.notification.impl.IDNameItem;
import com.allianz.sd.core.service.*;
import com.allianz.sd.core.service.bean.Goal;
import com.allianz.sd.core.service.bean.GoalSettingStatus;
import com.allianz.sd.core.service.bean.MessageType;
import com.allianz.sd.core.service.bean.NotificationCode;
import org.json.JSONArray;
import org.json.JSONObject;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.*;

/**
 * 判斷業務員如果送審件超過5天但主管沒approve要自動reject並推播主管跟業務員
 * Judging that the salesman has submitted the review for more than 5 days but the supervisor has not approved to automatically reject and push the supervisor and the salesman.
 */
public class ReminderAgentLeaderNoApprove extends QuartzJobBean {
	

	private static final Logger logger = LoggerFactory.getLogger(ReminderAgentLeaderNoApprove.class);

	@Autowired
	private GoalService goalService;

	@Autowired
	private GoalSettingVersionRepository goalSettingVersionRepository;

	@Autowired
	private SysDataRepository sysDataRepository;

	@Autowired
	private NotificationsService notificationsService;

	@Autowired
	private DateService dateService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private AgentDataService agentDataService;

	@Autowired
	private SysDataService sysDataService;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private GoalStatusListener goalStatusListener;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.info("ReminderAgentLeaderNoApprove Start");

		Date date = dateService.getTodayDate();

		String organizationalUnit = organizationService.getOrganizationalUnit();

		//查年份
		Set<Integer> years = sysDataService.getAPPDisplayYears(date,organizationalUnit);
		logger.trace("years size = "+years.size());

		if(years.size() != 0) {
			for (Integer year : years) {
				Map<String, Map<String,String>> leaderSendMap = new HashMap<String,Map<String,String>>();

				List<GoalSettingVersion> goalSetList = goalService.getPendingApproveList(year);
				for (GoalSettingVersion goalSetVersion : goalSetList) {

					try{
						Integer goalSettingSeq = goalSetVersion.getGoalSettingSeq();
						String agentID = goalSetVersion.getAgentID();
						String leaderID = goalSetVersion.getSigningSupervisor();

						logger.debug("RemainderAgentLeaderNoApprove agentID:"+agentID+",leaderID:"+leaderID + ",goalSettingSeq = " + goalSettingSeq);

						int dataYear = goalSetVersion.getDataYear();

						AgentYearData agentYearData = agentDataService.getAgentYearData(agentID, dataYear);

						SysData sysData = sysDataRepository.findByAppSysCtrl(agentYearData.getAppSysCtrl());
						if (sysData != null) {

							Date countDate = dateService.addDate(goalSetVersion.getGoalSubmitDate(), Calendar.DATE,sysData.getGoalApproveTimeLimit());

							if (dateService.getTodayDate().getTime() >= countDate.getTime()) {

								//更新狀態與日期
								goalSetVersion.setStatus(GoalSettingStatus.OVERDUE.toString());
								goalSetVersion.setStatusDate(dateService.getTodayDate());
								goalSettingVersionRepository.save(goalSetVersion);

								//往GoalStatus發送狀態變更事件
								GoalStatusSubject goalStatusSubject = new GoalStatusSubject();
								goalStatusSubject.setBeforeStatus(GoalSettingStatus.PENDING_APPROVAL);
								goalStatusSubject.setAfterStatus(GoalSettingStatus.OVERDUE);
								goalStatusSubject.setAgentYearData(agentYearData);
								goalStatusListener.changeStatus(goalStatusSubject);


								Map<String,String> agentMap = leaderSendMap.get(leaderID);
								if(agentMap == null) agentMap = new LinkedHashMap<String,String>();
								agentMap.put(agentID,agentYearData.getAgentData().getAgentName());
								leaderSendMap.put(leaderID, agentMap);

							}
						}
					}catch(Exception e) {
						logger.error("ReminderAgentLeaderNoApprove fail!!",e);
					}

				}

				//依照key去發送推播與訊息
				for(String leaderAgentID : leaderSendMap.keySet()) {

					IDNameBuilder idNameBuilder = new IDNameBuilder();
					Map<String,String> agentMap = leaderSendMap.get(leaderAgentID);

					JSONArray agentList = new JSONArray();
					for(String id : agentMap.keySet()) {
						String agentName = agentMap.get(id);

						idNameBuilder.addItem(new IDNameItem(id,agentName));

						//message
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("agentID", id);
						jsonObject.put("agentName", agentName);
						agentList.put(jsonObject);
					}

					//5.給當下主管新增一筆到Message訊息匣
					Message supervisorMessage = new Message();
					supervisorMessage.setMessageCategory(DataCategory.GOALSETTING);
					supervisorMessage.setMessageType(MessageType.GoalAutoRejectLeader.toString());
					supervisorMessage.setAgentID(leaderAgentID);
					supervisorMessage.setTitle(NotificationCode.Goal_Auto_Reject_Leader_Message_Title.toString());
					supervisorMessage.setDescription(NotificationCode.Goal_Auto_Reject_Leader_Message_Description.toString());

					JSONObject dataMess = new JSONObject();
					dataMess.put("agentList", agentList);

					supervisorMessage.setArguments(dataMess.toString());

					messageService.addMessage(supervisorMessage);


					//6.當下主管發送推播
					Notification supervisorNotification = new Notification();
					supervisorNotification.setAgentID(leaderAgentID); // 上層主管的AgentID
					supervisorNotification.setImmediately(true);

					supervisorNotification.setTitle(NotificationCode.Goal_Auto_Reject_Leader_Message_Title.toString());
					supervisorNotification.setBody(NotificationCode.Goal_Auto_Reject_Leader_Message_Description.toString());
					supervisorNotification.setPushType(Notification.NotificationType.SINGLE);
					supervisorNotification.addLanguageText("agentID",idNameBuilder.toText());
					supervisorNotification.setMessage(supervisorMessage);

					notificationsService.push(supervisorNotification);
				}
			}
		}



		logger.info("ReminderAgentLeaderNoApprove Finish");
    }
}
