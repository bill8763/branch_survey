package com.allianz.sd.core.service.bean;

/**
 * @author bill8
 *
 */
public enum GoalSettingValue {
	PERSON_FYFC,PERSON_ANP,PER_CASE_FYFC,
	TEAM_FYFC,TEAM_ANP,TEAM_MANPOWER,TEAM_RECRUITMENT,
	ACTIVITY_FIND,ACTIVITY_SCHEDULE,ACTIVITY_MEET,ACTIVITY_SUBMIT,ACTIVITY_INFORCE,ACTIVITY_FYFC,ACTIVITY_DAYS,
	ANNUAL_CONVENTION,MDRT,PROMOTION_PLAN;

	public String toAgencyValue() {
		switch(this) {
			case PERSON_FYFC: { return "FYFC"; }
			case PERSON_ANP: { return "ANP"; }
			case PER_CASE_FYFC: { return "PER_CASE_FYFC"; }
			case TEAM_FYFC: { return "FYFC"; }
			case TEAM_ANP: { return "ANP"; }
			case TEAM_MANPOWER: { return "Manpower"; }
			case TEAM_RECRUITMENT: { return "Recruitment"; }

		}
		return "";
	}
}
