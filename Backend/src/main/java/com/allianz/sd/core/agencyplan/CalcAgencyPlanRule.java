package com.allianz.sd.core.agencyplan;

import com.allianz.sd.core.jpa.model.AgencyPlan;
import com.allianz.sd.core.jpa.model.AgencyPlanDetail;
import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.service.bean.AgencyPlanDataType;
import com.allianz.sd.core.service.bean.AgencyPlanMaster;
import com.allianz.sd.core.service.bean.Goal;
import org.springframework.stereotype.Component;

@Component
public interface CalcAgencyPlanRule {

    public AgencyPlanMaster calcAgencyPlanMaster(AgencyPlan agencyPlan, int yearPlanStartMonth);
    public long calcDetailGoal(AgentYearData agentYearData , String displayBlock, Goal goalObj , AgencyPlanDataType agencyPlanDataType);
    public long calcDetailPlan(AgentYearData agentYearData , String displayBlock, Goal goalObj , int yearPlanStartMonth, int yearPlanEndMonth, AgencyPlanDataType agencyPlanDataType);
    public long calcDetailManpower(AgentYearData agentYearData , String displayBlock, Goal goalObj);
    public long calcDetailRecruitment(AgentYearData agentYearData , String displayBlock, Goal goalObj);
}
