package com.allianz.sd.core.impl;

import com.allianz.sd.core.SNDSpec;
import com.allianz.sd.core.progress.CalcTeamProgressShortfallStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
@SNDSpec(desc = "Forecast - Actual")
public class TeamProgressShortfallByForecastActual implements CalcTeamProgressShortfallStrategy {

    private Logger logger = LoggerFactory.getLogger(TeamProgressShortfallByForecastActual.class);

    @Override
    public long getValue(long goal, long actual, long forecase) {
        long shartfall = forecase - actual;

        logger.trace("forecase = " + forecase + ",actual = " + actual + ", shartfall = " + shartfall);

        if(shartfall < 0) shartfall = 0;

        return shartfall;
    }

}
