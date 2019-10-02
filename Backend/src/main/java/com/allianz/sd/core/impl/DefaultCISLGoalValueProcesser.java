package com.allianz.sd.core.impl;

import com.allianz.sd.core.cisl.CISLGoalValueProcesser;
import com.allianz.sd.core.jpa.model.*;
import com.allianz.sd.core.jpa.repository.GoalSettingSplitRepository;
import com.allianz.sd.core.jpa.repository.GoalSettingSplitValueRepository;
import com.allianz.sd.core.jpa.repository.GoalSettingValueRepository;
import com.allianz.sd.core.jpa.repository.GoalSettingVersionRepository;
import com.allianz.sd.core.service.*;
import com.allianz.sd.core.service.bean.AppUseMode;
import com.allianz.sd.core.service.bean.GoalSettingStatus;
import com.allianz.sd.core.service.bean.TimeBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class DefaultCISLGoalValueProcesser implements CISLGoalValueProcesser {

    private final static List<String> PERSONAL_VALUE_MAPPING_LIST = new ArrayList<String>();
    private final static List<String> TEAM_VALUE_MAPPING_LIST = new ArrayList<String>();
    static{
        PERSONAL_VALUE_MAPPING_LIST.add("PERSON_FYFC");
        PERSONAL_VALUE_MAPPING_LIST.add("PERSON_ANP");
        PERSONAL_VALUE_MAPPING_LIST.add("ANNUAL_CONVENTION");
        PERSONAL_VALUE_MAPPING_LIST.add("MDRT");
        PERSONAL_VALUE_MAPPING_LIST.add("PROMOTION_PLAN");
        PERSONAL_VALUE_MAPPING_LIST.add("PER_CASE_FYFC");
        PERSONAL_VALUE_MAPPING_LIST.add("PER_CASE_ANP");
        PERSONAL_VALUE_MAPPING_LIST.add("ACTIVITY_FIND");
        PERSONAL_VALUE_MAPPING_LIST.add("ACTIVITY_SCHEDULE");
        PERSONAL_VALUE_MAPPING_LIST.add("ACTIVITY_MEET");
        PERSONAL_VALUE_MAPPING_LIST.add("ACTIVITY_SUBMIT");
        PERSONAL_VALUE_MAPPING_LIST.add("ACTIVITY_INFORCE");

        TEAM_VALUE_MAPPING_LIST.add("TEAM_FYFC");
        TEAM_VALUE_MAPPING_LIST.add("TEAM_ANP");
        TEAM_VALUE_MAPPING_LIST.add("TEAM_MANPOWER");
        TEAM_VALUE_MAPPING_LIST.add("TEAM_RECRUITMENT");
    }

    @Autowired
    private DateService dateService;

    @Autowired
    private GoalSettingVersionRepository goalSettingVersionRepository;

    @Autowired
    private GoalService goalService;

    @Autowired
    private GoalSettingValueRepository goalSettingValueRepository;

    @Autowired
    private GoalSettingSplitRepository goalSettingSplitRepository;

    @Autowired
    private GoalSettingSplitValueRepository goalSettingSplitValueRepository;

    @Autowired
    private NumberService numberService;

    @Autowired
    private CISLAgentDataService agentDataService;

    @Autowired
    private SysDataService sysDataService;

    @Override
    @Transactional
    public void initGoalValue(CISLGoalSetting cislGoalSetting) throws Exception {
        setGoalValue(cislGoalSetting,1);
    }

    @Override
    @Transactional
    public void createNewGoalValue(CISLGoalSetting cislGoalSetting, GoalSettingVersion goalSettingVersion) throws Exception {
        setGoalValue(cislGoalSetting,goalSettingVersion.getGoalVersion()+1);
    }

    @Transactional
    protected void setGoalValue(CISLGoalSetting cislGoalSetting , int goalVersion) {
        String organizationUnit = cislGoalSetting.getIdentity().getOrganizationalUnit();
        String agentID = cislGoalSetting.getIdentity().getAgentID();
        int dataYear = cislGoalSetting.getIdentity().getDataYear();

        CISLAgentYearData agentYearData = agentDataService.getAgentYearData(agentID,dataYear,organizationUnit);
        SysYearData sysYearData = sysDataService.getSysYearData(organizationUnit,agentID,dataYear);

        //create new version
        GoalSettingVersion goalSettingVersion = new GoalSettingVersion();
        goalSettingVersion.setOrganizationalUnit(organizationUnit);
        goalSettingVersion.setAgentID(agentID);
        goalSettingVersion.setDataYear(dataYear);
        goalSettingVersion.setGoalVersion(goalVersion);
        goalSettingVersion.setStatus(GoalSettingStatus.APPROVED.toString());
        goalSettingVersion.setStatusDate(dateService.getTodayDate());
        goalSettingVersion.setTopVersion("Y");
        goalSettingVersion.setSigningSupervisor(agentYearData.getGoalSigningSupervisor());
        goalSettingVersion.setActivityGoalBase("Month");
        goalSettingVersion.setPersonnelGoalApplicableYM(cislGoalSetting.getPersonnelGoalApplicableYM());
        goalSettingVersion.setTeamGoalApplicableYM(cislGoalSetting.getTeamGoalApplicableYM());
        goalSettingVersion.setSubmitStatus("");
        goalSettingVersion.setCreateBy(agentID);
        goalSettingVersion.setUpdateBy(agentID);
        goalSettingVersion.setDataTime(dateService.getTodayDate());

        goalSettingVersionRepository.save(goalSettingVersion);

        //get SEQ
        int goalSettingSeq = goalSettingVersion.getGoalSettingSeq();

        //create init value
        List<String> mappingList = PERSONAL_VALUE_MAPPING_LIST;

        if(!agentYearData.getAppUseMode().equalsIgnoreCase(AppUseMode.AGENT)) {
            mappingList.addAll(TEAM_VALUE_MAPPING_LIST);
        }

        //Set GoalValue
        long defaultFYFC = 0;
        for(String mappingID : mappingList) {

            String goalValue = getGoalDefaultValue(agentYearData,mappingID);

            GoalSettingValueIdentity identity = new GoalSettingValueIdentity();
            identity.setGoalSettingSeq(goalSettingSeq);
            identity.setMappingID(mappingID);

            GoalSettingValue goalSettingValue = new GoalSettingValue();
            goalSettingValue.setIdentity(identity);
            goalSettingValue.setSetValue(goalValue);
            goalSettingValue.setCreateBy(agentID);
            goalSettingValue.setUpdateBy(agentID);

            if("PERSON_FYFC".equalsIgnoreCase(mappingID)) defaultFYFC = Long.parseLong(goalValue);

            goalSettingValueRepository.save(goalSettingValue);
        }

        //create new split for plan calc
        int goalSplitVersion = goalService.getGoalSplitVersion(goalSettingSeq) + 1;

        GoalSettingSplit goalSettingSplit = new GoalSettingSplit();

        GoalSettingSplitIdentity goalSettingSplitIdentity = new GoalSettingSplitIdentity();
        goalSettingSplitIdentity.setGoalSettingSeq(goalSettingSeq);
        goalSettingSplitIdentity.setSplitVersion(goalSplitVersion);

        goalSettingSplit.setIdentity(goalSettingSplitIdentity);
        goalSettingSplit.setPerformanceSettlementMonth(sysYearData.getSysData().getPerformanceSettlementMonth().getMonth());
        goalSettingSplit.setTopVersion("Y");
        goalSettingSplit.setVersionSettingDate(dateService.getTodayDate());
        goalSettingSplit.setCreateBy(agentID);
        goalSettingSplit.setUpdateBy(agentID);

        goalSettingSplitRepository.save(goalSettingSplit);


        //create new split for by month plan
        long monthPlan = (defaultFYFC / 12);
        for(int i=0;i<12;i++) {
            GoalSettingSplitValue goalSettingSplitValue = new GoalSettingSplitValue();

            GoalSettingSplitValueIdentity goalSettingSplitValueIdentity = new GoalSettingSplitValueIdentity();
            goalSettingSplitValueIdentity.setGoalSettingSeq(goalSettingSeq);
            goalSettingSplitValueIdentity.setMappingID("PERSON_FYFC");
            goalSettingSplitValueIdentity.setSplitVersion(goalSplitVersion);
            goalSettingSplitValueIdentity.setTimeBase(TimeBase.MONTH.toString());
            goalSettingSplitValueIdentity.setTimeBaseSeq(i+1);

            goalSettingSplitValue.setIdentity(goalSettingSplitValueIdentity);
            goalSettingSplitValue.setSetValue(String.valueOf(monthPlan));

            goalSettingSplitValueRepository.save(goalSettingSplitValue);

            saveANPPlan(goalSettingSplitValue,sysYearData.getFyfcCovertAnpRate());
        }
    }

    protected String getGoalDefaultValue(CISLAgentYearData agentYearData,String mappingID) {
        if ("PROMOTION_PLAN".equalsIgnoreCase(mappingID)) {
            String jobGrade = agentYearData.getJobGrade();
            if ("SA1".equalsIgnoreCase(jobGrade) || "SA2".equalsIgnoreCase(jobGrade)
                    || "ESA".equalsIgnoreCase(jobGrade) || "CSM".equalsIgnoreCase(jobGrade)
                    || "CAM".equalsIgnoreCase(jobGrade) || "CSP".equalsIgnoreCase(jobGrade))
                return "JobGrade_SP";
            else if ("SP".equalsIgnoreCase(jobGrade)) return "JobGrade_AM";
            else if ("AM".equalsIgnoreCase(jobGrade)) return "JobGrade_SM";
            return "";
        }
        else return "";
    }

    private void saveANPPlan(GoalSettingSplitValue goalSettingSplitValue,double fyfcConvertAnpRate) {
        GoalSettingSplitValue anpSettingSplitValue = new GoalSettingSplitValue();

        String fyfcPlan = goalSettingSplitValue.getSetValue();

        GoalSettingSplitValueIdentity anpSettingSplitValueIdentity = new GoalSettingSplitValueIdentity();
        anpSettingSplitValueIdentity.setGoalSettingSeq(goalSettingSplitValue.getIdentity().getGoalSettingSeq());
        anpSettingSplitValueIdentity.setMappingID(com.allianz.sd.core.service.bean.GoalSettingValue.PERSON_ANP.toString());
        anpSettingSplitValueIdentity.setSplitVersion(goalSettingSplitValue.getIdentity().getSplitVersion());
        anpSettingSplitValueIdentity.setTimeBase(goalSettingSplitValue.getIdentity().getTimeBase());
        anpSettingSplitValueIdentity.setTimeBaseSeq(goalSettingSplitValue.getIdentity().getTimeBaseSeq());

        anpSettingSplitValue.setIdentity(anpSettingSplitValueIdentity);
        anpSettingSplitValue.setSetValue(String.valueOf(numberService.calcMultipleAndRound(Long.parseLong(fyfcPlan),fyfcConvertAnpRate)));

        goalSettingSplitValueRepository.save(anpSettingSplitValue);
    }
}
