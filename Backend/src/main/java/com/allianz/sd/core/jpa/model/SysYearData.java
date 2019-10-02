package com.allianz.sd.core.jpa.model;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/3/16
 * Time: 下午 5:12
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="TW_LH_SD_Sys_Year_Data")
public class SysYearData extends CreateUpdateInfo{
	@EmbeddedId()
	private SysYearDataIdentity identity = null;
	
    @Column(name = "AppDisplayStartDate")
    private Date appDisplayStartDate = null;
    
    @Column(name = "AppDisplayEndDate")
    private Date appDisplayEndDate = null;
    
    @Column(name = "GoalSettingLastDate")
    private Date goalSettingLastDate = null;
    
    @Column(name = "FyfcCovertAnpRate")
    private Double fyfcCovertAnpRate = null;
    
    @Column(name = "ProgressDayPointsLimit")
    private Integer progressDayPointsLimit = null;
    
    @Column(name = "GoalSettingActivityProcMode")
    private String goalSettingActivityProcMode = null;
    
    @Column(name = "GoalAndPlanDifferenceLimit")
    private Double goalAndPlanDifferenceLimit = null;
    
    @Column(name = "FindConvertPointBase")
    private Double findConvertPointBase = null;
    
    @Column(name = "ScheduleConvertPointBase")
    private Double scheduleConvertPointBase = null;
    
    @Column(name = "MeetConvertPointBase")
    private Double meetConvertPointBase = null;
    
    @Column(name = "SubmitConvertPointBase")
    private Double submitConvertPointBase = null;
    
    @Column(name = "InforceConvertPointBase")
    private Double inforceConvertPointBase = null;
    
    @Column(name = "InforceConvertFindRate")
    private Double inforceConvertFindRate = null;
    
    @Column(name = "InforceConvertScheduleRate")
    private Double inforceConvertScheduleRate = null;
    
    @Column(name = "InforceConvertMeetRate")
    private Double inforceConvertMeetRate = null;
    
    @Column(name = "InforceConvertSubmitRate")
    private Double inforceConvertSubmitRate = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DataTime")
    private Date DataTime = null;

    @Column(name = "ProgressBarControlMode")
    private String progressBarControlMode = null;
    
    @ManyToOne(cascade = CascadeType.DETACH, optional = false)
	@JoinColumn(name = "AppSysCtrl", insertable = false, updatable = false )
    @JoinColumn(name = "OrganizationalUnit", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private SysData sysData = null;

	public SysYearDataIdentity getIdentity() {
		return identity;
	}

	public void setIdentity(SysYearDataIdentity identity) {
		this.identity = identity;
	}

	public Date getAppDisplayStartDate() {
		return appDisplayStartDate;
	}

	public void setAppDisplayStartDate(Date appDisplayStartDate) {
		this.appDisplayStartDate = appDisplayStartDate;
	}

	public Date getAppDisplayEndDate() {
		return appDisplayEndDate;
	}

	public void setAppDisplayEndDate(Date appDisplayEndDate) {
		this.appDisplayEndDate = appDisplayEndDate;
	}

	public Date getGoalSettingLastDate() {
		return goalSettingLastDate;
	}

	public void setGoalSettingLastDate(Date goalSettingLastDate) {
		this.goalSettingLastDate = goalSettingLastDate;
	}

	public Double getFyfcCovertAnpRate() {
		return fyfcCovertAnpRate;
	}

	public void setFyfcCovertAnpRate(Double fyfcCovertAnpRate) {
		this.fyfcCovertAnpRate = fyfcCovertAnpRate;
	}

	public Integer getProgressDayPointsLimit() {
		return progressDayPointsLimit;
	}

	public void setProgressDayPointsLimit(Integer progressDayPointsLimit) {
		this.progressDayPointsLimit = progressDayPointsLimit;
	}

	public String getGoalSettingActivityProcMode() {
		return goalSettingActivityProcMode;
	}

	public void setGoalSettingActivityProcMode(String goalSettingActivityProcMode) {
		this.goalSettingActivityProcMode = goalSettingActivityProcMode;
	}

	public Double getGoalAndPlanDifferenceLimit() {
		return goalAndPlanDifferenceLimit;
	}

	public void setGoalAndPlanDifferenceLimit(Double goalAndPlanDifferenceLimit) {
		this.goalAndPlanDifferenceLimit = goalAndPlanDifferenceLimit;
	}

	public Double getFindConvertPointBase() {
		return findConvertPointBase;
	}

	public void setFindConvertPointBase(Double findConvertPointBase) {
		this.findConvertPointBase = findConvertPointBase;
	}

	public Double getScheduleConvertPointBase() {
		return scheduleConvertPointBase;
	}

	public void setScheduleConvertPointBase(Double scheduleConvertPointBase) {
		this.scheduleConvertPointBase = scheduleConvertPointBase;
	}

	public Double getMeetConvertPointBase() {
		return meetConvertPointBase;
	}

	public void setMeetConvertPointBase(Double meetConvertPointBase) {
		this.meetConvertPointBase = meetConvertPointBase;
	}

	public Double getSubmitConvertPointBase() {
		return submitConvertPointBase;
	}

	public void setSubmitConvertPointBase(Double submitConvertPointBase) {
		this.submitConvertPointBase = submitConvertPointBase;
	}

	public Double getInforceConvertPointBase() {
		return inforceConvertPointBase;
	}

	public void setInforceConvertPointBase(Double inforceConvertPointBase) {
		this.inforceConvertPointBase = inforceConvertPointBase;
	}

	public Double getInforceConvertFindRate() {
		return inforceConvertFindRate;
	}

	public void setInforceConvertFindRate(Double inforceConvertFindRate) {
		this.inforceConvertFindRate = inforceConvertFindRate;
	}

	public Double getInforceConvertScheduleRate() {
		return inforceConvertScheduleRate;
	}

	public void setInforceConvertScheduleRate(Double inforceConvertScheduleRate) {
		this.inforceConvertScheduleRate = inforceConvertScheduleRate;
	}

	public Double getInforceConvertMeetRate() {
		return inforceConvertMeetRate;
	}

	public void setInforceConvertMeetRate(Double inforceConvertMeetRate) {
		this.inforceConvertMeetRate = inforceConvertMeetRate;
	}

	public Double getInforceConvertSubmitRate() {
		return inforceConvertSubmitRate;
	}

	public void setInforceConvertSubmitRate(Double inforceConvertSubmitRate) {
		this.inforceConvertSubmitRate = inforceConvertSubmitRate;
	}

	public Date getDataTime() {
		return DataTime;
	}

	public void setDataTime(Date dataTime) {
		DataTime = dataTime;
	}

	public String getProgressBarControlMode() {
		return progressBarControlMode;
	}

	public void setProgressBarControlMode(String progressBarControlMode) {
		this.progressBarControlMode = progressBarControlMode;
	}

	public SysData getSysData() {
		return sysData;
	}

	public void setSysData(SysData sysData) {
		this.sysData = sysData;
	}
    

    
   
}
