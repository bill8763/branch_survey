package com.allianz.sd.core.jpa.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/4/10
 * Time: 上午 9:49
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name="TW_LH_SD_LoginLog")
public class LoginLog {
    @Id
    @Column(name = "LogID")
    @GeneratedValue(generator = "loginLogIDSequence")
    @SequenceGenerator(name = "loginLogIDSequence", sequenceName = "TW_LH_SD_Sequence_LoginLog", allocationSize = 1)
    private Integer logID = null;

    @Column(name = "Username")
    private String username = null;

    @Column(name = "DeviceID")
    private String deviceID = null;

    @Column(name = "Success")
    private String success = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LoginTime")
    private Date loginTime = null;

    public Integer getLogID() {
        return logID;
    }

    public void setLogID(Integer logID) {
        this.logID = logID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }
}
