package com.allianz.sd.core.jpa.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 */
@Entity

@Table(name="TW_LH_SD_Personal_Progress")
public class PersonalProgress extends CreateInfo{
	
	@EmbeddedId()
    private ProgressIdentity identity = null;

    @Column(name = "Find")
    private BigDecimal find = null;
    
    @Column(name = "Schedule")
    private BigDecimal schedule = null; 
    
    @Column(name = "Meet")
    private BigDecimal meet = null;
    
    @Column(name = "Submit")
    private BigDecimal submit = null;
    
    @Column(name = "Inforce")
    private BigDecimal inforce = null;
    
    @Column(name = "FYFC")
    private BigDecimal fyfc = null;

	public ProgressIdentity getIdentity() {
		return identity;
	}

	public void setIdentity(ProgressIdentity identity) {
		this.identity = identity;
	}

	public BigDecimal getFind() {
		return find;
	}

	public void setFind(BigDecimal find) {
		this.find = find;
	}

	public BigDecimal getSchedule() {
		return schedule;
	}

	public void setSchedule(BigDecimal schedule) {
		this.schedule = schedule;
	}

	public BigDecimal getMeet() {
		return meet;
	}

	public void setMeet(BigDecimal meet) {
		this.meet = meet;
	}

	public BigDecimal getSubmit() {
		return submit;
	}

	public void setSubmit(BigDecimal submit) {
		this.submit = submit;
	}

	public BigDecimal getInforce() {
		return inforce;
	}

	public void setInforce(BigDecimal inforce) {
		this.inforce = inforce;
	}

	public BigDecimal getFyfc() {
		return fyfc;
	}

	public void setFyfc(BigDecimal fyfc) {
		this.fyfc = fyfc;
	}
    
    
}
