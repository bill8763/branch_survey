package com.allianz.sd.core.api.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.allianz.sd.core.authorization.bean.DeviceInfo;
import com.allianz.sd.core.exception.NotFoundAgentLoginToken;
import com.allianz.sd.core.exception.NotFoundContentType;
import com.allianz.sd.core.exception.SnDException;
import com.allianz.sd.core.exception.SystemError;
import com.allianz.sd.core.jpa.model.*;
import com.allianz.sd.core.service.AgentDataService;
import com.allianz.sd.core.service.AgentDeviceService;
import com.allianz.sd.core.service.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.allianz.sd.core.api.model.DeviceInfoRequest;
import com.allianz.sd.core.api.model.GenerationResponse;
import com.allianz.sd.core.jpa.repository.AgentLoginTokenRepository;
import com.allianz.sd.core.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiParam;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-04-11T12:19:29.739Z")

@RestController
public class DeviceChangeApiController implements DeviceChangeApi {

    private static final Logger logger = LoggerFactory.getLogger(DeviceChangeApiController.class);

    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    private final AgentDataService agentDataService;
    private final TokenService tokenService;
    private final AgentDeviceService agentDeviceService;
    private final AgentLoginTokenRepository agentLoginTokenRepository;
    private final OrganizationService organizationService;

    @org.springframework.beans.factory.annotation.Autowired
    public DeviceChangeApiController(AgentLoginTokenRepository agentLoginTokenRepository,AgentDeviceService agentDeviceService,ObjectMapper objectMapper, HttpServletRequest request,AgentDataService agentDataService,TokenService tokenService,OrganizationService organizationService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.agentDataService = agentDataService;
        this.tokenService = tokenService;
        this.agentDeviceService = agentDeviceService;
        this.agentLoginTokenRepository = agentLoginTokenRepository;
        this.organizationService = organizationService;
    }

    @RequestMapping("/deviceChange")
    @ResponseBody
    @Validated
    @Transactional
    public GenerationResponse deviceChange(@ApiParam(value = "Bearer oauth2_access_token" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization,@ApiParam(value = "[RFC 7231 Date/Time Formats](https://tools.ietf.org/html/rfc7231#section-7.1.1.1) - e.g : Sun, 06 Nov 1994 08:49:37 GMT " ,required=true) @RequestHeader(value="X-Date", required=true) String xDate,@ApiParam(value = "Unique request ID provided by the partner for logging and tracing transactions." ,required=true) @RequestHeader(value="X-Request-ID", required=true) String xRequestID,@ApiParam(value = "the body content type" ,required=true) @RequestHeader(value="Content-Type", required=true) String contentType, @Valid @ApiParam(value = "DeviceInfo" ,required=true ) @RequestBody DeviceInfoRequest deviceInfo,@ApiParam(value = "The identifier of the partner organization" ) @RequestHeader(value="X-Organization", required=false) String xOrganization,@ApiParam(value = "The identifier of the partner sub-organization" ) @RequestHeader(value="X-Organization-Branch", required=false) String xOrganizationBranch,@ApiParam(value = "The version of the resource" ) @RequestHeader(value="X-API-Version", required=false) String xAPIVersion) {
        String accept = request.getHeader("Content-Type");
        if (accept != null && accept.contains("application/json")) {

            String agentID = tokenService.getAgentIdFromToken(authorization);

            try {

                GenerationResponse response = new GenerationResponse();

                AgentYearData agentYearData = agentDataService.getCurrentAgentYearData(agentID);

                com.allianz.sd.core.authorization.bean.DeviceInfo deviceInfo2 = new DeviceInfo(
                        deviceInfo.getDeviceId(),
                        deviceInfo.getDeviceModel(),
                        deviceInfo.getDeviceSystem(),
                        deviceInfo.getPushId(),
                        deviceInfo.getDeviceType());


                String deviceID = deviceInfo2.getDeviceId();

                //查AgentLoginToken的Token
                AgentLoginTokenIdentity agentLoginTokenIdentity = new AgentLoginTokenIdentity();
                agentLoginTokenIdentity.setAgentID(agentID);
                agentLoginTokenIdentity.setDeviceID(deviceID);

                Optional<AgentLoginToken> agentLoginTokenOption = agentLoginTokenRepository.findById(agentLoginTokenIdentity);
                AgentLoginToken agentLoginTokenData = agentLoginTokenOption.orElse(null);

                if(agentLoginTokenData != null) {

                    boolean isSuccess = agentDeviceService.onPushIdChange(agentYearData,deviceInfo2,agentLoginTokenData);

                    response.setSuccess(isSuccess);
                    return response;
                }
                else throw new NotFoundAgentLoginToken();
            } catch(SnDException e) {
                logger.error("DeviceChange fail!!!["+agentID+"]", e);
                throw e;
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("DeviceChange fail!!!["+agentID+"]", e);
                throw new SystemError(e);
            }
        } else throw new NotFoundContentType();
    }

}
