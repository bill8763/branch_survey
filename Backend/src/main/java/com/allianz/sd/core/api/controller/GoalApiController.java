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

import com.allianz.sd.core.api.model.GoalGetResponse;
import com.allianz.sd.core.service.GoalService;
import com.allianz.sd.core.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiParam;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-06-10T08:03:14.100Z")

@RestController
public class GoalApiController implements GoalApi {

    private static final Logger log = LoggerFactory.getLogger(GoalApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private final TokenService tokenService;
    private final GoalService goalService;


    @org.springframework.beans.factory.annotation.Autowired
    public GoalApiController(ObjectMapper objectMapper,
                             HttpServletRequest request,
                             TokenService tokenService,
                             GoalService goalService
    ) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.tokenService = tokenService;
        this.goalService = goalService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/Goal/{agentID}")
    @ResponseBody
    @Validated
    public GoalGetResponse pullGoalByAgent(@ApiParam(value = "Bearer oauth2_access_token" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization,@ApiParam(value = "[RFC 7231 Date/Time Formats](https://tools.ietf.org/html/rfc7231#section-7.1.1.1) - e.g : Sun, 06 Nov 1994 08:49:37 GMT " ,required=true) @RequestHeader(value="X-Date", required=true) String xDate,@ApiParam(value = "Unique request ID provided by the partner for logging and tracing transactions." ,required=true) @RequestHeader(value="X-Request-ID", required=true) String xRequestID,@ApiParam(value = "the body content type" ,required=true) @RequestHeader(value="Content-Type", required=true) String contentType,@ApiParam(value = "agentID",required=true) @PathVariable("agentID") String agentID,@ApiParam(value = "The identifier of the partner organization" ) @RequestHeader(value="X-Organization", required=false) String xOrganization,@ApiParam(value = "The identifier of the partner sub-organization" ) @RequestHeader(value="X-Organization-Branch", required=false) String xOrganizationBranch,@ApiParam(value = "The identifier of the requester of the resource. The agents ID" ) @RequestHeader(value="X-Organization-User", required=false) BigDecimal xOrganizationUser,@ApiParam(value = "The version of the resource" ) @RequestHeader(value="X-API-Version", required=false) String xAPIVersion) {
        String accept = request.getHeader("content-type");
        if (accept != null && accept.contains("application/json")) {
            try {
                return goalService.getGoalResponse(agentID,false);
            } catch(SnDException e) {
                log.error("pullGoalByAgent fail!!["+agentID+"]", e);
                throw e;
            } catch (Exception e) {
                log.error("pullGoalByAgent fail!!["+agentID+"]", e);
                throw new SystemError(e);
            }
        }
        else throw new NotFoundContentType();
    }

    @RequestMapping(value = "/Goal" , method = RequestMethod.GET)
    @ResponseBody
    @Validated
    public GoalGetResponse pullGoal(@ApiParam(value = "Bearer oauth2_access_token" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization,
                                                    @ApiParam(value = "[RFC 7231 Date/Time Formats](https://tools.ietf.org/html/rfc7231#section-7.1.1.1) - e.g : Sun, 06 Nov 1994 08:49:37 GMT " ,required=true) @RequestHeader(value="X-Date", required=true) String xDate,
                                                    @ApiParam(value = "Unique request ID provided by the partner for logging and tracing transactions." ,required=true) @RequestHeader(value="X-Request-ID", required=true) String xRequestID,@ApiParam(value = "the body content type" ,required=true) @RequestHeader(value="Content-Type", required=true) String contentType,@NotNull @ApiParam(value = "Device last updatetime e.g. 2018-02-27T00:00:00+0800", required = true) @Valid @RequestParam(value = "lastUpdateTime", required = true) String lastUpdateTime,@ApiParam(value = "The identifier of the partner organization" ) @RequestHeader(value="X-Organization", required=false) String xOrganization,@ApiParam(value = "The identifier of the partner sub-organization" ) @RequestHeader(value="X-Organization-Branch", required=false) String xOrganizationBranch,@ApiParam(value = "The identifier of the requester of the resource. The agents ID" ) @RequestHeader(value="X-Organization-User", required=false) BigDecimal xOrganizationUser,@ApiParam(value = "The version of the resource" ) @RequestHeader(value="X-API-Version", required=false) String xAPIVersion) {
        String accept = request.getHeader("content-type");
        if (accept != null && accept.contains("application/json")) {
            String agentID = null;
            try {

                //get agentId
                agentID = tokenService.getAgentIdFromToken(authorization);

                return goalService.getGoalResponse(agentID,true);

            } catch(SnDException e) {
                log.error("pullGoal fail!!["+agentID+"]", e);
                throw e;
            } catch (Exception e) {
                log.error("pullGoal fail!!["+agentID+"]", e);
                throw new SystemError(e);
            }
        }
        else throw new NotFoundContentType();
    }

}
