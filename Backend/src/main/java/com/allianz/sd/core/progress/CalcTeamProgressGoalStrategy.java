package com.allianz.sd.core.progress;


import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.service.bean.Goal;
import com.allianz.sd.core.service.bean.PerformanceTime;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Component
public interface CalcTeamProgressGoalStrategy {
    public long getValue(PerformanceTime performanceTime,
                        AgentYearData agentYearData,
                        Goal goalObj);

}
