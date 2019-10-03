package com.allianz.sd.core.agencyplan;

import org.springframework.stereotype.Component;

import com.allianz.sd.core.jpa.model.AgencyPlanDetail;
import com.allianz.sd.core.jpa.model.AgentYearData;

@Component
public interface DrilldownRule {
	boolean isDrilldown(AgencyPlanDetail agencyPlanDetail,AgentYearData agentYearData);
}
