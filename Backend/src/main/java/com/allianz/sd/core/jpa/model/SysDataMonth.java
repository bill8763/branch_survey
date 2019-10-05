package com.allianz.sd.core.jpa.model;

import java.util.Date;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/3/16
 * Time: 下午 5:12
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="TW_LH_SD_Sys_Data_Month")
public class SysDataMonth extends CreateUpdateInfo{
	
	@EmbeddedId()
    private SysDataMonthIdentity identity = null;

	@Temporal(TemporalType.DATE)
    @Column(name = "EndDate")
    private Date endDate = null;

	@Temporal(TemporalType.DATE)
    @Column(name = "SaleMonth")
    private Date saleMonth = null;
    
	@Temporal(TemporalType.DATE)
    @Column(name = "PlanMonth")
    private Date planMonth = null;

	public SysDataMonthIdentity getIdentity() {
		return identity;
	}

	public void setIdentity(SysDataMonthIdentity identity) {
		this.identity = identity;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getSaleMonth() {
		return saleMonth;
	}

	public void setSaleMonth(Date saleMonth) {
		this.saleMonth = saleMonth;
	}

	public Date getPlanMonth() {
		return planMonth;
	}

	public void setPlanMonth(Date planMonth) {
		this.planMonth = planMonth;
	}
    
	
}
