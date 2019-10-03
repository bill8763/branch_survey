package com.allianz.sd.core.impl;

import com.allianz.sd.core.agencyplan.DrilldownRule;
import com.allianz.sd.core.jpa.model.AgencyPlanDetail;
import com.allianz.sd.core.jpa.model.AgentYearData;
import org.apache.commons.lang3.StringUtils;

public class DefaultDrilldownRule implements DrilldownRule {

	@Override
	public boolean isDrilldown(AgencyPlanDetail agencyPlanDetail, AgentYearData agentYearData) {
		String agentID = agencyPlanDetail.getIdentity().getAgentID();
		String subAgentGoalSigningSupervisor = agentYearData.getGoalSigningSupervisor();

		return StringUtils.isNotEmpty(subAgentGoalSigningSupervisor) && subAgentGoalSigningSupervisor.equalsIgnoreCase(agentID);
	}

}
