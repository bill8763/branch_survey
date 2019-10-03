package com.allianz.sd.core.jpa.model;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.util.Date;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/3/16
 * Time: 下午 5:12
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="TW_LH_SD_Agent_Year_Data")
public class AgentYearData extends CreateUpdateInfo{
	
	@EmbeddedId()
    private AgentYearDataIdentity identity = null;

    @Column(name = "AppUseMode")
    private String appUseMode = null;

    @Column(name = "AppSysCtrl")
    private String appSysCtrl = null;
    
    @Column(name = "PerformanceTable")
    private String performanceTable = null;
    
    @Column(name = "JobGrade")
    private String jobGrade = null;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "CurrentJobOBMonth")
    private Date currentJobOBMonth = null;

    @Column(name = "CurrentJobSeniorityMonth")
    private Integer currentJobSeniorityMonth = null;
    
    @Column(name = "GoalSigningSupervisor")
    private String goalSigningSupervisor = null;

    @Column(name = "PersonnelPerformanceType")
    private String personnelPerformanceType = null;
    
    @Column(name = "TeamPerformanceType")
    private String teamPerformanceType = null;
    
    @Column(name = "InitialPreCaseFyfc")
    private Integer initialPreCaseFyfc = null;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DataTime")
	private Date dataTime = null;

    @ManyToOne(cascade = CascadeType.DETACH , optional = false)
	@JoinColumn(name = "AgentID",insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private AgentData agentData = null;


	public AgentYearDataIdentity getIdentity() {
		return identity;
	}

	public void setIdentity(AgentYearDataIdentity identity) {
		this.identity = identity;
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

	public String getJobGrade() {
		return jobGrade;
	}

	public void setJobGrade(String jobGrade) {
		this.jobGrade = jobGrade;
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

	public String getPersonnelPerformanceType() {
		return personnelPerformanceType;
	}

	public void setPersonnelPerformanceType(String personnelPerformanceType) {
		this.personnelPerformanceType = personnelPerformanceType;
	}

	public String getTeamPerformanceType() {
		return teamPerformanceType;
	}

	public void setTeamPerformanceType(String teamPerformanceType) {
		this.teamPerformanceType = teamPerformanceType;
	}

	public Integer getInitialPreCaseFyfc() {
		return initialPreCaseFyfc;
	}

	public void setInitialPreCaseFyfc(Integer initialPreCaseFyfc) {
		this.initialPreCaseFyfc = initialPreCaseFyfc;
	}

	public Date getDataTime() {
		return dataTime;
	}

	public void setDataTime(Date dataTime) {
		this.dataTime = dataTime;
	}

	public AgentData getAgentData() {
		return agentData;
	}

	public void setAgentData(AgentData agentData) {
		this.agentData = agentData;
	}

	@Override
	public String toString() {
		return this.getClass().getName() + identity + ", appUseMode=" + appUseMode + ", appSysCtrl=" + appSysCtrl
				+ ", performanceTable=" + performanceTable + ", jobGrade=" + jobGrade + ", currentJobOBMonth="
				+ currentJobOBMonth + ", currentJobSeniorityMonth=" + currentJobSeniorityMonth
				+ ", goalSigningSupervisor=" + goalSigningSupervisor + ", personnelPerformanceType="
				+ personnelPerformanceType + ", teamPerformanceType=" + teamPerformanceType + ", initialPreCaseFyfc="
				+ initialPreCaseFyfc + ", dataTime=" + dataTime + ", agentData=" + agentData + "]";
	}
	
	

}
