package com.allianz.sd.core.jpa.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 12:20
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="TW_LH_SD_Calendar")
public class Calendar extends CreateUpdateInfo{

    @Column(name = "OrganizationalUnit")
    private String organizationalUnit = null;

    @Id
    @Column(name = "CalendarID")
    private Integer calendarId = null;

    @Column(name = "Title")
    private String title = null;

    @Column(name = "Location")
    private String location = null;

    @Column(name = "CalendarType")
    private String calendarType = null;

    @Column(name = "IsAllDay")
    private String isAllDay = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "StartTime")
    private Date startTime = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EndTime")
    private Date endTime = null;

    @Column(name = "IsAlert")
    private String isAlert = null;

    @Column(name = "Alert1")
    private String alert1 = null;

    @Column(name = "Alert2")
    private String alert2 = null;

    @Column(name = "Alert3")
    private String alert3 = null;

    @Column(name = "Remark")
    private String remark = null;

    @Column(name = "AgentID")
    private String agentId = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DataTime")
    private Date dataTime = null;

    @Column(name = "DataSource")
    private String dataSource = null;

    public String getOrganizationalUnit() {
        return organizationalUnit;
    }

    public void setOrganizationalUnit(String organizationalUnit) {
        this.organizationalUnit = organizationalUnit;
    }

    public Integer getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(Integer calendarId) {
        this.calendarId = calendarId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCalendarType() {
        return calendarType;
    }

    public void setCalendarType(String calendarType) {
        this.calendarType = calendarType;
    }

    public String getAllDay() {
        return isAllDay;
    }

    public void setAllDay(String allDay) {
        isAllDay = allDay;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getAlert() {
        return isAlert;
    }

    public void setAlert(String alert) {
        isAlert = alert;
    }

    public String getAlert1() {
        return alert1;
    }

    public void setAlert1(String alert1) {
        this.alert1 = alert1;
    }

    public String getAlert2() {
        return alert2;
    }

    public void setAlert2(String alert2) {
        this.alert2 = alert2;
    }

    public String getAlert3() {
        return alert3;
    }

    public void setAlert3(String alert3) {
        this.alert3 = alert3;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public Date getDataTime() {
        return dataTime;
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

}
