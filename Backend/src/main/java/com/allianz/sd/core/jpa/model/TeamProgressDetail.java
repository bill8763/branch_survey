package com.allianz.sd.core.jpa.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 */
@Entity

@Table(name="TW_LH_SD_Team_Progress_Detail")
public class TeamProgressDetail extends CreateInfo{
	
	@EmbeddedId()
    private TeamProgressDetailIdentity identity = null;

	@Column(name = "DisplayOrder")
	private Integer displayOrder = null;

	@Column(name = "SubordinateAgentAgentName")
	private String subordinateAgentAgentName = null;
	
	@Column(name = "SubordinateAgentJobGrade")
	private String subordinateAgentJobGrade = null;
	
	@Column(name = "SubordinateAgentOfficeName")
	private String subordinateAgentOfficeName = null;
	
	@Column(name = "IsDrilldown")
    private String IsDrilldown = null;
	
    @Column(name = "Goal")
    private long goal;
    
    @Column(name = "Forecast")
    private long forecast;
    
    @Column(name = "Actual")
    private long Actual;
    
    @Column(name = "Shortfall")
    private long shortfall;

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public TeamProgressDetailIdentity getIdentity() {
		return identity;
	}

	public void setIdentity(TeamProgressDetailIdentity identity) {
		this.identity = identity;
	}

	public String getSubordinateAgentAgentName() {
		return subordinateAgentAgentName;
	}

	public void setSubordinateAgentAgentName(String subordinateAgentAgentName) {
		this.subordinateAgentAgentName = subordinateAgentAgentName;
	}

	public String getSubordinateAgentJobGrade() {
		return subordinateAgentJobGrade;
	}

	public void setSubordinateAgentJobGrade(String subordinateAgentJobGrade) {
		this.subordinateAgentJobGrade = subordinateAgentJobGrade;
	}

	public String getSubordinateAgentOfficeName() {
		return subordinateAgentOfficeName;
	}

	public void setSubordinateAgentOfficeName(String subordinateAgentOfficeName) {
		this.subordinateAgentOfficeName = subordinateAgentOfficeName;
	}

	public String getIsDrilldown() {
		return IsDrilldown;
	}

	public void setIsDrilldown(String isDrilldown) {
		IsDrilldown = isDrilldown;
	}

	public long getGoal() {
		return goal;
	}

	public void setGoal(long goal) {
		this.goal = goal;
	}

	public long getForecast() {
		return forecast;
	}

	public void setForecast(long forecast) {
		this.forecast = forecast;
	}

	public long getActual() {
		return Actual;
	}

	public void setActual(long actual) {
		Actual = actual;
	}

	public long getShortfall() {
		return shortfall;
	}

	public void setShortfall(long shortfall) {
		this.shortfall = shortfall;
	}

}
