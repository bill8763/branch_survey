package com.allianz.sd.core.impl;

import com.allianz.sd.core.InstanceCode;
import com.allianz.sd.core.SNDSpec;
import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.progress.CalcTeamProgressForecastStrategy;
import com.allianz.sd.core.progress.CalcTeamProgressGoalStrategy;
import com.allianz.sd.core.progress.CalcTeamProgressShortfallStrategy;
import com.allianz.sd.core.service.bean.Goal;
import com.allianz.sd.core.service.bean.PerformanceTime;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Documented;
import java.util.List;
import java.util.Map;

@Component
@Scope("prototype")
@SNDSpec(desc = "顯示- -")
public class TeamProgressDashStrategy implements CalcTeamProgressGoalStrategy , CalcTeamProgressForecastStrategy , CalcTeamProgressShortfallStrategy {

    private long val = InstanceCode.DASHED;

    @Override
    public long getValue(PerformanceTime performanceTime, AgentYearData agentYearData, Goal goalObj) {
        return val;
    }

    @Override
    public long getValue(PerformanceTime performanceTime, AgentYearData agentYearData, Goal goalObj, long goal, long actual) {
        return val;
    }

    @Override
    public long getValue(long goal, long actual, long forecase) {
        return val;
    }
}
