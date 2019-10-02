package com.allianz.sd.core.cisl.bean;

import java.util.Date;

public class AgentDataCISL extends ProcessData {
	
	private String agentName = null;
	
	private String officeName = null;
	
	private String gender = null;
	
	private Date OBMonth = null;
	
	private Date leavingDate = null;
	
	private Date dataTime = null;

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getOBMonth() {
		return OBMonth;
	}

	public void setOBMonth(Date oBMonth) {
		OBMonth = oBMonth;
	}

	public Date getLeavingDate() {
		return leavingDate;
	}

	public void setLeavingDate(Date leavingDate) {
		this.leavingDate = leavingDate;
	}

	public Date getDataTime() {
		return dataTime;
	}

	public void setDataTime(Date dataTime) {
		this.dataTime = dataTime;
	}
	
	
}
