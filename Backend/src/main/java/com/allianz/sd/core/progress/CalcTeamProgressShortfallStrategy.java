package com.allianz.sd.core.progress;

import org.springframework.stereotype.Component;

@Component
public interface CalcTeamProgressShortfallStrategy {
    public long getValue(long goal,long actual,long forecase);

}
