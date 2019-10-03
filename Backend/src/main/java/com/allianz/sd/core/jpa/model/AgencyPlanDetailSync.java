package com.allianz.sd.core.jpa.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 */
@Entity

@Table(name="TW_LH_SD_Agency_Plan_Detail_Sync")
public class AgencyPlanDetailSync extends CreateInfo{

	@EmbeddedId()
    private AgencyPlanDetailSyncIdentity identity = null;

    @Column(name = "SubordinateAgentID")
    private String subordinateAgentID = null;

    @Column(name = "SubordinateAgentAgentName")
    private String subordinateAgentAgentName = null;

    @Column(name = "SubordinateAgentJobGrade")
    private String subordinateAgentJobGrade = null;

    @Column(name = "IsApprove")
    private String isApprove = null;

    @Column(name = "IsDrilldown")
    private String IsDrilldown = null;

    @Column(name = "Goal")
    private Long goal = null;

    @Column(name = "Forecast")
    private Long forecast = null;

    @Column(name = "Actual")
    private Long actual = null;

    @Column(name = "Plan")
    private Long plan = null;

    @Column(name = "Manpower")
    private Long manpower = null;

    @Column(name = "Recruitment")
    private Long recruitment = null;

    @Column(name = "CaseCount")
    private Long caseCount = null;

    @Column(name = "PerCase")
    private Long perCase = null;

	public AgencyPlanDetailSyncIdentity getIdentity() {
		return identity;
	}

	public void setIdentity(AgencyPlanDetailSyncIdentity identity) {
		this.identity = identity;
	}

	public String getSubordinateAgentID() {
		return subordinateAgentID;
	}

	public void setSubordinateAgentID(String subordinateAgentID) {
		this.subordinateAgentID = subordinateAgentID;
	}

	public String getSubordinateAgentAgentName() {
		return subordinateAgentAgentName;
	}

	public void setSubordinateAgentAgentName(String subordinateAgentAgentName) {
		this.subordinateAgentAgentName = subordinateAgentAgentName;
	}

	public String getSubordinateAgentJobGrade() {
		return subordinateAgentJobGrade;
	}

	public void setSubordinateAgentJobGrade(String subordinateAgentJobGrade) {
		this.subordinateAgentJobGrade = subordinateAgentJobGrade;
	}

	public String getIsApprove() {
		return isApprove;
	}

	public void setIsApprove(String isApprove) {
		this.isApprove = isApprove;
	}

	public String getIsDrilldown() {
		return IsDrilldown;
	}

	public void setIsDrilldown(String isDrilldown) {
		IsDrilldown = isDrilldown;
	}

	public Long getGoal() {
		return goal;
	}

	public void setGoal(Long goal) {
		this.goal = goal;
	}

	public Long getForecast() {
		return forecast;
	}

	public void setForecast(Long forecast) {
		this.forecast = forecast;
	}

	public Long getActual() {
		return actual;
	}

	public void setActual(Long actual) {
		this.actual = actual;
	}

	public Long getPlan() {
		return plan;
	}

	public void setPlan(Long plan) {
		this.plan = plan;
	}

	public Long getManpower() {
		return manpower;
	}

	public void setManpower(Long manpower) {
		this.manpower = manpower;
	}

	public Long getRecruitment() {
		return recruitment;
	}

	public void setRecruitment(Long recruitment) {
		this.recruitment = recruitment;
	}

	public Long getCaseCount() {
		return caseCount;
	}

	public void setCaseCount(Long caseCount) {
		this.caseCount = caseCount;
	}

	public Long getPerCase() {
		return perCase;
	}

	public void setPerCase(Long perCase) {
		this.perCase = perCase;
	}
}
