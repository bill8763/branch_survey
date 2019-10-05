package com.allianz.sd.core.cisl.bean;

import java.util.Date;

public class AgencyPlanDataCISL extends ProcessData {
	
	private String agentName = null;
	
	private String jobGrade = null;
	
	private Long anp = null ;
			
	private Long fyfc = null;

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
}
