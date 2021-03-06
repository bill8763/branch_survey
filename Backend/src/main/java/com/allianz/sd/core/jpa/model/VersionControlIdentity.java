package com.allianz.sd.core.jpa.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/4/10
 * Time: 下午 4:33
 * To change this template use File | Settings | File Templates.
 */
@Embeddable
public class VersionControlIdentity implements Serializable {
    @Column(name = "Version")
    private String version = null;

    @Column(name = "DeviceSystem")
    private String deviceSystem = null;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDeviceSystem() {
        return deviceSystem;
    }

    public void setDeviceSystem(String deviceSystem) {
        this.deviceSystem = deviceSystem;
    }
}
