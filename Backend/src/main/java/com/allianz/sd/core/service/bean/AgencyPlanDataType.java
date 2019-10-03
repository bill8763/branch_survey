package com.allianz.sd.core.service.bean;

public enum AgencyPlanDataType {
	FYFC,ANP,Manpower,Recruitment;
//	Actual, FYFC,ANP, Goal,Manpower, Recruitment , Shortfall;

	public String toGoalValueMapping() {
		switch (this) {
			case FYFC : return "PERSON_FYFC";
			case ANP : return "PERSON_ANP";
		}

		return "";
	}
}
