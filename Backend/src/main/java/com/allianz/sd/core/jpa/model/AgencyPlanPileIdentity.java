package com.allianz.sd.core.jpa.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 */
@Embeddable
public class AgencyPlanPileIdentity implements Serializable {

    @Column(name = "OrganizationalUnit")
    private String organizationalUnit = null;

    @Column(name = "LeaderAgentID")
    private String leaderAgentID = null;

    @Column(name = "DataYear")
    private Integer dataYear = null;

	@Column(name = "AgentID")
	private String agentID = null;

	public String getOrganizationalUnit() {
		return organizationalUnit;
	}

	public void setOrganizationalUnit(String organizationalUnit) {
		this.organizationalUnit = organizationalUnit;
	}

	public String getLeaderAgentID() {
		return leaderAgentID;
	}

	public void setLeaderAgentID(String leaderAgentID) {
		this.leaderAgentID = leaderAgentID;
	}

	public Integer getDataYear() {
		return dataYear;
	}

	public void setDataYear(Integer dataYear) {
		this.dataYear = dataYear;
	}

	public String getAgentID() {
		return agentID;
	}

	public void setAgentID(String agentID) {
		this.agentID = agentID;
	}
}
