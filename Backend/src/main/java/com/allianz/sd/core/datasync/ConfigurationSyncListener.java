package com.allianz.sd.core.datasync;

import com.allianz.sd.core.api.model.Appointment;
import com.allianz.sd.core.api.model.Extension;
import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.jpa.model.Calendar;
import com.allianz.sd.core.jpa.model.SysYearData;

import java.util.List;

public interface ConfigurationSyncListener {
    public void onPullData(SysYearData sysYearData , AgentYearData agentYearData, List<Extension> extensionList);
}
