package com.allianz.sd.core.impl;

import com.allianz.sd.core.SNDSpec;
import com.allianz.sd.core.jpa.repository.TeamProgressDetailRepository;
import com.allianz.sd.core.jpa.repository.TeamProgressRepository;
import com.allianz.sd.core.progress.CalcTeamProgressShortfallStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
@SNDSpec(desc = "Goal - Actual")
public class TeamProgressShortfallByGoalActual implements CalcTeamProgressShortfallStrategy {

    private Logger logger = LoggerFactory.getLogger(TeamProgressShortfallByGoalActual.class);

    @Autowired
    private TeamProgressRepository teamProgressRepository;

    @Autowired
    private TeamProgressDetailRepository teamProgressDetailRepository;

    @Override
    public long getValue(long goal, long actual,long forecase) {
        long shartfall = goal - actual;

        if(shartfall < 0) shartfall = 0;

        return shartfall;
    }

}
