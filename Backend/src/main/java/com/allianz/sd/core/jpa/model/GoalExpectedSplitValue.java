package com.allianz.sd.core.jpa.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 */
@Entity

@Table(name="TW_LH_SD_Goal_Expected_Split_Value")
public class GoalExpectedSplitValue extends CreateUpdateInfo{
	
	@EmbeddedId()
	private GoalExpectedSplitValueIdentity identity = null;
    
    @Column(name = "SetValue")
    private String setValue = null;

	public GoalExpectedSplitValueIdentity getIdentity() {
		return identity;
	}

	public void setIdentity(GoalExpectedSplitValueIdentity identity) {
		this.identity = identity;
	}

	public String getSetValue() {
		return setValue;
	}

	public void setSetValue(String setValue) {
		this.setValue = setValue;
	}
    
}
