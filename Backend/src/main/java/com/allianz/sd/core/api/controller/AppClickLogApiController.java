package com.allianz.sd.core.api.controller;



import com.allianz.sd.core.api.model.AppLog;
import com.allianz.sd.core.api.model.GenerationResponse;
import com.allianz.sd.core.exception.NotFoundContentType;
import com.allianz.sd.core.exception.SnDException;
import com.allianz.sd.core.exception.SystemError;
import com.allianz.sd.core.jpa.model.APPClickLog;
import com.allianz.sd.core.jpa.repository.APPClickLogRepository;
import com.allianz.sd.core.service.DateService;
import com.allianz.sd.core.service.OrganizationService;
import com.allianz.sd.core.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-05-10T08:42:43.317Z")

@RestController
public class AppClickLogApiController implements AppClickLogApi {

    private static final Logger logger = LoggerFactory.getLogger(AppClickLogApiController.class);

    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    private final APPClickLogRepository appClickLogRepository;
    private final DateService dateService;
    private final TokenService tokenService;
    private final OrganizationService organizationService;

    @org.springframework.beans.factory.annotation.Autowired
    public AppClickLogApiController(ObjectMapper objectMapper, HttpServletRequest request, APPClickLogRepository appClickLogRepository, DateService dateService, TokenService tokenService,OrganizationService organizationService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.appClickLogRepository = appClickLogRepository;
        this.dateService = dateService;
        this.tokenService = tokenService;
        this.organizationService = organizationService;
    }

    @RequestMapping("/AppClickLog")
    @ResponseBody
    @Validated
    public GenerationResponse appClickLog(@ApiParam(value = "Bearer oauth2_access_token" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization, @ApiParam(value = "[RFC 7231 Date/Time Formats](https://tools.ietf.org/html/rfc7231#section-7.1.1.1) - e.g : Sun, 06 Nov 1994 08:49:37 GMT " ,required=true) @RequestHeader(value="X-Date", required=true) String xDate, @ApiParam(value = "Unique request ID provided by the partner for logging and tracing transactions." ,required=true) @RequestHeader(value="X-Request-ID", required=true) String xRequestID, @ApiParam(value = "the body content type" ,required=true) @RequestHeader(value="Content-Type", required=true) String contentType, @ApiParam(value = "" ,required=true )  @Valid @RequestBody List<AppLog> log, @ApiParam(value = "The identifier of the partner organization" ) @RequestHeader(value="X-Organization", required=false) String xOrganization, @ApiParam(value = "The identifier of the partner sub-organization" ) @RequestHeader(value="X-Organization-Branch", required=false) String xOrganizationBranch, @ApiParam(value = "The version of the resource" ) @RequestHeader(value="X-API-Version", required=false) String xAPIVersion) {
        String accept = request.getHeader("Content-Type");
        if (accept != null && accept.contains("application/json")) {

            boolean isSuccess = false;
            try {

                String agentID = tokenService.getAgentIdFromToken(authorization);

                for(AppLog appLog : log) {

                    String message = appLog.getMessage();

                    if(StringUtils.isEmpty(message)) message = "success";

                    APPClickLog appClickLog = new APPClickLog();
                    appClickLog.setDeviceSystem(appLog.getDeviceSystem());
                    appClickLog.setDeviceModel(appLog.getDeviceModel());
                    appClickLog.setOrganizationalUnit(organizationService.getOrganizationalUnit());
                    appClickLog.setLogTime(dateService.toDateTimeFormatDate(appLog.getTime()));
                    appClickLog.setFunctionID(appLog.getFunctionID());
                    appClickLog.setActionID(appLog.getAction());
                    appClickLog.setAgentID(agentID);
                    appClickLog.setValid(appLog.getValid());
                    appClickLog.setMessage(message);

                    this.appClickLogRepository.save(appClickLog);
                }

                isSuccess = true;

            } catch(SnDException e) {
                logger.error("appClickLog fail!!", e);
                throw e;
            } catch (Exception e) {
                logger.error("appClickLog fail!!", e);
                throw new SystemError(e);
            }

            GenerationResponse generationResponse = new GenerationResponse();
            generationResponse.setSuccess(isSuccess);
            return generationResponse;

        }
        else throw new NotFoundContentType();

    }


}
