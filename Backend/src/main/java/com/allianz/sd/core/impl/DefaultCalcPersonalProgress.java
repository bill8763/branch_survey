package com.allianz.sd.core.impl;

import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.progress.CalcPersonalProgress;
import com.allianz.sd.core.service.AgentDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;


@Component
public class DefaultCalcPersonalProgress implements CalcPersonalProgress {

    @Autowired
    private AgentDataService agentDataService;

    @Override
    public List<AgentYearData> getCalcPersonalAgentYearDatas(int dataYear, String organizationalUnit) {
        return agentDataService.getAllIsAgentYearData(dataYear,organizationalUnit);
    }

    @Override
    public boolean isFindOverShowDetail(BigDecimal actual, BigDecimal goal) {
        return calcIsNotArriveGoal(actual,goal);
    }

    @Override
    public boolean isScheduleOverShowDetail(BigDecimal actual, BigDecimal goal) {
        return calcIsNotArriveGoal(actual,goal);
    }

    @Override
    public boolean isMeetOverShowDetail(BigDecimal actual, BigDecimal goal) {
        return calcIsNotArriveGoal(actual,goal);
    }

    @Override
    public boolean isSubmitOverShowDetail(BigDecimal actual, BigDecimal goal) {
        return calcIsNotArriveGoal(actual,goal);
    }

    @Override
    public boolean isInforceOverShowDetail(BigDecimal actual, BigDecimal goal) {
        return calcIsNotArriveGoal(actual,goal);
    }

    public static void main(String[] args) {
        DefaultCalcPersonalProgress defaultCalcPersonalProgress = new DefaultCalcPersonalProgress();

        System.out.println(defaultCalcPersonalProgress.isFindOverShowDetail(new BigDecimal(9),new BigDecimal(28.6)));
    }

    private boolean calcIsNotArriveGoal(BigDecimal actual, BigDecimal goal) {
        if(goal == null || goal.intValue() == 0) return true;
        else return !(actual.doubleValue() / goal.doubleValue() >= 0.7);
    }
}
