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
public class GoalSettingSplitIdentity implements Serializable {

	@Column(name = "GoalSettingSeq")
	private Integer goalSettingSeq = null;

	@Column(name = "SplitVersion")
	private Integer splitVersion = null;
	
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

}
