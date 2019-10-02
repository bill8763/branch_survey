package com.allianz.sd.core.cisl.bean;

import java.util.Date;

public class DailyDataCISL extends ProcessData {
	
    private Date dataDate = null;
    
    private Integer numberOfMeet = null; 
    
    private Integer numberOfSubmit = null;
    
    private Integer numberOfInforce = null;
    
    private Date dataTime = null;

	public Date getDataDate() {
		return dataDate;
	}

	public void setDataDate(Date dataDate) {
		this.dataDate = dataDate;
	}

	public Integer getNumberOfMeet() {
		return numberOfMeet;
	}

	public void setNumberOfMeet(Integer numberOfMeet) {
		this.numberOfMeet = numberOfMeet;
	}

	public Integer getNumberOfSubmit() {
		return numberOfSubmit;
	}

	public void setNumberOfSubmit(Integer numberOfSubmit) {
		this.numberOfSubmit = numberOfSubmit;
	}

	public Integer getNumberOfInforce() {
		return numberOfInforce;
	}

	public void setNumberOfInforce(Integer numberOfInforce) {
		this.numberOfInforce = numberOfInforce;
	}

	public Date getDataTime() {
		return dataTime;
	}

	public void setDataTime(Date dataTime) {
		this.dataTime = dataTime;
	}
    

}
