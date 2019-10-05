package com.allianz.sd.core.service.bean;

import com.allianz.sd.core.api.model.*;
import com.allianz.sd.core.exception.UnknownDirectUnit;

/**
 * Enumerated of GoalSetting Status
 */
public enum GoalSettingStatus {
    WAITING,
    OVERDUE,
    PENDING_APPROVAL,
    APPROVED,
    REJECT,
    NOTALLOWED;

 

	public GoalSetting.StatusEnum toAPIValue() {
        switch(this) {
	        case WAITING:
	        	return GoalSetting.StatusEnum.W;
	        case PENDING_APPROVAL:
	        	return GoalSetting.StatusEnum.P;
	        case APPROVED:
	        	return GoalSetting.StatusEnum.A;
	        case OVERDUE:
	        	return GoalSetting.StatusEnum.R;
	        case REJECT:
	        	return GoalSetting.StatusEnum.R;
	        case NOTALLOWED:
	        	return GoalSetting.StatusEnum.N;
	        default:
	        	return null;
        }

	}
}