package com.allianz.sd.core.api.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.allianz.sd.core.api.model.Progress;
import com.allianz.sd.core.datasync.AgentTableDataSync;
import com.allianz.sd.core.exception.NotFoundContentType;
import com.allianz.sd.core.exception.SnDException;
import com.allianz.sd.core.exception.SystemError;
import com.allianz.sd.core.jpa.bean.DataCategory;
import com.allianz.sd.core.service.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.allianz.sd.core.api.model.GenerationResponse;
import com.allianz.sd.core.api.model.ProgressGetResponse;
import com.allianz.sd.core.api.model.UpdateActivityRequest;
import com.allianz.sd.core.service.bean.AgentDailyAppDateType;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiParam;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-06-06T03:01:01.608Z")

@RestController
public class ProgressApiController implements ProgressApi {

    private static final Logger log = LoggerFactory.getLogger(ProgressApiController.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private AgentTableSyncService agentTableSyncService;

    @Autowired
    private DateService dateService;


    @RequestMapping(method = RequestMethod.GET, value = "/Progress/{agentID}")
    @ResponseBody
    @Validated
    public ProgressGetResponse getProgressByAgentId(@ApiParam(value = "Bearer oauth2_access_token" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization,@ApiParam(value = "[RFC 7231 Date/Time Formats](https://tools.ietf.org/html/rfc7231#section-7.1.1.1) - e.g : Sun, 06 Nov 1994 08:49:37 GMT " ,required=true) @RequestHeader(value="X-Date", required=true) String xDate,@ApiParam(value = "Unique request ID provided by the partner for logging and tracing transactions." ,required=true) @RequestHeader(value="X-Request-ID", required=true) String xRequestID,@ApiParam(value = "the body content type" ,required=true) @RequestHeader(value="Content-Type", required=true) String contentType,@ApiParam(value = "agentID",required=true) @PathVariable("agentID") String agentID,@ApiParam(value = "The identifier of the partner organization" ) @RequestHeader(value="X-Organization", required=false) String xOrganization,@ApiParam(value = "The identifier of the partner sub-organization" ) @RequestHeader(value="X-Organization-Branch", required=false) String xOrganizationBranch,@ApiParam(value = "The identifier of the requester of the resource. The agents ID" ) @RequestHeader(value="X-Organization-User", required=false) BigDecimal xOrganizationUser,@ApiParam(value = "The version of the resource" ) @RequestHeader(value="X-API-Version", required=false) String xAPIVersion) {
        String accept = request.getHeader("content-type");

        if (accept != null && accept.contains("application/json")) {
            try {
                //return progress model
                return getProgressResponse(agentID, null);
            } catch(SnDException e) {
                log.error("getProgressByAgentId fail!!["+agentID+"]", e);
                throw e;
            } catch (Exception e) {
                log.error("getProgressByAgentId fail!!["+agentID+"]", e);
                throw new SystemError(e);
            }
        }
        else throw new NotFoundContentType();
    }

    @RequestMapping(value = "/Progress" ,method = RequestMethod.GET)
    @ResponseBody
    @Validated
    public ProgressGetResponse getProgress(
            @ApiParam(value = "Bearer oauth2_access_token" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization,
            @ApiParam(value = "[RFC 7231 Date/Time Formats](https://tools.ietf.org/html/rfc7231#section-7.1.1.1) - e.g : Sun, 06 Nov 1994 08:49:37 GMT " ,required=true) @RequestHeader(value="X-Date", required=true) String xDate,
            @ApiParam(value = "Unique request ID provided by the partner for logging and tracing transactions." ,required=true) @RequestHeader(value="X-Request-ID", required=true) String xRequestID,
            @ApiParam(value = "the body content type" ,required=true) @RequestHeader(value="Content-Type", required=true) String contentType,
            @NotNull @ApiParam(value = "Device last updatetime e.g. 2018-02-27T00:00:00+0800", required = true) @Valid @RequestParam(value = "lastUpdateTime", required = true) String lastUpdateTime,
            @ApiParam(value = "The identifier of the partner organization" ) @RequestHeader(value="X-Organization", required=false) String xOrganization,
            @ApiParam(value = "The identifier of the partner sub-organization" ) @RequestHeader(value="X-Organization-Branch", required=false) String xOrganizationBranch,
            @ApiParam(value = "The identifier of the requester of the resource. The agents ID" ) @RequestHeader(value="X-Organization-User", required=false) BigDecimal xOrganizationUser,
            @ApiParam(value = "The version of the resource" ) @RequestHeader(value="X-API-Version", required=false) String xAPIVersion) {
        String accept = request.getHeader("content-type");
        if (accept != null && accept.contains("application/json")) {

            String agentID = tokenService.getAgentIdFromToken(authorization);

            try {
                //return progress model
                return getProgressResponse(agentID, lastUpdateTime);

            } catch(SnDException e) {
                log.error("getProgress fail!["+agentID+"]", e);
                throw e;
            } catch (Exception e) {
                log.error("getProgress fail!["+agentID+"]", e);
                throw new SystemError(e);
            }
        }
        else throw new NotFoundContentType();
    }

    private ProgressGetResponse getProgressResponse(String agentID,String lastUpdateTime) throws Exception{
        ProgressGetResponse response = new ProgressGetResponse();

        List<Progress> progresses = new ArrayList<>();
        Date lastUpdateDate = pullData(agentID,lastUpdateTime,progresses);

        response.setLastUpdateTime(dateService.toDateTimeFormatString(lastUpdateDate));
        response.setProgress(progresses);

        return response;
    }

    private Date pullData(String agentID, String lastUpdateTime, List<Progress> configurations) throws Exception{
        //realtime fetch data
        if(StringUtils.isEmpty(lastUpdateTime)) {
            AgentTableDataSync agentTableDataSync = agentTableSyncService.getAgentTableDatasync(DataCategory.PROGRESS);
            agentTableDataSync.pullData(agentID,configurations);
            return dateService.getTodayDate();
        }
        //catch data
        else return agentTableSyncService.pull(DataCategory.PROGRESS, configurations, agentID, lastUpdateTime);
    }


    @RequestMapping(method=RequestMethod.POST , value="/Activity")
    @ResponseBody
    @Validated
    @Transactional
    public GenerationResponse updateTheActivityActual(@ApiParam(value = "[RFC 7231 Date/Time Formats](https://tools.ietf.org/html/rfc7231#section-7.1.1.1) - e.g : Sun, 06 Nov 1994 08:49:37 GMT " ,required=true) @RequestHeader(value="X-Date", required=true) String xDate,@ApiParam(value = "Unique request ID provided by the partner for logging and tracing transactions." ,required=true) @RequestHeader(value="X-Request-ID", required=true) String xRequestID,@ApiParam(value = "the body content type" ,required=true) @RequestHeader(value="Content-Type", required=true) String contentType,@ApiParam(value = "" ,required=true )  @Valid @RequestBody UpdateActivityRequest submitInfo,@ApiParam(value = "The identifier of the partner organization" ) @RequestHeader(value="X-Organization", required=false) String xOrganization,@ApiParam(value = "The identifier of the partner sub-organization" ) @RequestHeader(value="X-Organization-Branch", required=false) String xOrganizationBranch,@ApiParam(value = "The version of the resource" ) @RequestHeader(value="X-API-Version", required=false) String xAPIVersion) {
        String accept = request.getHeader("content-type");
        if (accept != null && accept.contains("application/json")) {

            GenerationResponse generationResponse = new GenerationResponse();
            try {
                String field = submitInfo.getField().toString();
                String agentID = submitInfo.getAgentID();
                BigDecimal value = submitInfo.getValue();
                String dataDate = submitInfo.getDataDate();
                String organizationalUnit = organizationService.getOrganizationalUnit();

                AgentDailyAppDateType type = AgentDailyAppDateType.valueOf(field.toUpperCase());

                activityService.UpdateFindSchedule(agentID, organizationalUnit, type, Integer.parseInt(String.valueOf(value)),dataDate,true);

                //add to PersonalProgress
                activityService.updatePersonalProgress(agentID, organizationalUnit, Integer.parseInt(String.valueOf(value)), type,dataDate);

                generationResponse.setSuccess(true);
            } catch(SnDException e) {
                log.error("updateTheActivityActual fail!!", e);
                throw e;
            } catch (Exception e) {
                log.error("updateTheActivityActual fail!!", e);
                throw new SystemError(e);
            }

            return generationResponse;
        }
        else throw new NotFoundContentType();
    }

}
