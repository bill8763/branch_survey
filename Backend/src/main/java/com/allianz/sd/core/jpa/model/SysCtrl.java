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
@Table(name="TW_LH_SD_Sys_Ctrl")
public class SysCtrl {

    @EmbeddedId()
    private SysCtrlIdentity identity = null;

    @Column(name = "Description")
    private String description = null;

    @Column(name = "SysValue")
    private String sysValue = null;

    public SysCtrlIdentity getIdentity() {
        return identity;
    }

    public void setIdentity(SysCtrlIdentity identity) {
        this.identity = identity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSysValue() {
        return sysValue;
    }

    public void setSysValue(String sysValue) {
        this.sysValue = sysValue;
    }
}
