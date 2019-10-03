package com.allianz.sd.core.impl;

import com.allianz.sd.core.SNDSpec;
import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.progress.CalcTeamProgressForecastStrategy;
import com.allianz.sd.core.service.GoalService;
import com.allianz.sd.core.service.bean.Goal;
import com.allianz.sd.core.service.bean.PerformanceTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Component
@Scope("prototype")
@SNDSpec(desc = "將送審給我的業務員將GoalExpected加總")
public class TeamProgressRecruitmentTimeBetweenStrategy implements CalcTeamProgressForecastStrategy {

    @Autowired
    private GoalService goalService;

    private int startMonth = -1;
    private int endMonth = -1;

    public void setStartMonth(int startMonth) {
        this.startMonth = startMonth;
    }

    public void setEndMonth(int endMonth) {
        this.endMonth = endMonth;
    }

    @Override
    public long getValue(PerformanceTime performanceTime,
                        AgentYearData agentYearData,
                        Goal goalObj,long goal, long actual) {

        int year = agentYearData.getIdentity().getDataYear();
        String agentID = agentYearData.getIdentity().getAgentID();

        Map<Integer,Integer> goalExpectedTotalMap = goalService.getYearSubordinateGoalExpected(year,agentID);

        return goalService.calcGoalExpected(goalExpectedTotalMap,startMonth,endMonth);

    }

}
