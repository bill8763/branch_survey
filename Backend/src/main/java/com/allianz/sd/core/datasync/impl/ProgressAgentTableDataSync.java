package com.allianz.sd.core.datasync.impl;

import com.allianz.sd.core.InstanceCode;
import com.allianz.sd.core.api.model.*;
import com.allianz.sd.core.datasync.AgentTableDataSync;
import com.allianz.sd.core.datasync.PersonalProgressSyncListener;
import com.allianz.sd.core.datasync.TeamProgressDetailSyncListener;
import com.allianz.sd.core.datasync.TeamProgressMasterSyncListener;
import com.allianz.sd.core.exception.NotFoundAgentYearDataException;
import com.allianz.sd.core.exception.NotFoundSysYearDataException;
import com.allianz.sd.core.jpa.model.*;
import com.allianz.sd.core.jpa.model.PersonalProgress;
import com.allianz.sd.core.jpa.model.TeamProgress;
import com.allianz.sd.core.jpa.model.TeamProgressDetail;
import com.allianz.sd.core.jpa.repository.PersonalProgressRepository;
import com.allianz.sd.core.jpa.repository.TeamProgressDetailRepository;
import com.allianz.sd.core.jpa.repository.TeamProgressRepository;
import com.allianz.sd.core.service.*;
import com.allianz.sd.core.service.bean.AppUseMode;
import com.allianz.sd.core.service.bean.DirectUnit;
import com.allianz.sd.core.service.bean.PersonalActivity;
import com.allianz.sd.core.service.bean.TimeBase;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;

public class ProgressAgentTableDataSync implements AgentTableDataSync {

    private Logger logger = LoggerFactory.getLogger(ProgressAgentTableDataSync.class);

    @Autowired
    private DateService dateService;

    @Autowired
    private PersonalProgressRepository progressRepository;

    @Autowired
    private TeamProgressRepository teamProgressRepository;

    @Autowired
    private TeamProgressDetailRepository teamProgressDetailRepository;

    @Autowired
    private ProgressService progressService;

    @Autowired
    private AgentDataService agentDataService;

    @Autowired
    private SysDataService sysDataService;

    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private UtilsService utilsService;

    @Autowired
    private OrganizationService organizationService;

