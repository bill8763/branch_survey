package com.allianz.sd.core.jpa.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 */
@Entity
@Table(name="TW_LH_SD_Goal_Setting_Value")
public class GoalSettingValue extends CreateUpdateInfo{
	
	@EmbeddedId()
    private GoalSettingValueIdentity identity = null;

    @Column(name = "SetValue")
    private String setValue = null;

	public GoalSettingValueIdentity getIdentity() {
		return identity;
	}

	public void setIdentity(GoalSettingValueIdentity identity) {
		this.identity = identity;
	}

	public String getSetValue() {
		return setValue;
	}

	public void setSetValue(String setValue) {
		this.setValue = setValue;
	}
    
}
