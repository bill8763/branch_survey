package com.allianz.sd.core.authorization.bean;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/3/16
 * Time: 下午 5:36
 * To change this template use File | Settings | File Templates.
 */
public class DeviceInfo {
    private String deviceId;
    private String deviceModel;
    private String deviceSystem;
    private String pushId;
    private String deviceType;

    public DeviceInfo(String deviceId, String deviceModel, String deviceSystem, String pushId, String deviceType) {
        this.deviceId = deviceId;
        this.deviceModel = deviceModel;
        this.deviceSystem = deviceSystem;
        this.pushId = pushId;
        this.deviceType = deviceType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getDeviceSystem() {
        return deviceSystem;
    }

    public void setDeviceSystem(String deviceSystem) {
        this.deviceSystem = deviceSystem;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
    
}
