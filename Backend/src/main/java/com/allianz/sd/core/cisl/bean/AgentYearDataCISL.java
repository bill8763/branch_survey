package com.allianz.sd.core.cisl.bean;

import java.util.Date;

public class AgentYearDataCISL extends ProcessData {
	
	private String appUseMode = null;
	
	private String appSysCtrl = null;
	
	private String performanceTable = null;
	
	private String jobGrade = null;
	
	private Date currentJobOBMonth = null;
	
	private Integer currentJobSeniorityMonth = null;
	
	private String goalSigningSupervisor = null;
	
	private String performanceType = null;
	
	private Integer initialPrecaseFyfc = null;
	
	private Date dataTime = null;
	
	public String getJobGrade() {
		return jobGrade;
	}

	public void setJobGrade(String jobGrade) {
		this.jobGrade = jobGrade;
	}

	public String getAppUseMode() {
		return appUseMode;
	}

	public void setAppUseMode(String appUseMode) {
		this.appUseMode = appUseMode;
	}

	public String getAppSysCtrl() {
		return appSysCtrl;
	}

	public void setAppSysCtrl(String appSysCtrl) {
		this.appSysCtrl = appSysCtrl;
	}

	public String getPerformanceTable() {
		return performanceTable;
	}

	public void setPerformanceTable(String performanceTable) {
		this.performanceTable = performanceTable;
	}

	public Date getCurrentJobOBMonth() {
		return currentJobOBMonth;
	}

	public void setCurrentJobOBMonth(Date currentJobOBMonth) {
		this.currentJobOBMonth = currentJobOBMonth;
	}

	public Integer getCurrentJobSeniorityMonth() {
		return currentJobSeniorityMonth;
	}

	public void setCurrentJobSeniorityMonth(Integer currentJobSeniorityMonth) {
		this.currentJobSeniorityMonth = currentJobSeniorityMonth;
	}

	public String getGoalSigningSupervisor() {
		return goalSigningSupervisor;
	}

	public void setGoalSigningSupervisor(String goalSigningSupervisor) {
		this.goalSigningSupervisor = goalSigningSupervisor;
	}

	public String getPerformanceType() {
		return performanceType;
	}

	public void setPerformanceType(String performanceType) {
		this.performanceType = performanceType;
	}

	public Integer getInitialPrecaseFyfc() {
		return initialPrecaseFyfc;
	}

	public void setInitialPrecaseFyfc(Integer initialPrecaseFyfc) {
		this.initialPrecaseFyfc = initialPrecaseFyfc;
	}

	public Date getDataTime() {
		return dataTime;
	}

	public void setDataTime(Date dataTime) {
		this.dataTime = dataTime;
	}

}
