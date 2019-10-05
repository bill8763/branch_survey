package com.allianz.sd.core.jpa.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/3/16
 * Time: 下午 5:12
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="TW_LH_SD_ErrorLog")
public class ErrorLog {
    @Column(name = "OrganizationalUnit")
    private String organizationalUnit = null;

    @Id
    @Column(name = "LogID")
    @GeneratedValue(generator = "idSequence")
    @SequenceGenerator(name = "idSequence", sequenceName = "TW_LH_SD_Sequence_Error_Log", allocationSize = 1)
    private Integer LogID = null;

    @Column(name = "Message")
    private String message = null;

    @Column(name = "Stack")
    private String stack = null;

    @Column(name = "AgentID")
    private String agentID = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ErrorTime")
    private Date errorTime = null;

    @Column(name = "DeviceSystem")
    private String deviceSystem = null;

    @Column(name = "DeviceModel")
    private String deviceModel = null;

	public String getOrganizationalUnit() {
		return organizationalUnit;
	}

	public void setOrganizationalUnit(String organizationalUnit) {
		this.organizationalUnit = organizationalUnit;
	}

	public Integer getLogID() {
		return LogID;
	}

	public void setLogID(Integer logID) {
		LogID = logID;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStack() {
		return stack;
	}

	public void setStack(String stack) {
		this.stack = stack;
	}

	public String getAgentID() {
		return agentID;
	}

	public void setAgentID(String agentID) {
		this.agentID = agentID;
	}

	public Date getErrorTime() {
		return errorTime;
	}

	public void setErrorTime(Date errorTime) {
		this.errorTime = errorTime;
	}

	public String getDeviceSystem() {
		return deviceSystem;
	}

	public void setDeviceSystem(String deviceSystem) {
		this.deviceSystem = deviceSystem;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

    
}
