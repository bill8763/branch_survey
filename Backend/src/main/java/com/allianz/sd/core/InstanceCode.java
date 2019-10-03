package com.allianz.sd.core;

public final class InstanceCode {
    public static int DASHED = -1;

    //login use
    public static String Authorization = "authorization";
    public static String AppUpdateVersionMgr = "appUpdateVersionMgr";
    public static String DeviceBindingRule = "deviceBindingRule";

    //configuration use
    public static String CalcWorkingMonthRule = "calcWorkingMonth";

    //agencyplan calc logic use
    public static String DrilldownRule = "drilldownRule";
    public static String ApproveRule = "approveRule";
    public static String CalcAgencyPlanDetailPlan = "calcAgencyPlanDetailPlan";
    public static String CalcTeamProgress = "calcTeamProgress";

    //progress calc logic use
    public static String CalSchedulePointRule = "isCalcSchedulePoint";
    public static String ProgressActivity = "progressActivity";
    public static String CalcPersonalProgress = "calcPersonalProgress";

    //customer logic use
    public static String CustomerNameRule = "customerFullNameConvert";

    //datasync use
    public static String CalendarExtension = "calendarExtensionListener";
    public static String CustomerExtension = "customerExtensionListener";
    public static String CustomerContactExtension = "customerContactExtensionListener";

    public static String ConfigurationExtension = "configurationExtension";
    public static String GoalExtension = "goalExtension";
    public static String GoalSettingExtension = "goalSettingExtension";
    public static String GoalSettingValueExtension = "goalSettingValueExtension";
    public static String GoalSettingPlanExtension = "goalSettingPlanExtension";

    public static String AgencyPlanMasterExtension = "agencyPlanMasterExtension";
    public static String AgencyPlanDetailExtension = "agencyPlanDetailExtension";

    public static String PersonalProgressExtension = "personalProgressExtension";
    public static String TeamProgressMasterExtension = "teamProgressMasterExtension";
    public static String TeamProgressDetailExtension = "teamProgressDetailExtension";

    //message use
    public static String PushNotification = "pushNotification";


    //CISL use
    public static String AgentDataAPIFetch = "AgentDataAPIFetch";
    public static String AgentYearAPIFetch = "AgentYearAPIFetch";
    public static String AgencyPlanAPIFetch = "AgencyPlanAPIFetch";
    public static String AgencyPlanDetailAPIFetch = "AgencyPlanDetailAPIFetch";
    public static String AgencyPlanPileAPIFetch = "AgencyPlanPileAPIFetch";
    public static String SalesAPIFetch = "SalesAPIFetch";
    public static String GoalSettingAPIFetch = "GoalSettingAPIFetch";
    public static String CustomerAPIFetch = "CustomerAPIFetch";

    public static String CISLGoalValueProcesser = "CISLGoalValueProcesser";
    public static String CISLTokenProvider = "CISLTokenProvider";
}
