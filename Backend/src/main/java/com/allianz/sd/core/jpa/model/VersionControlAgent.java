package com.allianz.sd.core.jpa.model;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/4/10
 * Time: 上午 9:49
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name="TW_LH_SD_VersionControlAgent")
public class VersionControlAgent {

    @EmbeddedId
    private VersionControlAgentIdentity identity;

    public VersionControlAgentIdentity getIdentity() {
        return identity;
    }

    public void setIdentity(VersionControlAgentIdentity identity) {
        this.identity = identity;
    }
}
