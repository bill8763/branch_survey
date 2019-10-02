package com.allianz.sd.core.schedule.job;

import com.allianz.sd.core.jpa.model.*;
import com.allianz.sd.core.jpa.repository.*;
import com.allianz.sd.core.service.DateService;
import com.allianz.sd.core.service.OrganizationService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 2:50
 * To change this template use File | Settings | File Templates.
 */

/**
 * check calendar not all day event reminder
 */
@Component
public class TestingInsertAgentDailyData extends QuartzJobBean {

    private static final Logger logger = LoggerFactory.getLogger(TestingInsertAgentDailyData.class);

    private static Map<String, Map<Integer,Integer[]>> testDataMap = new LinkedHashMap<>();
    static{

        Map<Integer,Integer[]> ag02Map = new LinkedHashMap<>();
        Map<Integer,Integer[]> ag03Map = new LinkedHashMap<>();
        Map<Integer,Integer[]> ag211Map = new LinkedHashMap<>();
        Map<Integer,Integer[]> ag2132Map = new LinkedHashMap<>();
        Map<Integer,Integer[]> ag21322Map = new LinkedHashMap<>();

        ag02Map.put(1,new Integer[]{1,0,0});
        ag02Map.put(2,new Integer[]{2,0,0});
        ag02Map.put(3,new Integer[]{1,1,1});
        ag02Map.put(4,new Integer[]{2,1,0});
        ag02Map.put(5,new Integer[]{0,0,0});
        ag02Map.put(6,new Integer[]{0,2,1});
        ag02Map.put(7,new Integer[]{2,0,1});
        ag02Map.put(8,new Integer[]{0,0,0});
        ag02Map.put(9,new Integer[]{2,1,0});
        ag02Map.put(10,new Integer[]{1,1,1});
        ag02Map.put(11,new Integer[]{1,0,0});
        ag02Map.put(12,new Integer[]{2,1,0});
        ag02Map.put(13,new Integer[]{0,0,0});
        ag02Map.put(14,new Integer[]{0,0,0});
        ag02Map.put(15,new Integer[]{1,1,1});
        ag02Map.put(16,new Integer[]{0,0,0});
        ag02Map.put(17,new Integer[]{1,1,1});
        ag02Map.put(18,new Integer[]{1,0,0});
        ag02Map.put(19,new Integer[]{0,0,0});
        ag02Map.put(20,new Integer[]{0,0,0});
        ag02Map.put(21,new Integer[]{1,1,1});
        ag02Map.put(22,new Integer[]{2,1,0});
        ag02Map.put(23,new Integer[]{1,0,0});
        ag02Map.put(24,new Integer[]{2,1,0});
        ag02Map.put(25,new Integer[]{1,1,1});
        ag02Map.put(26,new Integer[]{1,0,0});
        ag02Map.put(27,new Integer[]{0,0,0});
        ag02Map.put(28,new Integer[]{0,0,0});
        ag02Map.put(29,new Integer[]{1,1,1});
        ag02Map.put(30,new Integer[]{1,1,1});
        ag02Map.put(31,new Integer[]{2,0,0});

        ag03Map.put(1,new Integer[]{2,1,1});
        ag03Map.put(2,new Integer[]{3,0,0});
        ag03Map.put(3,new Integer[]{1,1,1});
        ag03Map.put(4,new Integer[]{0,0,0});
        ag03Map.put(5,new Integer[]{1,0,1});
        ag03Map.put(6,new Integer[]{1,0,0});
        ag03Map.put(7,new Integer[]{1,1,0});
        ag03Map.put(8,new Integer[]{1,0,0});
        ag03Map.put(9,new Integer[]{1,1,1});
        ag03Map.put(10,new Integer[]{0,1,0});
        ag03Map.put(11,new Integer[]{1,0,0});
        ag03Map.put(12,new Integer[]{2,0,1});
        ag03Map.put(13,new Integer[]{0,0,0});
        ag03Map.put(14,new Integer[]{1,1,1});
        ag03Map.put(15,new Integer[]{1,1,1});
        ag03Map.put(16,new Integer[]{2,1,0});
        ag03Map.put(17,new Integer[]{1,1,1});
        ag03Map.put(18,new Integer[]{1,1,1});
        ag03Map.put(19,new Integer[]{0,1,0});
        ag03Map.put(20,new Integer[]{1,1,0});
        ag03Map.put(21,new Integer[]{1,0,1});
        ag03Map.put(22,new Integer[]{1,0,0});
        ag03Map.put(23,new Integer[]{1,0,0});
        ag03Map.put(24,new Integer[]{0,1,0});
        ag03Map.put(25,new Integer[]{1,1,0});
        ag03Map.put(26,new Integer[]{0,1,1});
        ag03Map.put(27,new Integer[]{1,0,1});
        ag03Map.put(28,new Integer[]{1,2,1});
        ag03Map.put(29,new Integer[]{1,0,0});
        ag03Map.put(30,new Integer[]{0,0,1});
        ag03Map.put(31,new Integer[]{1,0,0});

        ag211Map.put(1,new Integer[]{1,1,1});
        ag211Map.put(2,new Integer[]{0,0,0});
        ag211Map.put(3,new Integer[]{2,1,1});
        ag211Map.put(4,new Integer[]{2,0,0});
        ag211Map.put(5,new Integer[]{2,1,2});
        ag211Map.put(6,new Integer[]{3,1,1});
        ag211Map.put(7,new Integer[]{2,1,0});
        ag211Map.put(8,new Integer[]{0,2,1});
        ag211Map.put(9,new Integer[]{1,1,0});
        ag211Map.put(10,new Integer[]{0,0,1});
        ag211Map.put(11,new Integer[]{0,0,1});
        ag211Map.put(12,new Integer[]{1,0,0});
        ag211Map.put(13,new Integer[]{0,0,0});
        ag211Map.put(14,new Integer[]{1,0,1});
        ag211Map.put(15,new Integer[]{0,0,0});
        ag211Map.put(16,new Integer[]{1,1,0});
        ag211Map.put(17,new Integer[]{2,1,0});
        ag211Map.put(18,new Integer[]{0,1,0});
        ag211Map.put(19,new Integer[]{0,1,1});
        ag211Map.put(20,new Integer[]{1,0,0});
        ag211Map.put(21,new Integer[]{1,1,1});
        ag211Map.put(22,new Integer[]{2,0,0});
        ag211Map.put(23,new Integer[]{0,1,0});
        ag211Map.put(24,new Integer[]{2,1,0});
        ag211Map.put(25,new Integer[]{2,0,0});
        ag211Map.put(26,new Integer[]{1,0,0});
        ag211Map.put(27,new Integer[]{1,0,0});
        ag211Map.put(28,new Integer[]{0,0,1});
        ag211Map.put(29,new Integer[]{1,1,0});
        ag211Map.put(30,new Integer[]{1,1,1});
        ag211Map.put(31,new Integer[]{1,0,0});

        ag2132Map.put(1,new Integer[]{2,1,1});
        ag2132Map.put(2,new Integer[]{1,1,1});
        ag2132Map.put(3,new Integer[]{1,1,0});
        ag2132Map.put(4,new Integer[]{0,0,1});
        ag2132Map.put(5,new Integer[]{2,0,1});
        ag2132Map.put(6,new Integer[]{0,0,0});
        ag2132Map.put(7,new Integer[]{1,1,0});
        ag2132Map.put(8,new Integer[]{1,0,1});
        ag2132Map.put(9,new Integer[]{1,0,0});
        ag2132Map.put(10,new Integer[]{0,1,0});
        ag2132Map.put(11,new Integer[]{1,0,1});
        ag2132Map.put(12,new Integer[]{1,1,0});
        ag2132Map.put(13,new Integer[]{0,0,1});
        ag2132Map.put(14,new Integer[]{1,0,1});
        ag2132Map.put(15,new Integer[]{1,0,0});
        ag2132Map.put(16,new Integer[]{1,1,0});
        ag2132Map.put(17,new Integer[]{1,1,1});
        ag2132Map.put(18,new Integer[]{1,1,0});
        ag2132Map.put(19,new Integer[]{0,1,0});
        ag2132Map.put(20,new Integer[]{1,0,0});
        ag2132Map.put(21,new Integer[]{1,1,0});
        ag2132Map.put(22,new Integer[]{0,0,1});
        ag2132Map.put(23,new Integer[]{1,1,1});
        ag2132Map.put(24,new Integer[]{2,0,0});
        ag2132Map.put(25,new Integer[]{1,0,1});
        ag2132Map.put(26,new Integer[]{2,0,1});
        ag2132Map.put(27,new Integer[]{3,1,0});
        ag2132Map.put(28,new Integer[]{2,0,1});
        ag2132Map.put(29,new Integer[]{0,1,0});
        ag2132Map.put(30,new Integer[]{2,1,1});
        ag2132Map.put(31,new Integer[]{2,0,0});

        ag21322Map.put(1,new Integer[]{1,1,1});
        ag21322Map.put(2,new Integer[]{2,1,1});
        ag21322Map.put(3,new Integer[]{1,0,1});
        ag21322Map.put(4,new Integer[]{3,2,0});
        ag21322Map.put(5,new Integer[]{1,0,1});
        ag21322Map.put(6,new Integer[]{0,1,1});
        ag21322Map.put(7,new Integer[]{1,1,0});
        ag21322Map.put(8,new Integer[]{0,0,1});
        ag21322Map.put(9,new Integer[]{1,0,1});
        ag21322Map.put(10,new Integer[]{1,1,0});
        ag21322Map.put(11,new Integer[]{1,1,0});
        ag21322Map.put(12,new Integer[]{2,1,1});
        ag21322Map.put(13,new Integer[]{0,1,0});
        ag21322Map.put(14,new Integer[]{1,0,0});
        ag21322Map.put(15,new Integer[]{1,0,0});
        ag21322Map.put(16,new Integer[]{0,0,0});
        ag21322Map.put(17,new Integer[]{1,0,1});
        ag21322Map.put(18,new Integer[]{1,1,0});
        ag21322Map.put(19,new Integer[]{1,1,0});
        ag21322Map.put(20,new Integer[]{0,1,0});
        ag21322Map.put(21,new Integer[]{1,1,0});
        ag21322Map.put(22,new Integer[]{1,0,0});
        ag21322Map.put(23,new Integer[]{0,0,1});
        ag21322Map.put(24,new Integer[]{2,0,0});
        ag21322Map.put(25,new Integer[]{0,1,2});
        ag21322Map.put(26,new Integer[]{2,0,0});
        ag21322Map.put(27,new Integer[]{2,1,1});
        ag21322Map.put(28,new Integer[]{1,0,1});
        ag21322Map.put(29,new Integer[]{2,0,1});
        ag21322Map.put(30,new Integer[]{0,1,1});
        ag21322Map.put(31,new Integer[]{0,0,0});

        testDataMap.put("AG01",ag02Map);
        testDataMap.put("AG2",ag02Map);
        testDataMap.put("AG2132",ag02Map);
        testDataMap.put("AGZH2",ag02Map);
        testDataMap.put("AG422",ag02Map);
        testDataMap.put("AG431",ag02Map);
        testDataMap.put("AG511",ag02Map);

        testDataMap.put("AG3",ag03Map);
        testDataMap.put("AG2133",ag03Map);
        testDataMap.put("AG4",ag03Map);
        testDataMap.put("AG4221",ag03Map);
        testDataMap.put("AG4311",ag03Map);
        testDataMap.put("AG52",ag03Map);

        testDataMap.put("AG21",ag211Map);
        testDataMap.put("AG21332",ag211Map);
        testDataMap.put("AG41",ag211Map);
        testDataMap.put("AG4222",ag211Map);
        testDataMap.put("AG432",ag211Map);

        testDataMap.put("AG211",ag2132Map);
        testDataMap.put("AGCAO",ag2132Map);
        testDataMap.put("AG42",ag2132Map);
        testDataMap.put("AG42221",ag2132Map);
        testDataMap.put("AG5",ag2132Map);

        testDataMap.put("AG213",ag21322Map);
        testDataMap.put("ZH",ag21322Map);
        testDataMap.put("AG421",ag21322Map);
        testDataMap.put("AG43",ag21322Map);
        testDataMap.put("AG51",ag21322Map);

    }

