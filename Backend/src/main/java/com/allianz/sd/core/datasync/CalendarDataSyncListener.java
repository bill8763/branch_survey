package com.allianz.sd.core.datasync;

import com.allianz.sd.core.api.model.Appointment;
import com.allianz.sd.core.jpa.model.Calendar;

public interface CalendarDataSyncListener {
    public void postSave(Calendar calendar, Appointment appointment);

    public void onPullData(Calendar calendar, Appointment appointment);
}
