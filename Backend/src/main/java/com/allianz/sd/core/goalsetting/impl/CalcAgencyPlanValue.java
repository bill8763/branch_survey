package com.allianz.sd.core.goalsetting.impl;

import com.allianz.sd.core.goalsetting.GoalStatusChangeHandler;
import com.allianz.sd.core.goalsetting.bean.GoalStatusSubject;
import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.jpa.repository.AgencyPlanDetailSyncRepository;
import com.allianz.sd.core.service.AgencyPlanBottomUpService;
import com.allianz.sd.core.service.bean.Goal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CalcAgencyPlanValue implements GoalStatusChangeHandler {

    private Logger logger = LoggerFactory.getLogger(CalcAgencyPlanValue.class);

    @Autowired
    private AgencyPlanBottomUpService agencyPlanBottomUpService;

    @Autowired
    private AgencyPlanDetailSyncRepository agencyPlanDetailSyncRepository;

    @Override
    @Transactional
    public void onStatusChange(GoalStatusSubject goalStatusSubject) throws Exception{

        AgentYearData agentYearData = goalStatusSubject.getAgentYearData();
        Goal originGoalObj = goalStatusSubject.getApproveGoal();
        Goal goalObj = goalStatusSubject.getAfterGoal();

        int dataYear = agentYearData.getIdentity().getDataYear();
        String organizationalUnit = agentYearData.getIdentity().getOrganizationalUnit();
        String agentID = agentYearData.getIdentity().getAgentID();
        String goalSigningSupervisor = agentYearData.getGoalSigningSupervisor();

        logger.debug("BottomUp Update AgencyPlan");
        agencyPlanBottomUpService.bottomUp(agentYearData,originGoalObj,goalObj);

        logger.debug("Update agency plan isApprove status");
        agencyPlanDetailSyncRepository.updateAgencyPlanDetailSyncApproveStatus("N",dataYear,organizationalUnit,goalSigningSupervisor,agentID);
    }
}
