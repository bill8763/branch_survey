package com.allianz.sd.core.datasync;

import com.allianz.sd.core.api.model.Extension;
import com.allianz.sd.core.jpa.model.*;

import java.util.List;

public interface AgencyPlanMasterSyncListener {
    public void onPullData(
            SysYearData sysYearData,
            AgencyPlan agencyPlan,
            List<Extension> extensionList);
}
