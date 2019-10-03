package com.allianz.sd.core.impl;

import com.allianz.sd.core.InstanceCode;
import com.allianz.sd.core.SNDSpec;
import com.allianz.sd.core.agencyplan.CalcAgencyPlanRule;
import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.jpa.repository.TeamProgressDetailRepository;
import com.allianz.sd.core.jpa.repository.TeamProgressRepository;
import com.allianz.sd.core.progress.CalcTeamProgressForecastStrategy;
import com.allianz.sd.core.service.bean.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Map;

@Component
@Scope("prototype")
@SNDSpec(desc = "依月季年計算Plan-規則與AgencyPlan bottom-up一致")
public class TeamProgressFollowAgencyPlanStrategy implements CalcTeamProgressForecastStrategy {

    private Logger logger = LoggerFactory.getLogger(TeamProgressFollowAgencyPlanStrategy.class);

    private CalcAgencyPlanRule calcAgencyPlanRule;
    private String displayBlock = null;
    private int startMonth = -1;
    private int endMonth = -1;
    private AgencyPlanDataType agencyPlanDataType;

    public void setCalcAgencyPlanRule(CalcAgencyPlanRule calcAgencyPlanRule) {
        this.calcAgencyPlanRule = calcAgencyPlanRule;
    }

    public void setDisplayBlock(String displayBlock) {
        this.displayBlock = displayBlock;
    }

    public void setStartMonth(int startMonth) {
        this.startMonth = startMonth;
    }

    public void setEndMonth(int endMonth) {
        this.endMonth = endMonth;
    }

    public void setAgencyPlanDataType(AgencyPlanDataType agencyPlanDataType) {
        this.agencyPlanDataType = agencyPlanDataType;
    }

    @Override
    public long getValue(PerformanceTime performanceTime,
                        AgentYearData agentYearData,
                        Goal goalObj,long goal, long actual) {

        return calcAgencyPlanRule.calcDetailPlan(agentYearData,displayBlock,goalObj,startMonth,endMonth, agencyPlanDataType);
    }

}
