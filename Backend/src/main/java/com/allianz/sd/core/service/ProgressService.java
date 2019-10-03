package com.allianz.sd.core.service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;

import com.allianz.sd.core.InstanceCode;
import com.allianz.sd.core.agencyplan.DrilldownRule;
import com.allianz.sd.core.jpa.bean.DataCategory;
import com.allianz.sd.core.jpa.model.*;
import com.allianz.sd.core.jpa.repository.*;
import com.allianz.sd.core.progress.*;
import com.allianz.sd.core.service.bean.PersonalActivity;
import com.allianz.sd.core.service.bean.*;
import com.allianz.sd.core.service.bean.GoalSettingValue;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProgressService {

    private static final Logger logger = LoggerFactory.getLogger(ProgressService.class);

    @Autowired
    private PerformanceTableMonthRepository performanceTableMonthRepository;

    @Autowired
    private AgentDailyAppDataRepository agentDailyAppDataRepository;

    @Autowired
    private AgentDailyDataRepository agentDailyDataRepository;

    @Autowired
    private DateService dateService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private PersonalProgressRepository progressRepository;

    @Autowired
    private SalesDataService salesDataService;

    @Autowired
    private AgentDataService agentDataService;

    @Autowired
    private PersonalProgressRepository personalProgressRepository;

    @Autowired
    private TeamProgressRepository teamProgressRepository;

    @Autowired
    private TeamProgressDetailRepository teamProgressDetailRepository;

    @Autowired
    private AgencyPlanDetailRepository agencyPlanDetailRepository;

    @Autowired
    private AgencyPlanRepository agencyPlanRepository;

    @Autowired
    private GoalService goalService;

    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private AgentUpdateVersionService agentUpdateVersionService;

    public void clearPersonalProgressSyncTable(String organizationalUnit,int dataYear) {
        personalProgressRepository.deletePersonalProgress(dataYear,organizationalUnit);

    }

    public void clearTeamProgressSyncTable(String organizationalUnit,int dataYear) {
        teamProgressDetailRepository.deleteTeamProgressDetail(dataYear,organizationalUnit);
        teamProgressRepository.deleteTeamProgress(dataYear,organizationalUnit);
    }

    public void saveEmptyTeamProgress(AgencyPlan agencyPlan) {

        int year = agencyPlan.getIdentity().getDataYear();
        String organizationalUnit = agencyPlan.getIdentity().getOrganizationalUnit();
        String agentID = agencyPlan.getIdentity().getAgentID();

        for(TeamProgressTimeField timeField : TeamProgressTimeField.values()) {
            for (TeamProgressDataType dataType : TeamProgressDataType.values()) {
                saveTeamProgress(dataType,agentID,year,organizationalUnit,timeField,InstanceCode.DASHED,InstanceCode.DASHED,InstanceCode.DASHED,InstanceCode.DASHED);
            }
        }

    }

    public void saveEmptyTeamProgressDetail(AgentData agentData,AgencyPlanDetail agencyPlanDetail) {

        int year = agencyPlanDetail.getIdentity().getDataYear();
        String organizationalUnit = agencyPlanDetail.getIdentity().getOrganizationalUnit();
        String agentID = agencyPlanDetail.getIdentity().getAgentID();
        String subAgentID = agencyPlanDetail.getSubAgentID();
        String displayBlock = agencyPlanDetail.getIdentity().getDisplayBlock();
        Integer displayOrder = agencyPlanDetail.getIdentity().getDisplayOrder();
        String subAgentName = agencyPlanDetail.getSubAgentName();
        String subAgentJobGrade = agencyPlanDetail.getSubAgentJobGrade();

        String officeName = agentData != null ? agentData.getOfficeName() : "";

        for(TeamProgressTimeField timeField : TeamProgressTimeField.values()) {
            for (TeamProgressDataType dataType : TeamProgressDataType.values()) {
                saveDetail("",dataType,agentID,year,organizationalUnit,timeField,subAgentID,
                        displayBlock,displayOrder,subAgentName,subAgentJobGrade,
                        officeName,
                        false,
                        InstanceCode.DASHED,InstanceCode.DASHED,InstanceCode.DASHED,InstanceCode.DASHED);
            }
        }

    }

    public void processMasterData(AgentYearData agentYearData, Goal goalObj) {
        Date date = dateService.getTodayDate();

        //init all implements class
        CalcTeamProgress calcTeamProgress = (CalcTeamProgress)beanFactory.getBean(InstanceCode.CalcTeamProgress);

        int year = agentYearData.getIdentity().getDataYear();
        String agentID = agentYearData.getIdentity().getAgentID();
        String organizationalUnit = agentYearData.getIdentity().getOrganizationalUnit();
        String performanceTable = agentYearData.getPerformanceTable();

        PerformanceTime performanceTime = getPerformanceTime(date, organizationalUnit,performanceTable);

        String performanceType = agentYearData.getTeamPerformanceType();
        int actualStartMonth = agentDataService.getCalcTeamActualStartMonth(agentYearData);

        //取得Personal Sales Data
        Map<Integer,AgentSalesData> salesMap = salesDataService.getAgentSalesDataByYear(year,agentID,organizationalUnit,performanceType);

        //計算月/季/年的Actual加總值
        SumSalesData sumSalesData = salesDataService.calcSalesData(salesMap,actualStartMonth,performanceTime);

        //Month/Quarter/Year
        for(TeamProgressTimeField timeField : TeamProgressTimeField.values()) {

            //FYFC/ANP/Manpower/Recruitment
            for(TeamProgressDataType dataType : TeamProgressDataType.values()) {

                CalcTeamProgressGoalStrategy calcGoal = calcTeamProgress.getGoalCalcStrategy(performanceTime,timeField,dataType,agentYearData,"INDIRECT");
                CalcTeamProgressForecastStrategy calcForecast = calcTeamProgress.getForecastCalcStrategy(performanceTime,timeField,dataType,agentYearData,"INDIRECT");
                CalcTeamProgressShortfallStrategy calcShortfall = calcTeamProgress.getShortfallCalcStrategy(performanceTime,timeField,dataType,agentYearData,"INDIRECT");

                logger.trace("Agent["+agentID+"] , GoalStrategy["+calcGoal.getClass().getSimpleName()+"] , Forecast["+calcForecast.getClass().getSimpleName()+"] , Shortfall["+calcShortfall.getClass().getSimpleName()+"]");

                long goal = calcGoal.getValue(performanceTime,agentYearData,goalObj);
                long actual = getTeamProgressActual(sumSalesData,timeField,dataType);
                long forecase = calcForecast.getValue(performanceTime,agentYearData,goalObj,goal,actual);
                long shartfall = calcShortfall.getValue(goal,actual,forecase);

                saveTeamProgress(dataType,agentID,year,organizationalUnit,
                        timeField,goal,forecase,actual,shartfall);
            }
        }


    }

    public void processDetailData(AgencyPlanDetail agencyPlanDetail,AgentYearData subAgentYearData,Goal goalObj) {

        Date date = dateService.getTodayDate();

        //init all implements class
        DrilldownRule drilldownRule = (DrilldownRule)beanFactory.getBean(InstanceCode.DrilldownRule);
        CalcTeamProgress calcTeamProgress = (CalcTeamProgress)beanFactory.getBean(InstanceCode.CalcTeamProgress);

        int year = agencyPlanDetail.getIdentity().getDataYear();
        String organizationalUnit = agencyPlanDetail.getIdentity().getOrganizationalUnit();
        String agentID = agencyPlanDetail.getIdentity().getAgentID();
        String subAgentID = agencyPlanDetail.getSubAgentID();
        String subAgentName = agencyPlanDetail.getSubAgentName();
        String subAgentJobGrade = agencyPlanDetail.getSubAgentJobGrade();
        String subordinateAgentOfficeName = subAgentYearData.getAgentData().getOfficeName();
        String displayBlock = agencyPlanDetail.getIdentity().getDisplayBlock();
        Integer displayOrder = agencyPlanDetail.getIdentity().getDisplayOrder();
        String appUseModeSub = subAgentYearData.getAppUseMode();
        String performanceTable = subAgentYearData.getPerformanceTable();

        logger.debug("subAgentID = " + subAgentID + ",subordinateAgentOfficeName = " + subordinateAgentOfficeName);


        //判斷Actual是抓Personal or Team
        String performanceType = calcTeamProgress.getAgentActualPerformanceType(subAgentYearData,agencyPlanDetail);
        int actualStartMonth = calcTeamProgress.getActualStartMonth(subAgentYearData,agencyPlanDetail);
        if(StringUtils.isEmpty(performanceType)) {
            logger.error("can't find performanceType ["+subAgentID+"]");
            return;
        }

        PerformanceTime performanceTime = getPerformanceTime(date, organizationalUnit,performanceTable);

        //是否可以往下點
        boolean isDrilldown = drilldownRule.isDrilldown(agencyPlanDetail, subAgentYearData);

        //取得Personal Sales Data並計算月/季/年的Actual加總值
        Map<Integer,AgentSalesData> salesMap = salesDataService.getAgentSalesDataByYear(year,subAgentID,organizationalUnit,performanceType);
        SumSalesData sumSalesData = salesDataService.calcSalesData(salesMap,actualStartMonth,performanceTime);

        //Month/Quarter/Year
        for(TeamProgressTimeField timeField : TeamProgressTimeField.values()) {

            //FYFC/ANP/Manpower/Recruitment
            for(TeamProgressDataType dataType : TeamProgressDataType.values()) {

                CalcTeamProgressGoalStrategy calcGoal = calcTeamProgress.getGoalCalcStrategy(performanceTime,timeField,dataType,subAgentYearData,displayBlock);
                CalcTeamProgressForecastStrategy calcForecast = calcTeamProgress.getForecastCalcStrategy(performanceTime,timeField,dataType,subAgentYearData,displayBlock);
                CalcTeamProgressShortfallStrategy calcShortfall = calcTeamProgress.getShortfallCalcStrategy(performanceTime,timeField,dataType,subAgentYearData,displayBlock);

                logger.trace("Agent["+agentID+"] , GoalStrategy["+calcGoal.getClass().getSimpleName()+"] , Forecast["+calcForecast.getClass().getSimpleName()+"] , Shortfall["+calcShortfall.getClass().getSimpleName()+"]");

                long goal = calcGoal.getValue(performanceTime,subAgentYearData,goalObj);
                long actual = getTeamProgressActual(sumSalesData,timeField,dataType);
                long forecase = calcForecast.getValue(performanceTime,subAgentYearData,goalObj,goal,actual);
                long shartfall = calcShortfall.getValue(goal,actual,forecase);

                saveDetail(appUseModeSub,dataType,agentID,year,organizationalUnit,
                        timeField,subAgentID,displayBlock,displayOrder,subAgentName,subAgentJobGrade,
                        subordinateAgentOfficeName,
                        isDrilldown,goal,forecase,actual,shartfall);
            }
        }

    }

    private long getTeamProgressActual(SumSalesData sumSalesData , TeamProgressTimeField timeField ,TeamProgressDataType dataType ) {
        if(timeField.equals(TeamProgressTimeField.Month)) return sumSalesData.getMonthSalesData().getValue(dataType);
        else if(timeField.equals(TeamProgressTimeField.Quarter)) return sumSalesData.getQuarterSalesData().getValue(dataType);
        else if(timeField.equals(TeamProgressTimeField.Year)) return sumSalesData.getYearSalesData().getValue(dataType);
        return 0;
    }

    private void saveDetail(String appUseModeSub ,TeamProgressDataType dataType ,
                            String agentID , int year , String organizationalUnit , TeamProgressTimeField field ,
                            String subAgentID , String displayBlock , Integer displayOrder, String subAgentName , String subAgentJobGrade,
                            String subordinateAgentOfficeName,
                            boolean isDrilldown ,  long goal , long forecase , long actual , long shartfall) {

        TeamProgressDetail teamProgressDetail = new TeamProgressDetail();

        TeamProgressDetailIdentity identity = new TeamProgressDetailIdentity();
        identity.setAgentID(agentID);
        identity.setDataYear(year);
        identity.setOrganizationalUnit(organizationalUnit);
        identity.setTimeBase(field.toString());
        identity.setSubordinateAgentID(subAgentID);
        identity.setDirectUnitType(displayBlock);
        identity.setDataType(dataType.toString());

        teamProgressDetail.setIdentity(identity);
        teamProgressDetail.setDisplayOrder(displayOrder);
        teamProgressDetail.setSubordinateAgentAgentName(subAgentName);
        teamProgressDetail.setSubordinateAgentJobGrade(subAgentJobGrade);
        teamProgressDetail.setSubordinateAgentOfficeName(subordinateAgentOfficeName);
        teamProgressDetail.setIsDrilldown(isDrilldown ? "Y" : "N");
        teamProgressDetail.setGoal(goal);
        teamProgressDetail.setForecast(forecase);
        teamProgressDetail.setActual(actual);
        teamProgressDetail.setShortfall(shartfall);
        teamProgressDetail.setCreateBy("SD");

        teamProgressDetailRepository.save(teamProgressDetail);
        logger.trace("saveTeamProgressDetail AgentID=["+agentID+"] , SubAgentID=["+subAgentID+"] , appUseModeSub=["+appUseModeSub+"] , goal = " + goal + " , forecase = " + forecase + " , actual = " + actual + " , shartfall = " + shartfall);

    }

    private void saveTeamProgress(TeamProgressDataType dataType , String agentID , int year , String organizationalUnit ,
                                  TeamProgressTimeField field , long goal , long forecase , long actual , long shartfall) {

        TeamProgress teamProgress = new TeamProgress();

        logger.trace("saveTeamProgress agentID = " + agentID + " , goal = " + goal + ", forecase = " + forecase + ",actual = " + actual + ",shartfall = " + shartfall);

        ProgressIdentity identity = new ProgressIdentity();
        identity.setAgentID(agentID);
        identity.setDataYear(year);
        identity.setOrganizationalUnit(organizationalUnit);
        identity.setTimeBase(field.toString());
        identity.setDataType(dataType.toString());

        teamProgress.setIdentity(identity);
        teamProgress.setGoal(goal);
        teamProgress.setForecast(forecase);
        teamProgress.setActual(actual);
        teamProgress.setShortfall(shartfall);
        teamProgress.setCreateBy("SD");

        teamProgressRepository.save(teamProgress);
    }

    public List<String> getLowMiniPointAgents(String organizationalUnit , Date date , int lessPoint) {
        return personalProgressRepository.getAgentIdMiniPoint(organizationalUnit, date, lessPoint);
    }

    public void saveEmptyPersonalProgress(AgentYearData agentYearData) {

        int year = agentYearData.getIdentity().getDataYear();
        String agentID = agentYearData.getIdentity().getAgentID();
        String organizationalUnit = agentYearData.getIdentity().getOrganizationalUnit();

        for (ProgressTimeField field : ProgressTimeField.values()) {
            String type = field.toString();
            savePersonalProgress("Actual",organizationalUnit,agentID,year,type,InstanceCode.DASHED,InstanceCode.DASHED,InstanceCode.DASHED,InstanceCode.DASHED,InstanceCode.DASHED,InstanceCode.DASHED);
            savePersonalProgress("Goal",organizationalUnit,agentID,year,type,InstanceCode.DASHED,InstanceCode.DASHED,InstanceCode.DASHED,InstanceCode.DASHED,InstanceCode.DASHED,InstanceCode.DASHED);
        }

    }

    /**
     * 因為SubmitGoal跟ApproveGoal都會即時更新personal progress點數計算，
     * 所以將計算業務員的PersonalProgress單獨一支Function讓排程跟API能共用
     * @param agentYearData
     * @param approveGoal
     * @param performanceTime
     * @param sysYearData
     * @return 月/季/年哪些沒達標
     * @throws Exception
     */
    public void calcPersonalProgress(AgentYearData agentYearData,
                                     Goal approveGoal,
                                     PerformanceTime performanceTime,
                                     SysYearData sysYearData) throws Exception{

        String agentID = agentYearData.getIdentity().getAgentID();
        int year = agentYearData.getIdentity().getDataYear();
        String organizationalUnit = agentYearData.getIdentity().getOrganizationalUnit();

        //計算這個人的業績起算月
        int actualStartMonth = agentDataService.getCalcPersonalActualStartMonth(agentYearData);

        //拿該業務員的季對應月份
        List<Integer> monthOfQuarter = performanceTime.getMonthOfQuarter();
        int startMonthOfQuarter = monthOfQuarter.get(0);
        int endMonthOfQuarter = monthOfQuarter.get(monthOfQuarter.size()-1);

        //取得Personal Sales Data(整年度)
        Map<Integer,AgentSalesData> salesMap = salesDataService.getAgentPersonalSalesDataByYear(agentYearData);

        //get Personal Plan by month
        Map<Integer,String> planMap = approveGoal.getFyfcPlan();

        //計算月/季/年
        SumSalesData sumSalesData = salesDataService.calcSalesData(salesMap,actualStartMonth,performanceTime);

        for (ProgressTimeField field : ProgressTimeField.values()) {
            String type = field.toString();

            //計算個人月/季/年的FYFC
            long actualFyfc = -1;
            if(field == ProgressTimeField.Month) actualFyfc = sumSalesData.getMonthSalesData().getFyfc();
            else if(field == ProgressTimeField.Quarter) actualFyfc = sumSalesData.getQuarterSalesData().getFyfc();
            else if(field == ProgressTimeField.Year) actualFyfc = sumSalesData.getYearSalesData().getFyfc();

            //計算個人月/季/年的Activity Actual
            Activity activity = getActivity(agentID, field, performanceTime , year);

            int actualFind = activity.getNumberOfFind();
            int actualSchedule = activity.getNumberOfSchdule();
            int actualMeet = activity.getNumberOfMeet();
            int actualSubmit = activity.getNumberOfSubmit();
            int actualInforce = activity.getNumberOfInforce();

            savePersonalProgress("Actual",organizationalUnit,agentID,year,type,actualFind,actualSchedule,actualMeet,actualSubmit,actualInforce,actualFyfc);

            //計算Goal
            long personFYFC = Long.parseLong(approveGoal.getGoalValue(GoalSettingValue.PERSON_FYFC));
            long preCaseFyfc = Long.parseLong(approveGoal.getGoalValue(GoalSettingValue.PER_CASE_FYFC));
            long personalPlan = 0;
            if(field == ProgressTimeField.Month) personalPlan = Long.parseLong(planMap.getOrDefault(performanceTime.getMonth(), "0"));
            else if(field == ProgressTimeField.Year) personalPlan = personFYFC;
            else if(field == ProgressTimeField.Quarter) {
                for(int z = startMonthOfQuarter ; z <= endMonthOfQuarter ; z++) {
                    personalPlan += Long.parseLong(planMap.getOrDefault(z, "0"));
                }
            }


            logger.trace("getInforceConvertFindRate = "+sysYearData.getInforceConvertFindRate()+", getInforceConvertScheduleRate"+sysYearData.getInforceConvertScheduleRate()+", getInforceConvertMeetRate"+sysYearData.getInforceConvertMeetRate()+", getInforceConvertSubmitRate"+sysYearData.getInforceConvertSubmitRate());

            double inForce = preCaseFyfc != 0 ? (double)personalPlan / (double)preCaseFyfc : 0;
            double find = inForce * sysYearData.getInforceConvertFindRate();
            double schedule = inForce * sysYearData.getInforceConvertScheduleRate();
            double meet = inForce * sysYearData.getInforceConvertMeetRate();
            double submit = inForce * sysYearData.getInforceConvertSubmitRate();

            logger.trace("field = "+field + ", personalPlan = "+personalPlan + ", preCaseFyfc = "+ preCaseFyfc +", inForce = "+inForce+", find = "+find+", schedule = "+schedule + ", meet = "+meet+", submit = "+submit);

            savePersonalProgress("Goal",organizationalUnit,agentID,year,type,find,schedule,meet,submit,inForce,personalPlan);

        }

    }

    public void calcTeamProgress(List<AgencyPlan> agencyPlans, List<AgencyPlanDetail> agencyPlanDetails) {

        //All agencyplan detail
        for(AgencyPlanDetail agencyPlanDetail : agencyPlanDetails) {

            String agentID = agencyPlanDetail.getIdentity().getAgentID();
            String subAgentID = agencyPlanDetail.getSubAgentID();
            String displayBlock = agencyPlanDetail.getIdentity().getDisplayBlock();
            int dataYear = agencyPlanDetail.getIdentity().getDataYear();

            logger.debug("TeamProgressDetail ["+agentID+"]["+subAgentID+"]["+dataYear+"]");
            try{

                //get Goal & Goal Value
                Goal approveGoal = null;
                try {
                    approveGoal = goalService.getYearGoal(subAgentID, dataYear);
                }catch(Exception ex) {
                    throw new Exception("Can't found approveGoal["+subAgentID+"]["+dataYear+"]");
                }

                //Get AgentYearData
                AgentYearData agentYearData = agentDataService.getAgentYearData(subAgentID,dataYear);

                //如果連業務員年度都抓不到就帶- -
                if(agentYearData == null) {
                    logger.trace("Can't find AgentYearData ["+subAgentID+"]");

                    AgentData agentData = agentDataService.getAgentData(agentID);

                    saveEmptyTeamProgressDetail(agentData,agencyPlanDetail);

                    continue;
                }

                processDetailData(agencyPlanDetail,agentYearData,approveGoal);

            }catch(Exception e) {
                logger.error("Calc TeamProgressDetail Exception!! ["+dataYear+"]["+agentID+"]["+subAgentID+"]["+displayBlock+"]",e);
            }

        }

        for(AgencyPlan agencyPlan : agencyPlans) {

            String agentID = agencyPlan.getIdentity().getAgentID();
            int dataYear = agencyPlan.getIdentity().getDataYear();
            String organizationalUnit = agencyPlan.getIdentity().getOrganizationalUnit();

            logger.debug("TeamProgressMaster ["+agentID+"]["+dataYear+"]");

            try{

                //Get AgentYearData
                AgentYearData agentYearData = agentDataService.getAgentYearData(agentID,dataYear);

                //如果連業務員年度都抓不到就帶- -
                if(agentYearData == null) {
                    logger.debug("Can't find AgentYearData ["+agentID+"]");

                    saveEmptyTeamProgress(agencyPlan);
                    continue;
                }

                //get Goal & Goal Value
                Goal approveGoal = null;
                try {
                    approveGoal = goalService.getYearGoal(agentID, dataYear);
                }catch(Exception ex) {
                    throw new Exception("Can't found approveGoal["+agentID+"]["+dataYear+"]");
                }

                if(approveGoal != null) {
                    processMasterData(agentYearData,approveGoal);
                }

            }catch(Exception e) {
                logger.error("Calc TeamProgress Exception!! ["+agentID+"]",e);
            }


        }

    }

    @Transactional
    public void bottomUpUpdateTeamProgress(AgentYearData agentYearData,Goal originGoalObj, Goal goalObj)throws Exception{

        //撈對應哪些PileData
        int dataYear = agentYearData.getIdentity().getDataYear();
        String agentID = agentYearData.getIdentity().getAgentID();

        List<AgencyPlan> reCalcList = agencyPlanRepository.findBottomupAgencyPlan(agentID,dataYear);
        List<AgencyPlanDetail> reCalcDetailList = agencyPlanDetailRepository.findBottomupAgencyPlanDetail(agentID,dataYear);

        logger.trace("bottomUpUpdateTeamProgress reCalcList = " + reCalcList.size() + ", reCalcDetailList = " + reCalcDetailList.size());

        calcTeamProgress(reCalcList,reCalcDetailList);

        //clear bottom-up agents cache
        Set<String> clearAgentIds = new HashSet<>();
        for(AgencyPlan agencyPlan : reCalcList) {
            clearAgentIds.add(agencyPlan.getIdentity().getAgentID());
        }

        for(AgencyPlanDetail agencyPlanDetail : reCalcDetailList) {
            clearAgentIds.add(agencyPlanDetail.getIdentity().getAgentID());
        }

        if(clearAgentIds.size() != 0) {
            agentUpdateVersionService.clearAgentTableCache(clearAgentIds , DataCategory.PROGRESS);
        }

    }

    public void updateForecastRecruitment(TeamProgressTimeField timeField , long forecast , String leaderID , String organizationalUnit , int dataYear) {
        //更新Detail的ForecastRecruitment
        teamProgressDetailRepository.updateForecastRecruitment(forecast, leaderID,organizationalUnit,dataYear,timeField.toString());

        //更新Detail的shartfallRecruitment
        teamProgressDetailRepository.updateShartfallRecruitment(leaderID,organizationalUnit,dataYear);

        //更新Master的ForecastRecruitment
        teamProgressRepository.updateForecastRecruitment(forecast, leaderID,organizationalUnit,dataYear,timeField.toString());

        //update master month/quarter Shartfall(Forecast - Actual)
        teamProgressRepository.updateShartfallRecruitment(leaderID,organizationalUnit,dataYear);


    }

    /**
     * 依照PerformanceTable取得計績計算時間(年/季/月/天數)
     * @return
     */
    public PerformanceTime getPerformanceTime(Date date, String OrganizationalUnit , String performanceTable) {
        int currentYear = dateService.getFullYear(date);

        Date calcDate = dateService.setWholeHour(date);

        PerformanceTime performanceTime = null;
        PerformanceTableMonth performanceTableMonth = performanceTableMonthRepository.getPerformanceTime(calcDate, OrganizationalUnit,performanceTable);

        if(performanceTableMonth != null) {
            int year = performanceTableMonth.getPerformanceYear();
            int quarter = performanceTableMonth.getPerformanceQuarter();
            int month = performanceTableMonth.getPerformanceMonth();
            int days = performanceTableMonth.getDays();

            //calc performance actual & plan month
            int actualCalEndMonth = -1;

            if(currentYear > year) actualCalEndMonth = 12;
            else if(currentYear < year) actualCalEndMonth = 0;
            else actualCalEndMonth = month;

            int planStartMonth = actualCalEndMonth + 1 ;

            //計算該季的月份區間
            List<Integer> monthOfQuarter = performanceTableMonthRepository.findQuarterMonths(year,quarter,performanceTable);

            //計算該年的月份區間
            List<Integer> yearMonthList = performanceTableMonthRepository.findYearMonths(year, performanceTable);
            int endMonthOfYear = yearMonthList.size() != 0 ? yearMonthList.get(yearMonthList.size()-1) : 12;

            performanceTime = new PerformanceTime();
            performanceTime.setYear(year);
            performanceTime.setQuarter(quarter);
            performanceTime.setMonth(month);
            performanceTime.setDays(days);
            performanceTime.setActualCalcEndMonth(actualCalEndMonth);
            performanceTime.setPlanClacStartMonth(planStartMonth);
            performanceTime.setMonthOfQuarter(monthOfQuarter);
            performanceTime.setEndMonthOfYear(endMonthOfYear);
        }


        return performanceTime;
    }

    public PersonalActivity getPersonalActivity(String agentID , int dataYear , String organizationalUnit,TimeBase timeBase) {

        PersonalActivity personalActivity = new PersonalActivity();

        List<PersonalProgress> personalProgresses = progressRepository.getPersonalProgressSync(agentID, organizationalUnit, dataYear,timeBase.toString());
        for(PersonalProgress personalProgress : personalProgresses) {
            String dataType = personalProgress.getIdentity().getDataType();

            if("Actual".equalsIgnoreCase(dataType)) personalActivity.setActualValues(new BigDecimal[]{personalProgress.getFind(),personalProgress.getSchedule(),personalProgress.getMeet(),personalProgress.getSubmit(),personalProgress.getInforce()});
            else personalActivity.setGoalValues(new BigDecimal[]{personalProgress.getFind(),personalProgress.getSchedule(),personalProgress.getMeet(),personalProgress.getSubmit(),personalProgress.getInforce()});
        }


        return personalActivity;
    }

    public String getTeamProgressDetailAcvitity(PersonalActivity personalActivity) {
        List calcList = new ArrayList();

        if(personalActivity != null && personalActivity.getActualValues().length == personalActivity.getGoalValues().length) {
            CalcPersonalProgress calcPersonalProgress = (CalcPersonalProgress) beanFactory.getBean(InstanceCode.CalcPersonalProgress);

            BigDecimal[] actualValues = personalActivity.getActualValues();
            BigDecimal[] goalValues = personalActivity.getGoalValues();

            if(calcPersonalProgress.isFindOverShowDetail(actualValues[0],goalValues[0])) calcList.add("F");
            if(calcPersonalProgress.isScheduleOverShowDetail(actualValues[1],goalValues[1])) calcList.add("S");
            if(calcPersonalProgress.isMeetOverShowDetail(actualValues[2],goalValues[2])) calcList.add("M");
            if(calcPersonalProgress.isSubmitOverShowDetail(actualValues[3],goalValues[3])) calcList.add("Su");
            if(calcPersonalProgress.isInforceOverShowDetail(actualValues[4],goalValues[4])) calcList.add("I");
        }


        return String.join("/",calcList);
    }

    /**
     * 取得某業務員的活動量(by year/quarter/month)
     * @return
     */
    public Activity getActivity(String agentID, ProgressTimeField timeFiled, PerformanceTime performanceTime , int dataYear) {

        Activity activity = new Activity();

        //如果計算年度大於當年度就不用計算Activity，因為還沒開始
        if(performanceTime.getYear() < dataYear) return activity;

        int currentDate = dateService.getCurrentDate();
        int currentMonth = dateService.getCurrentMonth();
        Date[] week = dateService.getCurrentWeek();

        List<Object[]> findAndScheduleResult = new ArrayList<>();
        List<Object[]> meetAndSubmitAndInforceResult = new ArrayList<>();

        //Day & Week all use start & end query
        if(timeFiled == ProgressTimeField.Day || timeFiled == ProgressTimeField.Week) {
            Date startDate = dateService.getDate(dataYear,currentMonth,currentDate);
            Date endDate = dateService.getDate(dataYear,currentMonth,currentDate);

            if(timeFiled == ProgressTimeField.Week) {

                startDate = week[0];
                startDate = dateService.setYear(dataYear,startDate);

                endDate = week[1];
                endDate = dateService.setYear(dataYear,endDate);
            }

            String queryStartDate = dateService.toDateString(startDate,"yyyy-MM-dd");
            String queryEndDate = dateService.toDateString(endDate,"yyyy-MM-dd");

            logger.trace("agentID = " + agentID +", timeFiled = "+ timeFiled.toString() +", queryStartDate = "+queryStartDate+",  queryEndDate = "+queryEndDate);

            findAndScheduleResult = agentDailyAppDataRepository.getFindAndSchedule(agentID,queryStartDate,queryEndDate);
            meetAndSubmitAndInforceResult = agentDailyDataRepository.getMeetAndSubmitAndInforce(agentID,queryStartDate,queryEndDate);
        }
        else {
            int month = performanceTime.getMonth();
            int quarter = performanceTime.getQuarter();

            if(timeFiled == ProgressTimeField.Month) {
                findAndScheduleResult = agentDailyAppDataRepository.getMonthFindAndSchedule(agentID,dataYear,month);
                meetAndSubmitAndInforceResult = agentDailyDataRepository.getMonthMeetAndSubmitAndInforce(agentID,dataYear,month);

            }
            else if(timeFiled == ProgressTimeField.Quarter){
                findAndScheduleResult =agentDailyAppDataRepository.getQuarterFindAndSchedule(agentID,dataYear,quarter);
                meetAndSubmitAndInforceResult =agentDailyDataRepository.getQuarterMeetAndSubmitAndInforce(agentID,dataYear,quarter);
            }
            else if(timeFiled == ProgressTimeField.Year){
                findAndScheduleResult =agentDailyAppDataRepository.getYearFindAndSchedule(agentID,dataYear);
                meetAndSubmitAndInforceResult =agentDailyDataRepository.getYearMeetAndSubmitAndInforce(agentID,dataYear);
            }

        }

        if(findAndScheduleResult.size() != 0
                && meetAndSubmitAndInforceResult.size() != 0
                && findAndScheduleResult.size() == meetAndSubmitAndInforceResult.size()) {

            Object[] findAndSchedule = findAndScheduleResult.get(0);
            Object[] meetAndSubmitAndInforce = meetAndSubmitAndInforceResult.get(0);

            logger.trace("agentID = "+agentID+", timeFiled = "+timeFiled.toString()+", findAndSchedule[0] = "+findAndSchedule[0]+", findAndSchedule[1] = "+findAndSchedule[1]+", meetAndSubmitAndInforce[0] = "+meetAndSubmitAndInforce[0]+", meetAndSubmitAndInforce[1] = "+meetAndSubmitAndInforce[1]+", meetAndSubmitAndInforce[2] = "+meetAndSubmitAndInforce[2] );

            activity.setNumberOfFind(Integer.parseInt(String.valueOf(findAndSchedule[0])));
            activity.setNumberOfSchdule(Integer.parseInt(String.valueOf(findAndSchedule[1])));
            activity.setNumberOfMeet(Integer.parseInt(String.valueOf(meetAndSubmitAndInforce[0])));
            activity.setNumberOfSubmit(Integer.parseInt(String.valueOf(meetAndSubmitAndInforce[1])));
            activity.setNumberOfInforce(Integer.parseInt(String.valueOf(meetAndSubmitAndInforce[2])));
        }


        return activity;
    }


    public int getActivityPointByDate(String agentID, Date date) {

        int sum = 0;
        String organizationalUnit = organizationService.getOrganizationalUnit();

        logger.debug("getActivityPointByDate date:"+date.toString());

        List<BigDecimal> sums = progressRepository.getActivityPointByDate(agentID, organizationalUnit, date);
        for(BigDecimal i : sums) {
            sum += i.intValue();
        }

        return sum;
    }


    private void savePersonalProgress(String dataType , String organizationalUnit , String agentID , int year , String timebase,
                                      double find , double schedule , double meet , double submit , double inforce , long fyfc) {

//		logger.info("savePersonalProgress dataType = " + dataType);

        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        nf.setMinimumFractionDigits(0);
        nf.setMaximumFractionDigits(1);

        PersonalProgress personalProgress = new PersonalProgress();

        ProgressIdentity progressIdentity = new ProgressIdentity();
        progressIdentity.setOrganizationalUnit(organizationalUnit);
        progressIdentity.setAgentID(agentID);
        progressIdentity.setDataYear(year);
        progressIdentity.setTimeBase(timebase);
        progressIdentity.setDataType(dataType);

        personalProgress.setIdentity(progressIdentity);
        personalProgress.setFind(new BigDecimal(nf.format(find)));
        personalProgress.setSchedule(new BigDecimal(nf.format(schedule)));
        personalProgress.setMeet(new BigDecimal(nf.format(meet)));
        personalProgress.setSubmit(new BigDecimal(nf.format(submit)));
        personalProgress.setInforce(new BigDecimal(nf.format(inforce)));
        personalProgress.setFyfc(new BigDecimal(fyfc));
        personalProgress.setCreateBy("SD");

        personalProgressRepository.save(personalProgress);
    }
}
