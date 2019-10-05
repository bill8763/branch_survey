package com.allianz.sd.core.jpa.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 */
@Entity

@Table(name="TW_LH_SD_Goal_Setting_Version")
public class GoalSettingVersion extends CreateUpdateInfo{
	
	@Id
    @Column(name = "GoalSettingSeq")
    @GeneratedValue(generator = "idSequence")
    @SequenceGenerator(name = "idSequence", sequenceName = "TW_LH_SD_Sequence_Goal_Setting_Version", allocationSize = 1)
    private Integer goalSettingSeq = null;
	
	@Column(name = "OrganizationalUnit")
    private String organizationalUnit = null;
	
    @Column(name = "AgentID")
    private String agentID = null;

    @Column(name = "DataYear")
    private Integer dataYear = null;
    
    @Column(name = "GoalVersion")
    private Integer goalVersion = null;

    @Column(name = "Status")
    private String status = null;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "StatusDate")
    private Date statusDate = null;
    
    @Column(name = "TopVersion")
    private String topVersion = null;
    
    @Column(name = "SigningSupervisor")
    private String signingSupervisor = null;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "SigningDeadline")
    private Date signingDeadline = null;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "GoalSubmitDate")
    private Date goalSubmitDate = null;
    
    @Column(name = "ActivityGoalBase")
    private String activityGoalBase = null;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "GoalSettingStartDate")
    private Date goalSettingStartDate = null;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PersonnelGoalApplicableYM")
    private Date personnelGoalApplicableYM = null;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TeamGoalApplicableYM")
    private Date teamGoalApplicableYM = null;

	@Column(name = "SupervisorComment")
	private String supervisorComment;

	@Column(name = "SubmitStatus")
	private String submitStatus;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DataTime")
    private Date dataTime = null;
    
    public Integer getGoalSettingSeq() {
		return goalSettingSeq;
	}

	public void setGoalSettingSeq(Integer goalSettingSeq) {
		this.goalSettingSeq = goalSettingSeq;
	}

	public String getOrganizationalUnit() {
		return organizationalUnit;
	}

	public void setOrganizationalUnit(String organizationalUnit) {
		this.organizationalUnit = organizationalUnit;
	}

	public String getAgentID() {
		return agentID;
	}

	public void setAgentID(String agentID) {
		this.agentID = agentID;
	}

	public Integer getDataYear() {
		return dataYear;
	}

	public void setDataYear(Integer dataYear) {
		this.dataYear = dataYear;
	}

	public Integer getGoalVersion() {
		return goalVersion;
	}

	public void setGoalVersion(Integer goalVersion) {
		this.goalVersion = goalVersion;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}

	public String getTopVersion() {
		return topVersion;
	}

	public void setTopVersion(String topVersion) {
		this.topVersion = topVersion;
	}

	public String getSigningSupervisor() {
		return signingSupervisor;
	}

	public void setSigningSupervisor(String signingSupervisor) {
		this.signingSupervisor = signingSupervisor;
	}

	public Date getSigningDeadline() {
		return signingDeadline;
	}

	public void setSigningDeadline(Date signingDeadline) {
		this.signingDeadline = signingDeadline;
	}

	public Date getGoalSubmitDate() {
		return goalSubmitDate;
	}

	public void setGoalSubmitDate(Date goalSubmitDate) {
		this.goalSubmitDate = goalSubmitDate;
	}

	public String getActivityGoalBase() {
		return activityGoalBase;
	}

	public void setActivityGoalBase(String activityGoalBase) {
		this.activityGoalBase = activityGoalBase;
	}

	public Date getGoalSettingStartDate() {
		return goalSettingStartDate;
	}

	public void setGoalSettingStartDate(Date goalSettingStartDate) {
		this.goalSettingStartDate = goalSettingStartDate;
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

	public String getSupervisorComment() {
		return supervisorComment;
	}

	public void setSupervisorComment(String supervisorComment) {
		this.supervisorComment = supervisorComment;
	}

	public String getSubmitStatus() {
		return submitStatus;
	}

	public void setSubmitStatus(String submitStatus) {
		this.submitStatus = submitStatus;
	}

	public Date getDataTime() {
		return dataTime;
	}

	public void setDataTime(Date dataTime) {
		this.dataTime = dataTime;
	}

}
