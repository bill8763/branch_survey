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
@Table(name="TW_LH_SD_Agent_Daily_App_Data")
public class AgentDailyAppData extends CreateUpdateInfo implements CalcAcvitity{
	@EmbeddedId()
    private AgentDailyAppDataIdentity identity = null;

    @Column(name = "NumberOfFind")
    private Integer numberOfFind = 0;

    @Column(name = "NumberOfSchedule")
    private Integer numberOfSchedule = 0;

    @Column(name = "ProgressFind")
    private Integer progressFind = 0;
    
    @Column(name = "ProgressSchedule")
    private Integer progressSchedule = 0;
    
    @Column(name = "DataYear")
    private Integer dataYear = null;
    
    @Column(name = "DataMonth")
    private Integer dataMonth = null;

    @Column(name = "DataQuarter")
    private Integer dataQuarter = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DataTime")
    private Date dataTime = null;

	public AgentDailyAppDataIdentity getIdentity() {
		return identity;
	}

	public void setIdentity(AgentDailyAppDataIdentity identity) {
		this.identity = identity;
	}

	public Integer getNumberOfFind() {
		return numberOfFind;
	}

	public void setNumberOfFind(Integer numberOfFind) {
		this.numberOfFind = numberOfFind;
	}

	public Integer getNumberOfSchedule() {
		return numberOfSchedule;
	}

	public void setNumberOfSchedule(Integer numberOfSchedule) {
		this.numberOfSchedule = numberOfSchedule;
	}

	public Integer getProgressFind() {
		return progressFind;
	}

	public void setProgressFind(Integer progressFind) {
		this.progressFind = progressFind;
	}

	public Integer getProgressSchedule() {
		return progressSchedule;
	}

	public void setProgressSchedule(Integer progressSchedule) {
		this.progressSchedule = progressSchedule;
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
			case FIND : {
				if(isReplace) setNumberOfFind(val);
				else setNumberOfFind(getNumberOfFind() + val);				
				setProgressFind((int)(getNumberOfFind() * pointBase));
				
				break;
			}
			case SCHEDULE : {
				if(isReplace) setNumberOfSchedule(val);
				else setNumberOfSchedule(getNumberOfSchedule() + val);
				setProgressSchedule((int)(getNumberOfSchedule() * pointBase));
				break;
			}
		}
	}

}
