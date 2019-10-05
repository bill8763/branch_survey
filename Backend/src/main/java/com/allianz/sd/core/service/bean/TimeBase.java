package com.allianz.sd.core.service.bean;

import com.allianz.sd.core.api.model.PersonalProgressValue;
import com.allianz.sd.core.api.model.TeamProgressDetail;
import com.allianz.sd.core.api.model.TeamProgressValue;

/*
 * 
 */
public enum TimeBase {
    YEAR,QUARTER,MONTH,WEEK,DAY;

	public PersonalProgressValue.TimeBaseEnum toPersonalTimebase() {
		switch(this) {
			case YEAR:
				return PersonalProgressValue.TimeBaseEnum.YEAR;
			case QUARTER:
				return PersonalProgressValue.TimeBaseEnum.QUARTER;
			case MONTH:
				return PersonalProgressValue.TimeBaseEnum.MONTH;
			case WEEK:
				return PersonalProgressValue.TimeBaseEnum.WEEK;
			case DAY:
				return PersonalProgressValue.TimeBaseEnum.DAY;
			default:
				return null;
		}
	}

	public TeamProgressValue.TimeBaseEnum toTeamTimebase() {
		switch(this) {
			case YEAR:
				return TeamProgressValue.TimeBaseEnum.YEAR;
			case QUARTER:
				return TeamProgressValue.TimeBaseEnum.QUARTER;
			case MONTH:
				return TeamProgressValue.TimeBaseEnum.MONTH;
			default:
				return null;
		}
	}

	public TeamProgressDetail.TimeBaseEnum toTeamDetailTimebase() {
		switch(this) {
			case YEAR:
				return TeamProgressDetail.TimeBaseEnum.YEAR;
			case QUARTER:
				return TeamProgressDetail.TimeBaseEnum.QUARTER;
			case MONTH:
				return TeamProgressDetail.TimeBaseEnum.MONTH;
			default:
				return null;
		}
	}
}
