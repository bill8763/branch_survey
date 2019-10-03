package com.allianz.sd.core.jpa.model;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/3/16
 * Time: 下午 5:12
 * To change this template use File | Settings | File Templates.
 */
@Embeddable
public class PersonalPorgressIdentity implements Serializable{
	@Column(name = "OrganizationalUnit")
	private String organizationalUnit = null;

	@Column(name = "DataYear")
	private int dataYear ;

	@Column(name = "AgentID")
	private String agentID = null;

	@Column(name = "DataType")
	private String dataType = null;

	@Column(name = "TimeBase")
	private String timeBase = null;


	public String getOrganizationalUnit() {
		return organizationalUnit;
	}

	public void setOrganizationalUnit(String OrganizationalUnit) {
		this.organizationalUnit = OrganizationalUnit;
	}

	public String getAgentID() {
		return agentID;
	}

	public void setAgentID(String AgentID) {
		this.agentID = AgentID;
	}

	public String getDataType() {
		return dataType;
	}

	public void setdataType(String DataType) {
		this.dataType = DataType;
	}

	public String getTimeBasee() {
		return timeBase;
	}

	public void settimeBase(String TimeBase) {
		this.timeBase = TimeBase;
	}



}
