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
@Table(name="TW_LH_SD_APP_ClickLog")
public class APPClickLog {
    @Column(name = "OrganizationalUnit")
    private String organizationalUnit = null;

    @Id
    @Column(name = "LogID")
    @GeneratedValue(generator = "idSequence")
    @SequenceGenerator(name = "idSequence", sequenceName = "TW_LH_SD_Sequence_APP_ClickLog", allocationSize = 1)
    private Integer LogID = null;

    @Column(name = "FunctionID")
    private String functionID = null;

    @Column(name = "ActionID")
    private String actionID = null;

    @Column(name = "AgentID")
    private String agentID = null;

    @Column(name = "Valid")
    private String valid = null;

    @Column(name = "Message")
    private String message = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LogTime")
    private Date logTime = null;

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

    public String getFunctionID() {
        return functionID;
    }

    public void setFunctionID(String functionID) {
        this.functionID = functionID;
    }

    public String getActionID() {
        return actionID;
    }

    public void setActionID(String actionID) {
        this.actionID = actionID;
    }

    public String getAgentID() {
        return agentID;
    }

    public void setAgentID(String agentID) {
        this.agentID = agentID;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
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
