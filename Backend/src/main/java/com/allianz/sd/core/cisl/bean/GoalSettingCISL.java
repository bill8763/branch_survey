package com.allianz.sd.core.cisl.bean;

import java.util.Date;

public class GoalSettingCISL extends ProcessData {
	
	private String goalSettingFlag = null;
	
	private Date goalSettingStartDate = null;
	
	private Date goalSettingDeadLine = null;
	
	private Date personalGoalApplicableYM = null;
	
	private Date teamGoalApplicableDate = null;
	
	private Date dataTime = null;

	public String getGoalSettingFlag() {
		return goalSettingFlag;
	}

	public void setGoalSettingFlag(String goalSettingFlag) {
		this.goalSettingFlag = goalSettingFlag;
	}

	public Date getGoalSettingStartDate() {
		return goalSettingStartDate;
	}

	public void setGoalSettingStartDate(Date goalSettingStartDate) {
		this.goalSettingStartDate = goalSettingStartDate;
	}

	public Date getGoalSettingDeadLine() {
		return goalSettingDeadLine;
	}

	public void setGoalSettingDeadLine(Date goalSettingDeadLine) {
		this.goalSettingDeadLine = goalSettingDeadLine;
	}

	public Date getPersonalGoalApplicableYM() {
		return personalGoalApplicableYM;
	}

	public void setPersonalGoalApplicableYM(Date personalGoalApplicableYM) {
		this.personalGoalApplicableYM = personalGoalApplicableYM;
	}

	public Date getTeamGoalApplicableDate() {
		return teamGoalApplicableDate;
	}

	public void setTeamGoalApplicableDate(Date teamGoalApplicableDate) {
		this.teamGoalApplicableDate = teamGoalApplicableDate;
	}

	public Date getDataTime() {
		return dataTime;
	}

	public void setDataTime(Date dataTime) {
		this.dataTime = dataTime;
	}
	
}
