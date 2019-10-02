package com.allianz.sd.core.datasync;

import com.allianz.sd.core.api.model.Extension;
import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.jpa.model.GoalSetting;
import com.allianz.sd.core.jpa.model.GoalSettingVersion;
import com.allianz.sd.core.jpa.model.SysYearData;

import java.util.List;

public interface GoalSettingSyncListener {
    public void onPullData(
            SysYearData sysYearData,
            AgentYearData agentYearData,
            GoalSetting goalSetting,
            GoalSettingVersion goalSettingVersion,
            List<Extension> extensionList);
}
