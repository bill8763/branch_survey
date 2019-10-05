package com.allianz.sd.application.extension.datasync;

import com.allianz.sd.core.api.model.Extension;
import com.allianz.sd.core.datasync.*;
import com.allianz.sd.core.jpa.model.*;

import java.util.List;

public class TestOtherExtension
        implements ConfigurationSyncListener ,
        GoalSyncListener , GoalSettingSyncListener ,
        GoalSettingValueSyncListener,
        GoalSettingPlanSyncListener,
        AgencyPlanMasterSyncListener,
        AgencyPlanDetailSyncListener,
        PersonalProgressSyncListener,
        TeamProgressMasterSyncListener,
        TeamProgressDetailSyncListener {
    @Override
    public void onPullData(SysYearData sysYearData, AgencyPlanDetailSync agencyPlanDetailSync, List<Extension> extensionList) {
        addTest(extensionList);
    }

    @Override
    public void onPullData(SysYearData sysYearData, AgencyPlan agencyPlan, List<Extension> extensionList) {
        addTest(extensionList);
    }

    @Override
    public void onPullData(SysYearData sysYearData, AgentYearData agentYearData, List<Extension> extensionList) {
        addTest(extensionList);
    }

    @Override
    public void onPullData(AgentYearData agentYearData, GoalSetting goalSetting, GoalSettingVersion goalSettingVersion, List<Extension> extensionList) {
        addTest(extensionList);
    }

    @Override
    public void onPullData(GoalSettingValue goalSettingValue, List<Extension> extensionList) {
        addTest(extensionList);
    }

    @Override
    public void onPullData(SysYearData sysYearData, AgentYearData agentYearData, GoalSetting goalSetting, GoalSettingVersion goalSettingVersion, List<Extension> extensionList) {
        addTest(extensionList);
    }

    @Override
    public void onPullData(AgentYearData agentYearData, PersonalProgress personalProgress, List<Extension> extensionList) {
        addTest(extensionList);
    }

    @Override
    public void onPullData(AgentYearData agentYearData, TeamProgressDetail teamProgressDetail, List<Extension> extensionList) {
        addTest(extensionList);
    }

    @Override
    public void onPullData(AgentYearData agentYearData, TeamProgress teamProgress, List<Extension> extensionList) {
        addTest(extensionList);
    }

    public void addTest(List<Extension> extensionList) {
        Extension extension = new Extension();
        extension.setDatatype("string");
        extension.setValue("TEST");
        extension.setId("TestID");


        extensionList.add(extension);
    }
}
