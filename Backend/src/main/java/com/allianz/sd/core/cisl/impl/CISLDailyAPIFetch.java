package com.allianz.sd.core.cisl.impl;

import com.allianz.sd.core.cisl.bean.DailyDataCISL;
import com.allianz.sd.core.cisl.bean.ProcessData;
import com.allianz.sd.core.jpa.model.*;
import com.allianz.sd.core.jpa.repository.AgentYearDataRepository;
import com.allianz.sd.core.jpa.repository.CISLAgentDailyDataRepository;
import com.allianz.sd.core.service.DateService;
import com.allianz.sd.core.service.ProgressService;
import com.allianz.sd.core.service.SysDataService;
import com.allianz.sd.core.service.bean.PerformanceTime;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;

@Component
public class CISLDailyAPIFetch extends CISLSupportAgentAPIFetch {

    @Value("${CISL.API.DailyURL}")
    private String url;

    @Autowired
    private DateService dateService;

    @Autowired
    private CISLAgentDailyDataRepository agentDailyDataRepository;

    @Autowired
    private AgentYearDataRepository agentYearDataRepository;

    @Autowired
    private ProgressService progressService;

    @Autowired
    private SysDataService sysDataService;

    @Override
    protected String getCISLAPIURL() {
        return url;
    }

    @Override
    protected String getUsageModel() {
        return "DailyData";
    }

    @Override
    public ProcessData parse2ProcessData(JSONObject content) throws Exception {
        DailyDataCISL dailyData = new DailyDataCISL();

        String organizationalUnit = content.getJSONObject("organizationalUnit").getString("locationName");
        String agentID = content.getString("agentNumber");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dataDate = content.getString("accountManagerCategory");
        Date dataDateFormat = sdf.parse(dataDate);

        //取得年份
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(dataDateFormat);
        int dataYear = calendar.get(Calendar.YEAR);

        //get _refs Object >> get salesgoal Object
        JSONObject salesGoalObject = content.getJSONObject("_refs").getJSONObject("salesgoal");

        int numberOfMeet = salesGoalObject.getInt("plannedSales");
        int numberOfSubmit = salesGoalObject.getInt("plannedAppointments");

        // get extEntity Object to get numberOfInforce
        JSONObject extEntityObject = salesGoalObject.getJSONObject("extEntity");

        int numberOfInforce = extEntityObject.getInt("plannedContactsAchieved");

        //get lastUpdateValidityPeriod Object
        JSONObject lastUpdateValidityPeriodObject = extEntityObject.getJSONObject("lastUpdateValidityPeriod");

        //format yyyy-MM-dd'T'HH:mm:ss
        String dataTime = lastUpdateValidityPeriodObject.getString("startDateTime");
        Date dataTimeDate = dateService.getDateFormatCISL(dataTime) ;

        //set Bean
        dailyData.setOrganizationalUnit(organizationalUnit);
        dailyData.setAgentID(agentID);
        dailyData.setDataYear(dataYear);
        dailyData.setDataDate(dataDateFormat);
        dailyData.setDataTime(dataTimeDate);
        dailyData.setNumberOfMeet(numberOfMeet);
        dailyData.setNumberOfSubmit(numberOfSubmit);
        dailyData.setNumberOfInforce(numberOfInforce);

        return dailyData;
    }

    @Override
    public void saveToSND(ProcessData processData) throws Exception {

        DailyDataCISL dailyData1 = (DailyDataCISL) processData;
        String organizationalUnit = dailyData1.getOrganizationalUnit();
        String agentID = dailyData1.getAgentID();
        int year = dailyData1.getDataYear();
        Date dataDate = dailyData1.getDataDate();
        Date dataTime = dailyData1.getDataTime();
        int numberOfMeet = dailyData1.getNumberOfMeet();
        int numberOfSubmit = dailyData1.getNumberOfSubmit();
        int numberOfInforce = dailyData1.getNumberOfInforce();

        //query AgentYearData to get AppSysCtrl
        AgentYearDataIdentity agentYearDataIdentity = new AgentYearDataIdentity();
        agentYearDataIdentity.setAgentID(agentID);
        agentYearDataIdentity.setOrganizationalUnit(organizationalUnit);
        agentYearDataIdentity.setDataYear(year);
        Optional<AgentYearData> agentYearDataOptional = agentYearDataRepository.findById(agentYearDataIdentity);
        AgentYearData agentYearData = agentYearDataOptional.orElse(null);
        if(agentYearData == null ) throw new Exception("Can't find AgentYearData");

        String appSysCtrl = agentYearData.getAppSysCtrl();
        String performanceTable = agentYearData.getPerformanceTable();

        //query PerformanceTableMonth to get Year Quarter Month
        PerformanceTime performanceTime = progressService.getPerformanceTime(dataTime, organizationalUnit, performanceTable);
        if(performanceTime == null) throw new Exception("Can't find PerformanceTime["+dataTime+"]["+performanceTable+"]");

        //query SysYearData to get PointBase
        SysYearData sysYearData = sysDataService.getSysYearDataBySysCtrl(organizationalUnit,appSysCtrl,year);
        if(sysYearData == null) throw new Exception("Can't find SysYearData["+appSysCtrl+"]["+year+"]");

        int quarter = performanceTime.getQuarter();
        int month = performanceTime.getMonth();

        Double meetBase = sysYearData.getMeetConvertPointBase();
        Double submitBase = sysYearData.getSubmitConvertPointBase();
        Double inforceBase = sysYearData.getInforceConvertPointBase();

        //cal Point
        int pointMeet = (int) (meetBase * Double.valueOf(numberOfMeet));
        int pointSubmit = (int) (submitBase * Double.valueOf(numberOfSubmit));
        int pointInforce = (int) (inforceBase * Double.valueOf(numberOfInforce));

        AgentDailyDataIdentity identity = new AgentDailyDataIdentity();
        identity.setOrganizationalUnit(organizationalUnit);
        identity.setDataDate(dataDate);
        identity.setAgentID(agentID);

        Optional<CISLAgentDailyData> agentDailyDataOptional = agentDailyDataRepository.findById(identity);
        CISLAgentDailyData agentDailyData = agentDailyDataOptional.orElse(new CISLAgentDailyData());

        agentDailyData.setIdentity(identity);
        agentDailyData.setNumberOfMeet(numberOfMeet);
        agentDailyData.setNumberOfSubmit(numberOfSubmit);
        agentDailyData.setNumberOfInforce(numberOfInforce);
        agentDailyData.setProgressPointMeet(pointMeet);
        agentDailyData.setProgressPointSubmit(pointSubmit);
        agentDailyData.setProgressPointInforce(pointInforce);
        agentDailyData.setDataQuarter(quarter);
        agentDailyData.setDataYear(year);
        agentDailyData.setDataMonth(month);
        agentDailyData.setDataTime(dataTime);
        agentDailyData.setCreateBy("SNDBatchCISL");
        agentDailyData.setUpdateBy("SNDBatchCISL");
        agentDailyDataRepository.save(agentDailyData);
    }
}
