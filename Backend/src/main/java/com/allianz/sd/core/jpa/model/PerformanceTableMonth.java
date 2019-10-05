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
@Table(name="TW_LH_SD_Performance_Table_Month")
public class PerformanceTableMonth extends CreateUpdateInfo{
	
	@EmbeddedId()
    private PerformanceTableMonthIdentity identity = null;

	@Temporal(TemporalType.DATE)
    @Column(name = "EndDate")
    private Date endDate = null;

    @Column(name = "PerformanceYear")
    private Integer performanceYear = null;
    
    @Column(name = "PerformanceMonth")
    private Integer performanceMonth = null;
    
    @Column(name = "PerformanceQuarter")
    private Integer performanceQuarter = null;
    
    @Column(name = "Days")
    private Integer Days = null;

	public PerformanceTableMonthIdentity getIdentity() {
		return identity;
	}

	public void setIdentity(PerformanceTableMonthIdentity identity) {
		this.identity = identity;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getPerformanceYear() {
		return performanceYear;
	}

	public void setPerformanceYear(Integer performanceYear) {
		this.performanceYear = performanceYear;
	}

	public Integer getPerformanceMonth() {
		return performanceMonth;
	}

	public void setPerformanceMonth(Integer performanceMonth) {
		this.performanceMonth = performanceMonth;
	}

	public Integer getPerformanceQuarter() {
		return performanceQuarter;
	}

	public void setPerformanceQuarter(Integer performanceQuarter) {
		this.performanceQuarter = performanceQuarter;
	}

	public Integer getDays() {
		return Days;
	}

	public void setDays(Integer days) {
		Days = days;
	}
    
}
