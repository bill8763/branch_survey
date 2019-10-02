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
public class GoalExpectedSplitValueIdentity implements Serializable {

	@Column(name = "GoalExpectedSeq")
	private Integer goalExpectedSeq = null;

	@Column(name = "MappingID")
    private String mappingID = null;

    @Column(name = "TimeBase")
    private String timeBase = null;

    @Column(name = "TimeBaseSeq")
    private Integer timeBaseSeq = null;


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

	public String getTimeBase() {
		return timeBase;
	}

	public void setTimeBase(String timeBase) {
		this.timeBase = timeBase;
	}

	public Integer getTimeBaseSeq() {
		return timeBaseSeq;
	}

	public void setTimeBaseSeq(Integer timeBaseSeq) {
		this.timeBaseSeq = timeBaseSeq;
	}
}
