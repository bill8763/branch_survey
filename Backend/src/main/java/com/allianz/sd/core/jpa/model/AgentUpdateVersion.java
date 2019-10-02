package com.allianz.sd.core.jpa.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/22
 * Time: 下午 12:06
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="TW_LH_SD_AgentUpdateVersion")
public class AgentUpdateVersion {

    @Column(name = "DataCategory")
    private String dataCategory = null;

    @Id
    @Column(name = "AgentID")
    private String agentID = null;

    @Column(name = "UniqueID")
    private Integer uniqueID = null;

    @Column(name = "DataTime")
    private Date dataTime = null;

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

    public Date getDataTime() {
        return dataTime;
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }
}
