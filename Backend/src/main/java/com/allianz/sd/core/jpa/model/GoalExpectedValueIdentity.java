package com.allianz.sd.core.jpa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 */
@Embeddable
public class GoalExpectedValueIdentity implements Serializable {


    @Column(name = "GoalExpectedSeq")
    private Integer goalExpectedSeq = null;
    
    @Column(name = "MappingID")
    private String mappingID = null;

	public Integer getGoalExpectedSeq() {
		return goalExpectedSeq;
	}

	public void setGoalExpectedSeq(Integer goalExpectedSeq) {
		this.goalExpectedSeq = goalExpectedSeq;
	}

	public String getMappingID() {
		return mappingID;
	}

	public void setMappingID(String mappingID) {
		this.mappingID = mappingID;
	}





}
