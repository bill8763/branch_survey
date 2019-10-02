package com.allianz.sd.core.jpa.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 */
@Entity

@Table(name="TW_LH_SD_Team_Progress")
public class TeamProgress extends CreateInfo{
	
	@EmbeddedId()
    private ProgressIdentity identity = null;

    @Column(name = "Goal")
    private long goal;
    
    @Column(name = "Forecast")
    private long forecast;
    
    @Column(name = "Actual")
    private long Actual;
    
    @Column(name = "Shortfall")
    private long shortfall;

	public ProgressIdentity getIdentity() {
		return identity;
	}

	public void setIdentity(ProgressIdentity identity) {
		this.identity = identity;
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
