package com.allianz.sd.core.datasync;

import com.allianz.sd.core.api.model.Extension;
import com.allianz.sd.core.jpa.model.*;

import java.util.List;

public interface GoalSettingValueSyncListener {
    public void onPullData(
            GoalSettingValue goalSettingValue,
            List<Extension> extensionList);
}
