package com.allianz.sd.core.jpa.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 */
@Entity

@Table(name="TW_LH_SD_Agency_Plan_Detail")
public class AgencyPlanDetail extends CreateInfo{

	@EmbeddedId()
    private AgencyPlanDetailIdentity identity = null;

	@Column(name = "SubAgentID")
	private String subAgentID;

	@Column(name = "SubAgentName")
	private String subAgentName = null;

	@Column(name = "SubAgentJobGrade")
	private String subAgentJobGrade = null;

	@Column(name = "CumulativeProc")
	private String cumulativeProc = null;

	@Column(name = "PerformanceCalcStartMonth")
	private Date performanceCalcStartMonth = null;

	@Column(name = "Anp")
	private Long anp = null;

	@Column(name = "Fyfc")
	private Long fyfc = null;

	@Column(name = "NewBusinessCase")
	private Long newBusinessCase = null;

	@Column(name = "Recruitment")
	private Long recruitment = null;

	@Column(name = "Manpower")
	private Long manpower = null;


	public AgencyPlanDetailIdentity getIdentity() {
		return identity;
	}

	public void setIdentity(AgencyPlanDetailIdentity identity) {
		this.identity = identity;
	}

	public String getSubAgentID() {
		return subAgentID;
	}

	public void setSubAgentID(String subAgentID) {
		this.subAgentID = subAgentID;
	}

	public String getSubAgentName() {
		return subAgentName;
	}

	public void setSubAgentName(String subAgentName) {
		this.subAgentName = subAgentName;
	}

	public String getSubAgentJobGrade() {
		return subAgentJobGrade;
	}

	public void setSubAgentJobGrade(String subAgentJobGrade) {
		this.subAgentJobGrade = subAgentJobGrade;
	}

	public String getCumulativeProc() {
		return cumulativeProc;
	}

	public void setCumulativeProc(String cumulativeProc) {
		this.cumulativeProc = cumulativeProc;
	}

	public Date getPerformanceCalcStartMonth() {
		return performanceCalcStartMonth;
	}

	public void setPerformanceCalcStartMonth(Date performanceCalcStartMonth) {
		this.performanceCalcStartMonth = performanceCalcStartMonth;
	}

	public Long getAnp() {
		return anp;
	}

	public void setAnp(Long anp) {
		this.anp = anp;
	}

	public Long getFyfc() {
		return fyfc;
	}

	public void setFyfc(Long fyfc) {
		this.fyfc = fyfc;
	}

	public Long getNewBusinessCase() {
		return newBusinessCase;
	}

	public void setNewBusinessCase(Long newBusinessCase) {
		this.newBusinessCase = newBusinessCase;
	}

	public Long getRecruitment() {
		return recruitment;
	}

	public void setRecruitment(Long recruitment) {
		this.recruitment = recruitment;
	}

	public Long getManpower() {
		return manpower;
	}

	public void setManpower(Long manpower) {
		this.manpower = manpower;
	}
}
