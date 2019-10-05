package com.allianz.sd.core.schedule.job;

import com.allianz.sd.core.jpa.bean.DataCategory;
import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.jpa.model.GoalSettingVersion;
import com.allianz.sd.core.jpa.model.Message;
import com.allianz.sd.core.jpa.repository.GoalSettingVersionRepository;
import com.allianz.sd.core.notification.Notification;
import com.allianz.sd.core.notification.impl.IDNameBuilder;
import com.allianz.sd.core.notification.impl.IDNameItem;
import com.allianz.sd.core.service.AgentDataService;
import com.allianz.sd.core.service.GoalService;
import com.allianz.sd.core.service.MessageService;
import com.allianz.sd.core.service.NotificationsService;
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


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 依照資料交換後的資料判斷業務員如果超過15天沒有設定年度目標則發訊息推播以及帶入原本系統預設值
 * According to the data after the data exchange, it is judged that if the salesperson does not set the annual target for more than 15 days, the message will be pushed and brought into the original system default value.
 */
public class ReminderAgentDefaultGoal extends QuartzJobBean {

	private static final Logger logger = LoggerFactory.getLogger(ReminderAgentDefaultGoal.class);

	@Autowired
	private GoalService goalService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private AgentDataService agentDataService;

	@Autowired
	private NotificationsService notificationsService;

	@Autowired
	private IDNameBuilder agentListBuilder;

	@Autowired
	private GoalSettingVersionRepository goalSettingVersionRepository;

	@Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.info("ReminderAgentDefaultGoal Start");

		// 1.get overdeadline goalsetting data
		List<GoalSettingVersion> goalSetList = goalService.findOverDeadlineGoal();

		Map<String, Map<String,String>> leaderSendMap = new HashMap<String,Map<String,String>>();

		// 2.發送推播跟訊息匣給當下業務員以及上層主管
		for (GoalSettingVersion goalSetVersion : goalSetList) {
			String agentID = goalSetVersion.getAgentID();
			String leaderID = goalSetVersion.getSigningSupervisor();
			int year = goalSetVersion.getDataYear();

			logger.debug("ReminderAgentDefaultGoal year:"+year+",agentID: "+agentID+",leaderID:"+leaderID);

			goalSetVersion.setSubmitStatus("Y");
			goalSettingVersionRepository.save(goalSetVersion);

			AgentYearData agentYearData = agentDataService.getAgentYearData(agentID, year);

			//3.給當下業務員新增一筆到Message訊息匣
			JSONObject arg = new JSONObject();
			arg.put("year",goalSetVersion.getDataYear());

			Message message = new Message();
			message.setMessageCategory(DataCategory.GOALSETTING);
			message.setMessageType(MessageType.GoalAutoApprove.toString());
			message.setAgentID(agentID);
			message.setTitle( NotificationCode.Goal_Auto_Approve_Message_Title.toString() );
			message.setDescription( NotificationCode.Goal_Auto_Approve_Message_Desc.toString());
			message.setArguments(arg.toString());
			message.setIsPopup("Y");
			message.setIsShow("Y");
			messageService.addMessage(message);

			//4.當下業務員發送推播
			Notification notification = new Notification();
			notification.setAgentID(agentID);
			notification.setImmediately(true);
			notification.setTitle(NotificationCode.Goal_Auto_Approve_Message_Title.toString());
			notification.setBody(NotificationCode.Goal_Auto_Approve_Message_Desc.toString());
			notification.setPushType(Notification.NotificationType.SINGLE);
			notification.setMessage(message);

			notification.addLanguageText("year", String.valueOf(goalSetVersion.getDataYear()));
			notificationsService.push(notification);

			Map<String,String> agentMap = leaderSendMap.get(leaderID);

			if(agentMap == null) agentMap = new LinkedHashMap<String,String>();

			agentMap.put(agentID,agentYearData.getAgentData().getAgentName());
			leaderSendMap.put(leaderID, agentMap);
		}

		//依照key去發送推播與訊息
		for(String leaderAgentID : leaderSendMap.keySet()) {

			Map<String,String> agentMap = leaderSendMap.get(leaderAgentID);

			JSONArray agentList = new JSONArray();
			for(String id : agentMap.keySet()) {
				String agentName = agentMap.get(id);
				agentListBuilder.addItem(new IDNameItem(id,agentName));

				//message
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("agentID", id);
				jsonObject.put("agentName", agentName);
				agentList.put(jsonObject);
			}

			//5.給當下主管新增一筆到Message訊息匣
			Message supervisorMessage = new Message();
			supervisorMessage.setMessageCategory(DataCategory.GOALSETTING);
			supervisorMessage.setMessageType(MessageType.GoalAutoApproveLeader.toString());
			supervisorMessage.setAgentID(leaderAgentID);
			supervisorMessage.setTitle(NotificationCode.Goal_Auto_Approve_Leader_Message_Title.toString());
			supervisorMessage.setDescription(NotificationCode.Goal_Auto_Approve_Leader_Message_Desc.toString());

			JSONObject dataMess = new JSONObject();
			dataMess.put("agentList", agentList);

			supervisorMessage.setArguments(dataMess.toString());

			messageService.addMessage(supervisorMessage);


			//6.當下主管發送推播
			Notification supervisorNotification = new Notification();
			supervisorNotification.setAgentID(leaderAgentID); // 上層主管的AgentID
			supervisorNotification.setImmediately(true);

			supervisorNotification.setTitle(NotificationCode.Goal_Auto_Approve_Leader_Message_Title.toString());
			supervisorNotification.setBody(NotificationCode.Goal_Auto_Approve_Leader_Message_Desc.toString());
			supervisorNotification.setPushType(Notification.NotificationType.SINGLE);
			supervisorNotification.setMessage(supervisorMessage);

			JSONObject data = new JSONObject();
			data.put("agentList", agentList);

			supervisorNotification.setData(data);
			supervisorNotification.addLanguageText("agentID",agentListBuilder.toText());

			notificationsService.push(supervisorNotification);

		}

		logger.info("ReminderAgentDefaultGoal Finish");
    }
}
