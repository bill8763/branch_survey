package com.allianz.sd.core.jpa.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 */
@Embeddable
public class AgencyPlanDetailIdentity implements Serializable {

    @Column(name = "OrganizationalUnit")
    private String organizationalUnit = null;

    @Column(name = "AgentID")
    private String agentID = null;

    @Column(name = "DataYear")
    private Integer dataYear = null;

	@Column(name = "DisplayBlock")
	private String displayBlock = null;

	@Column(name = "DisplayOrder")
	private Integer displayOrder = null;


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

	public String getDisplayBlock() {
		return displayBlock;
	}

	public void setDisplayBlock(String displayBlock) {
		this.displayBlock = displayBlock;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
}
