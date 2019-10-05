package com.allianz.sd.core.datasync.impl;

import com.allianz.sd.core.InstanceCode;
import com.allianz.sd.core.api.model.Actual;
import com.allianz.sd.core.api.model.ActualValue;
import com.allianz.sd.core.api.model.Extension;
import com.allianz.sd.core.api.model.ModelConfiguration;
import com.allianz.sd.core.datasync.AgentTableDataSync;
import com.allianz.sd.core.datasync.ConfigurationSyncListener;
import com.allianz.sd.core.exception.NotFoundAgentYearDataException;
import com.allianz.sd.core.exception.NotFoundPerformanceTableMonthException;
import com.allianz.sd.core.exception.NotFoundSysYearDataException;
import com.allianz.sd.core.jpa.model.AgentSalesData;
import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.jpa.model.SysYearData;
import com.allianz.sd.core.jpa.repository.PerformanceTableMonthRepository;
import com.allianz.sd.core.service.*;
import com.allianz.sd.core.service.bean.DateType;
import com.allianz.sd.core.service.bean.PerformanceTime;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class ConfigurationAgentTableDataSync implements AgentTableDataSync {


    @Autowired
    private AgentDataService agentDataService;

    @Autowired
    private DateService dateService;

    @Autowired
    private PerformanceTableMonthRepository performanceTableMonthRepository;

    @Autowired
    private SysDataService sysDataService;

    @Autowired
    private NumberService numberService;

    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private ProgressService progressService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private UtilsService utilsService;

    @Override
    public void pullData(String agentID, List pullData) throws Exception{
        Date currentDate = dateService.getTodayDate();

        String organizationalUnit = organizationService.getOrganizationalUnit();

        Set<Integer> years = sysDataService.getAPPDisplayYears(currentDate,organizationalUnit);

        for(Integer dataYear : years) {
            SysYearData sysYearData = sysDataService.getSysYearData(organizationalUnit,agentID,dataYear);
            if(sysYearData != null) {

                AgentYearData agentYearData = agentDataService.getAgentYearData(agentID,dataYear);
                if(agentYearData != null) {

                    Integer initPrecaseFYFC = agentYearData.getInitialPreCaseFyfc();
                    String performanceTable = agentYearData.getPerformanceTable();

                    //查詢Performance_Table_Month 比對當下時間取得對應季的起始月、結束月
                    PerformanceTime performanceTime = progressService.getPerformanceTime(currentDate,organizationalUnit,performanceTable);

                    if(performanceTime != null) {

                        List<Integer> quarterMonths = performanceTime.getMonthOfQuarter();
                        if(quarterMonths.size() >= 2) {
                            String startMonth = String.valueOf(quarterMonths.get(0));
                            String endMonth = String.valueOf(quarterMonths.get(quarterMonths.size()-1));

                            //判斷該資料的年份 是否是該年度的年份
                            boolean isCurrent = dateService.getCurrentYear() == sysYearData.getIdentity().getDataYear();

                            //查Performance Month 年底日期  算出天數
                            Date endDate = performanceTableMonthRepository.findEndDate(performanceTable,organizationalUnit, dateService.getCurrentYear());

                            //減一天代表不計算今天
                            int days = isCurrent ? dateService.getBetweenTime(currentDate, endDate, DateType.DAY) : 365;

                            Double goalAndPlanDifferenceLimit = sysYearData.getGoalAndPlanDifferenceLimit();
                            Double fyfcConvertAnpRate = sysYearData.getFyfcCovertAnpRate();
                            String procMode = sysYearData.getGoalSettingActivityProcMode();

                            goalAndPlanDifferenceLimit = goalAndPlanDifferenceLimit / 100;
                            fyfcConvertAnpRate = fyfcConvertAnpRate / 100;

                            //塞Configuration 資料
                            ModelConfiguration modelConfiguration = new ModelConfiguration();
                            modelConfiguration.setIsCurrent(isCurrent);
                            modelConfiguration.setDataYear(new BigDecimal(sysYearData.getIdentity().getDataYear()));
                            modelConfiguration.setInitialPreCaseFyfc(new BigDecimal(initPrecaseFYFC != null ? initPrecaseFYFC : 0));
                            modelConfiguration.setFyfcCovertAnpRate(numberService.convertToDecimal(fyfcConvertAnpRate));
                            modelConfiguration.setProgressDayPointsLimit(new BigDecimal(sysYearData.getProgressDayPointsLimit()));
                            modelConfiguration.setGoalSettingActivityProcMode(procMode);
                            modelConfiguration.setGoalAndPlanDifferenceLimit(numberService.convertToDecimal(goalAndPlanDifferenceLimit));
                            modelConfiguration.setFindConvertPointBase(new BigDecimal(sysYearData.getFindConvertPointBase()));
                            modelConfiguration.setScheduleConvertPointBase(new BigDecimal(sysYearData.getScheduleConvertPointBase()));
                            modelConfiguration.setMeetConvertPointBase(new BigDecimal(sysYearData.getMeetConvertPointBase()));
                            modelConfiguration.setSubmitConvertPointBase(new BigDecimal(sysYearData.getSubmitConvertPointBase()));
                            modelConfiguration.setInforceConvertPointBase(new BigDecimal(sysYearData.getInforceConvertPointBase()));

                            modelConfiguration.setInforceConvertFindRate(numberService.convertToDecimal(sysYearData.getInforceConvertFindRate()));
                            modelConfiguration.setInforceConvertScheduleRate(numberService.convertToDecimal(sysYearData.getInforceConvertScheduleRate()));
                            modelConfiguration.setInforceConvertMeetRate(numberService.convertToDecimal(sysYearData.getInforceConvertMeetRate()));
                            modelConfiguration.setInforceConvertSubmitRate(numberService.convertToDecimal(sysYearData.getInforceConvertSubmitRate()));

                            modelConfiguration.setProgressBarControlMode(sysYearData.getProgressBarControlMode());
                            modelConfiguration.setQuarterStartMonth(new BigDecimal(startMonth));
                            modelConfiguration.setQuarterEndMonth(new BigDecimal(endMonth));

                            modelConfiguration.setPerformanceSettlementMonth(new BigDecimal(isCurrent ? dateService.getMonthString(sysYearData.getSysData().getPerformanceSettlementMonth()) : "0" ));
                            modelConfiguration.setNowToYearEndDays(new BigDecimal(days));

                            modelConfiguration.setWorkingMonth(new BigDecimal(isCurrent ? performanceTime.getMonth() : 0));
                            modelConfiguration.setWorkingQuarter(new BigDecimal(isCurrent ? performanceTime.getQuarter() : 0));

                            //count this year has how many month
                            int MonthQuantityOfYear = performanceTableMonthRepository.getMonthQuantityOfYear(dateService.getCurrentYear(),performanceTable);
                            modelConfiguration.setMonthQuantityOfYear(new BigDecimal(MonthQuantityOfYear));

                            Object configurationExtensionBean = beanFactory.getBean(InstanceCode.ConfigurationExtension);
                            if(utilsService.isNotEmptyBean(configurationExtensionBean)) {
                                List<Extension> extensionList = new ArrayList<>();
                                ConfigurationSyncListener listener = (ConfigurationSyncListener) configurationExtensionBean;
                                listener.onPullData(sysYearData,agentYearData,extensionList);
                                modelConfiguration.setExtensions(extensionList);
                            }

                            pullData.add(modelConfiguration);
                        }
                    }
                    else {
                        throw new NotFoundPerformanceTableMonthException(organizationalUnit,agentID,dataYear);
                    }
                }
                else throw new NotFoundAgentYearDataException(organizationalUnit,agentID,dataYear);

            }

        }
    }
}
