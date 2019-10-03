package com.allianz.sd.core.jpa.model;

import javax.persistence.*;
import java.util.Date;

/**
 */
@Entity

@Table(name="TW_LH_SD_CISL_Goal_Setting")
public class CISLGoalSetting extends CreateUpdateInfo{
	
	@EmbeddedId()
    private AgentYearDataIdentity identity = null;

    
    @Column(name = "GoalSettingFlag")
    private String goalSettingFlag = null;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "GoalSettingStartDate")
    private Date goalSettingStartDate = null;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "GoalSettingDeadline")
    private Date goalSettingDeadline = null;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PersonnelGoalApplicableYM")
    private Date personnelGoalApplicableYM = null;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TeamGoalApplicableYM")
    private Date teamGoalApplicableYM = null;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DataTime")
    private Date dataTime = null;

	public AgentYearDataIdentity getIdentity() {
		return identity;
	}

	public void setIdentity(AgentYearDataIdentity identity) {
		this.identity = identity;
	}

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

	public Date getGoalSettingDeadline() {
		return goalSettingDeadline;
	}

	public void setGoalSettingDeadline(Date goalSettingDeadline) {
		this.goalSettingDeadline = goalSettingDeadline;
	}

	public Date getPersonnelGoalApplicableYM() {
		return personnelGoalApplicableYM;
	}

	public void setPersonnelGoalApplicableYM(Date personnelGoalApplicableYM) {
		this.personnelGoalApplicableYM = personnelGoalApplicableYM;
	}

	public Date getTeamGoalApplicableYM() {
		return teamGoalApplicableYM;
	}

	public void setTeamGoalApplicableYM(Date teamGoalApplicableYM) {
		this.teamGoalApplicableYM = teamGoalApplicableYM;
	}

	public Date getDataTime() {
		return dataTime;
	}

	public void setDataTime(Date dataTime) {
		this.dataTime = dataTime;
	}
    
    
}
