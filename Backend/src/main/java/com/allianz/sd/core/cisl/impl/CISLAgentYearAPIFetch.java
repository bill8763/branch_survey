package com.allianz.sd.core.cisl.impl;

import com.allianz.sd.core.cisl.bean.AgentYearDataCISL;
import com.allianz.sd.core.cisl.bean.ProcessData;
import com.allianz.sd.core.jpa.model.AgentYearDataIdentity;
import com.allianz.sd.core.jpa.model.CISLAgentYearData;
import com.allianz.sd.core.jpa.repository.CISLAgentYearDataRepository;
import com.allianz.sd.core.service.DateService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class CISLAgentYearAPIFetch extends CISLSupportAgentAPIFetch {

    @Value("${CISL.API.AgentYearURL}")
    private String url;

    @Autowired
    private DateService dateService;

    @Autowired
    private CISLAgentYearDataRepository agentYearDataRepository;


    @Override
    protected String getCISLAPIURL() {
        return url;
    }

    @Override
    protected String getUsageModel() {
        return "AgentYear";
    }

    @Override
    public ProcessData parse2ProcessData(JSONObject content) throws Exception {
        AgentYearDataCISL agentYearDataCISL = new AgentYearDataCISL();

        JSONObject organizationalUnitObject = content.getJSONObject("organizationalUnit");
        String organizationalUnit = organizationalUnitObject.getString("locationName");
        String agentID = content.getString("agentNumber");
        int dataYear = content.getInt("accountManagerCategory");

        //get extEntityObject Object
        JSONObject extEntityObject = content.getJSONObject("extEntity");

        String appUseMode = extEntityObject.getString("approvalType");

        // get _refs Object >> get salesgoal Object >> get extEntity Object
        JSONObject salesgoalObject = content.getJSONObject("_refs").getJSONObject("salesgoal").getJSONObject("extEntity");

        String appSysCtrl = salesgoalObject.getString("systemControllerReference");
        String performanceTable = salesgoalObject.getString("performanceReference");
        String jobGrade = content.getString("distributionTitle");
        Date currentJobOBMonth = dateService.parseDateString2Date(extEntityObject.getString("currentJobOnBoardDate"));
        int currentJobSeniorityMonth = extEntityObject.getInt("currentJobNumberOfBusinessMonths");

        //get goalSigningSupervisor Array
        JSONArray goalSigningSupervisorArray = extEntityObject.getJSONArray("supervisorAgentTypes");
        String goalSigningSupervisor = String.valueOf(goalSigningSupervisorArray.get(0));

        //performancetType  >> Personal or Team
        String performanceType = extEntityObject.getString("performanceType");
        int initialPrecaseFyfc = extEntityObject.getInt("initialPerformanceValue");

        //get lastUpdateValidPeriod Object
        JSONObject lastUpdateValidityPeriodObject = salesgoalObject.getJSONObject("lastUpdateValidityPeriod");
        String dataTime = lastUpdateValidityPeriodObject.getString("startDateTime");

        //String  yyyy-MM-dd'T'HH:mm:ss Format to Date
        Date dataTimeDate = dateService.getDateFormatCISL(dataTime) ;

        agentYearDataCISL.setAgentID(agentID);
        agentYearDataCISL.setDataYear(dataYear);
        agentYearDataCISL.setOrganizationalUnit(organizationalUnit);
        agentYearDataCISL.setAppSysCtrl(appSysCtrl);
        agentYearDataCISL.setAppUseMode(appUseMode);
        agentYearDataCISL.setCurrentJobOBMonth(currentJobOBMonth);
        agentYearDataCISL.setCurrentJobSeniorityMonth(currentJobSeniorityMonth);
        agentYearDataCISL.setGoalSigningSupervisor(goalSigningSupervisor);
        agentYearDataCISL.setInitialPrecaseFyfc(initialPrecaseFyfc);
        agentYearDataCISL.setJobGrade(jobGrade);
        agentYearDataCISL.setPerformanceTable(performanceTable);
        agentYearDataCISL.setPerformanceType(performanceType);
        agentYearDataCISL.setDataTime(dataTimeDate);

        return agentYearDataCISL;
    }

    @Override
    public void saveToSND(ProcessData processData) throws Exception{

        AgentYearDataCISL agentYearDataCISL = (AgentYearDataCISL) processData;

        String organizationalUnit = agentYearDataCISL.getOrganizationalUnit();
        String agentID = agentYearDataCISL.getAgentID();
        int dataYear = agentYearDataCISL.getDataYear();
        String appUseMode = agentYearDataCISL.getAppUseMode();
        String appSysCtrl = agentYearDataCISL.getAppSysCtrl();
        String performanceTable = agentYearDataCISL.getPerformanceTable();
        String performanceType = agentYearDataCISL.getPerformanceType();
        String goalSigningSupervisor = agentYearDataCISL.getGoalSigningSupervisor();
        Date currentJobOBMonth = agentYearDataCISL.getCurrentJobOBMonth();
        Integer currentJobSeniorityMonth = agentYearDataCISL.getCurrentJobSeniorityMonth();
        String jobGrade = agentYearDataCISL.getJobGrade();
        Integer initialPrecaseFyfc = agentYearDataCISL.getInitialPrecaseFyfc();
        Date dataTimeDate = agentYearDataCISL.getDataTime();

        AgentYearDataIdentity identity = new AgentYearDataIdentity();
        identity.setOrganizationalUnit(organizationalUnit);
        identity.setAgentID(agentID);
        identity.setDataYear(dataYear);

        Optional<CISLAgentYearData> agnetYearDataOptional = agentYearDataRepository.findById(identity);
        CISLAgentYearData agentYear = agnetYearDataOptional.orElse(new CISLAgentYearData());

        //save or Update
        agentYear.setIdentity(identity);
        agentYear.setAppUseMode(appUseMode);
        agentYear.setAppSysCtrl(appSysCtrl);
        agentYear.setPerformanceTable(performanceTable);
        agentYear.setPersonnelPerformanceType(performanceType);
        agentYear.setTeamPerformanceType("T");
        agentYear.setCurrentJobOBMonth(currentJobOBMonth);
        agentYear.setCurrentJobSeniorityMonth(currentJobSeniorityMonth);
        agentYear.setGoalSigningSupervisor(goalSigningSupervisor);
        agentYear.setJobGrade(jobGrade);
        agentYear.setInitialPreCaseFyfc(initialPrecaseFyfc);
        agentYear.setDataTime(dataTimeDate);
        agentYear.setCreateBy("SNDBatchCISL");
        agentYear.setUpdateBy("SNDBatchCISL");

        agentYearDataRepository.save(agentYear);

    }
}
