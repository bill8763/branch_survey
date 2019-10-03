package com.allianz.sd.core.schedule.job;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.allianz.sd.core.notification.impl.IDNameBuilder;
import com.allianz.sd.core.notification.impl.IDNameItem;
import com.allianz.sd.core.service.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.allianz.sd.core.jpa.bean.DataCategory;
import com.allianz.sd.core.jpa.model.AgentData;
import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.jpa.model.AgentYearDataIdentity;
import com.allianz.sd.core.jpa.model.GoalSettingVersion;
import com.allianz.sd.core.jpa.model.Message;
import com.allianz.sd.core.jpa.repository.AgentDataRepository;
import com.allianz.sd.core.jpa.repository.AgentYearDataRepository;
import com.allianz.sd.core.jpa.repository.GoalSettingVersionRepository;
import com.allianz.sd.core.notification.Notification;
import com.allianz.sd.core.service.bean.GoalSettingStatus;
import com.allianz.sd.core.service.bean.MessageType;
import com.allianz.sd.core.service.bean.NotificationCode;

/**
 * supervisor change remaining to agent, old , new supervisor
 * pending approve remaining
 *
 */
@Component
public class Verify extends QuartzJobBean{

	private static final Logger logger = LoggerFactory.getLogger(Verify.class);


	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private GoalSettingVersionRepository goalSettingVersionRepository;

	@Autowired
	private MessageService messageService;

	@Autowired
	private NotificationsService notificationsService;

	@Autowired
	private IDNameBuilder agentListBuilder;

