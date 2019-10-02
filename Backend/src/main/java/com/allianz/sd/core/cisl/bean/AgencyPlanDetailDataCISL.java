package com.allianz.sd.core.cisl.bean;

import java.util.Date;

public class AgencyPlanDetailDataCISL extends ProcessData{
	
	private String displayBlock = null;
	
	private Integer displayOrder = null;
	
	private String subAgentID = null;
	
	private String subAgentName = null;
	
	private String subAgentJobGrade = null;
			
	private String cumulativeProc ;
	
	private Date performanceCalcStartMonth = null;
	
	private Long anp = null;
	
	private Long fyfc = null;
	
	private Long newBusinessCase = null;
	
	private Long recruitMent = null;
	
	private Long manPower = null;

	public String getDisplayBlock() {
		return displayBlock;
	}

	public void setDisplayBlock(String displayBlock) {
		this.displayBlock = displayBlock;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getSubAgentID() {
		return subAgentID;
	}

	public void setSubAgentID(String subAgentID) {
		this.subAgentID = subAgentID;
	}

	public String getSubAgentName() {
		return subAgentName;
	}

	public void setSubAgentName(String subAgentName) {
		this.subAgentName = subAgentName;
	}

	public String getSubAgentJobGrade() {
		return subAgentJobGrade;
	}

	public void setSubAgentJobGrade(String subAgentJobGrade) {
		this.subAgentJobGrade = subAgentJobGrade;
	}

	public String getCumulativeProc() {
		return cumulativeProc;
	}

	public void setCumulativeProc(String cumulativeProc) {
		this.cumulativeProc = cumulativeProc;
	}

	public Date getPerformanceCalcStartMonth() {
		return performanceCalcStartMonth;
	}

	public void setPerformanceCalcStartMonth(Date performanceCalcStartMonth) {
		this.performanceCalcStartMonth = performanceCalcStartMonth;
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

	public Long getRecruitMent() {
		return recruitMent;
	}

	public void setRecruitMent(Long recruitMent) {
		this.recruitMent = recruitMent;
	}

	public Long getManPower() {
		return manPower;
	}

	public void setManPower(Long manPower) {
		this.manPower = manPower;
	}
}
