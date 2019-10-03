package com.allianz.sd.core.jpa.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/3/16
 * Time: 下午 5:12
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="TW_LH_SD_Agent_Sales_Data")
public class AgentSalesData extends CreateUpdateInfo{
	
	@EmbeddedId()
    private AgentSalesDataIdentity identity = null;

    @Column(name = "Anp")    
    private Long anp = null;

    @Column(name = "Fyfc")
    private Long fyfc = null;

    @Column(name = "NewBusinessCase")
    private Long newBusinessCase = null;

    @Column(name = "Manpower")
    private Long manpower = null;
    
    @Column(name = "Recruitment")
    private Long recruitment = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DataTime")
    private Date dataTime = null;

	public AgentSalesDataIdentity getIdentity() {
		return identity;
	}

	public void setIdentity(AgentSalesDataIdentity identity) {
		this.identity = identity;
	}

	public Long getAnp() {
		return anp;
	}

	public void setAnp(Long anp) {
		this.anp = anp;
	}

	public Long getFyfc() {
		return fyfc;
	}

	public void setFyfc(Long fyfc) {
		this.fyfc = fyfc;
	}

	public Long getNewBusinessCase() {
		return newBusinessCase;
	}

	public void setNewBusinessCase(Long newBusinessCase) {
		this.newBusinessCase = newBusinessCase;
	}

	public Long getManpower() {
		return manpower;
	}

	public void setManpower(Long manpower) {
		this.manpower = manpower;
	}

	public Long getRecruitment() {
		return recruitment;
	}

	public void setRecruitment(Long recruitment) {
		this.recruitment = recruitment;
	}

	public Date getDataTime() {
		return dataTime;
	}

	public void setDataTime(Date dataTime) {
		this.dataTime = dataTime;
	}

	
}
