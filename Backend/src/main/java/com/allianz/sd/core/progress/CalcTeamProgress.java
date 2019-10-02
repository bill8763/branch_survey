package com.allianz.sd.core.progress;

import com.allianz.sd.core.jpa.model.*;
import com.allianz.sd.core.service.bean.*;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface CalcTeamProgress {


    /**
     * 取得計算Actual的業績起算月(需自行判斷個人或團隊)
     * @param agentYearData
     * @param agencyPlanDetail
     * @return
     */
    public int getActualStartMonth(AgentYearData agentYearData , AgencyPlanDetail agencyPlanDetail);

    /**
     * 取得計算Actual的PerformanceTable
     * @param agentYearData
     * @param agencyPlanDetail
     * @return
     */
    public String getAgentActualPerformanceType(AgentYearData agentYearData , AgencyPlanDetail agencyPlanDetail);

    public CalcTeamProgressGoalStrategy getGoalCalcStrategy(PerformanceTime performanceTime,TeamProgressTimeField timeField, TeamProgressDataType dataType, AgentYearData agentYearData, String displayBlock);
    public CalcTeamProgressForecastStrategy getForecastCalcStrategy(PerformanceTime performanceTime,TeamProgressTimeField timeField, TeamProgressDataType dataType, AgentYearData agentYearData, String displayBlock);
    public CalcTeamProgressShortfallStrategy getShortfallCalcStrategy(PerformanceTime performanceTime,TeamProgressTimeField timeField, TeamProgressDataType dataType, AgentYearData agentYearData, String displayBlock);

    public void onGoalExpectedChange(int dataYear , String agentID,String organizationalUnit);
}
