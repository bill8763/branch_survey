package com.allianz.sd.core.impl;

import com.allianz.sd.core.InstanceCode;
import com.allianz.sd.core.agencyplan.CalcAgencyPlanRule;
import com.allianz.sd.core.jpa.model.*;
import com.allianz.sd.core.progress.*;
import com.allianz.sd.core.service.*;
import com.allianz.sd.core.service.bean.*;
import com.allianz.sd.core.service.bean.GoalSettingValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Component
public class DefaultCalcTeamProgress implements CalcTeamProgress {

    private Logger logger = LoggerFactory.getLogger(DefaultCalcTeamProgress.class);

    @Autowired
    private GoalService goalService;

    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private AgentDataService agentDataService;

    @Autowired
    private ProgressService progressService;

    @Autowired
    private DateService dateService;

    @Override
    public int getActualStartMonth(AgentYearData agentYearData , AgencyPlanDetail agencyPlanDetail) {
        return "DIRECT".equalsIgnoreCase(agencyPlanDetail.getIdentity().getDisplayBlock()) ?
                agentDataService.getCalcPersonalActualStartMonth(agentYearData) :
                agentDataService.getCalcTeamActualStartMonth(agentYearData);
    }


    @Override
    public String getAgentActualPerformanceType(AgentYearData agentYearData, AgencyPlanDetail agencyPlanDetail) {
        String displayBlock = agencyPlanDetail.getIdentity().getDisplayBlock();
        return "DIRECT".equalsIgnoreCase(displayBlock) ?
                agentYearData.getPersonnelPerformanceType() :
                agentYearData.getTeamPerformanceType();
    }


    @Override
    public CalcTeamProgressGoalStrategy getGoalCalcStrategy(PerformanceTime performanceTime,TeamProgressTimeField timeField, TeamProgressDataType dataType, AgentYearData agentYearData, String displayBlock) {

        //其餘月與季的Goal統一都是Dash
        if(timeField.equals(TeamProgressTimeField.Month) || timeField.equals(TeamProgressTimeField.Quarter)) {
            return beanFactory.getBean(TeamProgressDashStrategy.class);
        }
        else {
            //DIRECT的Manpower / Recruitment都是DASH
            if("DIRECT".equalsIgnoreCase(displayBlock) && (dataType.equals(TeamProgressDataType.Manpower) || dataType.equals(TeamProgressDataType.Recruitment))) {
                return beanFactory.getBean(TeamProgressDashStrategy.class);
            }
            else {
                //其他統一都是抓Goal設定
                TeamProgressTeamGoalStrategy teamProgressTeamGoalStrategy = beanFactory.getBean(TeamProgressTeamGoalStrategy.class);
                teamProgressTeamGoalStrategy.setGoalSettingValue(dataType.toGoalSettingValue(displayBlock));
                return teamProgressTeamGoalStrategy;
            }

        }

    }

    @Override
    public CalcTeamProgressForecastStrategy getForecastCalcStrategy(PerformanceTime performanceTime,TeamProgressTimeField timeField, TeamProgressDataType dataType, AgentYearData agentYearData, String displayBlock) {
        CalcAgencyPlanRule calcAgencyPlanRule = (CalcAgencyPlanRule) beanFactory.getBean(InstanceCode.CalcAgencyPlanDetailPlan);

        //依照不同時間維度取得plan起迄時間
        int planStartMonth = -1;
        int planEndMonth = -1;

        if(timeField.equals(TeamProgressTimeField.Month)) {
            planStartMonth = planEndMonth = performanceTime.getMonth();
        }
        else if(timeField.equals(TeamProgressTimeField.Quarter)) {
            List<Integer> monthOfQuarter = performanceTime.getMonthOfQuarter();
            planStartMonth =  performanceTime.getPlanClacStartMonth();
            planEndMonth = monthOfQuarter.get(monthOfQuarter.size()-1);
        }
        else if(timeField.equals(TeamProgressTimeField.Year)) {
            planStartMonth = performanceTime.getPlanClacStartMonth();
            planEndMonth = performanceTime.getEndMonthOfYear();
        }

        //FYFC / ANP計算邏輯Follow AgencyPlan
        if(dataType.equals(TeamProgressDataType.FYFC) || dataType.equals(TeamProgressDataType.ANP)) {

            TeamProgressFollowAgencyPlanStrategy teamProgressFollowAgencyPlanStrategy = beanFactory.getBean(TeamProgressFollowAgencyPlanStrategy.class);
            teamProgressFollowAgencyPlanStrategy.setCalcAgencyPlanRule(calcAgencyPlanRule);
            teamProgressFollowAgencyPlanStrategy.setDisplayBlock(displayBlock);
            teamProgressFollowAgencyPlanStrategy.setStartMonth(planStartMonth);
            teamProgressFollowAgencyPlanStrategy.setEndMonth(planEndMonth);
            teamProgressFollowAgencyPlanStrategy.setAgencyPlanDataType(dataType.toAgencyPlanDataType());

            //如果是月就直接抓plan
            if(timeField.equals(TeamProgressTimeField.Month)) {
                return teamProgressFollowAgencyPlanStrategy;
            }
            //季跟年統一用plan + actual
            else {
                TeamProgressFollowAgencyPlanAddActualStrategy teamProgressFollowAgencyPlanAddActualStrategy = beanFactory.getBean(TeamProgressFollowAgencyPlanAddActualStrategy.class);
                teamProgressFollowAgencyPlanAddActualStrategy.setCalcTeamProgressForecastStrategy(teamProgressFollowAgencyPlanStrategy);
                return teamProgressFollowAgencyPlanAddActualStrategy;
            }
        }
        //Manpower統一的Forecast都是DASH
        else if(dataType.equals(TeamProgressDataType.Manpower)) {
            return beanFactory.getBean(TeamProgressDashStrategy.class);
        }
        else if(dataType.equals(TeamProgressDataType.Recruitment)) {

            //如果是DIRECT都沒有Recruitment的Forecast
            if("DIRECT".equalsIgnoreCase(displayBlock)) return beanFactory.getBean(TeamProgressDashStrategy.class);
            else {
                TeamProgressRecruitmentTimeBetweenStrategy teamProgressRecruitmentTimeBetweenStrategy = beanFactory.getBean(TeamProgressRecruitmentTimeBetweenStrategy.class);
                teamProgressRecruitmentTimeBetweenStrategy.setStartMonth(planStartMonth);
                teamProgressRecruitmentTimeBetweenStrategy.setEndMonth(planEndMonth);
                return teamProgressRecruitmentTimeBetweenStrategy;
            }
        }

        return null;
    }

