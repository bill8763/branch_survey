package com.allianz.sd.core.jpa.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 */
@Embeddable
public class GoalSettingValueIdentity implements Serializable {


    @Column(name = "GoalSettingSeq")
    private Integer goalSettingSeq = null;
    
    @Column(name = "MappingID")
    private String mappingID = null;

	public Integer getGoalSettingSeq() {
		return goalSettingSeq;
	}

	public void setGoalSettingSeq(Integer goalSettingSeq) {
		this.goalSettingSeq = goalSettingSeq;
	}

	public String getMappingID() {
		return mappingID;
	}

	public void setMappingID(String mappingID) {
		this.mappingID = mappingID;
	}




}
