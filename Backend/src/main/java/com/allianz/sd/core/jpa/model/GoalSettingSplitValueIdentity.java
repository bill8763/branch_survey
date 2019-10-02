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
public class GoalSettingSplitValueIdentity implements Serializable {

	@Column(name = "GoalSettingSeq")
	private Integer goalSettingSeq = null;

	@Column(name = "SplitVersion")
	private Integer splitVersion = null;
	
	@Column(name = "MappingID")
    private String mappingID = null;

    @Column(name = "TimeBase")
    private String timeBase = null;

    @Column(name = "TimeBaseSeq")
    private Integer timeBaseSeq = null;

	public Integer getGoalSettingSeq() {
		return goalSettingSeq;
	}

	public void setGoalSettingSeq(Integer goalSettingSeq) {
		this.goalSettingSeq = goalSettingSeq;
	}

	public Integer getSplitVersion() {
		return splitVersion;
	}

	public void setSplitVersion(Integer splitVersion) {
		this.splitVersion = splitVersion;
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
