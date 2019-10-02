package com.allianz.sd.core.api.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.allianz.sd.core.exception.AgentIsNotSuperVisitor;
import com.allianz.sd.core.exception.NotFoundContentType;
import com.allianz.sd.core.exception.SnDException;
import com.allianz.sd.core.exception.SystemError;
import com.allianz.sd.core.goalsetting.GoalStatusListener;
import com.allianz.sd.core.goalsetting.bean.GoalStatusSubject;
import com.allianz.sd.core.goalsetting.bean.GoalVersionSubject;
import com.allianz.sd.core.jpa.bean.DataCategory;
import com.allianz.sd.core.service.*;
import com.allianz.sd.core.service.bean.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.allianz.sd.core.api.model.ApproveGoalRequest;
import com.allianz.sd.core.api.model.GenerationResponse;
import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.jpa.model.GoalSettingSplit;
import com.allianz.sd.core.jpa.model.GoalSettingVersion;
import com.allianz.sd.core.jpa.repository.GoalSettingSplitRepository;
import com.allianz.sd.core.jpa.repository.GoalSettingValueRepository;
import com.allianz.sd.core.jpa.repository.GoalSettingVersionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiParam;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-06-21T03:48:11.211Z")

@RestController
@RequestMapping("/ApproveGoal")
public class ApproveGoalApiController implements ApproveGoalApi {

    private static final Logger logger = LoggerFactory.getLogger(ApproveGoalApiController.class);

    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    private final TokenService tokenService;
    private final AgentDataService agentDataService;
    private final GoalService goalService;
    private final OrganizationService organizationService;
    private final DateService dateSerivce;
    private final GoalSettingVersionRepository goalSettingVersionRepository;
    private final GoalSettingSplitRepository goalSettingSplitRepository;
    private final GoalStatusListener goalStatusListener;
    private final AgentUpdateVersionService agentUpdateVersionService;

