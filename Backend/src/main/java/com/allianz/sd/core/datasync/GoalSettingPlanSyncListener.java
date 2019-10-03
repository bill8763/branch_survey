package com.allianz.sd.core.datasync;

import com.allianz.sd.core.api.model.Extension;
import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.jpa.model.GoalSetting;
import com.allianz.sd.core.jpa.model.GoalSettingVersion;

import java.util.List;

public interface GoalSettingPlanSyncListener {
    public void onPullData(
            AgentYearData agentYearData,
            GoalSetting goalSetting,
            GoalSettingVersion goalSettingVersion,
            List<Extension> extensionList);
}
