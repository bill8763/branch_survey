package com.allianz.sd.core.impl;

import com.allianz.sd.core.agencyplan.CalcAgencyPlanRule;
import com.allianz.sd.core.jpa.model.AgencyPlan;
import com.allianz.sd.core.jpa.model.AgencyPlanDetail;
import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.service.AgencyPlanService;
import com.allianz.sd.core.service.PlanService;
import com.allianz.sd.core.service.bean.AgencyPlanDataType;
import com.allianz.sd.core.service.bean.AgencyPlanMaster;
import com.allianz.sd.core.service.bean.Goal;
import com.allianz.sd.core.service.bean.GoalSettingValue;
import org.springframework.beans.factory.annotation.Autowired;

public class DefaultCalcAgencyPlanRule implements CalcAgencyPlanRule {

    @Autowired
    private PlanService planService;

    @Autowired
    private AgencyPlanService agencyPlanService;

    @Override
    public AgencyPlanMaster calcAgencyPlanMaster(AgencyPlan agencyPlan, int yearPlanStartMonth) {

        String organizationalUnit = agencyPlan.getIdentity().getOrganizationalUnit();
        String agentID = agencyPlan.getIdentity().getAgentID();
        int dataYear = agencyPlan.getIdentity().getDataYear();

        return agencyPlanService.getYearAgencyPlanMaster(organizationalUnit,agentID , dataYear ,yearPlanStartMonth);
    }

    @Override
    public long calcDetailGoal(AgentYearData agentYearData , String displayBlock, Goal goalObj , AgencyPlanDataType agencyPlanDataType) {

        String personFYFC = goalObj.getGoalValue(GoalSettingValue.PERSON_FYFC);
        String personANP = goalObj.getGoalValue(GoalSettingValue.PERSON_ANP);
        String teamFYFC = goalObj.getGoalValue(GoalSettingValue.TEAM_FYFC);
        String teamANP = goalObj.getGoalValue(GoalSettingValue.TEAM_ANP);

        if(agencyPlanDataType == AgencyPlanDataType.FYFC) return "DIRECT".equalsIgnoreCase(displayBlock) ? Long.parseLong(personFYFC) : Long.parseLong(teamFYFC);
        else return "DIRECT".equalsIgnoreCase(displayBlock) ? Long.parseLong(personANP) : Long.parseLong(teamANP);
    }

    @Override
    public long calcDetailPlan(AgentYearData agentYearData , String displayBlock, Goal goalObj, int yearPlanStartMonth,int yearPlanEndMonth , AgencyPlanDataType agencyPlanDataType) {

        String subAgentID = agentYearData.getIdentity().getAgentID();
        int dataYear = agentYearData.getIdentity().getDataYear();

        //if Direct plan is calc Personal Plan , else if InDirect plan is calc Bottom-Plan
        return "DIRECT".equalsIgnoreCase(displayBlock) ?
                planService.getYearPersonalTotalPlan(goalObj,yearPlanStartMonth,yearPlanEndMonth, agencyPlanDataType) :
                planService.getYearSubordinateTotalPlan(subAgentID, dataYear,yearPlanStartMonth,yearPlanEndMonth, agencyPlanDataType);
    }

    @Override
    public long calcDetailManpower(AgentYearData agentYearData , String displayBlock, Goal goalObj) {
         return "INDIRECT".equalsIgnoreCase(displayBlock) ? Long.parseLong(goalObj.getGoalValue(GoalSettingValue.TEAM_MANPOWER)) : 0;
    }

    @Override
    public long calcDetailRecruitment(AgentYearData agentYearData , String displayBlock, Goal goalObj) {
        return "INDIRECT".equalsIgnoreCase(displayBlock) ? Long.parseLong(goalObj.getGoalValue(GoalSettingValue.TEAM_RECRUITMENT)) : 0;
    }
}
