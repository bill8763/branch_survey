package com.allianz.sd.core.jpa.model;

import javax.persistence.*;

import com.allianz.sd.core.service.bean.AgentDailyAppDateType;
import com.allianz.sd.core.progress.CalcAcvitity;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/3/16
 * Time: 下午 5:12
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="TW_LH_SD_Agent_Daily_Data")
public class AgentDailyData extends CreateUpdateInfo implements CalcAcvitity{
	@EmbeddedId()
    private AgentDailyDataIdentity identity = null;

    @Column(name = "NumberOfMeet")
    private Integer numberOfMeet = 0;

    @Column(name = "NumberOfSubmit")
    private Integer numberOfSubmit = 0;

    @Column(name = "NumberOfInforce")
    private Integer numberOfInforce = 0;
    
    @Column(name = "ProgressPointMeet")
    private Integer progressPointMeet = 0;
    
    @Column(name = "ProgressPointSubmit")
    private Integer progressPointSubmit = 0;
    
    @Column(name = "ProgressPointInforce")
    private Integer progressPointInforce = 0;
    
    @Column(name = "DataYear")
    private Integer dataYear = null;
    
    @Column(name = "DataMonth")
    private Integer dataMonth = null;

    @Column(name = "DataQuarter")
    private Integer dataQuarter = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DataTime")
    private Date dataTime = null;

	public AgentDailyDataIdentity getIdentity() {
		return identity;
	}

	public void setIdentity(AgentDailyDataIdentity identity) {
		this.identity = identity;
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

	public Integer getProgressPointMeet() {
		return progressPointMeet;
	}

	public void setProgressPointMeet(Integer progressPointMeet) {
		this.progressPointMeet = progressPointMeet;
	}

	public Integer getProgressPointSubmit() {
		return progressPointSubmit;
	}

	public void setProgressPointSubmit(Integer progressPointSubmit) {
		this.progressPointSubmit = progressPointSubmit;
	}

	public Integer getProgressPointInforce() {
		return progressPointInforce;
	}

	public void setProgressPointInforce(Integer progressPointInforce) {
		this.progressPointInforce = progressPointInforce;
	}

	public Integer getDataYear() {
		return dataYear;
	}

	public void setDataYear(Integer dataYear) {
		this.dataYear = dataYear;
	}

	public Integer getDataMonth() {
		return dataMonth;
	}

	public void setDataMonth(Integer dataMonth) {
		this.dataMonth = dataMonth;
	}

	public Integer getDataQuarter() {
		return dataQuarter;
	}

	public void setDataQuarter(Integer dataQuarter) {
		this.dataQuarter = dataQuarter;
	}

	public Date getDataTime() {
		return dataTime;
	}

	public void setDataTime(Date dataTime) {
		this.dataTime = dataTime;
	}
	public void calc(int val , double pointBase,AgentDailyAppDateType dataType,boolean isReplace) {
		switch(dataType) {
			case MEET : {
				if(isReplace) setNumberOfMeet(val);
				else setNumberOfMeet(getNumberOfMeet() + val);				
				setProgressPointMeet((int)(getNumberOfMeet() * pointBase));
				
				break;
			}
			case SUBMIT : {
				if(isReplace) setNumberOfSubmit(val);
				else setNumberOfSubmit(getNumberOfSubmit() + val);
				setProgressPointSubmit((int)(getNumberOfSubmit() * pointBase));
				break;
			}
			case INFORCE : {
				if(isReplace) setNumberOfInforce(val);
				else setNumberOfInforce(getNumberOfInforce() + val);
				setProgressPointInforce((int)(getNumberOfInforce() * pointBase));
				break;
			}
		}
	}
	
}
