package com.allianz.sd.core.impl;

import com.allianz.sd.core.jpa.model.Calendar;
import com.allianz.sd.core.progress.IsCalcSchedulePoint;

public class CalcSchedulePointRule implements IsCalcSchedulePoint {

	@Override
	public boolean isCalcToPoint(Calendar calendar) {
		String calendartype = calendar.getCalendarType();

		return "1".equalsIgnoreCase(calendartype) || "2".equalsIgnoreCase(calendartype)
				|| "3".equalsIgnoreCase(calendartype);
	}

}
