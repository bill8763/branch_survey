package com.allianz.sd.core.cisl.impl;

import com.allianz.sd.core.cisl.bean.AgencyPlanDataCISL;
import com.allianz.sd.core.cisl.bean.AgencyPlanDetailDataCISL;
import com.allianz.sd.core.cisl.bean.ProcessData;
import com.allianz.sd.core.jpa.model.AgencyPlanDetailIdentity;
import com.allianz.sd.core.jpa.model.AgencyPlanIdentity;
import com.allianz.sd.core.jpa.model.CISLAgencyPlan;
import com.allianz.sd.core.jpa.model.CISLAgencyPlanDetail;
import com.allianz.sd.core.jpa.repository.CISLAgencyPlanDetailRepository;
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
public class CISLAgencyPlanDetailAPIFetch extends CISLSupportAgentAPIFetch {

    @Value("${CISL.API.AgencyPlanDetailURL}")
    private String url;

    @Autowired
    private DateService dateService;

    @Autowired
    private CISLService cISLService;

    @Autowired
    private CISLAgencyPlanDetailRepository agencyPlanDetailRepository;

    @Override
    protected String getCISLAPIURL() {
        return url;
    }

    @Override
    protected String getUsageModel() {
        return "AgencyPlanDetail";
    }

    @Override
    public ProcessData parse2ProcessData(JSONObject content) throws Exception {

        AgencyPlanDetailDataCISL agencyPlanDetailCISL = new AgencyPlanDetailDataCISL();

        //get organizationalUnit Object
        JSONObject organizationalUnitObject = content.getJSONObject("organizationalUnit");
        String organizationalUnit = organizationalUnitObject.getString("locationName");

        String agentID = content.getString("agentNumberOfAdvisorOfAccountManager");
        String subAgentID = content.getString("agentNumber");
        String subAgentName = content.getString("name");
        String subAgentJobGrade = content.getString("distributionTitle");

        int dataYear = content.getInt("accountManagerCategory");

        //get extEntityObject
        JSONObject extEntityObject = content.getJSONObject("extEntity");
        String displayBlock = extEntityObject.getString("accountManagerSubcategory");
        int displayOrder = extEntityObject.getInt("displayOrder");
        boolean cumulativeProc = extEntityObject.getBoolean("cumulativeSalesGoals");

        // get _refs Object >> get salesgoal Object
        JSONObject salesgoalObject = content.getJSONObject("_refs").getJSONObject("salesgoal");

        // get extEntity Object
        JSONObject salesExtEntityObject = salesgoalObject.getJSONObject("extEntity");
        Date performanceCalcStartMonth = dateService.parseDateString2Date(salesExtEntityObject.getString("performanceStartMonth") + "-01");

        //get salesgoal Object >> goalDetailArray
        JSONArray goalDetailArray = salesgoalObject.getJSONArray("goalDetails");
        for(int j = 0 ; j < goalDetailArray.length() ; j++) {

            // get Object by index
            JSONObject goalDetailObject = goalDetailArray.getJSONObject(j);
            long anp = (long)Double.parseDouble(cISLService.filterSymbol(goalDetailObject.getString("annualPremium")));

            //get goalDetail >> extEntity Object
            JSONObject goalExtEntityObject = goalDetailObject.getJSONObject("extEntity");
            long fyfc = goalExtEntityObject.getInt("businessResult");

            agencyPlanDetailCISL.setAnp(anp);
            agencyPlanDetailCISL.setFyfc(fyfc);
        }

        long newBusinessCase = salesExtEntityObject.getLong("numberOfAppliedAccountManagers");
        long recruitMent = salesExtEntityObject.getLong("numberOfAccountManagers");
        long manPower = salesExtEntityObject.getLong("numberOfAccountManagers");

        //set Bean
        agencyPlanDetailCISL.setOrganizationalUnit(organizationalUnit);
        agencyPlanDetailCISL.setAgentID(agentID);
        agencyPlanDetailCISL.setDataYear(dataYear);
        agencyPlanDetailCISL.setDisplayBlock(displayBlock);
        agencyPlanDetailCISL.setDisplayOrder(displayOrder);
        agencyPlanDetailCISL.setSubAgentID(subAgentID);
        agencyPlanDetailCISL.setSubAgentName(subAgentName);
        agencyPlanDetailCISL.setSubAgentJobGrade(subAgentJobGrade);
        agencyPlanDetailCISL.setPerformanceCalcStartMonth(performanceCalcStartMonth);
        agencyPlanDetailCISL.setCumulativeProc(cumulativeProc ? "Y" : "N" );
        agencyPlanDetailCISL.setNewBusinessCase(newBusinessCase);
        agencyPlanDetailCISL.setRecruitMent(recruitMent);
        agencyPlanDetailCISL.setManPower(manPower);

        return agencyPlanDetailCISL;
    }

    @Override
    public void saveToSND(ProcessData processData) throws Exception{

        AgencyPlanDetailDataCISL agencyPlanDetailCISL = (AgencyPlanDetailDataCISL) processData;

        String organizationalUnit = agencyPlanDetailCISL.getOrganizationalUnit();
        String agentID = agencyPlanDetailCISL.getAgentID();
        int dataYear = agencyPlanDetailCISL.getDataYear();
        String displayBlock = agencyPlanDetailCISL.getDisplayBlock();
        int displayOrder = agencyPlanDetailCISL.getDisplayOrder();
        String subAgentID = agencyPlanDetailCISL.getSubAgentID();
        String subAgentName = agencyPlanDetailCISL.getSubAgentName();
        String subAgentJobGrade = agencyPlanDetailCISL.getSubAgentJobGrade();
        Date performanceCalcStartMonth = agencyPlanDetailCISL.getPerformanceCalcStartMonth();
        String cumulativeProc = agencyPlanDetailCISL.getCumulativeProc();
        long anp = agencyPlanDetailCISL.getAnp();
        long fyfc = agencyPlanDetailCISL.getFyfc();
        long newBusinessCase = agencyPlanDetailCISL.getNewBusinessCase();
        long recruitMent = agencyPlanDetailCISL.getRecruitMent();
        long manPower = agencyPlanDetailCISL.getManPower();

        AgencyPlanDetailIdentity identity = new AgencyPlanDetailIdentity();
        identity.setOrganizationalUnit(organizationalUnit);
        identity.setAgentID(agentID);
        identity.setDataYear(dataYear);
        identity.setDisplayBlock(displayBlock);
        identity.setDisplayOrder(displayOrder);

        Optional<CISLAgencyPlanDetail> agencyPlanDetailOptional = agencyPlanDetailRepository.findById(identity);
        CISLAgencyPlanDetail agencyPlanDetail = agencyPlanDetailOptional.orElse(new CISLAgencyPlanDetail());

        agencyPlanDetail.setIdentity(identity);
        agencyPlanDetail.setSubAgentID(subAgentID);
        agencyPlanDetail.setSubAgentName(subAgentName);
        agencyPlanDetail.setSubAgentJobGrade(subAgentJobGrade);
        agencyPlanDetail.setPerformanceCalcStartMonth(performanceCalcStartMonth);
        agencyPlanDetail.setCumulativeProc(cumulativeProc);
        agencyPlanDetail.setAnp(anp);
        agencyPlanDetail.setFyfc(fyfc);
        agencyPlanDetail.setNewBusinessCase(newBusinessCase);
        agencyPlanDetail.setRecruitment(recruitMent);
        agencyPlanDetail.setManpower(manPower);
        agencyPlanDetail.setCreateBy("SNDBatchCISL");

        agencyPlanDetailRepository.save(agencyPlanDetail);

    }
}
