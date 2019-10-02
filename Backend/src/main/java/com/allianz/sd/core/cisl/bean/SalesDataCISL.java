package com.allianz.sd.core.cisl.bean;

import java.util.Date;

public class SalesDataCISL extends ProcessData{
	private Date dataYearMonth = null;

	private String performanceType = null;
	
	private Long anp = null;
	
    private Long fyfc = null;

    private Long newBusinessCase = null;

    private Long manpower = null;
    
    private Long recruitment = null;
    
    private Date dataTime = null;


	public Date getDataYearMonth() {
		return dataYearMonth;
	}

	public void setDataYearMonth(Date dataYearMonth) {
		this.dataYearMonth = dataYearMonth;
	}

	public String getPerformanceType() {
		return performanceType;
	}

	public void setPerformanceType(String performanceType) {
		this.performanceType = performanceType;
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
