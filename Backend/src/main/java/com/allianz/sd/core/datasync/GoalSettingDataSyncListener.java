package com.allianz.sd.core.datasync;

import com.allianz.sd.core.api.model.GoalExpected;
import com.allianz.sd.core.jpa.model.GoalExpectedVersion;

public interface GoalSettingDataSyncListener {
    public void postSave(GoalExpectedVersion goalExpectedVersion, GoalExpected goalExpected);

    public void onPullData(GoalExpectedVersion goalExpectedVersion, GoalExpected goalExpected);
}
