package com.allianz.sd.core.api.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.allianz.sd.core.api.model.Actual;
import com.allianz.sd.core.api.model.ActualValue;
import com.allianz.sd.core.datasync.AgentTableDataSync;
import com.allianz.sd.core.exception.*;
import com.allianz.sd.core.jpa.bean.DataCategory;
import com.allianz.sd.core.jpa.model.AgentSalesData;
import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.jpa.model.SysYearData;
import com.allianz.sd.core.jpa.repository.AgentSalesDataRepository;
import com.allianz.sd.core.service.*;
import com.allianz.sd.core.service.bean.TimeBase;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.allianz.sd.core.api.model.ActualGetResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiParam;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-06-11T02:19:38.666Z")

@RestController
public class ActualApiController implements ActualApi {

    private static final Logger logger = LoggerFactory.getLogger(ActualApiController.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AgentTableSyncService agentTableSyncService;

    @Autowired
    private DateService dateService;

    //agentID 從URL 傳過來
    @RequestMapping(value="/Actual/{agentID}",method = RequestMethod.GET)
    @ResponseBody
    @Validated
    public ActualGetResponse pullActualByAgent(@ApiParam(value = "Bearer oauth2_access_token" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization,@ApiParam(value = "[RFC 7231 Date/Time Formats](https://tools.ietf.org/html/rfc7231#section-7.1.1.1) - e.g : Sun, 06 Nov 1994 08:49:37 GMT " ,required=true) @RequestHeader(value="X-Date", required=true) String xDate,@ApiParam(value = "Unique request ID provided by the partner for logging and tracing transactions." ,required=true) @RequestHeader(value="X-Request-ID", required=true) String xRequestID,@ApiParam(value = "the body content type" ,required=true) @RequestHeader(value="Content-Type", required=true) String contentType,@ApiParam(value = "agentID",required=true) @PathVariable("agentID") String agentID,@ApiParam(value = "The identifier of the partner organization" ) @RequestHeader(value="X-Organization", required=false) String xOrganization,@ApiParam(value = "The identifier of the partner sub-organization" ) @RequestHeader(value="X-Organization-Branch", required=false) String xOrganizationBranch,@ApiParam(value = "The identifier of the requester of the resource. The agents ID" ) @RequestHeader(value="X-Organization-User", required=false) BigDecimal xOrganizationUser,@ApiParam(value = "The version of the resource" ) @RequestHeader(value="X-API-Version", required=false) String xAPIVersion) {
        String accept = request.getHeader("Content-Type");
        if (accept != null && accept.contains("application/json")) {

            try{
                return getActualResponse(agentID,null);
            }
            catch(SnDException e) {
                logger.error("pullActual fail!!["+agentID+"]",e);
                throw e;
            }catch(Exception e) {
                logger.error("pullActual fail!!["+agentID+"]",e);
                throw new SystemError(e);
            }

        }
        else throw new NotFoundContentType();
    }

    //agentID 從Tooken取
    @RequestMapping(value="/Actual",method = RequestMethod.GET)
    @ResponseBody
    @Validated
    public ActualGetResponse pullActual(@ApiParam(value = "Bearer oauth2_access_token" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization,@ApiParam(value = "[RFC 7231 Date/Time Formats](https://tools.ietf.org/html/rfc7231#section-7.1.1.1) - e.g : Sun, 06 Nov 1994 08:49:37 GMT " ,required=true) @RequestHeader(value="X-Date", required=true) String xDate,@ApiParam(value = "Unique request ID provided by the partner for logging and tracing transactions." ,required=true) @RequestHeader(value="X-Request-ID", required=true) String xRequestID,@ApiParam(value = "the body content type" ,required=true) @RequestHeader(value="Content-Type", required=true) String contentType,@NotNull @ApiParam(value = "Device last updatetime e.g. 2018-02-27T00:00:00+0800", required = true) @Valid @RequestParam(value = "lastUpdateTime", required = true) String lastUpdateTime,@ApiParam(value = "The identifier of the partner organization" ) @RequestHeader(value="X-Organization", required=false) String xOrganization,@ApiParam(value = "The identifier of the partner sub-organization" ) @RequestHeader(value="X-Organization-Branch", required=false) String xOrganizationBranch,@ApiParam(value = "The identifier of the requester of the resource. The agents ID" ) @RequestHeader(value="X-Organization-User", required=false) BigDecimal xOrganizationUser,@ApiParam(value = "The version of the resource" ) @RequestHeader(value="X-API-Version", required=false) String xAPIVersion) {
        String accept = request.getHeader("Content-Type");
        if (accept != null && accept.contains("application/json")) {
            //從tooken取得agentID
            String agentID = tokenService.getAgentIdFromToken(authorization);

            try{
                return getActualResponse(agentID,lastUpdateTime);
            }
            catch(SnDException e) {
                logger.error("pullActual fail!!["+agentID+"]",e);
                throw e;
            }catch(Exception e) {
                logger.error("pullActual fail!!["+agentID+"]",e);
                throw new SystemError(e);
            }
        }
        else throw new NotFoundContentType();

    }

    private ActualGetResponse getActualResponse(String agentID,String lastUpdateTime) throws Exception{
        ActualGetResponse response = new ActualGetResponse();

        List<Actual> actuals = new ArrayList<>();
        Date lastUpdateDate = pullData(agentID,lastUpdateTime,actuals);

        response.setLastUpdateTime(dateService.toDateTimeFormatString(lastUpdateDate));
        response.setActual(actuals);

        return response;
    }

    private Date pullData(String agentID,String lastUpdateTime,List<Actual> actuals) throws Exception{
        //realtime fetch data
        if(StringUtils.isEmpty(lastUpdateTime)) {
            AgentTableDataSync agentTableDataSync = agentTableSyncService.getAgentTableDatasync(DataCategory.ACTUAL);
            agentTableDataSync.pullData(agentID,actuals);
            return dateService.getTodayDate();
        }
        //catch data
        else return agentTableSyncService.pull(DataCategory.ACTUAL, actuals, agentID, lastUpdateTime);
    }
}
