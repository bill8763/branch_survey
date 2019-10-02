package com.allianz.sd.core.cisl.impl;

import com.allianz.sd.core.cisl.bean.AgencyPlanDataCISL;
import com.allianz.sd.core.cisl.bean.ProcessData;
import com.allianz.sd.core.jpa.model.AgencyPlanIdentity;
import com.allianz.sd.core.jpa.model.CISLAgencyPlan;
import com.allianz.sd.core.jpa.repository.CISLAgencyPlanRepository;
import com.allianz.sd.core.service.CISLService;
import com.allianz.sd.core.service.DateService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Component
public class CISLAgencyPlanAPIFetch extends CISLSupportAgentAPIFetch {

    @Value("${CISL.API.AgencyPlanURL}")
    private String url;

    @Autowired
    private DateService dateService;

    @Autowired
    private CISLService cISLService;

    @Autowired
    private CISLAgencyPlanRepository agencyPlanRepository;

    @Override
    protected String getCISLAPIURL() {
        return url;
    }

    @Override
    protected String getUsageModel() {
        return "AgencyPlan";
    }

    @Override
    public ProcessData parse2ProcessData(JSONObject content) throws Exception {

        AgencyPlanDataCISL agencyPlanDataCISL = new AgencyPlanDataCISL();

        JSONObject organizationalUnitObject = content.getJSONObject("organizationalUnit");
        String organizationalUnit = organizationalUnitObject.getString("locationName");
        String agentID = content.getString("agentNumber");

        int dataYear = content.getInt("accountManagerCategory");

        String agentName = content.getString("name");
        String jobGrade = content.getString("distributionTitle");

        // get _refs Object >> get salesgoal Object
        JSONObject salesgoalObject = content.getJSONObject("_refs").getJSONObject("salesgoal");

        //get goalDetails Object
        JSONArray goalDetailArray = salesgoalObject.getJSONArray("goalDetails");
        for(int j = 0 ; j< goalDetailArray.length() ; j++) {

            JSONObject goalDetailObject = goalDetailArray.getJSONObject(j);
            long anp = (long)Double.parseDouble(cISLService.filterSymbol(goalDetailObject.getString("annualPremium")));

            // get goalDetails >> extEntity Object
            JSONObject goalExtEntityObject = goalDetailObject.getJSONObject("extEntity");
            long fyfc = goalExtEntityObject.getInt("businessResult");

            agencyPlanDataCISL.setAnp(anp);
            agencyPlanDataCISL.setFyfc(fyfc);
        }

        //set Bean
        agencyPlanDataCISL.setOrganizationalUnit(organizationalUnit);
        agencyPlanDataCISL.setAgentID(agentID);
        agencyPlanDataCISL.setDataYear(dataYear);
        agencyPlanDataCISL.setAgentName(agentName);
        agencyPlanDataCISL.setJobGrade(jobGrade);

        return agencyPlanDataCISL;
    }

    @Override
    public void saveToSND(ProcessData processData) throws Exception{

        AgencyPlanDataCISL agencyPlanDataCISL = (AgencyPlanDataCISL) processData;

        String organizationalUnit = agencyPlanDataCISL.getOrganizationalUnit();
        String agentID = agencyPlanDataCISL.getAgentID();
        int dataYear = agencyPlanDataCISL.getDataYear();
        String agentName = agencyPlanDataCISL.getAgentName();
        String jobGrade = agencyPlanDataCISL.getJobGrade();
        long anp = agencyPlanDataCISL.getAnp();
        long fyfc = agencyPlanDataCISL.getFyfc();

        AgencyPlanIdentity identity = new AgencyPlanIdentity();
        identity.setAgentID(agentID);
        identity.setDataYear(dataYear);
        identity.setOrganizationalUnit(organizationalUnit);
        Optional<CISLAgencyPlan> agencyPalnOptional = agencyPlanRepository.findById(identity);

        CISLAgencyPlan agencyPlan = agencyPalnOptional.orElse(new CISLAgencyPlan());

        //set agencyPlanData
        agencyPlan.setIdentity(identity);
        agencyPlan.setAgentName(agentName);
        agencyPlan.setJobGrade(jobGrade);
        agencyPlan.setAnp(anp);
        agencyPlan.setFyfc(fyfc);
        agencyPlan.setCreateBy("SNDBatchCISL");

        agencyPlanRepository.save(agencyPlan);

    }
}
