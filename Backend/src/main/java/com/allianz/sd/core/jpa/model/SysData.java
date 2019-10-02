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
@Table(name="TW_LH_SD_Sys_Data")
public class SysData extends CreateUpdateInfo{

    @EmbeddedId()
    private SysDataIdentity identity = null;

    @Column(name = "GoalSettingTimeLimit")
    private Integer goalSettingTimeLimit = null;

    @Column(name = "GoalApproveTimeLimit")
    private Integer goalApproveTimeLimit = null;

    @Column(name = "CustomerDataTrackingLimit")
    private Integer customerDataTrackingLimit = null;

    @Column(name = "CustomerDataDeleteLimit")
    private Integer customerDataDeleteLimit = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PerformanceSettlementMonth")
    private Date performanceSettlementMonth = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "RemainingPlanCalcStartMonth")
    private Date remainingPlanCalcStartMonth = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DataTime")
    private Date dataTime = null;

    
    public SysDataIdentity getIdentity() {
		return identity;
	}

	public void setIdentity(SysDataIdentity identity) {
		this.identity = identity;
	}


    public Integer getGoalSettingTimeLimit() {
        return goalSettingTimeLimit;
    }

    public void setGoalSettingTimeLimit(Integer goalSettingTimeLimit) {
        this.goalSettingTimeLimit = goalSettingTimeLimit;
    }

    public Integer getGoalApproveTimeLimit() {
        return goalApproveTimeLimit;
    }

    public void setGoalApproveTimeLimit(Integer goalApproveTimeLimit) {
        this.goalApproveTimeLimit = goalApproveTimeLimit;
    }

    public Integer getCustomerDataTrackingLimit() {
        return customerDataTrackingLimit;
    }

    public void setCustomerDataTrackingLimit(Integer customerDataTrackingLimit) {
        this.customerDataTrackingLimit = customerDataTrackingLimit;
    }

    public Integer getCustomerDataDeleteLimit() {
        return customerDataDeleteLimit;
    }

    public void setCustomerDataDeleteLimit(Integer customerDataDeleteLimit) {
        this.customerDataDeleteLimit = customerDataDeleteLimit;
    }

    public Date getPerformanceSettlementMonth() {
        return performanceSettlementMonth;
    }

    public void setPerformanceSettlementMonth(Date performanceSettlementMonth) {
        this.performanceSettlementMonth = performanceSettlementMonth;
    }

    public Date getRemainingPlanCalcStartMonth() {
        return remainingPlanCalcStartMonth;
    }

    public void setRemainingPlanCalcStartMonth(Date remainingPlanCalcStartMonth) {
        this.remainingPlanCalcStartMonth = remainingPlanCalcStartMonth;
    }

    public Date getDataTime() {
        return dataTime;
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }
}
