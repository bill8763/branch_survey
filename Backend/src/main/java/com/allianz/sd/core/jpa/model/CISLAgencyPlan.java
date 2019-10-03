package com.allianz.sd.core.jpa.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 */
@Entity

@Table(name="TW_LH_SD_CISL_Agency_Plan")
public class CISLAgencyPlan extends CreateInfo{

	@EmbeddedId()
    private AgencyPlanIdentity identity = null;

	@Column(name = "AgentName")
	private String agentName = null;

	@Column(name = "JobGrade")
	private String jobGrade = null;

	@Column(name = "ANP")
	private long  anp;

	@Column(name = "FYFC")
	private long  fyfc;
	
	@Column(name = "PerformanceCalcStartMonth")
	private Date performanceCalcStartMonth = null;

	public Date getPerformanceCalcStartMonth() {
		return performanceCalcStartMonth;
	}

	public void setPerformanceCalcStartMonth(Date performanceCalcStartMonth) {
		this.performanceCalcStartMonth = performanceCalcStartMonth;
	}

	public AgencyPlanIdentity getIdentity() {
		return identity;
	}

	public void setIdentity(AgencyPlanIdentity identity) {
		this.identity = identity;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getJobGrade() {
		return jobGrade;
	}

	public void setJobGrade(String jobGrade) {
		this.jobGrade = jobGrade;
	}

	public long getAnp() {
		return anp;
	}

	public void setAnp(long anp) {
		this.anp = anp;
	}

	public long getFyfc() {
		return fyfc;
	}

	public void setFyfc(long fyfc) {
		this.fyfc = fyfc;
	}
}
