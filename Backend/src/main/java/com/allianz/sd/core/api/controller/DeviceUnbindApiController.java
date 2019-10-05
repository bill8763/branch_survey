package com.allianz.sd.core.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.allianz.sd.core.exception.SnDException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.allianz.sd.core.api.model.DeviceUnbindInfoRequest;
import com.allianz.sd.core.api.model.GenerationResponse;
import com.allianz.sd.core.exception.NotFoundAgentDevice;
import com.allianz.sd.core.exception.NotFoundContentType;
import com.allianz.sd.core.exception.SystemError;
import com.allianz.sd.core.jpa.model.AgentDevice;
import com.allianz.sd.core.jpa.repository.AgentDeviceRepository;
import com.allianz.sd.core.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiParam;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-06T03:16:57.779Z")

@RestController
public class DeviceUnbindApiController implements DeviceUnbindApi {

    private static final Logger log = LoggerFactory.getLogger(DeviceUnbindApiController.class);

    private final ObjectMapper objectMapper;

    private final TokenService tokenService;

    private final HttpServletRequest request;

    private final AgentDeviceRepository agentDeviceRepository;

    @org.springframework.beans.factory.annotation.Autowired
    public DeviceUnbindApiController(AgentDeviceRepository agentDeviceRepository,TokenService tokenService,
                                     ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.tokenService = tokenService;
        this.agentDeviceRepository = agentDeviceRepository;
    }

    @Transactional
    @ResponseBody
    @Validated
    @RequestMapping(value="/deviceUnbind",method=RequestMethod.POST)
    public GenerationResponse unbindingDevice(@ApiParam(value = "Bearer oauth2_access_token" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization,@ApiParam(value = "[RFC 7231 Date/Time Formats](https://tools.ietf.org/html/rfc7231#section-7.1.1.1) - e.g : Sun, 06 Nov 1994 08:49:37 GMT " ,required=true) @RequestHeader(value="X-Date", required=true) String xDate,@ApiParam(value = "Unique request ID provided by the partner for logging and tracing transactions." ,required=true) @RequestHeader(value="X-Request-ID", required=true) String xRequestID,@ApiParam(value = "the body content type" ,required=true) @RequestHeader(value="Content-Type", required=true) String contentType,@ApiParam(value = "DeviceInfo" ,required=true )  @Valid @RequestBody DeviceUnbindInfoRequest deviceInfo,@ApiParam(value = "The identifier of the partner organization" ) @RequestHeader(value="X-Organization", required=false) String xOrganization,@ApiParam(value = "The identifier of the partner sub-organization" ) @RequestHeader(value="X-Organization-Branch", required=false) String xOrganizationBranch,@ApiParam(value = "The version of the resource" ) @RequestHeader(value="X-API-Version", required=false) String xAPIVersion) {
        String accept = request.getHeader("Content-Type");
        if (accept != null && accept.contains("application/json")) {
            String agentID = tokenService.getAgentIdFromToken(authorization);

            try {
                String deviceID = deviceInfo.getDeviceId();

                AgentDevice agentDevice = agentDeviceRepository.findByAgentIDAndDeviceID(agentID, deviceID);
                if(agentDevice != null) {
                    agentDeviceRepository.delete(agentDevice);

                    GenerationResponse response = new GenerationResponse();
                    response.setSuccess(true);

                    return response;
                }else {
                    throw new NotFoundAgentDevice();
                }
            } catch(SnDException e) {
                log.error("DeviceUnbindApiController Fail!!["+agentID+"]", e);
                throw e;
            } catch (Exception e) {
                log.error("DeviceUnbindApiController Fail!!["+agentID+"]", e);
                throw new SystemError(e);
            }
        }
        else throw new NotFoundContentType();
    }

}
