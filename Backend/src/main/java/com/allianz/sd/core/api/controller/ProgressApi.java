/**
 * NOTE: This class is auto generated by the swagger code generator program (2.4.8).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package com.allianz.sd.core.api.controller;

import java.math.BigDecimal;

import com.allianz.sd.core.api.model.GenerationResponse;
import com.allianz.sd.core.api.model.ProgressGetResponse;
import com.allianz.sd.core.api.model.UpdateActivityRequest;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

@Api(value = "Progress", description = "the Progress API")
public interface ProgressApi {

    @ApiOperation(value = "Fetch agent's Progress from backend", nickname = "getProgress", notes = "", response = ProgressGetResponse.class, tags={ "Progress", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = ProgressGetResponse.class),
        @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden Access"),
        @ApiResponse(code = 404, message = "Resource Not Found"),
        @ApiResponse(code = 500, message = "Internal server error occurred"),
        @ApiResponse(code = 503, message = "Service Unavailable"),
        @ApiResponse(code = 504, message = "Gateway Timeout") })
    @RequestMapping(value = "/Progress",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.GET)
    @ResponseBody
    @Validated
    ProgressGetResponse getProgress(@ApiParam(value = "Bearer oauth2_access_token", required = true) @RequestHeader(value = "Authorization", required = true) String authorization, @ApiParam(value = "[RFC 7231 Date/Time Formats](https://tools.ietf.org/html/rfc7231#section-7.1.1.1) - e.g : Sun, 06 Nov 1994 08:49:37 GMT ", required = true) @RequestHeader(value = "X-Date", required = true) String xDate, @ApiParam(value = "Unique request ID provided by the partner for logging and tracing transactions.", required = true) @RequestHeader(value = "X-Request-ID", required = true) String xRequestID, @ApiParam(value = "the body content type", required = true) @RequestHeader(value = "Content-Type", required = true) String contentType, @NotNull @ApiParam(value = "Device last updatetime e.g. 2018-02-27T00:00:00+0800", required = true) @Valid @RequestParam(value = "lastUpdateTime", required = true) String lastUpdateTime, @ApiParam(value = "The identifier of the partner organization") @RequestHeader(value = "X-Organization", required = false) String xOrganization, @ApiParam(value = "The identifier of the partner sub-organization") @RequestHeader(value = "X-Organization-Branch", required = false) String xOrganizationBranch, @ApiParam(value = "The identifier of the requester of the resource. The agents ID") @RequestHeader(value = "X-Organization-User", required = false) BigDecimal xOrganizationUser, @ApiParam(value = "The version of the resource") @RequestHeader(value = "X-API-Version", required = false) String xAPIVersion);


    @ApiOperation(value = "Fetch agent's Progress from backend", nickname = "getProgressByAgentId", notes = "", response = ProgressGetResponse.class, tags={ "Progress", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = ProgressGetResponse.class),
        @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden Access"),
        @ApiResponse(code = 404, message = "Resource Not Found"),
        @ApiResponse(code = 500, message = "Internal server error occurred"),
        @ApiResponse(code = 503, message = "Service Unavailable"),
        @ApiResponse(code = 504, message = "Gateway Timeout") })
    @RequestMapping(value = "/Progress/{agentID}",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.GET)
    @ResponseBody
    @Validated
    ProgressGetResponse getProgressByAgentId(@ApiParam(value = "Bearer oauth2_access_token", required = true) @RequestHeader(value = "Authorization", required = true) String authorization, @ApiParam(value = "[RFC 7231 Date/Time Formats](https://tools.ietf.org/html/rfc7231#section-7.1.1.1) - e.g : Sun, 06 Nov 1994 08:49:37 GMT ", required = true) @RequestHeader(value = "X-Date", required = true) String xDate, @ApiParam(value = "Unique request ID provided by the partner for logging and tracing transactions.", required = true) @RequestHeader(value = "X-Request-ID", required = true) String xRequestID, @ApiParam(value = "the body content type", required = true) @RequestHeader(value = "Content-Type", required = true) String contentType, @ApiParam(value = "agentID", required = true) @PathVariable("agentID") String agentID, @ApiParam(value = "The identifier of the partner organization") @RequestHeader(value = "X-Organization", required = false) String xOrganization, @ApiParam(value = "The identifier of the partner sub-organization") @RequestHeader(value = "X-Organization-Branch", required = false) String xOrganizationBranch, @ApiParam(value = "The identifier of the requester of the resource. The agents ID") @RequestHeader(value = "X-Organization-User", required = false) BigDecimal xOrganizationUser, @ApiParam(value = "The version of the resource") @RequestHeader(value = "X-API-Version", required = false) String xAPIVersion);


    @ApiOperation(value = "update activity actual", nickname = "updateTheActivityActual", notes = "", response = GenerationResponse.class, tags={ "Progress", })
    @ApiResponses(value = { 
        @ApiResponse(code = 202, message = "Accepted", response = GenerationResponse.class),
        @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden Access"),
        @ApiResponse(code = 404, message = "Resource Not Found"),
        @ApiResponse(code = 500, message = "Internal server error occurred"),
        @ApiResponse(code = 503, message = "Service Unavailable"),
        @ApiResponse(code = 504, message = "Gateway Timeout") })
    @RequestMapping(value = "/Progress/Activity",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    @ResponseBody
    @Validated
    @Transactional
    GenerationResponse updateTheActivityActual(@ApiParam(value = "[RFC 7231 Date/Time Formats](https://tools.ietf.org/html/rfc7231#section-7.1.1.1) - e.g : Sun, 06 Nov 1994 08:49:37 GMT ", required = true) @RequestHeader(value = "X-Date", required = true) String xDate, @ApiParam(value = "Unique request ID provided by the partner for logging and tracing transactions.", required = true) @RequestHeader(value = "X-Request-ID", required = true) String xRequestID, @ApiParam(value = "the body content type", required = true) @RequestHeader(value = "Content-Type", required = true) String contentType, @ApiParam(value = "", required = true) @Valid @RequestBody UpdateActivityRequest submitInfo, @ApiParam(value = "The identifier of the partner organization") @RequestHeader(value = "X-Organization", required = false) String xOrganization, @ApiParam(value = "The identifier of the partner sub-organization") @RequestHeader(value = "X-Organization-Branch", required = false) String xOrganizationBranch, @ApiParam(value = "The version of the resource") @RequestHeader(value = "X-API-Version", required = false) String xAPIVersion);

}
