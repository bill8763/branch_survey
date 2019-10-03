package com.allianz.sd.core.api.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.allianz.sd.core.api.model.*;
import com.allianz.sd.core.api.model.Goal;
import com.allianz.sd.core.exception.*;
import com.allianz.sd.core.goalsetting.GoalStatusListener;
import com.allianz.sd.core.goalsetting.bean.GoalStatusSubject;
import com.allianz.sd.core.goalsetting.bean.GoalVersionSubject;
import com.allianz.sd.core.jpa.bean.DataCategory;
import com.allianz.sd.core.service.*;
import com.allianz.sd.core.service.bean.*;
import io.jsonwebtoken.lang.Strings;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.jpa.model.GoalSetting;
import com.allianz.sd.core.jpa.model.GoalSettingSplit;
import com.allianz.sd.core.jpa.model.GoalSettingSplitIdentity;
import com.allianz.sd.core.jpa.model.GoalSettingSplitValue;
import com.allianz.sd.core.jpa.model.GoalSettingSplitValueIdentity;
import com.allianz.sd.core.jpa.model.GoalSettingValue;
import com.allianz.sd.core.jpa.model.GoalSettingValueIdentity;
import com.allianz.sd.core.jpa.model.GoalSettingVersion;
import com.allianz.sd.core.jpa.model.SysYearData;
import com.allianz.sd.core.jpa.repository.GoalSettingRepository;
import com.allianz.sd.core.jpa.repository.GoalSettingSplitRepository;
import com.allianz.sd.core.jpa.repository.GoalSettingSplitValueRepository;
import com.allianz.sd.core.jpa.repository.GoalSettingValueRepository;
import com.allianz.sd.core.jpa.repository.GoalSettingVersionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiParam;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-06-14T02:21:28.174Z")

@RestController
@RequestMapping("/SubmitGoal")
public class SubmitGoalApiController implements SubmitGoalApi {

	private static final Logger logger = LoggerFactory.getLogger(SubmitGoalApiController.class);

	private final ObjectMapper objectMapper;

	private final HttpServletRequest request;

	private final GoalSettingVersionRepository goalSettingVersionRepository;

	private final OrganizationService organizationService;
	private final TokenService tokenService;
	private final GoalService goalService;
	private final DateService dateService;
	private final AgentDataService agentDataService;
	private final GoalSettingRepository goalSettingRepository;
	private final GoalSettingValueRepository goalSettingValueRepository;
	private final GoalSettingSplitRepository goalSettingSplitRepository;
	private final GoalSettingSplitValueRepository goalSettingSplitValueRepository;
	private final GoalStatusListener goalStatusListener;
	private final SysDataService sysDataService;
	private final NumberService numberService;
	private final StringService stringService;
	private final AgentUpdateVersionService agentUpdateVersionService;

	private static enum submitTypeEnum{
		all,plan
	}

