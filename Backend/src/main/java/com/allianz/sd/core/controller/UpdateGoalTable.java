package com.allianz.sd.core.controller;

import com.allianz.sd.core.jpa.model.*;
import com.allianz.sd.core.jpa.repository.*;
import com.allianz.sd.core.service.*;
import com.allianz.sd.core.service.bean.AppUseMode;
import com.allianz.sd.core.service.bean.GoalSettingStatus;
import com.allianz.sd.core.service.bean.TimeBase;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 6:39
 * To change this template use File | Settings | File Templates.
 */
@Component
public class UpdateGoalTable {

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
    private OrganizationService organizationService = null;

    @Autowired
    private DateService dateService;

    @Autowired
    private GoalSettingRepository goalSettingRepository;

    @Autowired
    private GoalSettingVersionRepository goalSettingVersionRepository;

    @Autowired
    private GoalSettingValueRepository goalSettingValueRepository;

    @Autowired
    private GoalSettingSplitRepository goalSettingSplitRepository;

    @Autowired
    private GoalSettingSplitValueRepository goalSettingSplitValueRepository;

    @Autowired
    private GoalExpectedSplitValueRepository goalExpectedSplitValueRepository;

    @Autowired
    private GoalExpectedVersionRepository goalExpectedVersionRepository;

    @Autowired
    private GoalExpectedValueRepository goalExpectedValueRepository;

    @Autowired
    private AgentDataService agentDataService;

    @Autowired
    private SysDataService sysDataService;

    @Autowired
    private GoalService goalService;

    @Autowired
    private NumberService numberService;


    public boolean update(File file) throws Exception{
        InputStream is = new FileInputStream(file);
        HSSFWorkbook wb = new HSSFWorkbook(is);

        goalSettingSplitValueRepository.deleteAll();
        goalSettingSplitRepository.deleteAll();
        goalSettingValueRepository.deleteAll();
        goalSettingVersionRepository.deleteAll();
        goalSettingRepository.deleteAll();
        goalExpectedSplitValueRepository.deleteAll();
        goalExpectedVersionRepository.deleteAll();
        goalExpectedValueRepository.deleteAll();


        HSSFSheet goalSheet = wb.getSheetAt(3);
        int goalRows = goalSheet.getPhysicalNumberOfRows();
        for(int i=1;i<goalRows;i++) {
            HSSFRow row = goalSheet.getRow(i);

            if(row.getCell(1) == null) continue;

            double DataYear = row.getCell(0).getNumericCellValue();
            String agentID = row.getCell(1).getStringCellValue();
            double GoalSettingFlag = row.getCell(2).getNumericCellValue();
            Date GoalSettingStartDate = row.getCell(3).getDateCellValue();
            Date GoalSettingDeadline = row.getCell(4).getDateCellValue();
            Date PersonnelGoalApplicableYM = row.getCell(5).getDateCellValue();
            Date TeamGoalApplicableYM = null;
            try{
                TeamGoalApplicableYM = row.getCell(6).getDateCellValue();
            }catch(Exception e) {}

            String FirstTime = row.getCell(7).getStringCellValue();
            String NextYear = row.getCell(8).getStringCellValue();
            double DefaultValue =  row.getCell(9).getNumericCellValue();
            double TEAM_MANPOWER = row.getCell(10).getNumericCellValue();
            double TEAM_RECRUITMENT = row.getCell(11).getNumericCellValue();
            double PROMOTION_PLAN = row.getCell(12).getNumericCellValue();
            String IsNeedAdjust = row.getCell(13).getStringCellValue();

            GoalSetting goalSetting = new GoalSetting();
            AgentYearDataIdentity agentYearDataIdentity = new AgentYearDataIdentity();
            agentYearDataIdentity.setOrganizationalUnit(organizationService.getOrganizationalUnit());
            agentYearDataIdentity.setDataYear((int)DataYear);
            agentYearDataIdentity.setAgentID(agentID);

            goalSetting.setIdentity(agentYearDataIdentity);
            goalSetting.setGoalSettingStartDate(GoalSettingStartDate);
            goalSetting.setGoalSettingDeadline(GoalSettingDeadline);
            goalSetting.setGoalSettingFlag(String.valueOf((int)GoalSettingFlag));
            goalSetting.setPersonnelGoalApplicableYM(PersonnelGoalApplicableYM);
            goalSetting.setTeamGoalApplicableYM(TeamGoalApplicableYM);
            goalSetting.setCreateBy(agentID);

            goalSettingRepository.save(goalSetting);

            initGoalValue(goalSetting,DefaultValue,FirstTime,TEAM_MANPOWER,TEAM_RECRUITMENT,PROMOTION_PLAN,IsNeedAdjust);

            //如果還有明年
            if("Y".equalsIgnoreCase(NextYear)) {

                Date date = dateService.getTodayDate();
                date.setMonth(0);

                GoalSetting nextYearGoal = new GoalSetting();
                AgentYearDataIdentity nextAgentYearDataIdentity = new AgentYearDataIdentity();
                nextAgentYearDataIdentity.setOrganizationalUnit(organizationService.getOrganizationalUnit());
                nextAgentYearDataIdentity.setDataYear((int)(DataYear + 1));
                nextAgentYearDataIdentity.setAgentID(agentID);

                nextYearGoal.setIdentity(nextAgentYearDataIdentity);
                nextYearGoal.setGoalSettingStartDate(GoalSettingStartDate);
                nextYearGoal.setGoalSettingDeadline(GoalSettingDeadline);
                nextYearGoal.setGoalSettingFlag(String.valueOf((int)GoalSettingFlag));
                nextYearGoal.setPersonnelGoalApplicableYM(date);
                nextYearGoal.setTeamGoalApplicableYM(TeamGoalApplicableYM == null ? null : date);
                nextYearGoal.setCreateBy(agentID);

                goalSettingRepository.save(nextYearGoal);

                initGoalValue(nextYearGoal,DefaultValue,FirstTime,TEAM_MANPOWER,TEAM_RECRUITMENT,PROMOTION_PLAN,"Y");
            }
        }

        return true;
    }

