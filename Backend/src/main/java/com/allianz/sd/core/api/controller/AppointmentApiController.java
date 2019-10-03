package com.allianz.sd.core.api.controller;

import java.math.BigDecimal;

import com.allianz.sd.core.api.model.Appointment;
import com.allianz.sd.core.api.model.AppointmentGetResponse;
import com.allianz.sd.core.api.model.AppointmentPutResponse;
import com.allianz.sd.core.exception.NotFoundContentType;
import com.allianz.sd.core.exception.SnDException;
import com.allianz.sd.core.exception.SystemError;
import com.allianz.sd.core.jpa.bean.DataCategory;
import com.allianz.sd.core.service.ActivityService;
import com.allianz.sd.core.service.DataSyncService;
import com.allianz.sd.core.service.DateService;
import com.allianz.sd.core.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-04-27T12:43:26.347Z")

@RestController
@RequestMapping("/appointment")
public class AppointmentApiController implements AppointmentApi {

    private static final Logger log = LoggerFactory.getLogger(AppointmentApiController.class);

    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    private final DataSyncService dataSyncService;
    private final TokenService tokenService;
    private final DateService dateService;
    private final ActivityService activityService;

    @org.springframework.beans.factory.annotation.Autowired
    public AppointmentApiController(ObjectMapper objectMapper, HttpServletRequest request,DataSyncService dataSyncService,TokenService tokenService,DateService dateService,ActivityService activityService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.dataSyncService = dataSyncService;
        this.tokenService = tokenService;
        this.dateService = dateService;
        this.activityService = activityService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    @Validated
    public AppointmentGetResponse getAppointmentsByLastUpdateDateTime(@ApiParam(value = "Bearer oauth2_access_token" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization, @ApiParam(value = "[RFC 7231 Date/Time Formats](https://tools.ietf.org/html/rfc7231#section-7.1.1.1) - e.g : Sun, 06 Nov 1994 08:49:37 GMT " ,required=true) @RequestHeader(value="X-Date", required=true) String xDate, @ApiParam(value = "Unique request ID provided by the partner for logging and tracing transactions." ,required=true) @RequestHeader(value="X-Request-ID", required=true) String xRequestID, @ApiParam(value = "the body content type" ,required=true) @RequestHeader(value="Content-Type", required=true) String contentType, @NotNull @ApiParam(value = "Device last updatetime e.g. 2018-02-27T00:00:00+08:00", required = true) @Valid @RequestParam(value = "lastUpdateTime", required = true) String lastUpdateTime, @ApiParam(value = "The identifier of the partner organization" ) @RequestHeader(value="X-Organization", required=false) String xOrganization, @ApiParam(value = "The identifier of the partner sub-organization" ) @RequestHeader(value="X-Organization-Branch", required=false) String xOrganizationBranch, @ApiParam(value = "The identifier of the requester of the resource. The agents ID" ) @RequestHeader(value="X-Organization-User", required=false) BigDecimal xOrganizationUser, @ApiParam(value = "The version of the resource" ) @RequestHeader(value="X-API-Version", required=false) String xAPIVersion) {
        String accept = request.getHeader("Content-Type");
        if (accept != null && accept.contains("application/json")) {

            String agentID = null;

            try {
                agentID = tokenService.getAgentIdFromToken(authorization);

                List<Appointment> result = new ArrayList<Appointment>();
                List<BigDecimal> deleteList = new ArrayList<>();

                Date pullDate = dataSyncService.pull(DataCategory.CALENDAR,result,deleteList,agentID,lastUpdateTime);
                AppointmentGetResponse appointmentGetResponse = new AppointmentGetResponse();
                appointmentGetResponse.setAppointments(result);
                appointmentGetResponse.setDeletedAppointmentIds(deleteList);
                appointmentGetResponse.setLastUpdateTime(dateService.toDateTimeFormatString(pullDate));

                return appointmentGetResponse;
            } catch(SnDException e) {
                log.error("AppointmentAPI Sync fail!!["+agentID+"]",e);
                throw e;
            } catch (Exception e) {
                log.error("AppointmentAPI Sync fail!!["+agentID+"]", e);
                throw new SystemError(e);
            }
        }
        else throw new NotFoundContentType();
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    @Validated
    @Transactional
    public AppointmentPutResponse pushCalendar(@ApiParam(value = "Bearer oauth2_access_token" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization, @ApiParam(value = "[RFC 7231 Date/Time Formats](https://tools.ietf.org/html/rfc7231#section-7.1.1.1) - e.g : Sun, 06 Nov 1994 08:49:37 GMT " ,required=true) @RequestHeader(value="X-Date", required=true) String xDate, @ApiParam(value = "Unique request ID provided by the partner for logging and tracing transactions." ,required=true) @RequestHeader(value="X-Request-ID", required=true) String xRequestID, @ApiParam(value = "the body content type" ,required=true) @RequestHeader(value="Content-Type", required=true) String contentType, @ApiParam(value = "" ,required=true )  @Valid @RequestBody List<Appointment> appointments, @ApiParam(value = "The identifier of the partner organization" ) @RequestHeader(value="X-Organization", required=false) String xOrganization, @ApiParam(value = "The identifier of the partner sub-organization" ) @RequestHeader(value="X-Organization-Branch", required=false) String xOrganizationBranch, @ApiParam(value = "The identifier of the requester of the resource. The agents ID" ) @RequestHeader(value="X-Organization-User", required=false) BigDecimal xOrganizationUser, @ApiParam(value = "The version of the resource" ) @RequestHeader(value="X-API-Version", required=false) String xAPIVersion) {
        String accept = request.getHeader("Content-Type");
        if (accept != null && accept.contains("application/json")) {

            String agentID = null;

            try {
                agentID = tokenService.getAgentIdFromToken(authorization);

                int updateRecords = dataSyncService.push(DataCategory.CALENDAR,agentID,appointments);
                AppointmentPutResponse appointmentPutResponse = new AppointmentPutResponse();
                appointmentPutResponse.setSynchStatus(updateRecords != -1);
                appointmentPutResponse.setUpdateRecordsNumber(updateRecords);

                //check is send message
                activityService.calcPushProgressActivityMessage(agentID,dateService.getTodayDate());

                return appointmentPutResponse;
            } catch(SnDException e) {
                log.error("AppointmentAPI Sync fail!!["+agentID+"]",e);
                throw e;
            } catch (Exception e) {
                log.error("AppointmentAPI Sync fail!!["+agentID+"]", e);
                throw new SystemError(e);
            }
        }
        else throw new NotFoundContentType();
    }

}
