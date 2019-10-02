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

@Table(name="TW_LH_SD_Goal_Setting_Split")
public class GoalSettingSplit extends CreateUpdateInfo{
	
	@EmbeddedId()
	private GoalSettingSplitIdentity identity = null;
    
    @Column(name = "TopVersion")
    private String topVersion = null;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "VersionSettingDate")
    private Date versionSettingDate = null;
    
    @Column(name = "PerformanceSettlementMonth")
    private Integer performanceSettlementMonth = null;

	public GoalSettingSplitIdentity getIdentity() {
		return identity;
	}

	public void setIdentity(GoalSettingSplitIdentity identity) {
		this.identity = identity;
	}

	public String getTopVersion() {
		return topVersion;
	}

	public void setTopVersion(String topVersion) {
		this.topVersion = topVersion;
	}

	public Date getVersionSettingDate() {
		return versionSettingDate;
	}

	public void setVersionSettingDate(Date versionSettingDate) {
		this.versionSettingDate = versionSettingDate;
	}

	public Integer getPerformanceSettlementMonth() {
		return performanceSettlementMonth;
	}

	public void setPerformanceSettlementMonth(Integer performanceSettlementMonth) {
		this.performanceSettlementMonth = performanceSettlementMonth;
	}
    
}
