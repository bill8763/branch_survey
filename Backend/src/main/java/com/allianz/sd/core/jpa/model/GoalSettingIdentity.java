package com.allianz.sd.core.jpa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class GoalSettingIdentity implements Serializable {

	@Column(name = "OrganizationalUnit")    
    private String organizationalUnit = null;
	
    @Column(name = "AgentID")    
    private String agentID = null;

    @Column(name = "DataYear")
    private Integer dataYear = null;
	

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
}
