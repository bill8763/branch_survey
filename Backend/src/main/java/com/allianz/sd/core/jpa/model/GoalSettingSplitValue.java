package com.allianz.sd.core.jpa.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 */
@Entity

@Table(name="TW_LH_SD_Goal_Setting_Split_Value")
public class GoalSettingSplitValue extends CreateUpdateInfo{
	
	@EmbeddedId()
	private GoalSettingSplitValueIdentity identity = null;
    
    @Column(name = "SetValue")
    private String setValue = null;

	public GoalSettingSplitValueIdentity getIdentity() {
		return identity;
	}

	public void setIdentity(GoalSettingSplitValueIdentity identity) {
		this.identity = identity;
	}

	public String getSetValue() {
		return setValue;
	}

	public void setSetValue(String setValue) {
		this.setValue = setValue;
	}
    
}