	@org.springframework.beans.factory.annotation.Autowired
	public SubmitGoalApiController(
			ObjectMapper objectMapper, HttpServletRequest request,
			GoalSettingVersionRepository goalSettingVersionRepository,
			OrganizationService organizationService,
			TokenService tokenService,
			GoalService goalService,
			DateService dateService,
			GoalSettingRepository goalSettingRepository,
			GoalSettingValueRepository goalSettingValueRepository,
			AgentDataService agentDataService,
			GoalSettingSplitRepository goalSettingSplitRepository,
			GoalSettingSplitValueRepository goalSettingSplitValueRepository,
			GoalStatusListener goalStatusListener,
			SysDataService sysDataService,
			NumberService numberService,
			StringService stringService,
			AgentUpdateVersionService agentUpdateVersionService
	) {
		this.objectMapper = objectMapper;
		this.request = request;
		this.goalSettingVersionRepository = goalSettingVersionRepository;
		this.organizationService = organizationService;
		this.tokenService = tokenService;
		this.goalService = goalService;
		this.dateService = dateService;
		this.goalSettingRepository = goalSettingRepository;
		this.goalSettingValueRepository = goalSettingValueRepository;
		this.agentDataService = agentDataService;
		this.goalSettingSplitRepository = goalSettingSplitRepository;
		this.goalSettingSplitValueRepository = goalSettingSplitValueRepository;
		this.goalStatusListener = goalStatusListener;
		this.sysDataService = sysDataService;
		this.numberService = numberService;
		this.stringService = stringService;
		this.agentUpdateVersionService = agentUpdateVersionService;
	}


	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	@Validated
	@Transactional
	public SubmitGoalResponse submitTheGoal(@ApiParam(value = "Bearer oauth2_access_token" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization,
											@ApiParam(value = "[RFC 7231 Date/Time Formats](https://tools.ietf.org/html/rfc7231#section-7.1.1.1) - e.g : Sun, 06 Nov 1994 08:49:37 GMT " ,required=true) @RequestHeader(value="X-Date", required=true) String xDate,
											@ApiParam(value = "Unique request ID provided by the partner for logging and tracing transactions." ,required=true) @RequestHeader(value="X-Request-ID", required=true) String xRequestID,
											@ApiParam(value = "the body content type" ,required=true) @RequestHeader(value="Content-Type", required=true) String contentType,
											@ApiParam(value = "" ,required=true )  @Valid @RequestBody SubmitGoalRequest submitInfoRq,
											@ApiParam(value = "The identifier of the partner organization" ) @RequestHeader(value="X-Organization", required=false) String xOrganization,
											@ApiParam(value = "The identifier of the partner sub-organization" ) @RequestHeader(value="X-Organization-Branch", required=false) String xOrganizationBranch,@ApiParam(value = "The version of the resource" ) @RequestHeader(value="X-API-Version", required=false) String xAPIVersion) {
		String accept = request.getHeader("content-type");
		if (accept != null && accept.contains("application/json")) {

			SubmitGoalResponse goalResponse = new SubmitGoalResponse();

			String agentID = tokenService.getAgentIdFromToken(authorization);

			try{
				Date todayDate = dateService.getTodayDate();

				String organizationalUnit = organizationService.getOrganizationalUnit();
				SubmitInfo submitInfo = submitInfoRq.getSubmitInfo();
				GoalPlan goalPlan = submitInfoRq.getGoalPlan();
				List<GoalValue> goalValues = submitInfoRq.getGoalValue();
				Integer dataYear = new Integer(submitInfo.getDataYear().intValue());
				SubmitInfo.SubmitTypeEnum submitType = submitInfo.getSubmitType();
				boolean isIsNeedApprove = submitInfo.getIsNeedApprove() == SubmitInfo.IsNeedApproveEnum.Y;

				logger.debug("SubmitGoalStart AgentID["+agentID+"] , isIsNeedApprove["+isIsNeedApprove+"] , dataYear["+dataYear+"] , submitInfoRq["+submitInfoRq+"],timestampe = " + new Date().getTime());

				//check status not pending_approval (get last version)
				GoalSettingVersion goalVersion = goalService.getLastGoalVersionModel(agentID, organizationalUnit, dataYear);
				if(goalVersion==null) throw new GoalSettingCouldNotSubmitError();
				if(GoalSettingStatus.PENDING_APPROVAL.toString().equalsIgnoreCase(goalVersion.getStatus())) throw new GoalSettingCouldNotSubmitError();

				//get AgentYearData
				AgentYearData agentYearData = agentDataService.getAgentYearData(agentID,dataYear);
				String goalSigningSupervisor = agentYearData.getGoalSigningSupervisor();

				SysYearData sysYearData = sysDataService.getSysYearDataBySysCtrl(organizationalUnit,agentYearData.getAppSysCtrl(),dataYear);
				double fyfcConvertAnpRate = sysYearData.getFyfcCovertAnpRate();
				fyfcConvertAnpRate = fyfcConvertAnpRate / 100;
				fyfcConvertAnpRate  = numberService.convertToDecimal(fyfcConvertAnpRate).doubleValue();

				//get GoalSetting
				GoalSetting goalSetting = goalService.getGoalSetting(agentID, organizationalUnit, dataYear);
				int lastGoalVersion = goalVersion.getGoalVersion().intValue();

				//approveVersion
				GoalSettingVersion approvedVersion = goalService.getApprovedGoalVersionModel(agentID, organizationalUnit, dataYear);

				//Get Current ApproveGoal
				com.allianz.sd.core.service.bean.Goal approveGoal = goalService.getYearGoal(agentID,dataYear);
				com.allianz.sd.core.service.bean.Goal beforeGoal = goalService.getYearGoal(goalVersion.getGoalSettingSeq());

				GoalSettingStatus beforeStatus = GoalSettingStatus.valueOf(beforeGoal.getGoalSettingVersion().getStatus());

				Integer goalSettingSeq = null;

				//insert new version if all or (plan + needApprovel)
				if(SubmitInfo.SubmitTypeEnum.ALL == submitType || isIsNeedApprove) {

					logger.trace("Create New GoalVersion");

					//goal approve process ( is Approve: update topVerison > save > bottom up)
					if(!isIsNeedApprove) {
						//update all data topVersion to N
						goalSettingVersionRepository.updateTopVersion2N(agentID, organizationalUnit, dataYear);
						logger.trace("Update GoalTopVersion");
					}

					goalVersion = new GoalSettingVersion();

					//insert data
					goalVersion.setAgentID(agentID);
					goalVersion.setOrganizationalUnit(organizationalUnit);
					goalVersion.setDataYear(dataYear);
					goalVersion.setGoalVersion(lastGoalVersion+1);
					goalVersion.setStatus(isIsNeedApprove?GoalSettingStatus.PENDING_APPROVAL.toString():GoalSettingStatus.APPROVED.toString());
					goalVersion.setStatusDate(todayDate);
					goalVersion.setTopVersion(isIsNeedApprove?"N" : "Y");
					goalVersion.setActivityGoalBase(submitInfo.getActivityGoalBase());
					goalVersion.setGoalSubmitDate(todayDate);

					//from goal setting
					goalVersion.setGoalSettingStartDate(goalSetting.getGoalSettingStartDate());
					goalVersion.setPersonnelGoalApplicableYM(goalSetting.getPersonnelGoalApplicableYM());
					goalVersion.setTeamGoalApplicableYM(goalSetting.getTeamGoalApplicableYM());
					goalVersion.setSubmitStatus("Y");
					goalVersion.setCreateBy(agentID);
					goalVersion.setUpdateBy(agentID);

					//agenyYearData supervisor
					goalVersion.setSigningSupervisor(goalSigningSupervisor);
					goalSettingVersionRepository.save(goalVersion);

					logger.trace("Save New GoalVersion");

					//copy approved goal value (plan + need approved)
					if(SubmitInfo.SubmitTypeEnum.PLAN == submitType && isIsNeedApprove) {

						List<GoalSettingValue> goalSettingValues = goalSettingValueRepository.getApprovedGoalSettingValue(agentID, organizationalUnit, dataYear);

						for(GoalSettingValue goalSettingValue: goalSettingValues) {

							String mappingID = goalSettingValue.getIdentity().getMappingID();
							String value = goalSettingValue.getSetValue();

							GoalSettingValue cloneValue = new GoalSettingValue();
							cloneValue.setCreateBy(goalSettingValue.getCreateBy());
							cloneValue.setSetValue(value);
							cloneValue.setUpdateBy(goalSettingValue.getUpdateBy());

							GoalSettingValueIdentity identity = new GoalSettingValueIdentity();
							identity.setMappingID(mappingID);
							identity.setGoalSettingSeq(goalVersion.getGoalSettingSeq());
							cloneValue.setIdentity(identity);
							goalSettingValueRepository.save(cloneValue);

						}
					}
					goalSettingSeq = goalVersion.getGoalSettingSeq();
				}
				else {
					logger.trace("Dont need create New GoalVersion");
					//plan and no need approve : seq = approve seq
					//get approve version
					if(StringUtils.isEmpty(approvedVersion.getSigningSupervisor())) approvedVersion.setSigningSupervisor(goalSigningSupervisor);
					approvedVersion.setGoalSettingStartDate(goalSetting.getGoalSettingStartDate());
					approvedVersion.setSubmitStatus("Y");
					goalSettingVersionRepository.save(approvedVersion);

					goalSettingSeq = approvedVersion.getGoalSettingSeq();
				}

				logger.trace("final_goalSettingSeq = " + goalSettingSeq);

				//goal value
				for(GoalValue goalValue : goalValues) {
					String mappingID = goalValue.getDataType();
					String val = goalValue.getValue();
					if(StringUtils.isNotEmpty(val)) {
						GoalSettingValueIdentity identity = new GoalSettingValueIdentity();
						identity.setGoalSettingSeq(goalSettingSeq);
						identity.setMappingID(mappingID);

						GoalSettingValue value = new GoalSettingValue();
						value.setIdentity(identity);
						value.setSetValue(val);
						value.setCreateBy(agentID);
						value.setUpdateBy(agentID);
						goalSettingValueRepository.save(value);

						//自己依照PERSON_FYFC計算PERSON_ANP
						if(com.allianz.sd.core.service.bean.GoalSettingValue.PERSON_FYFC.toString().equalsIgnoreCase(mappingID)) {
							GoalSettingValueIdentity personalAnpIdentity = new GoalSettingValueIdentity();
							personalAnpIdentity.setGoalSettingSeq(goalSettingSeq);
							personalAnpIdentity.setMappingID(com.allianz.sd.core.service.bean.GoalSettingValue.PERSON_ANP.toString());

							GoalSettingValue personalAnp = new GoalSettingValue();
							personalAnp.setIdentity(personalAnpIdentity);
							personalAnp.setSetValue(String.valueOf(numberService.calcMultipleAndRound(Long.parseLong(val) , fyfcConvertAnpRate)));
							personalAnp.setCreateBy(agentID);
							personalAnp.setUpdateBy(agentID);
							goalSettingValueRepository.save(personalAnp);
						}

					}
				}

				//goal plan
				String timeBase = goalPlan.getTimeBase();
				int splitVersion = goalService.getGoalSplitVersion(goalSettingSeq) +1;

				//get approved split values and save one
				List<Object[]> goalSettingSplitValues = goalSettingSplitValueRepository.getPlanByMonth(approvedVersion.getGoalSettingSeq(),AgencyPlanDataType.FYFC.toGoalValueMapping());
				for(Object[] obj:goalSettingSplitValues) {
					Integer timeBaseSeq = new Integer(String.valueOf(obj[0]) );
					Object val = obj[1];

					String setValue = null;
					if(val != null) setValue = String.valueOf(val);

					GoalSettingSplitValue goalSettingSplitValue = new GoalSettingSplitValue();

					GoalSettingSplitValueIdentity goalSettingSplitValueIdentity = new GoalSettingSplitValueIdentity();
					goalSettingSplitValueIdentity.setGoalSettingSeq(goalSettingSeq);
					goalSettingSplitValueIdentity.setMappingID(com.allianz.sd.core.service.bean.GoalSettingValue.PERSON_FYFC.toString());
					goalSettingSplitValueIdentity.setSplitVersion(splitVersion);
					goalSettingSplitValueIdentity.setTimeBase(TimeBase.valueOf(timeBase.toUpperCase()).toString());
					goalSettingSplitValueIdentity.setTimeBaseSeq(timeBaseSeq);

					goalSettingSplitValue.setIdentity(goalSettingSplitValueIdentity);
					goalSettingSplitValue.setSetValue(setValue);

					goalSettingSplitValueRepository.save(goalSettingSplitValue);

					//自己依照PERSON_FYFC計算PERSON_ANP
					saveANPPlan(goalSettingSplitValue,fyfcConvertAnpRate);
				}

				//update topVersion to N if no need to approve
				if(!isIsNeedApprove) {
					goalSettingSplitRepository.updateTopVersion2N(goalSettingSeq);
				}

				GoalSettingSplitIdentity identity = new GoalSettingSplitIdentity();
				identity.setGoalSettingSeq(goalSettingSeq);
				identity.setSplitVersion(splitVersion);

				GoalSettingSplit goalSettingSplit = new GoalSettingSplit();
				goalSettingSplit.setIdentity(identity);
				goalSettingSplit.setTopVersion(isIsNeedApprove?"N" : "Y");
				goalSettingSplit.setVersionSettingDate(todayDate);
				goalSettingSplit.setCreateBy(agentID);
				goalSettingSplit.setUpdateBy(agentID);
				goalSettingSplit.setPerformanceSettlementMonth(new Integer(dateService.getMonthString(sysYearData.getSysData().getPerformanceSettlementMonth())));
				goalSettingSplitRepository.save(goalSettingSplit);

				List<GoalPlanValue> goalPlanValues = goalPlan.getValues();
				for(GoalPlanValue goalPlanValue: goalPlanValues) {
					GoalSettingSplitValue goalSettingSplitValue = new GoalSettingSplitValue();

					GoalSettingSplitValueIdentity goalSettingSplitValueIdentity = new GoalSettingSplitValueIdentity();
					goalSettingSplitValueIdentity.setGoalSettingSeq(goalSettingSeq);
					goalSettingSplitValueIdentity.setMappingID(com.allianz.sd.core.service.bean.GoalSettingValue.PERSON_FYFC.toString());
					goalSettingSplitValueIdentity.setSplitVersion(splitVersion);
					goalSettingSplitValueIdentity.setTimeBase(TimeBase.valueOf(timeBase.toUpperCase()).toString());
					goalSettingSplitValueIdentity.setTimeBaseSeq( goalPlanValue.getMonth().intValue());

					goalSettingSplitValue.setIdentity(goalSettingSplitValueIdentity);
					goalSettingSplitValue.setSetValue(goalPlanValue.getValue().toString());

					goalSettingSplitValueRepository.save(goalSettingSplitValue);

					//自己依照PERSON_FYFC計算PERSON_ANP
					saveANPPlan(goalSettingSplitValue,fyfcConvertAnpRate);
				}

				//clear this agent configucation cache
				Set<String> clearAgentId = new HashSet<>();
				clearAgentId.add(agentID);
				agentUpdateVersionService.clearAgentTableCache(clearAgentId, DataCategory.CONFIGURATION);

				com.allianz.sd.core.service.bean.Goal changeGoal = goalService.getYearGoal(goalSettingSeq);

				//create GoalStatusSubject
				GoalStatusSubject goalStatusSubject = new GoalStatusSubject();
				goalStatusSubject.setAgentYearData(agentYearData);
				goalStatusSubject.setApproveGoal(approveGoal);
				goalStatusSubject.setBeforeStatus(beforeStatus);
				goalStatusSubject.setAfterStatus(GoalSettingStatus.valueOf(changeGoal.getGoalSettingVersion().getStatus()));
				goalStatusSubject.setBeforeGoal(beforeGoal);
				goalStatusSubject.setAfterGoal(changeGoal);
				goalStatusListener.changeStatus(goalStatusSubject);

				//goal info (every year)
				for(Goal goal: goalService.getGoals(agentID, organizationalUnit, true)) {
					goalResponse.addGoalsItem(goal);
				}

				goalResponse.setSuccess(true);

				logger.debug("SubmitGoal["+agentID+"] End timestampe = " + new Date().getTime());

			} catch(SnDException e) {
				logger.error("SubmitGoal fail!!["+agentID+"]",e);
				throw e;
			} catch(Exception e) {
				goalResponse.setSuccess(false);
				goalResponse.setErrorCode(e.getMessage());
				logger.error("SubmitGoal fail!!["+agentID+"]",e);
			}

			return goalResponse;

		}
		else throw new NotFoundContentType();
	}

	private void saveANPPlan(GoalSettingSplitValue goalSettingSplitValue,double fyfcConvertAnpRate) {
		GoalSettingSplitValue anpSettingSplitValue = new GoalSettingSplitValue();

		String fyfcPlan = goalSettingSplitValue.getSetValue();

		if(!stringService.isNumeric(fyfcPlan)) fyfcPlan = "0";

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
