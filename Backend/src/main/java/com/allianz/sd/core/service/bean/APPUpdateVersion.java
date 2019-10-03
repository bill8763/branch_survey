package com.allianz.sd.core.service.bean;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/4/18
 * Time: 上午 9:48
 * To change this template use File | Settings | File Templates.
 */
public class APPUpdateVersion {
    private String version = null;
    private String updateType = null;
    private String applink = null;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUpdateType() {
        return updateType;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }

    public String getApplink() {
        return applink;
    }

    public void setApplink(String applink) {
        this.applink = applink;
    }
}
