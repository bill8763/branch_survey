package com.allianz.sd.core.impl;

import com.allianz.sd.core.InstanceCode;
import com.allianz.sd.core.SNDSpec;
import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.progress.CalcTeamProgressGoalStrategy;
import com.allianz.sd.core.service.bean.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@SNDSpec(desc = "抓Step5的值，若沒有值則顯示- -")
public class TeamProgressTeamGoalStrategy implements CalcTeamProgressGoalStrategy {

    private Logger logger = LoggerFactory.getLogger(TeamProgressTeamGoalStrategy.class);
    private GoalSettingValue goalSettingValue = null;

    public void setGoalSettingValue(GoalSettingValue goalSettingValue) {
        this.goalSettingValue = goalSettingValue;
    }

    @Override
    public long getValue(PerformanceTime performanceTime, AgentYearData agentYearData, Goal goalObj) {
        return goalSettingValue != null ? Long.parseLong(goalObj.getGoalValue(goalSettingValue)) :InstanceCode.DASHED;
    }
}
