package com.allianz.sd.core.datasync;

import com.allianz.sd.core.api.model.Extension;
import com.allianz.sd.core.jpa.model.AgencyPlanDetailSync;
import com.allianz.sd.core.jpa.model.SysYearData;

import java.util.List;

public interface AgencyPlanDetailSyncListener {
    public void onPullData(
            SysYearData sysYearData,
            AgencyPlanDetailSync agencyPlanDetailSync,
            List<Extension> extensionList);
}
