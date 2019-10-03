package com.allianz.sd.core.service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;

import com.allianz.sd.core.InstanceCode;
import com.allianz.sd.core.api.model.*;
import com.allianz.sd.core.api.model.GoalSetting;
import com.allianz.sd.core.datasync.GoalSettingPlanSyncListener;
import com.allianz.sd.core.datasync.GoalSettingSyncListener;
import com.allianz.sd.core.datasync.GoalSettingValueSyncListener;
import com.allianz.sd.core.datasync.GoalSyncListener;
import com.allianz.sd.core.exception.NotFoundApproveGoalSettingVersionException;
import com.allianz.sd.core.jpa.model.*;
import com.allianz.sd.core.jpa.model.GoalSettingValue;
import com.allianz.sd.core.jpa.repository.*;
import com.allianz.sd.core.service.bean.*;
import com.allianz.sd.core.service.bean.Goal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GoalService {

    private static final Logger logger = LoggerFactory.getLogger(GoalService.class);

    private final OrganizationService organizationService;
    private final GoalSettingRepository goalSettingRepository;
    private final GoalSettingVersionRepository goalSettingVersionRepository;
    private final GoalSettingValueRepository goalSettingValueRepository;
    private final GoalSettingSplitRepository goalSettingSplitRepository;
    private final UtilsService utilsService;
    private final DateService dateService;
    private final AgentDataService agentDataService;
    private final PlanService planService;
    private final SysDataService sysDataService;
    private final GoalExpectedSplitValueRepository goalExpectedSplitValueRepository;
    private final PerformanceTableMonthRepository performanceTableMonthRepository;
    private final BeanFactory beanFactory;
    private final NumberService numberService;
    private final StringService stringService;


    @Autowired
    public GoalService(OrganizationService organizationService,
                       GoalSettingRepository goalSettingRepository,
                       DateService dateService,
                       GoalSettingVersionRepository goalSettingVersionRepository,
                       GoalSettingValueRepository goalSettingValueRepository,
                       GoalSettingSplitRepository goalSettingSplitRepository,
                       UtilsService utilsService,
                       AgentDataService agentDataService,
                       PlanService planService,
                       SysDataService sysDataService,
                       GoalExpectedSplitValueRepository goalExpectedSplitValueRepository,
                       PerformanceTableMonthRepository performanceTableMonthRepository,
                       BeanFactory beanFactory,
                       NumberService numberService,
                       StringService stringService) {
        this.organizationService = organizationService;
        this.goalSettingRepository = goalSettingRepository;
        this.dateService = dateService;
        this.goalSettingVersionRepository = goalSettingVersionRepository;
        this.goalSettingValueRepository = goalSettingValueRepository;
        this.goalSettingSplitRepository = goalSettingSplitRepository;
        this.utilsService = utilsService;
        this.agentDataService = agentDataService;
        this.planService = planService;
        this.sysDataService = sysDataService;
        this.goalExpectedSplitValueRepository = goalExpectedSplitValueRepository;
        this.performanceTableMonthRepository = performanceTableMonthRepository;
        this.beanFactory = beanFactory;
        this.numberService = numberService;
        this.stringService = stringService;
    }

    public List<GoalSettingVersion> findOverDeadlineGoal() {
        return goalSettingVersionRepository.findOverDeadlineGoal();
    }

    public List<NeedSetting> getNeedSetting(String organizationalUnit , int year , Date date) {

        List<NeedSetting> needSettings = new ArrayList<NeedSetting>();

        List<Object[]> object = goalSettingRepository.getAllNeedSetting(organizationalUnit, String.valueOf(year), date);

        for(Object[] goal :object) {
            String agentID = (String) goal[0];
            int days = dateService.getBetweenTime(date, (Date) goal[1], DateType.DAY);
            String flag = String.valueOf(goal[2]);

            NeedSetting needSetting = new NeedSetting();
            needSetting.setAgentID(agentID);
            needSetting.setDays(days);
            needSetting.setPromo("2".equalsIgnoreCase(flag));

            needSettings.add(needSetting);
        }

        return needSettings;
    }

    public Goal getYearGoal(int goalSettingSeq) {
        Goal goal = new Goal();

        logger.debug("getYearGoal goalSettingSeq = " + goalSettingSeq);

        GoalSettingVersion goalSettingVersion = goalSettingVersionRepository.getGoalSettingVersion(goalSettingSeq);

        if(goalSettingVersion != null) {

            goal.setGoalSettingVersion(goalSettingVersion);

            int dataYear = goalSettingVersion.getDataYear();
            String agentID = goalSettingVersion.getAgentID();

            List<GoalSettingValue> goalValues = this.getGoalValue(goalSettingSeq);
            this.setGoalValue(goal,goalValues);

            //設定GoalPlan
            planService.setPlanByMonth(goal,agentID, dataYear);
        }



        return goal;
    }

    /**
     * 取得某年度審核後的Goal及Goal設定值
     * @param agentID
     * @param dataYear
     * @return
     */
    public Goal getYearGoal(String agentID , int dataYear) {
        Goal goal = new Goal();

        String organizationUnit = organizationService.getOrganizationalUnit();

        logger.debug("getYearGoal = " + agentID + " , todayYear = " + dataYear + ",organizationUnit = " + organizationUnit);

        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        nf.setMaximumFractionDigits(0);

        GoalSettingVersion goalSettingVersion = goalSettingVersionRepository.getGoalSettingIsTopVersion(agentID,organizationUnit,dataYear);

        if(goalSettingVersion != null) {

            goal.setGoalSettingVersion(goalSettingVersion);

            List<GoalSettingValue> goalValues = this.getGoalValue(dataYear, organizationUnit , agentID);
            this.setGoalValue(goal,goalValues);

            //設定GoalPlan
            planService.setPlanByMonth(goal,agentID, dataYear);
        }


        return goal;
    }

    public List<GoalSettingValue> getGoalValue(int dataYear , String organizationUnit , String agentID) {
        List<GoalSettingValue> values = goalSettingValueRepository.getYearGoal(dataYear, organizationUnit , agentID);
        convertGoalValue(values);

        return values;
    }

    public List<GoalSettingValue> getGoalValue(int goalSettingSeq) {
        List<GoalSettingValue> values = goalSettingValueRepository.getGoalSettingValue(goalSettingSeq);
        convertGoalValue(values);

        return values;
    }

    private void convertGoalValue(List<GoalSettingValue> goalValues) {
        for(GoalSettingValue gsv : goalValues) {
            String value = gsv.getSetValue();

            if(stringService.isNull(value)) value = "";
            if(stringService.isNumeric(value)) value = String.valueOf(numberService.round(Double.parseDouble(value)));

            gsv.setSetValue(value);
        }
    }

    private void setGoalValue(Goal goal , List<GoalSettingValue> goalValues) {

        convertGoalValue(goalValues);

        logger.trace("GoalSettingValueList = " + goalValues.size());

        for(GoalSettingValue gsv : goalValues) {
            String mappingID = gsv.getIdentity().getMappingID();
            String value = gsv.getSetValue();

            logger.trace("MappingID = " + mappingID + ",value = " + value);
            goal.addGoalValue(mappingID, value );
        }
    }


    /**
     * 取得該年度該業務員累加Recruitment的主管
     * @param year
     * @param agentID
     * @param organizationalUnit
     * @return
     */
    public List<String> getBottomupYearSubordinateLeaders(int year , String agentID,String organizationalUnit) {
        return goalExpectedSplitValueRepository.getBottomupYearSubordinateLeaders(year , agentID , organizationalUnit);
    }

    //取得Leader向下所有Leader的GoalExpected(Key = Month , Value = Sum)
    public Map<Integer,Integer> getYearSubordinateGoalExpected(int year , String agentID) {

        //撈自己的
        List<GoalExpectedSplitValue> selfExpectArray = goalExpectedSplitValueRepository.getYearMasterGoalExpected(year,agentID);
        logger.trace("getYearSubordinateExpected agentID = "+agentID +" , expectArray size = "+selfExpectArray.size());

        //撈底下的
        List<GoalExpectedSplitValue> childExpectArray = goalExpectedSplitValueRepository.getYearSubordinateGoalExpected(year,agentID);
        logger.trace("getYearSubordinateExpected agentID = "+agentID +" , expectArray size = "+childExpectArray.size());

        //1~4季 的 ExprctValue 塞進quarterMap
        Map<Integer,String> selfQuarterMap = goalExpectedSplitVal2Map(selfExpectArray);
        Map<Integer,String> childRuarterMap = goalExpectedSplitVal2Map(childExpectArray);

        utilsService.fillMap(selfQuarterMap,4,"0");
        utilsService.fillMap(childRuarterMap,4,"0");

        Map<Integer,String> goalExpectedTotalMap = utilsService.mergeMap(childRuarterMap,selfQuarterMap);

        for(Integer key : goalExpectedTotalMap.keySet()) {
            logger.trace("goalExpectedTotalMap : key = " + key +" , value = "+goalExpectedTotalMap.get(key));
        }

        //quarter to month
        Map<Integer,Integer> goalExpectedMonthMap = new LinkedHashMap<>();
        convertQuarterPlanToMonthly(goalExpectedTotalMap,year,goalExpectedMonthMap);

        logger.trace("goalExpectedMonthMap = "+goalExpectedMonthMap);
        return goalExpectedMonthMap;
    }

    private Map<Integer,String> goalExpectedSplitVal2Map(List<GoalExpectedSplitValue> goalExpectedSplitValues) {

        Map<Integer,String> expectMap= new LinkedHashMap<>();

        for(GoalExpectedSplitValue goalExpectedSplitValue : goalExpectedSplitValues) {
            Integer timeBaseSeq = goalExpectedSplitValue.getIdentity().getTimeBaseSeq();
            String val = goalExpectedSplitValue.getSetValue();
            String mapVal = expectMap.get(timeBaseSeq);

            if(!stringService.isNumeric(val)) val = "0";

            if(stringService.isNull(mapVal)) mapVal = val;
            else mapVal = (Long.parseLong(val) + Long.parseLong(mapVal)) + "";

            expectMap.put(timeBaseSeq,mapVal);
        }

        return expectMap;
    }

    //季的Plan轉成Progress Month Plan
    public void convertQuarterPlanToMonthly(Map<Integer,String> quarterMap,int year,Map<Integer,Integer> goalExpectedMonthMap) {
        for(int quarter : quarterMap.keySet()) {
            String expectedVal = quarterMap.getOrDefault(quarter,"0");
            List<Integer> monthList = performanceTableMonthRepository.getMonthByQuarter(quarter,year);

            if(stringService.isNumeric(expectedVal)) {
                int expected = Integer.parseInt(expectedVal);

                int remainder = expected % 3;
                int avg = expected / 3;
                for(Integer month : monthList) {
                    goalExpectedMonthMap.put(month,avg);
                }

                //把餘數分在前面月份
                for(int i=0;i<remainder;i++) {
                    int month = monthList.get(i);
                    int tmp = goalExpectedMonthMap.get(month);
                    goalExpectedMonthMap.put(month,tmp + 1);
                }
            }

        }
    }

    //根據每月的GoalExpected做加總
    public int calcGoalExpected(Map<Integer,Integer> monthMap ,int startMonth , int endMonth) {

        int total = 0;
        logger.trace("startMonth = "+startMonth+" , endMonth = "+endMonth);
        logger.trace("monthMap size = "+monthMap.size()+" , monthMap = "+monthMap);
        //先把該季往後月份加總
        for(int m : monthMap.keySet()) {
            if(m >= startMonth && m <=endMonth) total += monthMap.get(m);
            logger.trace("total = "+ total);
        }

        return total;
    }

    public List<GoalSettingVersion> getPendingApproveList(int year) {
        return goalSettingVersionRepository.findByStatusAndDataYear(GoalSettingStatus.PENDING_APPROVAL.toString(),year);
    }

    //取得待審核的Map,key = AgentID , value = SigningSupervisor
    public Map<String,String> getPendingApproveMap(int year) {

        Map<String,String> pendingApproveMap = new HashMap<>();

        List<GoalSettingVersion> list = getPendingApproveList(year);

        for (GoalSettingVersion gsv : list) {
            pendingApproveMap.put(gsv.getAgentID(),gsv.getSigningSupervisor());
        }

        return pendingApproveMap;
    }

    /*
     * get goal api response model
     * */
    public GoalGetResponse getGoalResponse(String agentID,boolean getApproveGoal) throws Exception {

        //response model
        GoalGetResponse goalGetResponse = new GoalGetResponse();
        goalGetResponse.setLastUpdateTime(dateService.toDateTimeFormatString(new Date()));

        //get organization
        String organizationalUnit = organizationService.getOrganizationalUnit();

        Date today = dateService.getTodayDate();

        //sys_year_data query get data years
        Set<Integer> years = sysDataService.getAPPDisplayYears(today,organizationalUnit);

        for(int dataYear: years) {

            SysYearData sysYearData = sysDataService.getSysYearData(organizationalUnit,agentID,dataYear);

            if(sysYearData != null) {
                com.allianz.sd.core.api.model.Goal goal = getGoal(agentID, organizationalUnit,dataYear,sysYearData,getApproveGoal);

                if(goal != null) {
                    goalGetResponse.addGoalsItem(goal);
                }
            }


        }

        return goalGetResponse;

    }

    //get Goal model by agentId oranizationUnit, dataYear
    public com.allianz.sd.core.api.model.Goal getGoal(
            String agentID, String organizationalUnit,
            Integer dataYear,SysYearData sysYearData,boolean getApproveGoal) {

        Date today = dateService.getTodayDate();
        int currentYear = dateService.getCurrentYear();

        //query goal_setting by agentId datayear oe
        com.allianz.sd.core.jpa.model.GoalSetting goalSettingData = goalSettingRepository.getGoalSetting(agentID, organizationalUnit, dataYear);

        if(goalSettingData != null) {
            Date goalLastDate = sysYearData.getGoalSettingLastDate();
            Date goalSettingDealline = goalSettingData.getGoalSettingDeadline();

            logger.trace("dataYear = ["+dataYear+"],goalLastDate["+goalLastDate+"],today["+today+"]");

            //get top version data
            GoalSettingVersion goalSettingVersion = goalSettingVersionRepository.getGoalSettingIsTopVersion(agentID, organizationalUnit, dataYear);
            GoalSettingVersion lastVersion = getLastGoalVersionModel(agentID, organizationalUnit, dataYear);

            //如果是往下drilldown要看goal，要看最後一個版本的goal是否為待審核，如果是待審核就要用最後一個版本
            if(!getApproveGoal && GoalSettingStatus.valueOf(lastVersion.getStatus()) == GoalSettingStatus.PENDING_APPROVAL) {
                goalSettingVersion = lastVersion;
            }

            if(goalSettingVersion!=null) {

                AgentYearData agentYearData = agentDataService.getAgentYearData(agentID, dataYear);
                String appUseMode = agentYearData.getAppUseMode();

                //Get GoalSettingVersion Sequence
                Integer goalSettingSeq = goalSettingVersion.getGoalSettingSeq();
                String comment = lastVersion.getSupervisorComment();

                com.allianz.sd.core.api.model.Goal goal = new com.allianz.sd.core.api.model.Goal();
                goal.setDataYear(new BigDecimal(dataYear));

                //if needSetting then waiting
                GoalSettingStatus status = GoalSettingStatus.valueOf(lastVersion.getStatus());

                //check today is betweet in today year
                if (goalLastDate.getTime() < today.getTime()) status = GoalSettingStatus.NOTALLOWED;

                boolean isNeedSetting = status != GoalSettingStatus.NOTALLOWED && isNeedSetting(agentID, organizationalUnit, dataYear);

                Date personalGoalYM = goalSettingData.getPersonnelGoalApplicableYM();
                Date teamGoalYM = goalSettingData.getTeamGoalApplicableYM();

                //set GoalSettingInfo
                GoalSetting goalSettingModel = new GoalSetting();
                goalSettingModel.setIsNeedSetting(isNeedSetting);
                goalSettingModel.setIsCurrent(dateService.getCurrentYear() == dataYear);
                goalSettingModel.setIsFirstTime(isFirstTime(organizationalUnit, agentID, dataYear));
                goalSettingModel.setSupervisorComment(comment);
                goalSettingModel.setStatus(status.toAPIValue());
                if(goalSettingVersion.getGoalSettingStartDate() != null) goalSettingModel.setGoalSetMonth(new BigDecimal(dateService.getMonthString(goalSettingVersion.getGoalSettingStartDate())));
                else goalSettingModel.setGoalSetMonth(new BigDecimal(0));

                if(isNeedSetting) goalSettingModel.setRemainingdays(new BigDecimal(dateService.getBetweenTime(today, goalSettingDealline, DateType.DAY)));
                else goalSettingModel.setRemainingdays(new BigDecimal(0));

                if (personalGoalYM != null) {
                    int tmpYear = dateService.getFullYear(personalGoalYM);
                    BigDecimal personalMonth = new BigDecimal(dateService.getMonthString(personalGoalYM));
                    if (currentYear > tmpYear) personalMonth = new BigDecimal(1);

                    goalSettingModel.setPersonnelGoalApplicableYM(personalMonth);
                }

                if (teamGoalYM != null) {
                    int tmpYear = dateService.getFullYear(teamGoalYM);
                    BigDecimal teamMonth = new BigDecimal(dateService.getMonthString(teamGoalYM));
                    if (currentYear > tmpYear) teamMonth = new BigDecimal(1);

                    goalSettingModel.setTeamGoalApplicableYM(teamMonth);
                }

                //for Extension
                Object goalSettingExtensionBean = beanFactory.getBean(InstanceCode.GoalSettingExtension);
                if (utilsService.isNotEmptyBean(goalSettingExtensionBean)) {
                    List<Extension> extensionList = new ArrayList<>();
                    GoalSettingSyncListener listener = (GoalSettingSyncListener) goalSettingExtensionBean;
                    listener.onPullData(sysYearData, agentYearData, goalSettingData, goalSettingVersion, extensionList);
                    goalSettingModel.setExtensions(extensionList);
                }

                goal.setGoalSetting(goalSettingModel);

                //set GoalSettingValue
                List<GoalSettingValue> goalSettingValues = getGoalValue(goalSettingSeq);
                for (GoalSettingValue goalSettingValue : goalSettingValues) {
                    GoalValue goalValueItem = new GoalValue();
                    goalValueItem.setDataType(goalSettingValue.getIdentity().getMappingID());
                    goalValueItem.setValue(goalSettingValue.getSetValue() != null ? goalSettingValue.getSetValue() : "0");

                    //for Extension
                    Object goalSettingValueExtensionBean = beanFactory.getBean(InstanceCode.GoalSettingValueExtension);
                    if (utilsService.isNotEmptyBean(goalSettingValueExtensionBean)) {
                        List<Extension> extensionList = new ArrayList<>();
                        GoalSettingValueSyncListener listener = (GoalSettingValueSyncListener) goalSettingValueExtensionBean;
                        listener.onPullData(goalSettingValue, extensionList);
                        goalValueItem.setExtensions(extensionList);
                    }

                    goal.addGoalValueItem(goalValueItem);
                }

                //set GoalSettingPlan
                GoalPlan goalPlan = new GoalPlan();
                Map<Integer, String> personalPlanMap = planService.getPlanByMonth(goalSettingSeq, AgencyPlanDataType.FYFC);

                if (personalPlanMap != null) {
                    for (int month : personalPlanMap.keySet()) {
                        goalPlan.setTimeBase("Month");

                        BigDecimal timeBaseSeq = new BigDecimal(month);
                        String setValue = personalPlanMap.get(month);

                        GoalPlanValue valuesItem = new GoalPlanValue();
                        valuesItem.setPerformanceType(GoalPlanValue.PerformanceTypeEnum.P);
                        valuesItem.setMonth(timeBaseSeq);
                        valuesItem.setValue(setValue);

                        goalPlan.addValuesItem(valuesItem);
                    }
                }

                //team
                if (!AppUseMode.AGENT.equalsIgnoreCase(appUseMode)) {

                    //如果是leader的話，這邊不能抓都是送審的plan，要加底下已送審plan + 自己本次送審的plan總合
                    Map<Integer, String> teamBottomUpPlanMap = planService.getYearSubordinatePlanExceptSelf(agentID, dataYear, AgencyPlanDataType.FYFC);
                    utilsService.fillMap(teamBottomUpPlanMap, 1, 12, "0");

                    //把自己的也加進去
                    if (personalPlanMap != null) {
                        teamBottomUpPlanMap = utilsService.mergeMap(teamBottomUpPlanMap, personalPlanMap);
                    }

                    for (Integer month : teamBottomUpPlanMap.keySet()) {
                        GoalPlanValue valuesItem = new GoalPlanValue();
                        valuesItem.setPerformanceType(GoalPlanValue.PerformanceTypeEnum.T);
                        valuesItem.setMonth(new BigDecimal(month));
                        valuesItem.setValue(teamBottomUpPlanMap.get(month));
                        goalPlan.addValuesItem(valuesItem);
                    }
                }

                //for Extension
                Object goalSettingPlanExtensionBean = beanFactory.getBean(InstanceCode.GoalSettingPlanExtension);
                if (utilsService.isNotEmptyBean(goalSettingPlanExtensionBean)) {
                    List<Extension> extensionList = new ArrayList<>();
                    GoalSettingPlanSyncListener listener = (GoalSettingPlanSyncListener) goalSettingPlanExtensionBean;
                    listener.onPullData(agentYearData, goalSettingData, goalSettingVersion, extensionList);
                    goalPlan.setExtensions(extensionList);
                }

                goal.setGoalPlan(goalPlan);

                //for Extension
                Object goalExtensionBean = beanFactory.getBean(InstanceCode.GoalExtension);
                if (utilsService.isNotEmptyBean(goalExtensionBean)) {
                    List<Extension> extensionList = new ArrayList<>();
                    GoalSyncListener listener = (GoalSyncListener) goalExtensionBean;
                    listener.onPullData(sysYearData, agentYearData, goalSettingData, goalSettingVersion, extensionList);
                    goal.setExtensions(extensionList);
                }

                return goal;
            }
            else {
                throw new NotFoundApproveGoalSettingVersionException(organizationalUnit,agentID,dataYear);
            }

        }
        else {
            logger.error("Can't found GoalSetting Data["+agentID+"]["+dataYear+"]");
        }

        return null;

    }


    public boolean firstUseApp(String organizationalUnit,String agentID) {
        Integer firstUseAppNum = goalSettingVersionRepository.getFirstUseAPP(agentID,organizationalUnit);
        return firstUseAppNum == 0;
    }

    public boolean isFirstTime(String organizationalUnit,String agentID , int dataYear) {
        Integer isFirstTimeNum = goalSettingVersionRepository.isFirstTime(agentID,organizationalUnit,dataYear);
        return isFirstTimeNum == 0;
    }

    /*
     * get goals(every year) api model list
     * */
    public List<com.allianz.sd.core.api.model.Goal> getGoals(String agentID, String organizationalUnit, boolean getApproveGoal) throws Exception {
        List<com.allianz.sd.core.api.model.Goal> goals = new ArrayList<com.allianz.sd.core.api.model.Goal>();

        Date today = dateService.getTodayDate();

        //sys_year_data query get data years
        Set<Integer> years = sysDataService.getAPPDisplayYears(today,organizationalUnit);
        logger.debug("years size "+ years.size() );

        for(int dataYear: years) {

            SysYearData sysYearData = sysDataService.getSysYearData(organizationalUnit,agentID,dataYear);

            if(sysYearData != null) {
                com.allianz.sd.core.api.model.Goal goal = getGoal(agentID, organizationalUnit,dataYear,sysYearData,getApproveGoal);

                if(goal != null) {
                    goals.add(goal);
                }
            }


        }

        return goals;
    }

    //return  is need setting
    public boolean isNeedSetting(String agentID, String organizationalUnit, Integer dataYear) {
        //if setting Flag data size>0 then need setting
        Date todayDate = dateService.getTodayDate();
        List<String> settingFlag = goalSettingRepository.goalSettingFlag(agentID, organizationalUnit, dataYear,todayDate);
//        logger.debug("goalSettingFlag "+agentID+","+organizationalUnit+","+dataYear+","+todayDate);
//        logger.debug("settingFlag "+settingFlag.size());
        return settingFlag.size()>0;
    }

    public com.allianz.sd.core.jpa.model.GoalSetting getGoalSetting(String agentID ,String organizationalUnit, Integer dataYear) {
        return goalSettingRepository.getGoalSetting(agentID, organizationalUnit, dataYear);
    }

    public int getLastGoalVersion(String agentID, String organizationalUnit, Integer dataYear) {
        GoalSettingVersion goalSettingVersion = this.getLastGoalVersionModel(agentID,organizationalUnit,dataYear);
        return goalSettingVersion != null ? goalSettingVersion.getGoalVersion().intValue() : 0;
    }

    public GoalSettingVersion getLastGoalVersionModel(String agentID, String organizationalUnit, Integer dataYear) {
        List<GoalSettingVersion> goalSettingVersions = goalSettingVersionRepository.getGoalSettingVersion(agentID, organizationalUnit, dataYear);
        return goalSettingVersions.size()>0 ? goalSettingVersions.get(0) : null;
    }

    public GoalSettingVersion getApprovedGoalVersionModel(String agentID, String organizationalUnit, Integer dataYear) {
        return goalSettingVersionRepository.getGoalSettingIsTopVersion(agentID, organizationalUnit, dataYear);
    }

    @Transactional
    public void updateGoalVersion(GoalSettingVersion goalSettingVersion) {
        goalSettingVersionRepository.save(goalSettingVersion);
    }

    public int getGoalSplitVersion(Integer goalSettingSeq) {
        GoalSettingSplit goalSettingSplit = this.getGoalSplitVersionModel(goalSettingSeq);
        return goalSettingSplit != null ? goalSettingSplit.getIdentity().getSplitVersion().intValue() : 0;
    }

    public GoalSettingSplit getGoalSplitVersionModel(Integer goalSettingSeq) {
        List<GoalSettingSplit> goalSettingSplits = goalSettingSplitRepository.getGoalSettingSplitVersion(goalSettingSeq);
        return goalSettingSplits.size()>0 ? goalSettingSplits.get(0) : null;
    }


}
