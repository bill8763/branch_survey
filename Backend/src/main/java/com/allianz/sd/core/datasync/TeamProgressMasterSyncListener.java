package com.allianz.sd.core.datasync;

import com.allianz.sd.core.api.model.Extension;
import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.jpa.model.TeamProgress;

import java.util.List;

public interface TeamProgressMasterSyncListener {
    public void onPullData(
            AgentYearData agentYearData,
            TeamProgress teamProgress,
            List<Extension> extensionList);
}
