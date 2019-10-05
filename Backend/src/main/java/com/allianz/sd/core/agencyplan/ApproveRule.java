package com.allianz.sd.core.agencyplan;

import com.allianz.sd.core.jpa.model.AgencyPlanDetail;
import com.allianz.sd.core.jpa.model.AgentYearData;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface ApproveRule {
    boolean isApprove(Map<String,String> pendingApproveMap,AgencyPlanDetail agencyPlanDetail, AgentYearData agentYearData);
}
