package com.allianz.sd.core.cisl.impl;

import com.allianz.sd.core.cisl.bean.AgentDataCISL;
import com.allianz.sd.core.cisl.bean.ProcessData;
import com.allianz.sd.core.jpa.model.CISLAgentData;
import com.allianz.sd.core.jpa.repository.CISLAgentDataRepository;
import com.allianz.sd.core.service.DateService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CISLAgentDataAPIFetch extends CISLUsageModelAPIFetch {

    @Value("${CISL.API.AgentDataURL}")
    private String url;

    @Autowired
    private DateService dateService;

    @Autowired
    private CISLAgentDataRepository agentDataRepository;

    @Override
    protected String getCISLAPIURL() {
        return url;
    }

    @Override
    protected String getUsageModel() {
        return "AgentData";
    }

    @Override
    public JSONObject fetchByAgent(String agentID, int pageNo) throws Exception {
        throw new Exception("Not Support API by Agent Query");
    }

    @Override
    public ProcessData parse2ProcessData(JSONObject content) throws Exception {

        AgentDataCISL agentDataCISL = new AgentDataCISL();

        String agentID = content.getString("agentNumber");
        String agentName = content.getString("name");
        String gender = content.getString("accountManagerGenderIdentity");
        JSONObject organizationalUnitObject = content.getJSONObject("organizationalUnit");

        String organizationalUnit = organizationalUnitObject.getString("locationName");
        String officeName = organizationalUnitObject.getString("name");

        //get extEntity Object
        JSONObject extEntityObject = content.getJSONObject("extEntity");
        Date oBMonth = dateService.parseDateString2Date(extEntityObject.getString("onBoardDate"));

        String dataTime = content.getJSONObject("_refs").getJSONObject("salesgoal").getJSONObject("extEntity").getJSONObject("lastUpdateValidityPeriod").getString("startDateTime");

        //format yyyy-MM-dd'T'HH:mm:ss
        Date dataTimeDate = dateService.getDateFormatCISL(dataTime) ;

        agentDataCISL.setAgentID(agentID);
        agentDataCISL.setAgentName(agentName);
        agentDataCISL.setGender(gender);
        agentDataCISL.setOrganizationalUnit(organizationalUnit);
        agentDataCISL.setOfficeName(officeName);
        agentDataCISL.setOBMonth(oBMonth);
        agentDataCISL.setDataTime(dataTimeDate);

        return agentDataCISL;
    }

    @Override
    public void saveToSND(ProcessData processData) throws Exception{

        AgentDataCISL agentDataCISL = (AgentDataCISL) processData;

        String agentID = agentDataCISL.getAgentID();
        String agentName = agentDataCISL.getAgentName();
        String organizationalUnit = agentDataCISL.getOrganizationalUnit();
        String officeName = agentDataCISL.getOfficeName();
        String gender = agentDataCISL.getGender();
        Date oBMonth = agentDataCISL.getOBMonth();
        Date dataTimeDate = agentDataCISL.getDataTime();

        CISLAgentData agentDataJPA = agentDataRepository.findByAgentIDAndOrganizationalUnit(agentID, organizationalUnit);
        if(agentDataJPA == null) {
            agentDataJPA = new com.allianz.sd.core.jpa.model.CISLAgentData();
        }

        agentDataJPA.setAgentID(agentID);
        agentDataJPA.setOrganizationalUnit(organizationalUnit);
        agentDataJPA.setAgentName(agentName);
        agentDataJPA.setOfficeName(officeName);
        agentDataJPA.setGender(gender);
        agentDataJPA.setOBMonth(oBMonth);
        agentDataJPA.setDataTime(dataTimeDate);
        agentDataJPA.setCreateBy("SNDBatchCISL");
        agentDataJPA.setUpdateBy("SNDBatchCISL");

        agentDataRepository.save(agentDataJPA);

    }
}