	@Autowired
	private AgentDataService agentDataService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.trace("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< QuartzJob Verify Start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		String organizationalUnit = organizationService.getOrganizationalUnit();

		//query all status is PENDING_APPROVAL data
		List<GoalSettingVersion> goalSettingVersionList = goalSettingVersionRepository.findByStatus(GoalSettingStatus.PENDING_APPROVAL.toString());
		logger.trace("goalSettingVersionList SIZE = " + goalSettingVersionList.size());

		Map<Integer,SendGoalVersion> yearSendGoalMap = new LinkedHashMap<>();

		//query all SigningSupervisor Have changed
		for(GoalSettingVersion version :goalSettingVersionList) {
			String signingSupervisorVersion = version.getSigningSupervisor();
			String agentID = version.getAgentID();
			int dateYear = version.getDataYear();

			logger.trace("dateYear = " + dateYear + ",agentID["+agentID+"],signingSupervisorVersion["+signingSupervisorVersion+"]");

			SendGoalVersion sendGoalVersion = yearSendGoalMap.get(dateYear);
			if(sendGoalVersion == null) sendGoalVersion = new SendGoalVersion();

			List<String> agentsList = sendGoalVersion.getAgentsList();
			Map<String, Map<String, String>> needSetting = sendGoalVersion.getNeedSetting();
			Map<String, Map<String, String>> oldSupervisor = sendGoalVersion.getOldSupervisor();
			Map<String, Map<String, String>> newSupervisor = sendGoalVersion.getNewSupervisor();

			AgentYearData agentYearData = agentDataService.getAgentYearData(agentID,dateYear,organizationalUnit);

			if(agentYearData != null) {

				//query agnetName
				String agentName = agentYearData.getAgentData().getAgentName();

				String goalSigningSupervisor = agentYearData.getGoalSigningSupervisor();
				logger.trace("goalSigningSupervisor = "+goalSigningSupervisor + "signingSupervisorVersion = "+signingSupervisorVersion );
				boolean isDifferent = !goalSigningSupervisor.equalsIgnoreCase(signingSupervisorVersion) ;

				//Supervisor is Different
				if(isDifferent) {
					logger.trace("Supervisor is Different !!");

					//放入舊主管送審清單
					Map<String,String> oldSupervisorMap = oldSupervisor.get(signingSupervisorVersion);
					if(oldSupervisorMap == null) oldSupervisorMap = new LinkedHashMap<>();
					oldSupervisorMap.put(agentID,agentName);
					oldSupervisor.put(signingSupervisorVersion,oldSupervisorMap);

					//放入新主管送審清單
					Map<String,String> newSupervisorMap = newSupervisor.get(goalSigningSupervisor);
					if(newSupervisorMap == null) newSupervisorMap = new LinkedHashMap<>();
					newSupervisorMap.put(agentID,agentName);
					newSupervisor.put(goalSigningSupervisor,newSupervisorMap);

					//放入通知Agent
					agentsList.add(agentID);

					//更新主管
					version.setSigningSupervisor(goalSigningSupervisor);
					GoalSettingVersion goalSettingVersion = goalSettingVersionRepository.save(version);
					boolean updateSuccess = goalSettingVersion != null;
					logger.trace("update = "+ updateSuccess);

				}
				else {
					logger.trace("Supervisor Is The Same !!");

					Map<String,String> needSettingMap = needSetting.get(signingSupervisorVersion);
					if(needSettingMap == null) needSettingMap = new LinkedHashMap<>();
					needSettingMap.put(agentID,agentName);

					needSetting.put(signingSupervisorVersion,needSettingMap);

				}

				yearSendGoalMap.put(dateYear,sendGoalVersion);

			}
		}

		//by年度發送推播
		for(int year : yearSendGoalMap.keySet()) {

			SendGoalVersion sendGoalVersion = yearSendGoalMap.get(year);

			List<String> agentsList = sendGoalVersion.getAgentsList();
			Map<String, Map<String, String>> needSetting = sendGoalVersion.getNeedSetting();
			Map<String, Map<String, String>> oldSupervisor = sendGoalVersion.getOldSupervisor();
			Map<String, Map<String, String>> newSupervisor = sendGoalVersion.getNewSupervisor();

			//發送 old 主管
			for(String key : oldSupervisor.keySet()) {
				Map<String,String> agentList = oldSupervisor.get(key);

				JSONObject jsonObj = toNotifyJSONObject(agentList,year);

				Message message = new Message();
				message.setMessageCategory(DataCategory.GOALSETTING);
				message.setMessageType(MessageType.SupervisorHaveChangeOld.toString());
				message.setAgentID(key);
				message.setTitle(NotificationCode.Supervisor_Have_Change_Old_Title.toString());
				message.setDescription(NotificationCode.Supervisor_Have_Change_Old_Body.toString());
				message.setArguments(jsonObj.toString());
				message.setIsPopup("Y");

				messageService.addMessage(message);

			}

			//發送 new 主管
			for(String key : newSupervisor.keySet()) {

				Map<String,String> agentList = newSupervisor.get(key);

				JSONObject jsonObj = toNotifyJSONObject(agentList,year);

				Message message = new Message();
				message.setMessageCategory(DataCategory.GOALSETTING);
				message.setMessageType(MessageType.SupervisorHaveChangeNew.toString());
				message.setAgentID(key);
				message.setTitle(NotificationCode.Supervisor_Have_Change_New_Title.toString());
				message.setDescription(NotificationCode.Supervisor_Have_Change_New_Body.toString());
				message.setArguments(jsonObj.toString());
				message.setIsPopup("Y");

				messageService.addMessage(message);

			}
			//Agent
			for(String agent : agentsList) {

				Message message = new Message();
				message.setMessageCategory(DataCategory.GOALSETTING);
				message.setMessageType(MessageType.SupervisorHaveChangeAgent.toString());
				message.setAgentID(agent);
				message.setTitle(NotificationCode.Supervisor_Have_Change_Agent_Title.toString());
				message.setDescription(NotificationCode.Supervisor_Have_Change_Agent_Body.toString());
				message.setIsPopup("Y");
//            message.setArguments(jsonObj.toString());

				messageService.addMessage(message);
			}

			//發送未審核推播
			for(String key : needSetting.keySet()) {

				Map<String,String> agentList = needSetting.get(key);

				JSONObject jsonObj = toNotifyJSONObject(agentList,year);

				//寫入訊息夾
				Message message = new Message();
				message.setMessageCategory(DataCategory.GOALSETTING);
				message.setMessageType(MessageType.PendingReview.toString());
				message.setAgentID(key);
				message.setTitle(NotificationCode.Pending_Review_Title.toString());
				message.setDescription(NotificationCode.Pending_Review_Body.toString());
				message.setArguments(jsonObj.toString());
				message.setIsPopup("Y");

				messageService.addMessage(message);

				//notification
				for(String agentID : agentList.keySet()) {
					agentListBuilder.addItem(new IDNameItem(agentID,agentList.get(agentID)));
				}

				Notification notification = new Notification();
				notification.setAgentID(key);
				notification.setTitle(NotificationCode.Pending_Review_Title.toString());
				notification.setBody(NotificationCode.Pending_Review_Body.toString());
				notification.setImmediately(true);
				notification.setPushType(Notification.NotificationType.SINGLE);
				notification.setMessage(message);
				notification.addLanguageText("agentList", agentListBuilder.toText());

				notificationsService.push(notification);
			}
		}


		logger.trace("<<<<<<<<<<<<<<<<<<<<<<<<<<<<< QuartzJob Verify Finish >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

	}

	private JSONObject toNotifyJSONObject(Map<String,String> agentMap , int year) {
		JSONObject jsonObj = new JSONObject();
		JSONArray jsonArray = new JSONArray();

		for(String id:agentMap.keySet()) {
			JSONObject agentObj = new JSONObject();
			agentObj.put("agentID", id);
			agentObj.put("agentName", agentMap.get(id));
			jsonArray.put(agentObj);
		}
		jsonObj.put("agentList", jsonArray);
		jsonObj.put("year", year);

		return jsonObj;
	}

	class SendGoalVersion{
		private Map<String,Map<String,String>> needSetting = new LinkedHashMap<String,Map<String,String>>();

		//送審主管換人-舊主管
		Map<String,Map<String,String>> oldSupervisor = new LinkedHashMap<String,Map<String,String>>();

		//送審主管換人-新主管
		Map<String,Map<String,String>> newSupervisor = new LinkedHashMap<String,Map<String,String>>();

		//送審主管換人-業務員
		List<String> agentsList = new ArrayList<String>();



		public Map<String, Map<String, String>> getNeedSetting() {
			return needSetting;
		}

		public Map<String, Map<String, String>> getOldSupervisor() {
			return oldSupervisor;
		}

		public Map<String, Map<String, String>> getNewSupervisor() {
			return newSupervisor;
		}

		public List<String> getAgentsList() {
			return agentsList;
		}
	}

}
