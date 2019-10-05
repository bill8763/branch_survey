package com.allianz.sd.core.goalsetting.impl;

import com.allianz.sd.core.goalsetting.GoalStatusChangeHandler;
import com.allianz.sd.core.goalsetting.bean.GoalStatusSubject;
import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.jpa.model.SysYearData;
import com.allianz.sd.core.service.DateService;
import com.allianz.sd.core.service.ProgressService;
import com.allianz.sd.core.service.SysDataService;
import com.allianz.sd.core.service.bean.Goal;
import com.allianz.sd.core.service.bean.PerformanceTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Component
public class CalcProgressValue implements GoalStatusChangeHandler {

    private Logger logger = LoggerFactory.getLogger(CalcProgressValue.class);

    @Autowired
    private DateService dateService;

    @Autowired
    private ProgressService progressService;

    @Autowired
    private SysDataService sysDataService;

    @Override
    @Transactional
    public void onStatusChange(GoalStatusSubject goalStatusSubject)  throws Exception{

        AgentYearData agentYearData = goalStatusSubject.getAgentYearData();
        String appSysCtrl = agentYearData.getAppSysCtrl();
        int dataYear = agentYearData.getIdentity().getDataYear();

        Date todayDate = dateService.getTodayDate();
        String organizationalUnit = agentYearData.getIdentity().getOrganizationalUnit();

        Goal originGoalObj = goalStatusSubject.getApproveGoal();
        Goal goalObj = goalStatusSubject.getAfterGoal();

        SysYearData sysYearData = sysDataService.getSysYearDataBySysCtrl(organizationalUnit,appSysCtrl,dataYear);

        logger.debug("BottomUp Update PersonalProgress & TeamProgress");
        PerformanceTime performanceTime = progressService.getPerformanceTime(todayDate, organizationalUnit,agentYearData.getPerformanceTable());
        progressService.calcPersonalProgress(agentYearData, goalObj, performanceTime, sysYearData);
        progressService.bottomUpUpdateTeamProgress(agentYearData,originGoalObj,goalObj);
    }
}