    @Override
    public void pullData(String agentID, List pullData) throws Exception{

        Date today = dateService.getTodayDate();
        String organizationalUnit = organizationService.getOrganizationalUnit();

        //查詢SysData 的資料
        Set<Integer> sysYearDataList = sysDataService.getAPPDisplayYears(today,organizationalUnit);
        if(sysYearDataList.size() != 0) {
            for(int dataYear :sysYearDataList ) {

                AgentYearData agentYearData = agentDataService.getAgentYearData(agentID,dataYear);

                //帶入Progress 撈資料  ，沒有該年份塞初值-1
                if(agentYearData != null) {

                    Date yesterday = dateService.addDate(today, java.util.Calendar.DATE, -1);
                    dateService.setYear(dataYear,yesterday);

                    //YesterDay Point
                    int yesterdayPoint = progressService.getActivityPointByDate(agentID, yesterday);

                    //progress model start
                    Progress progress = new Progress();
                    progress.setDataYear(new BigDecimal(dataYear));
                    progress.setYesterdayPoint(new BigDecimal(yesterdayPoint) );

                    //query personal progress by organizationUnit,agentID,year
                    com.allianz.sd.core.api.model.PersonalProgress personal = new com.allianz.sd.core.api.model.PersonalProgress();
                    List<PersonalProgress> personalProgresses = progressRepository.getPersonalProgressSync(agentID, organizationalUnit, dataYear);

                    for(PersonalProgress personalProgress: personalProgresses) {

                        ProgressIdentity identity = personalProgress.getIdentity();
                        TimeBase timeBase = TimeBase.valueOf(identity.getTimeBase().toUpperCase());
                        BigDecimal find = personalProgress.getFind();
                        BigDecimal schedule = personalProgress.getSchedule();
                        BigDecimal meet = personalProgress.getMeet();
                        BigDecimal submit = personalProgress.getSubmit();
                        BigDecimal inForce = personalProgress.getInforce();
                        PersonalProgressValue valuesItem = new PersonalProgressValue();

                        valuesItem.setDataType(PersonalProgressValue.DataTypeEnum.fromValue(identity.getDataType()));
                        valuesItem.setTimeBase(timeBase.toPersonalTimebase());
                        valuesItem.setFind(find);
                        valuesItem.setSchedule(schedule);
                        valuesItem.setMeet(meet);
                        valuesItem.setSubmit(submit);
                        valuesItem.setInforce(inForce);
                        valuesItem.setFYFC(personalProgress.getFyfc());

                        //for Extension
                        Object personalProgressExtension = beanFactory.getBean(InstanceCode.PersonalProgressExtension);
                        if(utilsService.isNotEmptyBean(personalProgressExtension)) {
                            List<Extension> extensionList = new ArrayList<>();
                            PersonalProgressSyncListener listener = (PersonalProgressSyncListener) personalProgressExtension;
                            listener.onPullData(agentYearData,personalProgress,extensionList);
                            valuesItem.setExtensions(extensionList);
                        }

                        personal.addValuesItem(valuesItem);
                    }

                    progress.setPersonal(personal);

                    //query team progress by organizationUnit,agentID,year
                    com.allianz.sd.core.api.model.TeamProgress team = new com.allianz.sd.core.api.model.TeamProgress();
                    List<com.allianz.sd.core.jpa.model.TeamProgress> teamProgresses= teamProgressRepository.getTeamProgress(agentID, organizationalUnit, dataYear);

                    for(com.allianz.sd.core.jpa.model.TeamProgress teamProgress: teamProgresses) {
                        TeamProgressValue value = new TeamProgressValue();
                        ProgressIdentity identity = teamProgress.getIdentity();
                        value.setDataType(TeamProgressValue.DataTypeEnum.fromValue(identity.getDataType()));
                        value.setTimeBase(TimeBase.valueOf(identity.getTimeBase().toUpperCase()).toTeamTimebase() );
                        value.setGoal(new BigDecimal(teamProgress.getGoal()));
                        value.setForecast(new BigDecimal(teamProgress.getForecast()));
                        value.setActual(new BigDecimal(teamProgress.getActual()));
                        value.setShortfall(new BigDecimal(teamProgress.getShortfall()));

                        //for Extension
                        Object teamProgressMasterExtension = beanFactory.getBean(InstanceCode.TeamProgressMasterExtension);
                        if(utilsService.isNotEmptyBean(teamProgressMasterExtension)) {
                            List<Extension> extensionList = new ArrayList<>();
                            TeamProgressMasterSyncListener listener = (TeamProgressMasterSyncListener) teamProgressMasterExtension;
                            listener.onPullData(agentYearData,teamProgress,extensionList);
                            value.setExtensions(extensionList);
                        }

                        team.addValuesItem(value);
                    }
//
                    //query team detail progress by organizationUnit,agentID,year
                    List<com.allianz.sd.core.jpa.model.TeamProgressDetail> teamProgressDetails = teamProgressDetailRepository.getTeamProgressDetail(agentID, organizationalUnit, dataYear);
                    for(com.allianz.sd.core.jpa.model.TeamProgressDetail detail: teamProgressDetails) {
                        TeamProgressDetailIdentity identity = detail.getIdentity();

                        String subAgentId = identity.getSubordinateAgentID();
                        String direct = identity.getDirectUnitType().toUpperCase();
                        DirectUnit directUnit = DirectUnit.valueOf(direct);

                        TimeBase timeBase = TimeBase.valueOf(identity.getTimeBase().toUpperCase());

                        String appUseMode = agentDataService.getAppUseMode(subAgentId, dataYear);

                        String activities = "";
                        if(directUnit == DirectUnit.DIRECT && (timeBase == TimeBase.MONTH || timeBase == TimeBase.QUARTER || timeBase == TimeBase.YEAR)) {

                            if(AppUseMode.AGENT.equalsIgnoreCase(appUseMode) || AppUseMode.LEADER.equalsIgnoreCase(appUseMode)) {
                                PersonalActivity personalActivity = progressService.getPersonalActivity(subAgentId,dataYear,organizationalUnit,timeBase);
                                activities = progressService.getTeamProgressDetailAcvitity(personalActivity);
                            }

                        }

                        com.allianz.sd.core.api.model.TeamProgressDetail teamProgressDetail = new com.allianz.sd.core.api.model.TeamProgressDetail();
                        teamProgressDetail.setAgentID(subAgentId);
                        teamProgressDetail.setAgentName(detail.getSubordinateAgentAgentName());
                        teamProgressDetail.setJobGrade(detail.getSubordinateAgentJobGrade());
                        teamProgressDetail.setDrilldown("Y".equalsIgnoreCase(detail.getIsDrilldown()) );
                        teamProgressDetail.setDataType(com.allianz.sd.core.api.model.TeamProgressDetail.DataTypeEnum.fromValue(identity.getDataType()));
                        teamProgressDetail.setTimeBase(timeBase.toTeamDetailTimebase());
                        teamProgressDetail.setGoal(new BigDecimal(detail.getGoal()));
                        teamProgressDetail.setForecast(new BigDecimal(detail.getForecast()));
                        teamProgressDetail.setActual(new BigDecimal(detail.getActual()));
                        teamProgressDetail.setShortfall(new BigDecimal(detail.getShortfall()));
                        teamProgressDetail.setActivities(activities);
                        teamProgressDetail.setAppUseMode(StringUtils.isNotEmpty(appUseMode) ? com.allianz.sd.core.api.model.TeamProgressDetail.AppUseModeEnum.fromValue(appUseMode) : null);

                        //for Extension
                        Object teamProgressDetailExtension = beanFactory.getBean(InstanceCode.TeamProgressDetailExtension);
                        if(utilsService.isNotEmptyBean(teamProgressDetailExtension)) {
                            List<Extension> extensionList = new ArrayList<>();
                            TeamProgressDetailSyncListener listener = (TeamProgressDetailSyncListener) teamProgressDetailExtension;
                            listener.onPullData(agentYearData,detail,extensionList);
                            teamProgressDetail.setExtensions(extensionList);
                        }

                        //add to direct or indirect array by directunit
                        directUnit.addProgressTeamUnit(team, teamProgressDetail);
                    }

                    progress.setTeam(team);

                    pullData.add(progress);

                }

            }
        }
    }
}
