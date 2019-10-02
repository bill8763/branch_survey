package com.allianz.sd.core.service.bean;

import com.allianz.sd.core.api.model.AgencyPlan;
import com.allianz.sd.core.api.model.AgencyPlanDetailInfo;
import com.allianz.sd.core.api.model.TeamProgress;
import com.allianz.sd.core.api.model.TeamProgressDetail;
import com.allianz.sd.core.exception.UnknownDirectUnit;

/**
 * Enumerated of Direct unit
 * for 
 * 1. progress add team unit
 * 2. agency plan add direct unit
 */
public enum DirectUnit {
    DIRECT,INDIRECT;
	
	public void addProgressTeamUnit(TeamProgress teamProgress, TeamProgressDetail directUnitItem) throws Exception {
        switch(this) {
            case DIRECT:
            	teamProgress.addDirectUnitItem(directUnitItem);
            	break;
            case INDIRECT:
	            teamProgress.addInDirectUnitItem(directUnitItem);
	            break;
            default:
    			throw new UnknownDirectUnit("progress team unit",this.toString());
        }
    }

	public void addAgencyPlanDetailUnit(AgencyPlan agencyPlans, AgencyPlanDetailInfo agencyPlanDetailInfo) {
		switch(this) {
		case DIRECT:
			agencyPlans.addDirectUnitItem(agencyPlanDetailInfo);
			break;
		case INDIRECT:
			agencyPlans.addInDirectUnitItem(agencyPlanDetailInfo);
			break;
		default:
			throw new UnknownDirectUnit("agency plan detail unit",this.toString());
		}
	}
}
