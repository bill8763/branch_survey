package com.allianz.sd.core.datasync;

import com.allianz.sd.core.api.model.Extension;
import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.jpa.model.TeamProgressDetail;

import java.util.List;

public interface TeamProgressDetailSyncListener {
    public void onPullData(
            AgentYearData agentYearData,
            TeamProgressDetail teamProgressDetail,
            List<Extension> extensionList);
}
