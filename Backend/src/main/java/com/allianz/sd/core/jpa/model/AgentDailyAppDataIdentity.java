package com.allianz.sd.core.jpa.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 6:00
 * To change this template use File | Settings | File Templates.
 */
@Embeddable
public class AgentDailyAppDataIdentity implements Serializable {

    @Column(name = "OrganizationalUnit")
    private String organizationalUnit = null;

    @Column(name = "AgentID")
    private String agentID = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "DataDate")
    private Date dataDate = null;

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

	public Date getDataDate() {
		return dataDate;
	}

	public void setDataDate(Date dataDate) {
		this.dataDate = dataDate;
	}


}
