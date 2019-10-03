package com.allianz.sd.core.progress;

import org.springframework.stereotype.Component;

import com.allianz.sd.core.jpa.model.Calendar;

@Component
public interface IsCalcSchedulePoint {
  public boolean isCalcToPoint(Calendar calendar);
}
