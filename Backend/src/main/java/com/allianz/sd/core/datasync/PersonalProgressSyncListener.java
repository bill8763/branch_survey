package com.allianz.sd.core.datasync;

import com.allianz.sd.core.api.model.Extension;
import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.jpa.model.PersonalProgress;

import java.util.List;

public interface PersonalProgressSyncListener {
    public void onPullData(
            AgentYearData agentYearData,
            PersonalProgress personalProgress,
            List<Extension> extensionList);
}
