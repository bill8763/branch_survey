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
public class VersionControlAgentIdentity implements Serializable {
    @Column(name = "Version")
    private String version = null;

    @Column(name = "AgentID")
    private String agentId = null;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }
}
