package com.allianz.sd.core.api.controller;


import com.allianz.sd.core.api.model.VersionResponse;
import com.allianz.sd.core.exception.NotFoundContentType;
import com.allianz.sd.core.service.APPUpdateSQLService;
import com.allianz.sd.core.service.TokenService;
import com.allianz.sd.core.service.bean.APPUpdateVersion;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-04-10T10:13:51.616Z")

@RestController
public class VersionApiController implements VersionApi {

    private static final Logger logger = LoggerFactory.getLogger(VersionApiController.class);

    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    private final TokenService tokenService;
    private final APPUpdateSQLService appUpdateSQLService;

    @org.springframework.beans.factory.annotation.Autowired
    public VersionApiController(ObjectMapper objectMapper, HttpServletRequest request,TokenService tokenService,APPUpdateSQLService appUpdateSQLService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.tokenService = tokenService;
        this.appUpdateSQLService = appUpdateSQLService;
    }

    @RequestMapping("/version")
    @ResponseBody
    @Validated
    public VersionResponse appUpdate(@ApiParam(value = "Bearer oauth2_access_token" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization,@ApiParam(value = "[RFC 7231 Date/Time Formats](https://tools.ietf.org/html/rfc7231#section-7.1.1.1) - e.g : Sun, 06 Nov 1994 08:49:37 GMT " ,required=true) @RequestHeader(value="X-Date", required=true) String xDate,@ApiParam(value = "Unique request ID provided by the partner for logging and tracing transactions." ,required=true) @RequestHeader(value="X-Request-ID", required=true) String xRequestID,@ApiParam(value = "the body content type" ,required=true) @RequestHeader(value="Content-Type", required=true) String contentType,@NotNull @ApiParam(value = "1.0", required = true) @Valid @RequestParam(value = "version", required = true) String version,@NotNull @ApiParam(value = "iOS | android", required = true) @Valid @RequestParam(value = "DeviceSystem", required = true) String deviceSystem,@ApiParam(value = "The identifier of the partner organization" ) @RequestHeader(value="X-Organization", required=false) String xOrganization,@ApiParam(value = "The identifier of the partner sub-organization" ) @RequestHeader(value="X-Organization-Branch", required=false) String xOrganizationBranch,@ApiParam(value = "The version of the resource" ) @RequestHeader(value="X-API-Version", required=false) String xAPIVersion) {
        String accept = request.getHeader("Content-Type");
        if (accept != null && accept.contains("application/json")) {
            VersionResponse versionResponse = new VersionResponse();

            String agentID = tokenService.getAgentIdFromToken(authorization);

            APPUpdateVersion appUpdateVersion = appUpdateSQLService.getAppUpdateVersion(version,agentID,deviceSystem);

            versionResponse.setVersion(appUpdateVersion.getVersion());
            versionResponse.setUpdateType(appUpdateVersion.getUpdateType());
            versionResponse.setAppPath(appUpdateVersion.getApplink());

            //read sqlite file
            List<String> sqlLines = new ArrayList<String>();
            versionResponse.setSqls(sqlLines);

            return versionResponse;
        }
        else throw new NotFoundContentType();


    }

}