    public boolean initGoalValue(com.allianz.sd.core.jpa.model.GoalSetting goalSetting,double DefaultValue,String FirstTime,double TEAM_MANPOWER,double TEAM_RECRUITMENT,double PROMOTION_PLAN,String IsNeedAdjust) {
        boolean isSuccess = false;

        String agentID = goalSetting.getIdentity().getAgentID();
        int dataYear = goalSetting.getIdentity().getDataYear();
        String organizationalUnit = goalSetting.getIdentity().getOrganizationalUnit();

        //get AgentYearData
        AgentYearData agentYearData = agentDataService.getAgentYearData(agentID,dataYear);

        if(agentYearData != null) {

            //get SYS_DATa
            SysData sysData = sysDataService.getSysData(organizationalUnit,agentYearData.getAppSysCtrl());
            if(sysData != null) {

                int versionCount = "Y".equals(FirstTime) ? 1 : 2;

                for(int z=0;z<versionCount;z++) {

                    String isTopVersion = z == versionCount-1 ? "Y" : "N";

                    int goalVersion = goalService.getLastGoalVersion(agentID,organizationalUnit,dataYear);
                    goalVersion++;

                    //create new version
                    GoalSettingVersion goalSettingVersion = new GoalSettingVersion();
                    goalSettingVersion.setOrganizationalUnit(organizationalUnit);
                    goalSettingVersion.setAgentID(agentID);
                    goalSettingVersion.setDataYear(dataYear);
                    goalSettingVersion.setGoalVersion(goalVersion);
                    goalSettingVersion.setStatus(GoalSettingStatus.APPROVED.toString());
                    goalSettingVersion.setStatusDate(dateService.getTodayDate());
                    goalSettingVersion.setTopVersion(isTopVersion);
                    goalSettingVersion.setSigningSupervisor(agentYearData.getGoalSigningSupervisor());
                    goalSettingVersion.setActivityGoalBase("Month");
                    goalSettingVersion.setPersonnelGoalApplicableYM(goalSetting.getPersonnelGoalApplicableYM());
                    goalSettingVersion.setTeamGoalApplicableYM(goalSetting.getTeamGoalApplicableYM());
                    goalSettingVersion.setSubmitStatus("Y".equals(FirstTime) ? "" : "Y");
                    goalSettingVersion.setCreateBy(agentID);
                    goalSettingVersion.setUpdateBy(agentID);
                    goalSettingVersion.setDataTime(dateService.getTodayDate());

                    if("N".equalsIgnoreCase(IsNeedAdjust)) {
                        goalSettingVersion.setGoalSettingStartDate(goalSetting.getGoalSettingStartDate());
                    }

                    goalSettingVersionRepository.save(goalSettingVersion);

                    //get SEQ
                    int goalSettingSeq = goalSettingVersion.getGoalSettingSeq();

                    //create init value
                    List<String>mappingList = PERSONAL_VALUE_MAPPING_LIST;

                    if(!agentYearData.getAppUseMode().equalsIgnoreCase(AppUseMode.AGENT)) {
                        mappingList.addAll(TEAM_VALUE_MAPPING_LIST);
                    }

                    for(String mappingID : mappingList) {
                        GoalSettingValue goalSettingValue = new GoalSettingValue();

                        GoalSettingValueIdentity identity = new GoalSettingValueIdentity();
                        identity.setGoalSettingSeq(goalSettingSeq);
                        identity.setMappingID(mappingID);

                        goalSettingValue.setIdentity(identity);


                        if((int)DefaultValue == 0) {
                            goalSettingValue.setSetValue("");
                        }
                        else {
                            if("PERSON_FYFC".equalsIgnoreCase(mappingID)) {
                                goalSettingValue.setSetValue(String.valueOf(numberService.round(DefaultValue)));
                            }
                            else if("PERSON_ANP".equalsIgnoreCase(mappingID)) {
                                goalSettingValue.setSetValue(String.valueOf(numberService.round((DefaultValue * 1.2))));
                            }
                            else if("TEAM_FYFC".equalsIgnoreCase(mappingID)) {
                                goalSettingValue.setSetValue(String.valueOf(numberService.round(DefaultValue)));
                            }
                            else if("TEAM_ANP".equalsIgnoreCase(mappingID)) {
                                goalSettingValue.setSetValue(String.valueOf(numberService.round(DefaultValue * 1.2)));
                            }
                            else if("TEAM_MANPOWER".equalsIgnoreCase(mappingID)) {
                                goalSettingValue.setSetValue(String.valueOf(numberService.round(TEAM_MANPOWER)));
                            }
                            else if("TEAM_RECRUITMENT".equalsIgnoreCase(mappingID)) {
                                goalSettingValue.setSetValue(String.valueOf(numberService.round(TEAM_RECRUITMENT)));
                            }
                            else if("PER_CASE_FYFC".equalsIgnoreCase(mappingID)) {
                                goalSettingValue.setSetValue("5000");
                            }
                            else if("PER_CASE_ANP".equalsIgnoreCase(mappingID)) {
                                goalSettingValue.setSetValue("6000");
                            }
                            else if("ANNUAL_CONVENTION".equalsIgnoreCase(mappingID)) {
                                goalSettingValue.setSetValue("Allianz_Star_Club");
                            }
                            else if("MDRT".equalsIgnoreCase(mappingID)) {
                                goalSettingValue.setSetValue("MDRT");
                            }
                            else if("PROMOTION_PLAN".equalsIgnoreCase(mappingID)) {

                                String jobGrade = agentYearData.getJobGrade();

                                String promotionPlan = "";
                                if("SA1".equalsIgnoreCase(jobGrade) || "SA2".equalsIgnoreCase(jobGrade)
                                || "ESA".equalsIgnoreCase(jobGrade) || "CSM".equalsIgnoreCase(jobGrade)
                                || "CAM".equalsIgnoreCase(jobGrade) || "CSP".equalsIgnoreCase(jobGrade)) promotionPlan = "JobGrade_SP";
                                else if("SP".equalsIgnoreCase(jobGrade)) promotionPlan = "JobGrade_AM";
                                else if("AM".equalsIgnoreCase(jobGrade)) promotionPlan = "JobGrade_SM";

                                goalSettingValue.setSetValue(promotionPlan);
                            }
                            else {
                                goalSettingValue.setSetValue("0");
                            }
                        }


                        goalSettingValue.setCreateBy(agentID);
                        goalSettingValue.setUpdateBy(agentID);

                        goalSettingValueRepository.save(goalSettingValue);
                    }

                    //create new split for plan calc
                    int goalSplitVersion = goalService.getGoalSplitVersion(goalSettingVersion.getGoalSettingSeq());
                    goalSplitVersion++;

                    GoalSettingSplit goalSettingSplit = new GoalSettingSplit();

                    GoalSettingSplitIdentity goalSettingSplitIdentity = new GoalSettingSplitIdentity();
                    goalSettingSplitIdentity.setGoalSettingSeq(goalSettingSeq);
                    goalSettingSplitIdentity.setSplitVersion(goalSplitVersion);

                    goalSettingSplit.setIdentity(goalSettingSplitIdentity);
                    goalSettingSplit.setPerformanceSettlementMonth(sysData.getPerformanceSettlementMonth().getMonth());
                    goalSettingSplit.setTopVersion(isTopVersion);
                    goalSettingSplit.setVersionSettingDate(dateService.getTodayDate());
                    goalSettingSplit.setCreateBy(agentID);
                    goalSettingSplit.setUpdateBy(agentID);

                    goalSettingSplitRepository.save(goalSettingSplit);


                    //create new split for by month plan
                    int monthPlan = (int)(DefaultValue / 12);
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

                        saveANPPlan(goalSettingSplitValue,1.2);
                    }

                    isSuccess = true;
                }


            }


        }


        return isSuccess;
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
        anpSettingSplitValue.setSetValue(String.valueOf(numberService.calcMultipleAndRound(Integer.parseInt(fyfcPlan),fyfcConvertAnpRate)));

        goalSettingSplitValueRepository.save(anpSettingSplitValue);
    }
}
