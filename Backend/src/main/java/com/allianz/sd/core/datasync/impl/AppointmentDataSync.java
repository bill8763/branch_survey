package com.allianz.sd.core.datasync.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.allianz.sd.core.InstanceCode;
import com.allianz.sd.core.service.*;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.allianz.sd.core.api.model.Appointment;
import com.allianz.sd.core.api.model.SynchDetail;
import com.allianz.sd.core.datasync.CalendarDataSyncListener;
import com.allianz.sd.core.datasync.DataMatch;
import com.allianz.sd.core.datasync.DataSync;
import com.allianz.sd.core.jpa.bean.DataCategory;
import com.allianz.sd.core.jpa.model.Calendar;
import com.allianz.sd.core.jpa.repository.CalendarRepository;
import com.allianz.sd.core.progress.IsCalcSchedulePoint;
import com.allianz.sd.core.service.bean.AgentDailyAppDateType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class AppointmentDataSync implements DataSync , DataMatch {

    private Logger logger = LoggerFactory.getLogger(AppointmentDataSync.class);
    private String agentID = null;

    @Autowired
    private CalendarRepository calendarRepository = null;

    @Autowired
    private AgentUpdateVersionService agentUpdateVersionService = null;

    @Autowired
    private DateService dateService = null;

    @Autowired
    private OrganizationService organizationService = null;

    @Autowired
    private BeanFactory beanFactory;
    
    @Autowired
    private ActivityService activityService;

    @Autowired
    private UtilsService utilsService;

    @Override
    public boolean isDifference(Object dbData, Object pushData) {
        Calendar calendar = (Calendar) dbData;
        Appointment appointment = (Appointment) pushData;

        Date pushDate = dateService.toDateTimeFormatDate(appointment.getSynchDetail().getLastUpdateDateTimeBackend());
        return calendar.getDataTime().getTime() != pushDate.getTime();
    }

    @Override
    public Map findPullData(Date appSyncDate) {
        Map<Integer, Calendar> map = new HashedMap<>();

        List<Integer> idList = agentUpdateVersionService.getAgentLastSyncData(agentID, DataCategory.CALENDAR,appSyncDate);

        for(Integer calendarID : idList) {

            Optional<Calendar> calendarOptional = calendarRepository.findById(calendarID);
            Calendar calendar = calendarOptional.orElse(null);

            map.put(calendarID,calendar);
        }

        return map;
    }

    @Override
    public Object findPushData(Object data) {
        Appointment appointment = (Appointment) data;
        Optional<Calendar> calendarOptional = calendarRepository.findById(appointment.getAppointmentId().intValue());

        return calendarOptional.orElse(null);
    }

    @Override
    public Object data2SyncObj(Object data) {
        Calendar calendar = (Calendar) data;

        SynchDetail synchDetail = new SynchDetail();
        synchDetail.setToDelete(false);
        synchDetail.setLastUpdateDateTimeBackend(dateService.toDateTimeFormatString(calendar.getDataTime()));

        List<String> alertTimes = new ArrayList<String>();
        if("Y".equalsIgnoreCase(calendar.getAlert())) {
            if(StringUtils.isNotEmpty(calendar.getAlert1())) alertTimes.add(calendar.getAlert1());
            if(StringUtils.isNotEmpty(calendar.getAlert2())) alertTimes.add(calendar.getAlert2());
            if(StringUtils.isNotEmpty(calendar.getAlert3())) alertTimes.add(calendar.getAlert3());
        }

        Appointment appointment = new Appointment();
        appointment.setAppointmentId(new BigDecimal(calendar.getCalendarId()));
        appointment.setName(calendar.getTitle());
        appointment.setMeetingLocation(calendar.getLocation());
        appointment.setAppointmentType(calendar.getCalendarType());
        appointment.setAllDay("Y".equalsIgnoreCase(calendar.getAllDay()));
        appointment.setStartDateTime(dateService.toDateTimeFormatString(calendar.getStartTime()));
        appointment.setEndDateTime(dateService.toDateTimeFormatString(calendar.getEndTime()));
        appointment.setAlertTimes(alertTimes);
        appointment.setPersonId(null);
        appointment.setDescription(calendar.getRemark());
        appointment.setIsChangeable(!"OPUS".equalsIgnoreCase(calendar.getDataSource()));
        appointment.setSynchDetail(synchDetail);

        Object listenerBean = beanFactory.getBean(InstanceCode.CalendarExtension);
        if(utilsService.isNotEmptyBean(listenerBean)) {
            CalendarDataSyncListener listener = (CalendarDataSyncListener) listenerBean;
            listener.onPullData(calendar,appointment);
        }

        return appointment;
    }

    @Override
    public Integer getIdentityID(Object data) {
        Calendar calendar = (Calendar) data;
        return calendar.getCalendarId();
    }

    @Override
    public boolean isDeleteData(Object data) {
        Appointment appointment = (Appointment) data;
        return appointment.getSynchDetail().isToDelete();
    }

    @Override
    public void onDeleteData(Object data) {
        Calendar calendar = (Calendar) data;
        this.calendarRepository.deleteExtensionData(calendar.getCalendarId(),calendar.getOrganizationalUnit());
        this.calendarRepository.delete(calendar);

    }

    @Override
    public Object onSaveData(Object dbData , Object data) {
    	IsCalcSchedulePoint isCalcSchedulePoint = (IsCalcSchedulePoint)beanFactory.getBean(InstanceCode.CalSchedulePointRule);
        Calendar calendar = (Calendar) dbData;
        boolean exist = calendar != null;
        if(!exist) {
            calendar = new Calendar();
            calendar.setCreateBy(agentID + "");
        }
        Appointment appointment = (Appointment) data;

        List<String> alertTimes = appointment.getAlertTimes();

        calendar.setCalendarId(appointment.getAppointmentId().intValue());
        calendar.setOrganizationalUnit(organizationService.getOrganizationalUnit());
        calendar.setAgentId(agentID);
        calendar.setTitle(appointment.getName());
        calendar.setLocation(appointment.getMeetingLocation());
        calendar.setAlert(alertTimes.size() != 0 ? "Y" : "N");
        calendar.setAlert1(alertTimes.size() >= 1 ? alertTimes.get(0) : null);
        calendar.setAlert2(alertTimes.size() >= 2 ? alertTimes.get(1) : null);
        calendar.setAlert3(alertTimes.size() >= 3 ? alertTimes.get(2) : null);
        calendar.setCalendarType(appointment.getAppointmentType());
        calendar.setDataSource(appointment.isIsChangeable() ? "APP" : "OPUS");
        calendar.setRemark(appointment.getDescription());
        calendar.setUpdateBy(agentID + "");

        calendar.setDataTime(dateService.getTodayDate());
        calendar.setAllDay(appointment.isAllDay() ? "Y" : "N");
        calendar.setStartTime(dateService.toDateTimeFormatDate(appointment.getStartDateTime()));
        calendar.setEndTime(dateService.toDateTimeFormatDate(appointment.getEndDateTime()));

        calendarRepository.save(calendar);
        boolean isAdd = isCalcSchedulePoint.isCalcToPoint(calendar);
        if(!exist) {
            calendarRepository.saveExtension(calendar.getCalendarId(), calendar.getOrganizationalUnit());

//            String dataDate = dateService.toDateString(dateService.getTodayDate(),"yyyy-MM-dd");
            String clientTime = appointment.getSynchDetail().getClientTime();
            String agentID = calendar.getAgentId();
            String organizationalUnit = calendar.getOrganizationalUnit();

            if(isAdd) {
            	//add to Agent_Daily_App_Data
                activityService.UpdateFindSchedule(agentID, organizationalUnit, AgentDailyAppDateType.SCHEDULE, 1,clientTime, false);
            
                //add to PersonalProgress
                activityService.updatePersonalProgress(agentID, organizationalUnit, 1, AgentDailyAppDateType.SCHEDULE,clientTime);
            }
            
            
        }

        Object listenerBean = beanFactory.getBean(InstanceCode.CalendarExtension);
        if(utilsService.isNotEmptyBean(listenerBean)) {
            CalendarDataSyncListener listener = (CalendarDataSyncListener) listenerBean;
            listener.postSave(calendar,appointment);
        }

        return calendar;
    }

    @Override
    public DataMatch getDataMatchPolicy() {
        return this;
    }

	@Override
	public void setAgentID(String agentID) {
		this.agentID = agentID;
	}

}
