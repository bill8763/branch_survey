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
@Table(name="TW_LH_SD_CISL_BatchLog_Detail")
public class CISLBatchLogDetail {

    @EmbeddedId
    private CISLBatchLogDetailIdentity identity;

    @Column(name = "AgentID")
    private String agentID = null;

    public CISLBatchLogDetailIdentity getIdentity() {
        return identity;
    }

    public void setIdentity(CISLBatchLogDetailIdentity identity) {
        this.identity = identity;
    }

    public String getAgentID() {
        return agentID;
    }

    public void setAgentID(String agentID) {
        this.agentID = agentID;
    }
}
