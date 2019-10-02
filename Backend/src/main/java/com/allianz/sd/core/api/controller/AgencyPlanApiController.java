package com.allianz.sd.core.api.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.allianz.sd.core.exception.NotFoundContentType;
import com.allianz.sd.core.exception.SnDException;
import com.allianz.sd.core.exception.SystemError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.allianz.sd.core.api.model.AgencyPlanGetResponse;
import com.allianz.sd.core.jpa.repository.AgencyPlanDetailSyncRepository;
import com.allianz.sd.core.service.AgencyPlanSyncService;
import com.allianz.sd.core.service.DateService;
import com.allianz.sd.core.service.OrganizationService;
import com.allianz.sd.core.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiParam;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-06-03T09:00:51.698Z")

@RestController
public class AgencyPlanApiController implements AgencyPlanApi {

    private static final Logger logger = LoggerFactory.getLogger(AgencyPlanApiController.class);

    private final ObjectMapper objectMapper;
    private final TokenService tokenService;
    private final OrganizationService organizationService;
    private final AgencyPlanSyncService agencyPlanSyncService;
    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public AgencyPlanApiController(ObjectMapper objectMapper, HttpServletRequest request,
                                   TokenService tokenService,
                                   OrganizationService organizationService,
                                   DateService dateService,
                                   AgencyPlanDetailSyncRepository agencyPlanDetailSyncRepository,
                                   AgencyPlanSyncService agencyPlanSyncService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.tokenService = tokenService;
        this.organizationService = organizationService;
        this.agencyPlanSyncService = agencyPlanSyncService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/AgencyPlan/{agentID}")
    @ResponseBody
    @Validated
    public AgencyPlanGetResponse getAgencyPlanByAgentId(@ApiParam(value = "Bearer oauth2_access_token" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization,@ApiParam(value = "[RFC 7231 Date/Time Formats](https://tools.ietf.org/html/rfc7231#section-7.1.1.1) - e.g : Sun, 06 Nov 1994 08:49:37 GMT " ,required=true) @RequestHeader(value="X-Date", required=true) String xDate,@ApiParam(value = "Unique request ID provided by the partner for logging and tracing transactions." ,required=true) @RequestHeader(value="X-Request-ID", required=true) String xRequestID,@ApiParam(value = "the body content type" ,required=true) @RequestHeader(value="Content-Type", required=true) String contentType,@ApiParam(value = "agentID",required=true) @PathVariable("agentID") String agentID,@ApiParam(value = "The identifier of the partner organization" ) @RequestHeader(value="X-Organization", required=false) String xOrganization,@ApiParam(value = "The identifier of the partner sub-organization" ) @RequestHeader(value="X-Organization-Branch", required=false) String xOrganizationBranch,@ApiParam(value = "The identifier of the requester of the resource. The agents ID" ) @RequestHeader(value="X-Organization-User", required=false) BigDecimal xOrganizationUser,@ApiParam(value = "The version of the resource" ) @RequestHeader(value="X-API-Version", required=false) String xAPIVersion,@ApiParam(value = "summary or detail , default is summary") @Valid @RequestParam(value = "queryMode", required = false) String queryMode) {
        String accept = request.getHeader("content-type");
        if (accept != null && accept.contains("application/json")) {
            String organizationalUnit = organizationService.getOrganizationalUnit();

            // default get detail
            boolean getDetail = !"summary".equalsIgnoreCase(queryMode);

            try{
                //get response
                return agencyPlanSyncService.getAgencyPlanSyncResponse(agentID, organizationalUnit, getDetail,true);
            }catch(SnDException e) {
                logger.error("getAgencyPlanByAgentId fail!!["+agentID+"]",e);
                throw e;
            }catch(Exception e) {
                logger.error("getAgencyPlanByAgentId fail!!["+agentID+"]",e);
                throw new SystemError(e);
            }
        }
        else throw new NotFoundContentType();
    }

    @RequestMapping(value = "/AgencyPlan" , method = RequestMethod.GET)
    @ResponseBody
    @Validated
    public AgencyPlanGetResponse getAgencyPlan(@ApiParam(value = "Bearer oauth2_access_token" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization,@ApiParam(value = "[RFC 7231 Date/Time Formats](https://tools.ietf.org/html/rfc7231#section-7.1.1.1) - e.g : Sun, 06 Nov 1994 08:49:37 GMT " ,required=true) @RequestHeader(value="X-Date", required=true) String xDate,@ApiParam(value = "Unique request ID provided by the partner for logging and tracing transactions." ,required=true) @RequestHeader(value="X-Request-ID", required=true) String xRequestID,@ApiParam(value = "the body content type" ,required=true) @RequestHeader(value="Content-Type", required=true) String contentType,@NotNull @ApiParam(value = "Device last updatetime e.g. 2018-02-27T00:00:00+0800", required = true) @Valid @RequestParam(value = "lastUpdateTime", required = true) String lastUpdateTime,@ApiParam(value = "The identifier of the partner organization" ) @RequestHeader(value="X-Organization", required=false) String xOrganization,@ApiParam(value = "The identifier of the partner sub-organization" ) @RequestHeader(value="X-Organization-Branch", required=false) String xOrganizationBranch,@ApiParam(value = "The identifier of the requester of the resource. The agents ID" ) @RequestHeader(value="X-Organization-User", required=false) BigDecimal xOrganizationUser,@ApiParam(value = "The version of the resource" ) @RequestHeader(value="X-API-Version", required=false) String xAPIVersion) {
        String accept = request.getHeader("content-type");
        if (accept != null && accept.contains("application/json")) {
            String agentID = tokenService.getAgentIdFromToken(authorization);
            String organizationalUnit = organizationService.getOrganizationalUnit();

            try{
                //get response
                return agencyPlanSyncService.getAgencyPlanSyncResponse(agentID, organizationalUnit);
            }catch(SnDException e) {
                logger.error("getAgencyPlan fail!!["+agentID+"]",e);
                throw e;
            }catch(Exception e) {
                logger.error("getAgencyPlan fail!!["+agentID+"]",e);
                throw new SystemError(e);
            }

        }
        else throw new NotFoundContentType();
    }



}