    @org.springframework.beans.factory.annotation.Autowired
    public ApproveGoalApiController(ObjectMapper objectMapper, HttpServletRequest request,
    		TokenService tokenService,
    		AgencyPlanBottomUpService agencyPlanBottomUpService,
    		GoalService goalService,
    		OrganizationService organizationService,
    		DateService dateSerivce, NotificationsService notificationsService,
            GoalSettingVersionRepository goalSettingVersionRepository,
            GoalSettingSplitRepository goalSettingSplitRepository,
            AgentDataService agentDataService,
            GoalStatusListener goalStatusListener,
            AgentUpdateVersionService agentUpdateVersionService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.tokenService = tokenService;
        this.goalService = goalService;
        this.organizationService = organizationService;
        this.dateSerivce = dateSerivce;
        this.agentDataService = agentDataService;
        this.goalSettingVersionRepository = goalSettingVersionRepository;
        this.goalSettingSplitRepository = goalSettingSplitRepository;
        this.goalStatusListener = goalStatusListener;
        this.agentUpdateVersionService = agentUpdateVersionService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    @ResponseBody
    @Validated
    public GenerationResponse approveTheGoal(
    		@ApiParam(value = "Bearer oauth2_access_token" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization,
    		@ApiParam(value = "[RFC 7231 Date/Time Formats](https://tools.ietf.org/html/rfc7231#section-7.1.1.1) - e.g : Sun, 06 Nov 1994 08:49:37 GMT " ,required=true) @RequestHeader(value="X-Date", required=true) String xDate,
    		@ApiParam(value = "Unique request ID provided by the partner for logging and tracing transactions." ,required=true) @RequestHeader(value="X-Request-ID", required=true) String xRequestID,
    		@ApiParam(value = "the body content type" ,required=true) @RequestHeader(value="Content-Type", required=true) String contentType,
    		@ApiParam(value = "" ,required=true )  @Valid @RequestBody ApproveGoalRequest submitInfo,
    		@ApiParam(value = "The identifier of the partner organization" ) @RequestHeader(value="X-Organization", required=false) String xOrganization,
    		@ApiParam(value = "The identifier of the partner sub-organization" ) @RequestHeader(value="X-Organization-Branch", required=false) String xOrganizationBranch,@ApiParam(value = "The version of the resource" ) @RequestHeader(value="X-API-Version", required=false) String xAPIVersion) {
        String accept = request.getHeader("content-type");
        if (accept != null && accept.contains("application/json")) {

        	//return model
        	GenerationResponse generationResponse = new GenerationResponse();

            String agentID = submitInfo.getAgentID();
            String comment = submitInfo.getComment();
            boolean isApprove = submitInfo.isIsApprove();
            int dataYear = submitInfo.getDataYear().intValue();
            String supervisorAgentID = null;

            try {
                Date todayDate = dateSerivce.getTodayDate();
            	
                supervisorAgentID = tokenService.getAgentIdFromToken(authorization);

                logger.debug("ApproveGoal Start AgentID=["+agentID+"],isApprove=["+isApprove+"],supervisorAgentID=["+supervisorAgentID+"],comment=["+comment+"],timestampe = " + new Date().getTime());

                String organizationalUnit = organizationService.getOrganizationalUnit();
                AgentYearData agentYearData = agentDataService.getAgentYearData(agentID, dataYear);

                //判斷登入的人是否可以審這個Agent
                if(!agentYearData.getGoalSigningSupervisor().equalsIgnoreCase(supervisorAgentID)) throw new AgentIsNotSuperVisitor(supervisorAgentID,agentID);

                GoalSettingStatus status = isApprove ? GoalSettingStatus.APPROVED : GoalSettingStatus.REJECT;

                //get goal setting last version
                GoalSettingVersion goalSettingVersion = goalService.getLastGoalVersionModel(agentID, organizationalUnit, dataYear);
                goalSettingVersion.setStatusDate(todayDate);
                goalSettingVersion.setStatus(status.toString());
                goalSettingVersion.setSupervisorComment(comment);

                //Get Current ApproveGoal
                com.allianz.sd.core.service.bean.Goal approveGoal = goalService.getYearGoal(agentID,dataYear);
                com.allianz.sd.core.service.bean.Goal beforeGoal = goalService.getYearGoal(goalSettingVersion.getGoalSettingSeq());

                //goal approve process ( is Approve: update topVerison > save > bottom up)
                if(isApprove) {

                	//update all data topVersion to N
                	goalSettingVersionRepository.updateTopVersion2N(agentID, organizationalUnit, dataYear);
                	goalSettingVersion.setTopVersion(isApprove?"Y":"N");
                	goalSettingVersionRepository.save(goalSettingVersion);
                	
                	Integer goalSettingSeq = goalSettingVersion.getGoalSettingSeq();

                	//split
                	GoalSettingSplit goalSettingSplit = goalService.getGoalSplitVersionModel(goalSettingSeq);
                	goalSettingSplitRepository.updateTopVersion2N(goalSettingSeq);
                	goalSettingSplit.setTopVersion(isApprove?"Y":"N");
                	goalSettingSplitRepository.save(goalSettingSplit);


                }else {
                	goalSettingVersionRepository.save(goalSettingVersion);
                }

                //get ChangeGoal
                com.allianz.sd.core.service.bean.Goal afterGoal = goalService.getYearGoal(goalSettingVersion.getGoalSettingSeq());

                //create GoalVersionSubject
                //create GoalStatusSubject
                GoalStatusSubject goalStatusSubject = new GoalStatusSubject();
                goalStatusSubject.setAgentYearData(agentYearData);
                goalStatusSubject.setApproveGoal(approveGoal);
                goalStatusSubject.setBeforeStatus(GoalSettingStatus.PENDING_APPROVAL);
                goalStatusSubject.setAfterStatus(GoalSettingStatus.valueOf(afterGoal.getGoalSettingVersion().getStatus()));
                goalStatusSubject.setBeforeGoal(beforeGoal);
                goalStatusSubject.setAfterGoal(afterGoal);
                goalStatusListener.changeStatus(goalStatusSubject);

                generationResponse.setSuccess(true);

                logger.debug("ApproveGoal End AgentID=["+agentID+"],timestampe = " + new Date().getTime());

                return generationResponse;

            } catch(SnDException e) {
                logger.error("ApproveGoal fail!!["+supervisorAgentID+"]["+agentID+"]["+dataYear+"]", e);
                throw e;
            } catch (Exception e) {
                generationResponse.setSuccess(false);
                logger.error("ApproveGoal fail!!["+supervisorAgentID+"]["+agentID+"]["+dataYear+"]", e);
                throw new SystemError(e);
            }
        }
        else throw new NotFoundContentType();
    }

}