    @Override
    public CalcTeamProgressShortfallStrategy getShortfallCalcStrategy(PerformanceTime performanceTime,TeamProgressTimeField timeField, TeamProgressDataType dataType, AgentYearData agentYearData, String displayBlock) {

        String appUseMode = agentYearData.getAppUseMode();

        if(appUseMode.equalsIgnoreCase(AppUseMode.MANAGER) || appUseMode.equalsIgnoreCase(AppUseMode.SUPERVISOR)) return beanFactory.getBean(TeamProgressDashStrategy.class);
        else if(timeField.equals(TeamProgressTimeField.Year)) return beanFactory.getBean(TeamProgressShortfallByGoalActual.class);
        else return beanFactory.getBean(TeamProgressShortfallByForecastActual.class);
    }

    @Override
    public void onGoalExpectedChange(int dataYear , String agentID,String organizationalUnit) {

        List<String> leaders = goalService.getBottomupYearSubordinateLeaders(dataYear,agentID,organizationalUnit);
        if(leaders != null) {

            //自己也要重算
            leaders.add(agentID);

            for(String leaderID : leaders) {

                AgentYearData agentYearData = agentDataService.getAgentYearData(leaderID,dataYear,organizationalUnit);
                String performanceTable = agentYearData.getPerformanceTable();

                Date date = dateService.getTodayDate();
                PerformanceTime performanceTime = progressService.getPerformanceTime(date, organizationalUnit,performanceTable);

                //依照不同時間維度取得plan起迄時間
                int planStartMonth = -1;
                int planEndMonth = -1;

                for(TeamProgressTimeField timeField : TeamProgressTimeField.values()) {
                    if(timeField.equals(TeamProgressTimeField.Month)) {
                        planStartMonth = planEndMonth = performanceTime.getMonth();
                    }
                    else if(timeField.equals(TeamProgressTimeField.Quarter)) {
                        List<Integer> monthOfQuarter = performanceTime.getMonthOfQuarter();
                        planStartMonth =  performanceTime.getPlanClacStartMonth();
                        planEndMonth = monthOfQuarter.get(monthOfQuarter.size()-1);
                    }
                    else if(timeField.equals(TeamProgressTimeField.Year)) {
                        planStartMonth = performanceTime.getPlanClacStartMonth();
                        planEndMonth = performanceTime.getEndMonthOfYear();
                    }

                    TeamProgressRecruitmentTimeBetweenStrategy teamProgressRecruitmentTimeBetweenStrategy = beanFactory.getBean(TeamProgressRecruitmentTimeBetweenStrategy.class);
                    teamProgressRecruitmentTimeBetweenStrategy.setStartMonth(planStartMonth);
                    teamProgressRecruitmentTimeBetweenStrategy.setEndMonth(planEndMonth);
                    long sum = teamProgressRecruitmentTimeBetweenStrategy.getValue(performanceTime,agentYearData,null,0,0);

                    progressService.updateForecastRecruitment(timeField,sum,leaderID,organizationalUnit,dataYear);
                }


            }


        }



    }
}
