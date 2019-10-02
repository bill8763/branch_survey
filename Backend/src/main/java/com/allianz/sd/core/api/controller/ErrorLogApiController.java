package com.allianz.sd.core.api.controller;

import java.util.List;

import com.allianz.sd.core.api.model.AppLog;
import com.allianz.sd.core.api.model.ErrorLog;
import com.allianz.sd.core.api.model.GenerationResponse;
import com.allianz.sd.core.exception.NotFoundContentType;
import com.allianz.sd.core.exception.SnDException;
import com.allianz.sd.core.exception.SystemError;
import com.allianz.sd.core.jpa.model.APPClickLog;
import com.allianz.sd.core.jpa.repository.ErrorLogRepository;
import com.allianz.sd.core.service.DateService;
import com.allianz.sd.core.service.OrganizationService;
import com.allianz.sd.core.service.TokenService;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-05-13T11:47:45.780Z")

@Controller
public class ErrorLogApiController implements ErrorLogApi {

    private static final Logger logger = LoggerFactory.getLogger(ErrorLogApiController.class);

    private final ObjectMapper objectMapper;
    private final TokenService tokenService;
    private final OrganizationService organizationService;
    private final DateService dateService;
    private final ErrorLogRepository errorLogRepository;


    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public ErrorLogApiController(ObjectMapper objectMapper, HttpServletRequest request, 
    		DateService dateService,TokenService tokenService,
    		OrganizationService organizationService, ErrorLogRepository errorLogRepository) {
        this.objectMapper = objectMapper;
        objectMapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        
        this.request = request;
        this.tokenService = tokenService;
        this.organizationService = organizationService;
        this.dateService = dateService;
        this.errorLogRepository = errorLogRepository;

    }

    @RequestMapping("/errorLog")
    @ResponseBody
    @Validated
    public GenerationResponse errorLog(@ApiParam(value = "Bearer oauth2_access_token" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization,@ApiParam(value = "[RFC 7231 Date/Time Formats](https://tools.ietf.org/html/rfc7231#section-7.1.1.1) - e.g : Sun, 06 Nov 1994 08:49:37 GMT " ,required=true) @RequestHeader(value="X-Date", required=true) String xDate,@ApiParam(value = "Unique request ID provided by the partner for logging and tracing transactions." ,required=true) @RequestHeader(value="X-Request-ID", required=true) String xRequestID,@ApiParam(value = "the body content type" ,required=true) @RequestHeader(value="Content-Type", required=true) String contentType,@ApiParam(value = "" ,required=true )  @Valid @RequestBody List<ErrorLog> log,@ApiParam(value = "The identifier of the partner organization" ) @RequestHeader(value="X-Organization", required=false) String xOrganization,@ApiParam(value = "The identifier of the partner sub-organization" ) @RequestHeader(value="X-Organization-Branch", required=false) String xOrganizationBranch,@ApiParam(value = "The version of the resource" ) @RequestHeader(value="X-API-Version", required=false) String xAPIVersion) {
        String accept = request.getHeader("Content-Type");
        if (accept != null && accept.contains("application/json")) {
            boolean isSuccess = false;

            String agentID = tokenService.getAgentIdFromToken(authorization);

            try {

                for (ErrorLog elog : log) {

                    com.allianz.sd.core.jpa.model.ErrorLog errorLog = new com.allianz.sd.core.jpa.model.ErrorLog();
                    errorLog.setDeviceSystem(elog.getDeviceSystem());
                    errorLog.setDeviceModel(elog.getDeviceModel());
                    errorLog.setMessage(elog.getMessage());
                    errorLog.setOrganizationalUnit(organizationService.getOrganizationalUnit());
                    errorLog.setErrorTime(dateService.toDateTimeFormatDate(elog.getTime()));
                    errorLog.setStack(elog.getStack());
                    errorLog.setAgentID(agentID);

                    this.errorLogRepository.save(errorLog);
                }

                isSuccess = true;

            } catch(SnDException e) {
                logger.error("addErrorLog fail!!["+agentID+"]", e);
                throw e;
            } catch (Exception e) {
                logger.error("addErrorLog fail!!["+agentID+"]", e);
                throw new SystemError(e);
            }

            GenerationResponse generationResponse = new GenerationResponse();
            generationResponse.setSuccess(isSuccess);
            return generationResponse;
        }
        else throw new NotFoundContentType();
    }

}
