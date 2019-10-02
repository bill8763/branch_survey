package com.allianz.sd.core.service.bean;

import com.allianz.sd.core.InstanceCode;

public class AgencyPlanMaster {
    private long fyfcPlan = InstanceCode.DASHED;
    private long anpPlan = InstanceCode.DASHED;
    private long manPowerPlan = InstanceCode.DASHED;
    private long recruitmentPlan = InstanceCode.DASHED;

    public long getFyfcPlan() {
        return fyfcPlan;
    }

    public void setFyfcPlan(long fyfcPlan) {
        this.fyfcPlan = fyfcPlan;
    }

    public long getAnpPlan() {
        return anpPlan;
    }

    public void setAnpPlan(long anpPlan) {
        this.anpPlan = anpPlan;
    }

    public long getManPowerPlan() {
        return manPowerPlan;
    }

    public void setManPowerPlan(long manPowerPlan) {
        this.manPowerPlan = manPowerPlan;
    }

    public long getRecruitmentPlan() {
        return recruitmentPlan;
    }

    public void setRecruitmentPlan(long recruitmentPlan) {
        this.recruitmentPlan = recruitmentPlan;
    }
}
