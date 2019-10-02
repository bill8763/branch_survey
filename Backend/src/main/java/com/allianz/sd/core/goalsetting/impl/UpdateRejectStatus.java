package com.allianz.sd.core.goalsetting.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.allianz.sd.core.goalsetting.GoalStatusChangeHandler;
import com.allianz.sd.core.goalsetting.bean.GoalStatusSubject;
import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.jpa.repository.AgencyPlanDetailSyncRepository;

@Component
public class UpdateRejectStatus implements GoalStatusChangeHandler {

    private Logger logger = LoggerFactory.getLogger(UpdateRejectStatus.class);

    @Autowired
    private AgencyPlanDetailSyncRepository agencyPlanDetailSyncRepository;

    @Override
    @Transactional
    public void onStatusChange(GoalStatusSubject goalStatusSubject) throws Exception{

        AgentYearData agentYearData = goalStatusSubject.getAgentYearData();

        int dataYear = agentYearData.getIdentity().getDataYear();
        String organizationalUnit = agentYearData.getIdentity().getOrganizationalUnit();
        String agentID = agentYearData.getIdentity().getAgentID();
        String goalSigningSupervisor = agentYearData.getGoalSigningSupervisor();

        logger.debug("Update agency plan isApprove status");
        agencyPlanDetailSyncRepository.updateAgencyPlanDetailSyncApproveStatus("N",dataYear,organizationalUnit,goalSigningSupervisor,agentID);
    }
}
