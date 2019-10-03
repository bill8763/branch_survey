package com.allianz.sd.core.progress;

import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.service.bean.Goal;
import com.allianz.sd.core.service.bean.PerformanceTime;
import org.springframework.stereotype.Component;

@Component
public interface CalcTeamProgressForecastStrategy {
    public long getValue(PerformanceTime performanceTime,
                        AgentYearData agentYearData,
                        Goal goalObj,long goal, long actual);

}
