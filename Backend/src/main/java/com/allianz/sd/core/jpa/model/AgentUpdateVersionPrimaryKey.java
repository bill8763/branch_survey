package com.allianz.sd.core.jpa.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/26
 * Time: 下午 5:24
 * To change this template use File | Settings | File Templates.
 */

@Embeddable
public class AgentUpdateVersionPrimaryKey implements Serializable {

    @Column(name = "DataCategory")
    private String dataCategory = null;

    @Column(name = "AgentID")
    private String agentID = null;

    @Column(name = "UniqueID")
    private Integer uniqueID = null;

    public String getDataCategory() {
        return dataCategory;
    }

    public void setDataCategory(String dataCategory) {
        this.dataCategory = dataCategory;
    }

    public String getAgentID() {
        return agentID;
    }

    public void setAgentID(String agentID) {
        this.agentID = agentID;
    }

    public Integer getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(Integer uniqueID) {
        this.uniqueID = uniqueID;
    }
}
