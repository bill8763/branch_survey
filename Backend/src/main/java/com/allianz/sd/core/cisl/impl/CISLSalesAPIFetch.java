package com.allianz.sd.core.cisl.impl;

import com.allianz.sd.core.cisl.bean.GoalSettingCISL;
import com.allianz.sd.core.cisl.bean.ProcessData;
import com.allianz.sd.core.cisl.bean.SalesDataCISL;
import com.allianz.sd.core.jpa.model.AgentSalesDataIdentity;
import com.allianz.sd.core.jpa.model.AgentYearDataIdentity;
import com.allianz.sd.core.jpa.model.CISLAgentSalesData;
import com.allianz.sd.core.jpa.model.CISLGoalSetting;
import com.allianz.sd.core.jpa.repository.CISLAgentSalesDataRepository;
import com.allianz.sd.core.jpa.repository.CISLGoalSettingRepository;
import com.allianz.sd.core.service.CISLService;
import com.allianz.sd.core.service.DateService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;

@Component
public class CISLSalesAPIFetch extends CISLSupportAgentAPIFetch {

    @Value("${CISL.API.SalesURL}")
    private String url;

    @Autowired
    private DateService dateService;

    @Autowired
    private CISLService cISLService;

    @Autowired
    private CISLAgentSalesDataRepository agentSalesDataRepository;

    @Override
    protected String getCISLAPIURL() {
        return url;
    }

    @Override
    protected String getUsageModel() {
        return "SalesData";
    }

    @Override
    public ProcessData parse2ProcessData(JSONObject content) throws Exception {

        SalesDataCISL salesData = new SalesDataCISL();

        String organizationalUnit = content.getJSONObject("organizationalUnit").getString("locationName");
        String agentID = content.getString("agentNumber");
        String performanceType = content.getJSONObject("extEntity").getString("performanceType");
        String dataYearMonth = content.getString("accountManagerCategory") + "01";
        Date dateYearM = dateService.parseDateString2Date(dataYearMonth);

        //get salesgoal Object
        JSONObject salesObject = content.getJSONObject("_refs").getJSONObject("salesgoal");

        // get goalDetails Array to get anp
        JSONArray goalDetailsArray = salesObject.getJSONArray("goalDetails");
        for (int i = 0; i < goalDetailsArray.length(); i++) {
            JSONObject goalDetailsObject = goalDetailsArray.getJSONObject(i);

            // 過濾特殊符號
            String anp = cISLService.filterSymbol(goalDetailsObject.getString("annualPremium"));

            // get extEntity Object to get fyfc
            String fyfc = goalDetailsObject.getJSONObject("extEntity").getString("businessResult");

            salesData.setAnp(Long.parseLong(anp));
            salesData.setFyfc(Long.parseLong(fyfc));
        }

        // get _refs Ojbect to get N M R
        JSONObject refsObject = content.getJSONObject("_refs").getJSONObject("salesgoal").getJSONObject("extEntity");
        long newBusinessCase = refsObject.getLong("numberOfAppliedAccountManagers");
        long manPower = refsObject.getLong("numberOfAccountManagers");
        long recruitment = refsObject.getLong("numberOfRecruitedAccountManagers");

        // get _refs >> lastUpdateValidityPeriod Object to get dataTime
        String dataTime =  refsObject.getJSONObject("lastUpdateValidityPeriod").getString("startDateTime");

        //format to yyyy-MM-dd'T'HH:mm:ss
        Date dataTimeFormat = dateService.getDateFormatCISL(dataTime);

        //get dataYear
        int dataYear = dateService.getFullYear(dataTimeFormat);

        //save Data
        salesData.setOrganizationalUnit(organizationalUnit);
        salesData.setAgentID(agentID);
        salesData.setDataYear(dataYear);
        salesData.setPerformanceType(performanceType);
        salesData.setDataYearMonth(dateYearM);
        salesData.setManpower(manPower);
        salesData.setRecruitment(recruitment);
        salesData.setNewBusinessCase(newBusinessCase);
        salesData.setDataTime(dataTimeFormat);

        return salesData;
    }

    @Override
    public void saveToSND(ProcessData processData) throws Exception{

        SalesDataCISL dailyDataTH = (SalesDataCISL) processData;

        String agentID = dailyDataTH.getAgentID();
        String organizationalUnit = dailyDataTH.getOrganizationalUnit();
        Date dataYearMonth = dailyDataTH.getDataYearMonth();
        String performanceType = dailyDataTH.getPerformanceType();
        long anp = dailyDataTH.getAnp();
        long fyfc = dailyDataTH.getFyfc();
        long newBusinessCase = dailyDataTH.getNewBusinessCase();
        long manPower = dailyDataTH.getManpower();
        long recruitment = dailyDataTH.getRecruitment();
        Date dataTime = dailyDataTH.getDataTime();

        AgentSalesDataIdentity identity = new AgentSalesDataIdentity();
        identity.setOrganizationalUnit(organizationalUnit);
        identity.setAgentID(agentID);
        identity.setDataYearMonth(dataYearMonth);
        identity.setPerformanceType(performanceType);
        Optional<CISLAgentSalesData> agentSalesDataOptional = agentSalesDataRepository.findById(identity);
        CISLAgentSalesData agentSalesData = agentSalesDataOptional.orElse(new CISLAgentSalesData());

        agentSalesData.setIdentity(identity);
        agentSalesData.setAnp(anp);
        agentSalesData.setFyfc(fyfc);
        agentSalesData.setDataTime(dataTime);
        agentSalesData.setManpower(manPower);
        agentSalesData.setNewBusinessCase(newBusinessCase);
        agentSalesData.setRecruitment(recruitment);
        agentSalesData.setCreateBy("SD");
        agentSalesData.setUpdateBy("SD");

        agentSalesDataRepository.save(agentSalesData);

    }
}
