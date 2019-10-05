package com.allianz.sd.core.service;

import com.allianz.sd.core.jpa.model.AgencyPlan;
import com.allianz.sd.core.jpa.model.AgencyPlanDetail;
import com.allianz.sd.core.jpa.repository.AgencyPlanDetailRepository;
import com.allianz.sd.core.jpa.repository.AgencyPlanDetailSyncRepository;
import com.allianz.sd.core.jpa.repository.AgencyPlanRepository;
import com.allianz.sd.core.jpa.repository.GoalExpectedSplitValueRepository;
import com.allianz.sd.core.service.bean.AgencyPlanMaster;
import com.allianz.sd.core.service.bean.Goal;
import com.allianz.sd.core.service.bean.GoalSettingValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgencyPlanService {

    private Logger logger = LoggerFactory.getLogger(AgencyPlanService.class);

    @Autowired
    private AgencyPlanRepository agencyPlanRepository;

    @Autowired
    private AgencyPlanDetailRepository agencyPlanDetailRepository;

    @Autowired
    private AgencyPlanDetailSyncRepository agencyPlanDetailSyncRepository;

    @Autowired
    private GoalExpectedSplitValueRepository goalExpectedSplitValueRepository;


    /**
     * 取得AgencyPlan Plan的加總
     * @param agentID
     * @param todayYear
     * @return
     */
    public AgencyPlanMaster getYearAgencyPlanMaster(String unit, String agentID , int todayYear , int yearPlanStartMonth) {
        AgencyPlanMaster agencyPlanMaster = new AgencyPlanMaster();

        long fyfc = 0;
        long anp = 0;
        long manPower = 0;
        long manPowerCount = 0;
        long recruitMent=0;

        logger.trace("getAgencyPlanMasterBottomupPlan["+todayYear+"],["+agentID+"],["+yearPlanStartMonth+"]");

        //query fyfc & anp
        fyfc = agencyPlanDetailRepository.getAgencyPlanMasterBottomupPlan(todayYear,agentID,yearPlanStartMonth, GoalSettingValue.PERSON_FYFC.toString());
        anp = agencyPlanDetailRepository.getAgencyPlanMasterBottomupPlan(todayYear,agentID,yearPlanStartMonth, GoalSettingValue.PERSON_ANP.toString());

        //query manpower & recruitmen
        List<Object[]> manpowerAndRecruitmen = agencyPlanDetailSyncRepository.getManpowerAndRecruitment(unit,todayYear,agentID);
        if(manpowerAndRecruitmen.size() != 0) {
            manPower = Long.parseLong(String.valueOf(manpowerAndRecruitmen.get(0)[0]));
            recruitMent = Long.parseLong(String.valueOf(manpowerAndRecruitmen.get(0)[1]));
        }

        //manpower need sum DIRECT people
        manPowerCount = agencyPlanDetailRepository.getManPowerCount(todayYear,agentID);

        //recruitMent need add Agent Goal Expected
        int leaderGoalExpected = goalExpectedSplitValueRepository.getYearGoalExpectedTotal(todayYear,agentID,unit);

        agencyPlanMaster.setFyfcPlan(fyfc);
        agencyPlanMaster.setAnpPlan(anp);
        agencyPlanMaster.setManPowerPlan(manPower+manPowerCount);
        agencyPlanMaster.setRecruitmentPlan(recruitMent+leaderGoalExpected);


        return agencyPlanMaster;
    }

    public List<AgencyPlan> getMasters(int dataYear,String organizationalUnit) {
        logger.info("find AgencyPlan ["+dataYear+"]");
        List<AgencyPlan> agencyPlans = agencyPlanRepository.getAgencyPlans(dataYear,organizationalUnit);
        logger.info("find AgencyPlan size ["+dataYear+"] = " + agencyPlans.size());

        return agencyPlans;
    }

    public List<AgencyPlanDetail> getDetails(int dataYear,String organizationalUnit) {
        logger.info("find AgencyPlanDetail ["+dataYear+"]");
        List<AgencyPlanDetail> agencyPlanDetails = agencyPlanDetailRepository.findDetailAgents(dataYear,organizationalUnit);
        logger.info("find AgencyPlanDetail size ["+dataYear+"] = " + agencyPlanDetails.size());

        return agencyPlanDetails;
    }

    public AgencyPlan findMaster(String agentID , int dataYear,String organizationalUnit) {
        return agencyPlanRepository.getAgencyPlan(agentID,dataYear,organizationalUnit);
    }
}
