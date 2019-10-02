package com.allianz.sd.core.jpa.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 */
@Embeddable
public class TeamProgressDetailIdentity extends ProgressIdentity {

	@Column(name = "OrganizationalUnit")
    private String organizationalUnit = null;

    @Column(name = "AgentID")
    private String agentID = null;

    @Column(name = "DataYear")
    private Integer dataYear = null;
    
    @Column(name = "DataType")
    private String dataType = null;
    
    @Column(name = "TimeBase")
    private String timeBase = null;
    
    @Column(name = "DirectUnitType")
    private String directUnitType = null;

    @Column(name = "SubordinateAgentID")
    private String subordinateAgentID = null;

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

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getTimeBase() {
		return timeBase;
	}

	public void setTimeBase(String timeBase) {
		this.timeBase = timeBase;
	}

	public String getDirectUnitType() {
		return directUnitType;
	}

	public void setDirectUnitType(String directUnitType) {
		this.directUnitType = directUnitType;
	}

	public String getSubordinateAgentID() {
		return subordinateAgentID;
	}

	public void setSubordinateAgentID(String subordinateAgentID) {
		this.subordinateAgentID = subordinateAgentID;
	}
}
