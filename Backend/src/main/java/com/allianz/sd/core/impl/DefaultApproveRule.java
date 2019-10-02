package com.allianz.sd.core.impl;

import com.allianz.sd.core.agencyplan.ApproveRule;
import com.allianz.sd.core.jpa.model.AgencyPlanDetail;
import com.allianz.sd.core.jpa.model.AgentYearData;

import java.util.Map;

public class DefaultApproveRule implements ApproveRule {
    @Override
    public boolean isApprove(Map<String, String> pendingApproveMap, AgencyPlanDetail agencyPlanDetail, AgentYearData agentYearData) {

        String agentID = agencyPlanDetail.getIdentity().getAgentID();
        String subAgentID = agencyPlanDetail.getSubAgentID();

        return pendingApproveMap.containsKey(subAgentID) &&
                pendingApproveMap.get(subAgentID).equalsIgnoreCase(agentID);
    }
}
