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
@Table(name="TW_LH_SD_VersionControl")
public class VersionControl {
    @EmbeddedId
    private VersionControlIdentity identity = null;

    @Column(name = "IsEnabled")
    private String isEnabled = null;

    @Column(name = "UpdateType")
    private String updateType = null;

    @Column(name = "Description")
    private String description = null;

    @Column(name = "APPLink")
    private String appLink = null;

    public VersionControlIdentity getIdentity() {
        return identity;
    }

    public void setIdentity(VersionControlIdentity identity) {
        this.identity = identity;
    }

    public String getEnabled() {
        return isEnabled;
    }

    public void setEnabled(String enabled) {
        isEnabled = enabled;
    }

    public String getUpdateType() {
        return updateType;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAppLink() {
        return appLink;
    }

    public void setAppLink(String appLink) {
        this.appLink = appLink;
    }
}
