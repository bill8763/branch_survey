package com.allianz.sd.core.impl;

import com.allianz.sd.core.InstanceCode;
import com.allianz.sd.core.SNDSpec;
import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.jpa.repository.TeamProgressDetailRepository;
import com.allianz.sd.core.jpa.repository.TeamProgressRepository;
import com.allianz.sd.core.progress.CalcTeamProgressForecastStrategy;
import com.allianz.sd.core.service.bean.Goal;
import com.allianz.sd.core.service.bean.GoalSettingValue;
import com.allianz.sd.core.service.bean.PerformanceTime;
import com.allianz.sd.core.service.bean.TeamProgressTimeField;
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
@SNDSpec(desc = "Actual + Plan")
public class TeamProgressFollowAgencyPlanAddActualStrategy implements CalcTeamProgressForecastStrategy {

    private Logger logger = LoggerFactory.getLogger(TeamProgressFollowAgencyPlanAddActualStrategy.class);

    private CalcTeamProgressForecastStrategy calcTeamProgressForecastStrategy;

    public void setCalcTeamProgressForecastStrategy(CalcTeamProgressForecastStrategy calcTeamProgressForecastStrategy) {
        this.calcTeamProgressForecastStrategy = calcTeamProgressForecastStrategy;
    }

    @Override
    public long getValue(PerformanceTime performanceTime,
                        AgentYearData agentYearData,
                        Goal goalObj,long goal, long actual) {

        long plan = calcTeamProgressForecastStrategy.getValue(performanceTime,agentYearData,goalObj,goal,actual);

        if(actual == InstanceCode.DASHED) actual = 0;
        return plan + actual;

    }

}
