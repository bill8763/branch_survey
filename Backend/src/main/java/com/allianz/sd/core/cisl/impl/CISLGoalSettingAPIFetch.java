package com.allianz.sd.core.cisl.impl;

import com.allianz.sd.core.cisl.bean.GoalSettingCISL;
import com.allianz.sd.core.cisl.bean.ProcessData;
import com.allianz.sd.core.jpa.model.AgentYearDataIdentity;
import com.allianz.sd.core.jpa.model.CISLGoalSetting;
import com.allianz.sd.core.jpa.repository.CISLGoalSettingRepository;
import com.allianz.sd.core.service.DateService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class CISLGoalSettingAPIFetch extends CISLSupportAgentAPIFetch {

    @Value("${CISL.API.GoalSettingURL}")
    private String url;

    @Autowired
    private DateService dateService;

    @Autowired
    private CISLGoalSettingRepository goalSettingRepository;

    @Override
    protected String getCISLAPIURL() {
        return url;
    }

    @Override
    protected String getUsageModel() {
        return "GoalSetting";
    }

    @Override
    public ProcessData parse2ProcessData(JSONObject content) throws Exception {

        GoalSettingCISL goalSettingCISL = new GoalSettingCISL();

        JSONObject organizationalUnitObject = content.getJSONObject("organizationalUnit");
        String organizationalUnit = organizationalUnitObject.getString("locationName");
        String agentID = content.getString("agentNumber");
        int dataYear = content.getInt("accountManagerCategory");

        //get _refs Object >> get salesgoal Object
        JSONObject salesGoalObject = content.getJSONObject("_refs").getJSONObject("salesgoal");

        //get salesgoal Object >> extEntity Object
        JSONObject salesExtEntityObject = salesGoalObject.getJSONObject("extEntity");
        String goalSettingFlag = salesExtEntityObject.getString("goalSettingType");

        //get validityPeriod Object
        JSONObject validityPeriodObject = salesGoalObject.getJSONObject("validityPeriod");
        String goalSettingStartDate = validityPeriodObject.getString("startDateTime");
        String goalSettingDeadLine = validityPeriodObject.getString("endDateTime");
        Date personalGoalApplicableYM = dateService.parseDateString2Date(salesExtEntityObject.getString("personalGoalApplicableDate"));
        Date teamGoalApplicableYM = dateService.parseDateString2Date(salesExtEntityObject.getString("teamGoalApplicableDate"));

        //format to yyyy-MM-dd'T'HH:mm:ss
        Date goalStartDate = dateService.getDateFormatCISL(goalSettingStartDate) ;
        Date goalDeadLine = dateService.getDateFormatCISL(goalSettingDeadLine) ;

        //get lastUpdateValidity Object
        JSONObject lastUpdateValidityObject = salesExtEntityObject.getJSONObject("lastUpdateValidityPeriod");
        Date dataTime = dateService.getDateFormatCISL(lastUpdateValidityObject.getString("startDateTime"));

        //set goalSettingCISL
        goalSettingCISL.setOrganizationalUnit(organizationalUnit);
        goalSettingCISL.setAgentID(agentID);
        goalSettingCISL.setDataYear(dataYear);
        goalSettingCISL.setGoalSettingFlag(goalSettingFlag);
        goalSettingCISL.setGoalSettingStartDate(goalStartDate);
        goalSettingCISL.setGoalSettingDeadLine(goalDeadLine);
        goalSettingCISL.setPersonalGoalApplicableYM(personalGoalApplicableYM);
        goalSettingCISL.setTeamGoalApplicableDate(teamGoalApplicableYM);
        goalSettingCISL.setDataTime(dataTime);

        return goalSettingCISL;
    }

    @Override
    public void saveToSND(ProcessData processData) throws Exception{

        GoalSettingCISL goalSettingCISLData = (GoalSettingCISL) processData;

        String organizationalUnit = goalSettingCISLData.getOrganizationalUnit();
        String agentID = goalSettingCISLData.getAgentID();
        int dataYear = goalSettingCISLData.getDataYear();
        String goalSettingFlag = goalSettingCISLData.getGoalSettingFlag();
        Date goalSettingStartDate = goalSettingCISLData.getGoalSettingStartDate();
        Date goalSettingDeadLine = goalSettingCISLData.getGoalSettingDeadLine();
        Date personalGoalApplicableYM = goalSettingCISLData.getPersonalGoalApplicableYM();
        Date teamGoalApplicableYM = goalSettingCISLData.getTeamGoalApplicableDate();
        Date dataTime = goalSettingCISLData.getDataTime();

        AgentYearDataIdentity identity = new AgentYearDataIdentity();
        identity.setAgentID(agentID);
        identity.setDataYear(dataYear);
        identity.setOrganizationalUnit(organizationalUnit);

        Optional<CISLGoalSetting> goalSettingOptional = goalSettingRepository.findById(identity);
        CISLGoalSetting goalSetting = goalSettingOptional.orElse(new CISLGoalSetting());

//        goalSetting.setGoalSettingDeadline(goalSettingDeadLine.after(goalSettingLastDate) ? goalSettingLastDate : goalSettingDeadLine);

        goalSetting.setIdentity(identity);
        goalSetting.setGoalSettingFlag(goalSettingFlag);
        goalSetting.setGoalSettingStartDate(goalSettingStartDate);
        goalSetting.setGoalSettingDeadline(goalSettingDeadLine);
        goalSetting.setPersonnelGoalApplicableYM(personalGoalApplicableYM);
        goalSetting.setTeamGoalApplicableYM(teamGoalApplicableYM);
        goalSetting.setDataTime(dataTime);
        goalSetting.setCreateBy("SNDBatchCISL");
        goalSetting.setUpdateBy("SNDBatchCISL");

        goalSettingRepository.save(goalSetting);

    }
}
