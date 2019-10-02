package com.allianz.sd.core.service.bean;

public enum TeamProgressDataType {
	 FYFC,ANP,Manpower,Recruitment;

	 public GoalSettingValue toGoalSettingValue(String displayBlock) {
	 	switch(this) {
			case FYFC : return "DIRECT".equalsIgnoreCase(displayBlock) ? GoalSettingValue.PERSON_FYFC : GoalSettingValue.TEAM_FYFC;
			case ANP : return "DIRECT".equalsIgnoreCase(displayBlock) ? GoalSettingValue.PERSON_ANP : GoalSettingValue.TEAM_ANP;
			case Manpower : return GoalSettingValue.TEAM_MANPOWER;
			case Recruitment : return GoalSettingValue.TEAM_RECRUITMENT;
		}

		return null;
	 }

	 public AgencyPlanDataType toAgencyPlanDataType() {
		 switch(this) {
			 case FYFC : return AgencyPlanDataType.FYFC;
			 case ANP : return AgencyPlanDataType.ANP;

		 }

		 return null;
	 }
}
