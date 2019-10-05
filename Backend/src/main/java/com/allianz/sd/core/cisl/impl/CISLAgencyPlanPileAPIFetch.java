package com.allianz.sd.core.cisl.impl;

import com.allianz.sd.core.cisl.bean.AgencyPlanPileDataCISL;
import com.allianz.sd.core.cisl.bean.ProcessData;
import com.allianz.sd.core.jpa.model.AgencyPlanPileIdentity;
import com.allianz.sd.core.jpa.model.CISLAgencyPlanPile;
import com.allianz.sd.core.jpa.repository.CISLAgencyPlanPileRepository;
import com.allianz.sd.core.service.CISLService;
import com.allianz.sd.core.service.DateService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Component
public class CISLAgencyPlanPileAPIFetch extends CISLSupportAgentAPIFetch {

    @Value("${CISL.API.AgencyPlanPileURL}")
    private String url;

    @Autowired
    private DateService dateService;

    @Autowired
    private CISLService cISLService;

    @Autowired
    private CISLAgencyPlanPileRepository agencyPlanPileRepository;


    @Override
    protected String getCISLAPIURL() {
        return url;
    }

    @Override
    protected String getUsageModel() {
        return "AgencyPlanPile";
    }

    @Override
    public ProcessData parse2ProcessData(JSONObject content) throws Exception {

        AgencyPlanPileDataCISL agencyPlanPileDataCISL = new AgencyPlanPileDataCISL();

        JSONObject organizationalUnitObject = content.getJSONObject("organizationalUnit");
        String organizationalUnit = organizationalUnitObject.getString("locationName");
        String agentID = content.getString("agentNumber");

        int dataYear = content.getInt("accountManagerCategory");

        String leaderAgentID = content.getString("agentNumberOfAdvisorOfAccountManager");

        //set Bean
        agencyPlanPileDataCISL.setOrganizationalUnit(organizationalUnit);
        agencyPlanPileDataCISL.setAgentID(agentID);
        agencyPlanPileDataCISL.setDataYear(dataYear);
        agencyPlanPileDataCISL.setLeaderAgentID(leaderAgentID);

        return agencyPlanPileDataCISL;
    }

    @Override
    public void saveToSND(ProcessData processData) throws Exception{

        AgencyPlanPileDataCISL agencyPlanPileDataCISL = (AgencyPlanPileDataCISL) processData;

        String organizationalUnit = agencyPlanPileDataCISL.getOrganizationalUnit();
        String agentID = agencyPlanPileDataCISL.getAgentID();
        String leaderAgentID = agencyPlanPileDataCISL.getLeaderAgentID();
        int dataYear = agencyPlanPileDataCISL.getDataYear();

        AgencyPlanPileIdentity identity = new AgencyPlanPileIdentity();
        identity.setAgentID(agentID);
        identity.setOrganizationalUnit(organizationalUnit);
        identity.setDataYear(dataYear);
        identity.setLeaderAgentID(leaderAgentID);

        Optional<CISLAgencyPlanPile> agencyPlanPileOptional = agencyPlanPileRepository.findById(identity);
        CISLAgencyPlanPile agencyPlanPile = agencyPlanPileOptional.orElse(new CISLAgencyPlanPile());

        agencyPlanPile.setIdentity(identity);
        agencyPlanPile.setCreateBy("SNDBatchCISL");

        agencyPlanPileRepository.save(agencyPlanPile);

    }
}