    @Autowired
    private AgentDailyDataRepository agentDailyDataRepository;

    @Autowired
    private DateService dateService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private AgentYearDataRepository agentYearDataRepository;

    @Autowired
    private SysYearDataRepository sysYearDataRepository;

    @Autowired
    private PerformanceTableMonthRepository performanceTableMonthRepository;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        logger.trace("Startup TestingInsertAgentDailyData");

        int currentYear = dateService.getCurrentYear();
        int currentMonth = dateService.getCurrentMonth();

        for(int i=1;i<=12;i++) {
            int month = i;

            Date date = dateService.getTodayDate();
            date = dateService.setYear(currentYear,date);
            date = dateService.setMonth(month,date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int dayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

            for(String agentID : testDataMap.keySet()) {

                logger.trace("agentID = " + agentID);

                Map<Integer,Integer[]> dataMap = testDataMap.get(agentID);

                for(int j=1;j<=dayOfMonth;j++) {

                    Integer[] datas = dataMap.get(j);

                    date = dateService.setDate(j,date);
                    date.setHours(0);
                    date.setMinutes(0);
                    date.setSeconds(0);

                    logger.trace("now = " + date);

                    String organizationalUnit = organizationService.getOrganizationalUnit();

                    AgentYearDataIdentity agentYearDataIdentity = new AgentYearDataIdentity();
                    agentYearDataIdentity.setAgentID(agentID);
                    agentYearDataIdentity.setOrganizationalUnit(organizationalUnit);
                    agentYearDataIdentity.setDataYear(currentYear);
                    Optional<AgentYearData> agentYearDataOption = agentYearDataRepository.findById(agentYearDataIdentity);
                    AgentYearData agentYearDataQuery = agentYearDataOption.orElse(null);

                    if(agentYearDataQuery != null) {
                        String appSysCtrl = agentYearDataQuery.getAppSysCtrl();
                        String performanceTable = agentYearDataQuery.getPerformanceTable();

                        SysYearDataIdentity sysYearDataIdentity = new SysYearDataIdentity();
                        sysYearDataIdentity.setAppSysCtrl(appSysCtrl);
                        sysYearDataIdentity.setDataYear(currentYear);
                        sysYearDataIdentity.setOrganizationalUnit(organizationalUnit);
                        Optional<SysYearData> sysYearDataOption = sysYearDataRepository.findById(sysYearDataIdentity);
                        SysYearData sysYearDataQuery = sysYearDataOption.orElse(null);
                        if(sysYearDataQuery != null) {

                            double meetConvertPointBase = sysYearDataQuery.getMeetConvertPointBase();
                            double submitConvertPointBase = sysYearDataQuery.getSubmitConvertPointBase();
                            double inforceConvertPointBase = sysYearDataQuery.getInforceConvertPointBase();

                            //step4. query Year/Quarter/Month
                            PerformanceTableMonthIdentity performanceTableMonthIdentity = new PerformanceTableMonthIdentity();
                            performanceTableMonthIdentity.setOrganizationalUnit(organizationalUnit);
                            performanceTableMonthIdentity.setPerformanceTable(performanceTable);
                            performanceTableMonthIdentity.setStartDate(date);

                            PerformanceTableMonth performanceTableMonth = performanceTableMonthRepository.findDataByOrganizationalUnitPerformanceTableDate(organizationalUnit, performanceTable,date,date);
                            if(performanceTableMonth != null) {
                                int performanceMonth = performanceTableMonth.getPerformanceMonth();
                                int performanceYear = performanceTableMonth.getPerformanceYear();
                                int performanceQuarter = performanceTableMonth.getPerformanceQuarter();

                                AgentDailyData agentDailyData = new AgentDailyData();
                                AgentDailyDataIdentity identity = new AgentDailyDataIdentity();
                                identity.setDataDate(date);
                                identity.setAgentID(agentID);
                                identity.setOrganizationalUnit(organizationalUnit);

                                agentDailyData.setIdentity(identity);
                                agentDailyData.setNumberOfMeet(datas[0]);
                                agentDailyData.setNumberOfSubmit(datas[1]);
                                agentDailyData.setNumberOfInforce(datas[2]);
                                agentDailyData.setProgressPointMeet((int)(agentDailyData.getNumberOfMeet() * meetConvertPointBase));
                                agentDailyData.setProgressPointSubmit((int)(agentDailyData.getNumberOfSubmit() * submitConvertPointBase));
                                agentDailyData.setProgressPointInforce((int)(agentDailyData.getNumberOfInforce() * inforceConvertPointBase));

                                agentDailyData.setDataYear(performanceYear);
                                agentDailyData.setDataQuarter(performanceQuarter);
                                agentDailyData.setDataMonth(performanceMonth);

                                agentDailyData.setCreateBy(agentID);

                                agentDailyDataRepository.save(agentDailyData);
                            }
                            else {
                                logger.trace("can't found PerformanceTable = ["+agentID+"],["+currentYear+"]["+performanceTable+"]");
                            }


                        }
                        else {
                            logger.trace("can't found SysYearData = ["+agentID+"],["+currentYear+"]["+appSysCtrl+"]");
                        }
                    }
                    else {
                        logger.trace("can't found AgentYearData = ["+agentID+"],["+currentYear+"]["+organizationalUnit+"]");
                    }
                }

            }
        }


    }
}
