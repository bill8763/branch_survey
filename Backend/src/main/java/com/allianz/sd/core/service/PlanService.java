package com.allianz.sd.core.service;

import java.util.List;
import java.util.Map;

import com.allianz.sd.core.jpa.model.GoalSettingSplitValue;
import com.allianz.sd.core.service.bean.AgencyPlanDataType;
import com.allianz.sd.core.service.bean.Goal;
import com.allianz.sd.core.service.bean.GoalSettingValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allianz.sd.core.jpa.repository.AgencyPlanPileRepository;
import com.allianz.sd.core.jpa.repository.GoalSettingSplitValueRepository;

@Service
public class PlanService {

    private static final Logger logger = LoggerFactory.getLogger(PlanService.class);

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private GoalSettingSplitValueRepository goalSettingSplitValueRepository;

    @Autowired
    private AgencyPlanPileRepository agencyPlanPileRepository;

    @Autowired
    private UtilsService utilsService;

    @Autowired
    private StringService stringService;

    /**
     * 取得年度某月之後的Plan加總
     * @param goal
     * @param planCalcStartMonth
     * @return
     */
    public long getYearPersonalTotalPlan(Goal goal , int planCalcStartMonth , AgencyPlanDataType agencyPlanDataType) {
        return getYearPersonalTotalPlan(goal,planCalcStartMonth,-1, agencyPlanDataType);
    }

    /**
     * 取得年度某月之後到結束月的Plan加總
     * @param goal
     * @param planCalcStartMonth
     * @param planCalcEndMonth
     * @return
     */
    public long getYearPersonalTotalPlan(Goal goal, int planCalcStartMonth , int planCalcEndMonth , AgencyPlanDataType agencyPlanDataType) {

        logger.trace("planCalcStartMonth = "+planCalcStartMonth+" , planCalcEndMonth = "+planCalcEndMonth);

        if(planCalcEndMonth == -1) planCalcEndMonth = 12;

        Map<Integer, String> planMap = (agencyPlanDataType == AgencyPlanDataType.FYFC) ? goal.getFyfcPlan() : goal.getAnpPlan();

        return calcTotalPlan(planMap,planCalcStartMonth,planCalcEndMonth);
    }

    public long getYearPersonalTotalPlan(int goalSettingSeq, int planCalcStartMonth, AgencyPlanDataType agencyPlanDataType) {
        Map<Integer,String> planMap = getPlanByMonth(goalSettingSeq, agencyPlanDataType);

        return calcTotalPlan(planMap,planCalcStartMonth,12);
    }

    private long calcTotalPlan(Map<Integer, String> planMap , int planCalcStartMonth , int planCalcEndMonth) {
        long YearTotalPlan = 0;

        for (Map.Entry<Integer, String> entry : planMap.entrySet()) {
            int month = entry.getKey();
            if (month >= planCalcStartMonth && month <= planCalcEndMonth) {
                String value = entry.getValue();
                if(stringService.isNull(value) || !stringService.isNumeric(value)) value = "0";
                YearTotalPlan += Long.parseLong(value);
            }
            logger.trace("YearTotalPlan = "+YearTotalPlan);
        }

        return YearTotalPlan;
    }

    public Map<Integer,String> getPlanByMonth(int goalSettingSeq, AgencyPlanDataType agencyPlanDataType) {
        List<Object[]> list = goalSettingSplitValueRepository.getPlanByMonth(goalSettingSeq , agencyPlanDataType.toGoalValueMapping());

        return utilsService.listInteger2Map(list);
    }

    /**
     * 設定某業務員最後被approve後的每月plan
     * @param agentID
     * @param todayYear
     * @return
     */
    public void setPlanByMonth(Goal goal , String agentID, int todayYear) {
        List<GoalSettingSplitValue> list = goalSettingSplitValueRepository.getPlanByMonth(organizationService.getOrganizationalUnit(), agentID,todayYear);
        for(GoalSettingSplitValue goalSettingSplitValue: list) {
            String mappingID = goalSettingSplitValue.getIdentity().getMappingID();
            int timebaseSeq = goalSettingSplitValue.getIdentity().getTimeBaseSeq();
            String planStr = goalSettingSplitValue.getSetValue();

            if(!stringService.isNumeric(planStr) || stringService.isNull(planStr)) planStr = "0";
            int month = Integer.parseInt(String.valueOf(timebaseSeq));

            if(GoalSettingValue.PERSON_FYFC.toString().equals(mappingID)) goal.addFyfcPlan(month,planStr);
            else if(GoalSettingValue.PERSON_ANP.toString().equals(mappingID)) goal.addAnpPlan(month,planStr);
        }

    }


    /**
     * 取得某年度TeamLeader下屬Plan總合
     * @param agentID
     * @param todayYear
     * @param planCalcStartMonth
     * @return
     */

    public long getYearSubordinateTotalPlan(String agentID,int todayYear,int planCalcStartMonth , int planCalcEndMonth , AgencyPlanDataType agencyPlanDataType) {
        long plan = 0;

        if(planCalcEndMonth == -1) planCalcEndMonth = 12;

        List<String> list = agencyPlanPileRepository.getAgentIDList(agentID, todayYear);
        if (list.size() > 0) {

            List<String> planVals = goalSettingSplitValueRepository.getTotalPlan(todayYear, planCalcStartMonth,planCalcEndMonth, list , agencyPlanDataType.toGoalValueMapping());
            for(String planVal : planVals) {
                if(stringService.isNumeric(planVal)) plan += Long.parseLong(planVal);
            }

        }

        return plan;
    }

    /**
     * 取得Leader底下已核准的每月plan(不包含自己)
     * @param agentID
     * @param todayYear
     * @return
     */
    public Map<Integer, String> getYearSubordinatePlanExceptSelf(String agentID, int todayYear,AgencyPlanDataType agencyPlanDataType) {
        List<Object[]> list = goalSettingSplitValueRepository.getYearSubordinatePlanExceptSelf(agentID, todayYear,agencyPlanDataType.toGoalValueMapping());
        return utilsService.listInteger2Map(list);
    }

}
