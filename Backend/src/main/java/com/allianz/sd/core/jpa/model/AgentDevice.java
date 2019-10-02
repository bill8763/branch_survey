package com.allianz.sd.core.jpa.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/22
 * Time: 下午 12:06
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="TW_LH_SD_AGENTDEVICE")
public class AgentDevice extends CreateUpdateInfo{

    @Id
    @Column(name = "DeviceSeq")
    @GeneratedValue(generator = "idSequence")
    @SequenceGenerator(name = "idSequence", sequenceName = "TW_LH_SD_Sequence_AgentDevice", allocationSize = 1)
    private Integer deviceSeq = null;

    @Column(name = "PushID")
    private String pushId = null;

    @Column(name = "AgentID")
    private String agentID = null;

    @Column(name = "DeviceCategory")
    private String deviceCategory = null;

    @Column(name = "DeviceID")
    private String deviceID = null;

    @Column(name = "IsRegisterKernel")
    private String isRegisterKernel = null;

    @Column(name = "DeviceSystem")
    private String deviceSystem = null;

    @Column(name = "DeviceModel")
    private String deviceModel = null;

    @Column(name = "DeviceType")
    private String deviceType = null;
    

    public Integer getDeviceSeq() {
        return deviceSeq;
    }

    public void setDeviceSeq(Integer deviceSeq) {
        this.deviceSeq = deviceSeq;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getAgentID() {
        return agentID;
    }

    public void setAgentID(String agentID) {
        this.agentID = agentID;
    }

    public String getDeviceCategory() {
        return deviceCategory;
    }

    public void setDeviceCategory(String deviceCategory) {
        this.deviceCategory = deviceCategory;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getRegisterKernel() {
        return isRegisterKernel;
    }

    public void setRegisterKernel(String registerKernel) {
        isRegisterKernel = registerKernel;
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

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

    
}
