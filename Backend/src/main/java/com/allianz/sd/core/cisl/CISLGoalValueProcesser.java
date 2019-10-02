package com.allianz.sd.core.cisl;

import com.allianz.sd.core.jpa.model.CISLGoalSetting;
import com.allianz.sd.core.jpa.model.GoalSettingVersion;
import org.springframework.transaction.annotation.Transactional;

public interface CISLGoalValueProcesser {

    @Transactional
    public void initGoalValue(CISLGoalSetting cislGoalSetting) throws Exception;

    @Transactional
    public void createNewGoalValue(CISLGoalSetting cislGoalSetting, GoalSettingVersion goalSettingVersion) throws Exception;
}
