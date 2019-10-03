package com.allianz.sd.core.service;

import java.util.List;
import java.util.Map;

import com.allianz.sd.core.InstanceCode;
import com.allianz.sd.core.jpa.model.AgencyPlanDetail;
import com.allianz.sd.core.jpa.repository.AgencyPlanDetailRepository;
import com.allianz.sd.core.service.bean.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.jpa.model.SysYearData;
import com.allianz.sd.core.jpa.repository.AgencyPlanDetailSyncRepository;
import com.allianz.sd.core.jpa.repository.AgencyPlanPileRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AgencyPlanBottomUpService {

	private static final Logger logger = LoggerFactory.getLogger(AgencyPlanBottomUpService.class);

	@Autowired
	private AgencyPlanPileRepository agencyPlanPileRepository;

	@Autowired
	private AgencyPlanDetailSyncRepository agencyPlanDetailSyncRepository;

	@Autowired
	private AgencyPlanDetailRepository agencyPlanDetailRepository;

	@Autowired
	private SysDataService sysDataService;

	@Autowired
	private AgencyPlanSyncService agencyPlanSyncService;

	@Autowired
	private GoalService goalService;

	@Transactional
	public void bottomUp(AgentYearData agentYearData,Goal originGoalObj, Goal goalObj) {
		String organizationalUnit = agentYearData.getIdentity().getOrganizationalUnit();
		int dataYear = agentYearData.getIdentity().getDataYear();
		String agentID = agentYearData.getIdentity().getAgentID();

		//取得待審核清單
		Map<String,String> pendingApproveMap = goalService.getPendingApproveMap(dataYear);

		//從PileData抓有哪些人需要bottom-up
		List<AgencyPlanDetail> reCalcDetailList = agencyPlanDetailRepository.findBottomupAgencyPlanDetail(agentID,dataYear);

		agencyPlanSyncService.calcAgencyPlan(reCalcDetailList,dataYear,pendingApproveMap);

//		SysYearData sysYearData = sysDataService.getSysYearDataBySysCtrl(organizationalUnit,agentYearData.getAppSysCtrl(),dataYear);
//		int yearPlanStartMonth = sysDataService.getRemainingPlanCalcStartMonth(sysYearData);
//
//		long personalFYFCGaol = Long.parseLong(goalObj.getGoalValue(GoalSettingValue.PERSON_FYFC));
//		long personalAnpGoal = Long.parseLong(goalObj.getGoalValue(GoalSettingValue.PERSON_ANP));
//		long perCaseFYFC = Long.parseLong(goalObj.getGoalValue(GoalSettingValue.PER_CASE_FYFC));
//
//		long fyfcDifferenct = goalObj.getTotalFyfcPlan(yearPlanStartMonth) - originGoalObj.getTotalFyfcPlan(yearPlanStartMonth);
//		long anpDifferent = goalObj.getTotalAnpPlan(yearPlanStartMonth) - originGoalObj.getTotalAnpPlan(yearPlanStartMonth);
//
//		logger.debug("[bottomUp start] agent:"+agentID+",fyfcDifferenct:"+fyfcDifferenct+"anpDifferent:"+anpDifferent+",dataYear:"+dataYear);
//
//		//Get Detail Bottom-up List
//		List<String> planBottomDetailList = agencyPlanPileRepository.getLeader(organizationalUnit, dataYear, agentID);
//		String appUseMode = agentYearData.getAppUseMode();
//
//		if(planBottomDetailList.size() != 0) {
//
//			//update detail indirect plan (self & leader : plan, forecast, caseCount)
//			int updateFYFCPlan = agencyPlanDetailSyncRepository.updateForecastPlanByDisPlayBlock(
//					fyfcDifferenct, planBottomDetailList, GoalSettingValue.PERSON_FYFC.toAgencyValue(),
//					organizationalUnit, DirectUnit.INDIRECT.toString(),dataYear);
//			logger.debug("updateFYFCPlan = "+updateFYFCPlan);
//
//			int updateANPPlan = agencyPlanDetailSyncRepository.updateForecastPlanByDisPlayBlock(
//					anpDifferent, planBottomDetailList, GoalSettingValue.PERSON_ANP.toAgencyValue(),
//					organizationalUnit, DirectUnit.INDIRECT.toString(), dataYear);
//			logger.debug("updateANPPlan = "+updateANPPlan);
//
//			//update detail direct plan (self: plan, forecast, per-case, caseCount)
//			int updateFYFCPerCase = agencyPlanDetailSyncRepository.updateForecastAndPercase(
//					fyfcDifferenct, perCaseFYFC, agentID, GoalSettingValue.PERSON_FYFC.toAgencyValue(),
//					organizationalUnit,  DirectUnit.DIRECT.toString(),dataYear);
//			logger.debug("updateFYFCPerCase = "+updateFYFCPerCase);
//
//			//update detail direct Goal (self)
//			int updateFYFCGoalDirect= agencyPlanDetailSyncRepository.updateSelfGoal(
//					personalFYFCGaol, agentID, GoalSettingValue.PERSON_FYFC.toAgencyValue(),
//					organizationalUnit, dataYear);
//			logger.debug("updateFYFCGoalDirect = "+updateFYFCGoalDirect);
//
//			int updateANPGoalDirect = agencyPlanDetailSyncRepository.updateSelfGoal(
//					personalAnpGoal, agentID, GoalSettingValue.PERSON_ANP.toAgencyValue(),
//					organizationalUnit, dataYear);
//			logger.debug("updateANPGoalDirect = "+updateANPGoalDirect);
//
//
//			//leader update InDirect manpower & RECRUITMENT
//			if(!AppUseMode.AGENT.equalsIgnoreCase(appUseMode)) {
//				long teamFYFCGoal = Long.parseLong(goalObj.getGoalValue(GoalSettingValue.TEAM_FYFC));
//				long teamAnpGoal = Long.parseLong(goalObj.getGoalValue(GoalSettingValue.TEAM_ANP));
//
//				//update detail indirect goal
//				int updateFYFCGoalIn = agencyPlanDetailSyncRepository.updateInDirectGoal(
//						teamFYFCGoal, agentID, GoalSettingValue.PERSON_FYFC.toAgencyValue(),
//						organizationalUnit, dataYear);
//				logger.debug("updateFYFCGoalIn = "+updateFYFCGoalIn);
//
//				int updateANPGoalIn = agencyPlanDetailSyncRepository.updateInDirectGoal(
//						teamAnpGoal, agentID, GoalSettingValue.PERSON_ANP.toAgencyValue(),
//						organizationalUnit, dataYear);
//				logger.debug("updateANPGoalIn = "+updateANPGoalIn);
//
//
//				long originTeamManpower = Long.parseLong(originGoalObj.getGoalValue(GoalSettingValue.TEAM_MANPOWER));
//				long originTeamRecruitment = Long.parseLong(originGoalObj.getGoalValue(GoalSettingValue.TEAM_RECRUITMENT));
//				long teamManpower = Long.parseLong(goalObj.getGoalValue(GoalSettingValue.TEAM_MANPOWER));
//				long teamRecruitment = Long.parseLong(goalObj.getGoalValue(GoalSettingValue.TEAM_RECRUITMENT));
//				long manpowerDifference =  teamManpower - originTeamManpower;
//				long recruitmentDifference =  teamRecruitment - originTeamRecruitment;
//
//				// update detail manpower Recruitment (self)
//				int updateDetailManpowerAndRecruitment = agencyPlanDetailSyncRepository.updateManpowerAndRecruitment(
//						manpowerDifference, recruitmentDifference, agentID, organizationalUnit, dataYear);
//				logger.debug("updateDetailManpowerAndRecruitment = "+updateDetailManpowerAndRecruitment);
//
//			}
//		}

		logger.debug("[bottomUp end]");

	}

}
