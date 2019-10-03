package com.allianz.sd.core.service;

import java.math.BigDecimal;
import java.util.*;

import com.allianz.sd.core.InstanceCode;
import com.allianz.sd.core.agencyplan.ApproveRule;
import com.allianz.sd.core.agencyplan.CalcAgencyPlanRule;
import com.allianz.sd.core.agencyplan.DrilldownRule;
import com.allianz.sd.core.api.model.*;
import com.allianz.sd.core.api.model.AgencyPlan;
import com.allianz.sd.core.api.model.AgencyPlanMaster;
import com.allianz.sd.core.datasync.AgencyPlanDetailSyncListener;
import com.allianz.sd.core.datasync.AgencyPlanMasterSyncListener;
import com.allianz.sd.core.jpa.model.*;
import com.allianz.sd.core.service.bean.*;
import com.allianz.sd.core.service.bean.Goal;
import com.allianz.sd.core.service.bean.GoalSettingValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.allianz.sd.core.exception.NotFoundAgencyPlanSyncException;
import com.allianz.sd.core.jpa.repository.AgencyPlanDetailSyncRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AgencyPlanSyncService {

	private Logger logger = LoggerFactory.getLogger(AgencyPlanSyncService.class);
	private final DateService dateService;
	private final AgencyPlanService agencyPlanService;
	private final AgencyPlanDetailSyncRepository agencyPlanDetailSyncRepository;
	private final AgentDataService agentDataService;
	private final BeanFactory beanFactory;
	private final SysDataService sysDataService;
	private final GoalService goalService;
	private final PlanService planService;
	private final NumberService numberService;
	private final UtilsService utilsService;
	private final OrganizationService organizationService;


	@Autowired
	public AgencyPlanSyncService(
			AgencyPlanService agencyPlanService,
			DateService dateService,
			AgencyPlanDetailSyncRepository agencyPlanDetailSyncRepository,
			AgentDataService agentDataService,
			BeanFactory beanFactory,
			SysDataService sysDataService,
			GoalService goalService,
			PlanService planService,
			NumberService numberService,
			UtilsService utilsService,
			OrganizationService organizationService) {
		this.agencyPlanDetailSyncRepository = agencyPlanDetailSyncRepository;
		this.dateService = dateService;
		this.agencyPlanService = agencyPlanService;
		this.agentDataService = agentDataService;
		this.beanFactory = beanFactory;
		this.sysDataService = sysDataService;
		this.goalService = goalService;
		this.planService = planService;
		this.numberService = numberService;
		this.utilsService = utilsService;
		this.organizationService = organizationService;
	}

	public AgencyPlanGetResponse getAgencyPlanSyncResponse(String agentID , String organizationalUnit) throws Exception {
		return getAgencyPlanSyncResponse(agentID,organizationalUnit,true,false);
	}


	public AgencyPlanGetResponse getAgencyPlanSyncResponse(String agentID, String organizationalUnit,boolean getDetail,boolean isDrilldown) throws Exception{

		logger.debug(">>>>>>>>>> getAgencyPlanSyncResponse START <<<<<<<<<<");
		logger.debug("DataSync agentID = " + agentID + ",organizationalUnit = " + organizationalUnit+", getDetail = "+getDetail + ",isDrilldown = " + isDrilldown);

		//reponse model
		AgencyPlanGetResponse agencyPlanResp = new AgencyPlanGetResponse();

		Date today = dateService.getTodayDate();

		CalcAgencyPlanRule calcAgencyPlanRule = (CalcAgencyPlanRule) beanFactory.getBean(InstanceCode.CalcAgencyPlanDetailPlan);
		Set<Integer> years = sysDataService.getAPPDisplayYears(today, organizationalUnit);

		if(years.size()>0) {

			for(Integer year : years) {

				AgencyPlan agencyPlans = new AgencyPlan();
				agencyPlans.setDataYear(new BigDecimal(year));

				//TODO
				agencyPlans.setCompletionRate(new BigDecimal(30));

				List<AgencyPlanMaster> masterList = new ArrayList<AgencyPlanMaster>();
				agencyPlans.setValues(masterList);

				//realtime calc AgencyPlanMaster
				SysYearData sysYearData = sysDataService.getSysYearData(organizationalUnit,agentID,year);
				if(sysYearData != null) {

					int yearPlanStartMonth = sysDataService.getRemainingPlanCalcStartMonth(sysYearData);

					//get AgencyPlan from DB
					com.allianz.sd.core.jpa.model.AgencyPlan agencyPlan = agencyPlanService.findMaster(agentID,year,organizationalUnit);

					if(agencyPlan != null) {

						//calc AgencyPlanMaster Rule
						com.allianz.sd.core.service.bean.AgencyPlanMaster calcAgencyPlanMaster = calcAgencyPlanRule.calcAgencyPlanMaster(agencyPlan,yearPlanStartMonth);

						//如果是別人看這個主管的AgencyPlan，要判斷這個主管最新的GoalVersion是否在PENDING_APPVOVE中，如果是要把差額補在FYFCForecast
						if(isDrilldown) {
							GoalSettingVersion lastVersion = goalService.getLastGoalVersionModel(agentID, organizationalUnit, year);

							if(lastVersion != null && GoalSettingStatus.valueOf(lastVersion.getStatus()) == GoalSettingStatus.PENDING_APPROVAL) {

								Goal goal = goalService.getYearGoal(agentID,year);

								//取出approve的plan加總
								long oriTotalPlan = planService.getYearPersonalTotalPlan(goal,yearPlanStartMonth, AgencyPlanDataType.FYFC);
								long oriTotalAnpPlan = planService.getYearPersonalTotalPlan(goal,yearPlanStartMonth, AgencyPlanDataType.ANP);

								//再取出最新版本的plan加總
								long newTotalPlan = planService.getYearPersonalTotalPlan(lastVersion.getGoalSettingSeq(),yearPlanStartMonth, AgencyPlanDataType.FYFC);
								long newTotalAnpPlan = planService.getYearPersonalTotalPlan(lastVersion.getGoalSettingSeq(),yearPlanStartMonth, AgencyPlanDataType.ANP);

								//取差額補到FYFCForecast
								long fyfcPlan = calcAgencyPlanMaster.getFyfcPlan();
								long anpPlan = calcAgencyPlanMaster.getAnpPlan();

								logger.trace("Drilldown agencyplan , oriTotalPlan=["+oriTotalPlan+"],newVersionTotalPlan=["+newTotalPlan+"],fyfcPlan=["+fyfcPlan+"]");
								logger.trace("Drilldown agencyplan , oriTotalAnpPlan=["+oriTotalAnpPlan+"],newTotalAnpPlan=["+newTotalAnpPlan+"],anpPlan=["+anpPlan+"]");

								calcAgencyPlanMaster.setFyfcPlan(fyfcPlan - oriTotalPlan + newTotalPlan);
								calcAgencyPlanMaster.setAnpPlan(anpPlan - oriTotalAnpPlan + newTotalAnpPlan);
							}
						}

						//FYFC & ANP & Manpower & Recruitment
						AgencyPlanMaster fyfcMaster = getAgencyPlanMaster(agencyPlan.getFyfc(),calcAgencyPlanMaster.getFyfcPlan(), AgencyPlanDataType.FYFC);
						AgencyPlanMaster anpMaster = getAgencyPlanMaster(agencyPlan.getAnp(),calcAgencyPlanMaster.getAnpPlan(), AgencyPlanDataType.ANP);
						AgencyPlanMaster manpowerMaster = getAgencyPlanMaster(0,calcAgencyPlanMaster.getManPowerPlan(), AgencyPlanDataType.Manpower);
						AgencyPlanMaster recruitmentMaster = getAgencyPlanMaster(0,calcAgencyPlanMaster.getRecruitmentPlan(), AgencyPlanDataType.Recruitment);

						masterList.add(fyfcMaster);
						masterList.add(anpMaster);
						masterList.add(manpowerMaster);
						masterList.add(recruitmentMaster);

						//query direct and indirect unit
						//skip  if query mode is summary
						if(getDetail) {
							List<AgencyPlanDetailSync> agencyPlanDetailSyncs = agencyPlanDetailSyncRepository.getAgencyPlanDetailSync(agentID, organizationalUnit, year);
							for(AgencyPlanDetailSync agencyPlanDetailSync : agencyPlanDetailSyncs) {

								AgencyPlanDetailSyncIdentity agencyPlanDetailSyncIdentity = agencyPlanDetailSync.getIdentity();

								String directAgentID = agencyPlanDetailSync.getSubordinateAgentID();
								String displayBlock = agencyPlanDetailSyncIdentity.getDisplayBlock().toUpperCase();
								AgentYearData agentYearData = agentDataService.getAgentYearData(directAgentID, year);
								if(agentYearData != null) {
									String appUseMode = agentYearData.getAppUseMode();

									AgencyPlanDetailInfo agencyPlanDetailInfo = new AgencyPlanDetailInfo();
									agencyPlanDetailInfo.setDataType(AgencyPlanDetailInfo.DataTypeEnum.valueOf(agencyPlanDetailSyncIdentity.getDataType()));
									agencyPlanDetailInfo.setAgentID(directAgentID);
									agencyPlanDetailInfo.setAgentName(agencyPlanDetailSync.getSubordinateAgentAgentName());
									agencyPlanDetailInfo.setJobGrade(agencyPlanDetailSync.getSubordinateAgentJobGrade());
									agencyPlanDetailInfo.setIsApprove("Y".equalsIgnoreCase(agencyPlanDetailSync.getIsApprove()));
									agencyPlanDetailInfo.setDrilldown("Y".equalsIgnoreCase(agencyPlanDetailSync.getIsDrilldown()) );
									agencyPlanDetailInfo.setGoal(new BigDecimal(agencyPlanDetailSync.getGoal()));
									agencyPlanDetailInfo.setForecast(new BigDecimal(agencyPlanDetailSync.getForecast()));
									agencyPlanDetailInfo.setActual(new BigDecimal(agencyPlanDetailSync.getActual()));
									agencyPlanDetailInfo.setPlan(new BigDecimal(agencyPlanDetailSync.getPlan()));
									agencyPlanDetailInfo.setManpower(new BigDecimal(agencyPlanDetailSync.getManpower()));
									agencyPlanDetailInfo.setRecruitment(new BigDecimal(agencyPlanDetailSync.getRecruitment()));
									agencyPlanDetailInfo.setCaseCount(new BigDecimal(agencyPlanDetailSync.getCaseCount()));
									agencyPlanDetailInfo.setPerCase(new BigDecimal(agencyPlanDetailSync.getPerCase()));
									agencyPlanDetailInfo.setAppUseMode(AgencyPlanDetailInfo.AppUseModeEnum.fromValue(appUseMode));

									//for Extension
									Object agencyPlanDetailExtension = beanFactory.getBean(InstanceCode.AgencyPlanDetailExtension);
									if(utilsService.isNotEmptyBean(agencyPlanDetailExtension)) {
										List<Extension> extensionList = new ArrayList<>();
										AgencyPlanDetailSyncListener listener = (AgencyPlanDetailSyncListener) agencyPlanDetailExtension;
										listener.onPullData(sysYearData,agencyPlanDetailSync,extensionList);
										agencyPlanDetailInfo.setExtensions(extensionList);
									}

									// add direct unit by deisplay block
									DirectUnit.valueOf(displayBlock).addAgencyPlanDetailUnit(agencyPlans, agencyPlanDetailInfo);
								}

							}
						}

						//for Extension
						Object agencyPlanMasterExtension = beanFactory.getBean(InstanceCode.AgencyPlanMasterExtension);
						if(utilsService.isNotEmptyBean(agencyPlanMasterExtension)) {
							List<Extension> extensionList = new ArrayList<>();
							AgencyPlanMasterSyncListener listener = (AgencyPlanMasterSyncListener) agencyPlanMasterExtension;
							listener.onPullData(sysYearData,agencyPlan,extensionList);
							agencyPlans.setExtensions(extensionList);
						}
					}


				}

				agencyPlanResp.addAgencyPlansItem(agencyPlans);
			}
		}else {
			throw new NotFoundAgencyPlanSyncException();
		}

		agencyPlanResp.setLastUpdateTime( dateService.toDateTimeFormatString(new Date()));

		return agencyPlanResp;
	}

	private AgencyPlanMaster getAgencyPlanMaster(long actual , long plan , AgencyPlanDataType agencyPlanDataType) {
		long forecast=actual+plan;

		AgencyPlanMaster agencyPlanMaster = new AgencyPlanMaster();
		agencyPlanMaster.setPlan(new BigDecimal(plan));
		agencyPlanMaster.setForecast(new BigDecimal(forecast));
		agencyPlanMaster.setActual(new BigDecimal(actual));
		agencyPlanMaster.setGoal(new BigDecimal(0));
		agencyPlanMaster.setDataType(AgencyPlanMaster.DataTypeEnum.fromValue(agencyPlanDataType.toString()));

		return agencyPlanMaster;

	}

	public void clearSyncTable(String organizationalUnit,int dataYear) {
		agencyPlanDetailSyncRepository.deleteAgencyPlanDetailSync(dataYear,organizationalUnit);
	}

	public void calcAgencyPlan(List<AgencyPlanDetail> agencyPlanDetails , int dataYear , Map<String,String> pendingApproveMap) {

		String organizationalUnit = organizationService.getOrganizationalUnit();

		for(AgencyPlanDetail agencyPlanDetail : agencyPlanDetails) {

			String agentID = agencyPlanDetail.getIdentity().getAgentID();
			String subAgentID = agencyPlanDetail.getSubAgentID();
			String displayBlock = agencyPlanDetail.getIdentity().getDisplayBlock();

			try{
				//get Goal & Goal Value
				Goal approveGoal = goalService.getYearGoal(subAgentID,dataYear);

				//Get AgentYearData
				AgentYearData agentYearData = agentDataService.getAgentYearData(subAgentID,dataYear);

				//Get SysYearData
				SysYearData sysYearData = sysDataService.getSysYearData(organizationalUnit,subAgentID,dataYear);

				//如果連業務員年度或系統年度都抓不到就帶- -
				if(agentYearData == null) {
					logger.trace("Can't find AgentYearData ["+subAgentID+"]");

					AgentData agentData = agentDataService.getAgentData(agentID);
					saveEmptyAgencyPlanDetail(agentData,agencyPlanDetail);

					continue;
				}

				processDetailData(agencyPlanDetail,agentYearData,sysYearData,pendingApproveMap,approveGoal);

			}catch(Exception e) {
				logger.error("Calc AgencyPlan Exception!! ["+agentID+"] , ["+subAgentID+"] , ["+displayBlock+"]",e);
			}

		}
	}

	public void processDetailData(AgencyPlanDetail agencyPlanDetail,AgentYearData agentYearData,
								  SysYearData sysYearData,Map<String,String> pendingApproveMap,
								  Goal approveGoal) {

		//init all implements class
		DrilldownRule drilldownRule = (DrilldownRule)beanFactory.getBean(InstanceCode.DrilldownRule);
		ApproveRule approveRule = (ApproveRule)beanFactory.getBean(InstanceCode.ApproveRule);
		CalcAgencyPlanRule calcAgencyPlanRule = (CalcAgencyPlanRule) beanFactory.getBean(InstanceCode.CalcAgencyPlanDetailPlan);

		int dataYear = agencyPlanDetail.getIdentity().getDataYear();
		String agentID = agencyPlanDetail.getIdentity().getAgentID();
		String subAgentID = agencyPlanDetail.getSubAgentID();
		String displayBlock = agencyPlanDetail.getIdentity().getDisplayBlock();

		boolean isApprove = false;

		//use interface check isApprove or isDrilldown
		if(!agentID.equalsIgnoreCase(subAgentID)) isApprove = approveRule.isApprove(pendingApproveMap,agencyPlanDetail,agentYearData);
		boolean isDrilldown = sysYearData != null && drilldownRule.isDrilldown(agencyPlanDetail,agentYearData);

		logger.trace("CalculationAgencyPlan detail ["+dataYear+"] ,["+agentID+"] = ["+subAgentID+"] : ["+isApprove+"]");

		int yearPlanStartMonth = sysDataService.getRemainingPlanCalcStartMonth(sysYearData);

		long fyfcGoal = calcAgencyPlanRule.calcDetailGoal(agentYearData,displayBlock,approveGoal, AgencyPlanDataType.FYFC);
		long fyfcPlan = calcAgencyPlanRule.calcDetailPlan(agentYearData,displayBlock,approveGoal,yearPlanStartMonth,12, AgencyPlanDataType.FYFC);
		long perCase = Long.parseLong(approveGoal.getGoalValue(GoalSettingValue.PER_CASE_FYFC));
		long anpGoal = calcAgencyPlanRule.calcDetailGoal(agentYearData,displayBlock,approveGoal, AgencyPlanDataType.ANP);
		long anpPlan = calcAgencyPlanRule.calcDetailPlan(agentYearData,displayBlock,approveGoal,yearPlanStartMonth,12, AgencyPlanDataType.ANP);
		long manpower = calcAgencyPlanRule.calcDetailManpower(agentYearData,displayBlock,approveGoal);
		long recruitment = calcAgencyPlanRule.calcDetailRecruitment(agentYearData,displayBlock,approveGoal);

		//save FYFC
		saveAgencyPlanDetail(fyfcGoal,fyfcPlan,manpower,recruitment,perCase,agencyPlanDetail.getFyfc()
				, AgencyPlanDataType.FYFC,agencyPlanDetail,isApprove,isDrilldown);

		//save ANP
		saveAgencyPlanDetail(anpGoal,anpPlan,manpower,recruitment,InstanceCode.DASHED,agencyPlanDetail.getAnp()
				, AgencyPlanDataType.ANP,agencyPlanDetail,isApprove,isDrilldown);
	}

	public void saveEmptyAgencyPlanDetail(AgentData agentData,AgencyPlanDetail agencyPlanDetail) {
		saveAgencyPlanDetail(InstanceCode.DASHED, InstanceCode.DASHED, InstanceCode.DASHED, InstanceCode.DASHED, InstanceCode.DASHED, InstanceCode.DASHED, AgencyPlanDataType.FYFC,agencyPlanDetail,false,false);
		saveAgencyPlanDetail(InstanceCode.DASHED, InstanceCode.DASHED, InstanceCode.DASHED, InstanceCode.DASHED, InstanceCode.DASHED, InstanceCode.DASHED, AgencyPlanDataType.ANP,agencyPlanDetail,false,false);
	}


	private void saveAgencyPlanDetail(long goal , long plan , long manpower , long recruitment , long perCase,long actual ,
									  AgencyPlanDataType agencyPlanDataType,
									  AgencyPlanDetail agencyPlanDetail,
									  boolean isApprove,boolean isDrilldown) {

		logger.trace("Insert AgencyPlanDetailSync ["+agencyPlanDetail.getIdentity().getAgentID()+"] , ["+agencyPlanDetail.getIdentity().getDataYear()+"] ,  ["+agencyPlanDetail.getSubAgentID()+"] : ["+isApprove+"]");

		long forecast = (plan == -1 || actual == -1) ? -1 : plan + actual;
		long caseCount = getCaseCount(plan,perCase);

		AgencyPlanDetailSyncIdentity identity = new AgencyPlanDetailSyncIdentity();

		identity.setOrganizationalUnit(agencyPlanDetail.getIdentity().getOrganizationalUnit());
		identity.setDataYear(agencyPlanDetail.getIdentity().getDataYear());
		identity.setAgentID(agencyPlanDetail.getIdentity().getAgentID());
		identity.setDisplayBlock(agencyPlanDetail.getIdentity().getDisplayBlock());
		identity.setDisplayOrder(agencyPlanDetail.getIdentity().getDisplayOrder());
		identity.setDataType(agencyPlanDataType.toString());

		AgencyPlanDetailSync detailSync = new AgencyPlanDetailSync();
		detailSync.setIdentity(identity);
		detailSync.setSubordinateAgentID(agencyPlanDetail.getSubAgentID());
		detailSync.setSubordinateAgentAgentName(agencyPlanDetail.getSubAgentName());
		detailSync.setSubordinateAgentJobGrade(agencyPlanDetail.getSubAgentJobGrade());
		detailSync.setIsApprove(isApprove ? "Y" : "N");
		detailSync.setIsDrilldown(isDrilldown ? "Y" : "N");
		detailSync.setGoal(goal);
		detailSync.setForecast(forecast);
		detailSync.setActual(actual);
		detailSync.setPlan(plan);
		detailSync.setManpower(manpower);
		detailSync.setRecruitment(recruitment);
		detailSync.setCaseCount(caseCount);
		detailSync.setPerCase(perCase);
		detailSync.setCreateBy("SD");
		detailSync.setCreateTime(dateService.getTodayDate());

		agencyPlanDetailSyncRepository.save(detailSync);
	}

	private long getCaseCount(long plan,long perCase)
	{

		if(plan == -1 || perCase == -1) return -1;
		else return  (plan !=0 && perCase != 0) ? numberService.calcDevideAndRound(plan , perCase) : 0;
	}
}
