package com.allianz.sd.core.schedule.job;

import com.allianz.sd.core.jpa.bean.DataCategory;
import com.allianz.sd.core.jpa.model.Calendar;
import com.allianz.sd.core.jpa.model.Message;
import com.allianz.sd.core.jpa.repository.AgentDeviceRepository;
import com.allianz.sd.core.jpa.repository.CalendarRepository;
import com.allianz.sd.core.notification.Notification;
import com.allianz.sd.core.service.CodeService;
import com.allianz.sd.core.service.DateService;
import com.allianz.sd.core.service.NotificationsService;
import com.allianz.sd.core.service.bean.MessageType;
import com.allianz.sd.core.service.bean.NotificationCode;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 2:50
 * To change this template use File | Settings | File Templates.
 */

/**
 * check calendar not all day event reminder
 */
public class CalendarReminderByMin extends QuartzJobBean {

	private static final Logger logger = LoggerFactory.getLogger(CalendarReminderByMin.class);

	@Autowired
	private CalendarRepository calendarRepository;

	@Autowired
	private AgentDeviceRepository agentDeviceRepository;

	@Autowired
	private CodeService codeService;

	@Autowired
	private DateService dateService;

	@Autowired
	private NotificationsService notificationsService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		logger.info(">>>>>>>>>>>>>>>>CalendarReminderByMin Start<<<<<<<<<<<<<<<<<<<<<");

		String today = dateService.getTodayString();
		today = today.substring(0, 12) + "00";

		java.util.Date now = dateService.parseDateString2Date(today);
		logger.info("now = " + now + ",today = " + today);
		List<Calendar> calendarList = calendarRepository.findByReminder(new Date(now.getTime()), "N");

		logger.info("calendarList By min size = " + calendarList.size());

		Map<String, List<Calendar>> calendarMap = new LinkedHashMap<String, List<Calendar>>();

		for (Calendar calendar : calendarList) {
//            logger.trace(calendar.toString());

			String agentId = calendar.getAgentId();
			Date startTime = calendar.getStartTime();
			String alert1 = calendar.getAlert1();
			String alert2 = calendar.getAlert2();

			String[] alertArray = new String[] { alert1, alert2 };
			for (String alert : alertArray) {
//                logger.trace("alert = " + alert);

				if (StringUtils.isNotEmpty(alert)) {
					JSONObject jsonObject = codeService.getCodeArguments("Calendar_RemindTime", alert);

					if (jsonObject != null) {
						int min = jsonObject.getInt("min");

						Date afterDate = dateService.addDate(startTime, java.util.Calendar.MINUTE, min * -1);
						String afterDateString = dateService.toDateString(afterDate);

						logger.trace("alert = " + alert + ",min = " + min + ",subject = " + calendar.getTitle()
								+ ",afterDate = " + afterDate + ",afterDateString = " + afterDateString);

						// if reminder time is now
						if (today.substring(0, 12).equalsIgnoreCase(afterDateString.substring(0, 12))) {
							logger.info("[" + calendar.getTitle() + "]" + "overtime ready to push notifycation");

							// List<Calendar>
							// 塞map <agentID,List<Calendar>>
							List<Calendar> calAgentList = calendarMap.getOrDefault(agentId, new ArrayList<Calendar>());
							calAgentList.add(calendar);
							calendarMap.put(agentId, calAgentList);

						}
					} else {
						logger.trace("alert1 arguments not JSON format");
					}
				}
			}

		}

		for (String key : calendarMap.keySet()) {
			String body = "";
			List<Calendar> calenList = calendarMap.get(key);
			int calendarSize = calenList.size();

			JSONArray dataArray = new JSONArray();
			for (Calendar calendar : calenList) {
				body += calendar.getTitle() + "@" + dateService.toDateString(calendar.getStartTime(), "yyyy-MM-dd")
						+ "~" + dateService.toDateString(calendar.getEndTime(), "yyyy-MM-dd") + "\n" + calendar.getLocation()
						+ "\n";
				JSONObject calendarTypeArgs = codeService.getCodeArguments("Calendar_Type", calendar.getCalendarType());
				if (calendarTypeArgs != null) {

					JSONObject data = new JSONObject();
					JSONObject color = new JSONObject();
					color.put("primary", calendarTypeArgs.getString("color"));
					data.put("color", color);
					data.put("title", calendar.getTitle());
					data.put("location", calendar.getLocation());
					data.put("start", calendar.getStartTime().getTime());
					data.put("end", calendar.getEndTime().getTime());
					data.put("appointments", calendarSize);
					dataArray.put(data);
				} else {
					logger.trace("CalendarType arguments not JSON format");
				}
			}
			Notification notification = new Notification();
			notification.setAgentID(key);
			notification.setImmediately(true);
			notification.setTitle(NotificationCode.You_Have_N_Appointment.toString());
			notification.setBody(body);
			notification.setPushType(Notification.NotificationType.SINGLE);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("calendars", dataArray);

			notification.setData(jsonObject);
			notification.addLanguageText("appointments", String.valueOf(calendarSize));

			// set notification category
			Message message = new Message();
			message.setMessageCategory(DataCategory.CALENDAR);
			message.setMessageType(MessageType.ReminderEvent.toString());
			message.setArguments(jsonObject.toString());
			notification.setMessage(message);

			notificationsService.push(notification);

		}

		logger.info(">>>>>>>>>>>>>>>>CalendarReminderByMin End<<<<<<<<<<<<<<<<<<<<<");
	}
}
