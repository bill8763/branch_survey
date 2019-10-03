package com.allianz.sd.core.api.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.allianz.sd.core.api.model.ModelConfiguration;
import com.allianz.sd.core.datasync.AgentTableDataSync;
import com.allianz.sd.core.exception.NotFoundContentType;
import com.allianz.sd.core.exception.SnDException;
import com.allianz.sd.core.exception.SystemError;
import com.allianz.sd.core.jpa.bean.DataCategory;
import com.allianz.sd.core.service.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.allianz.sd.core.api.model.ConfigurationGetResponse;
import com.allianz.sd.core.jpa.repository.AgentDataRepository;
import com.allianz.sd.core.jpa.repository.AgentYearDataRepository;
import com.allianz.sd.core.jpa.repository.PerformanceTableMonthRepository;
import com.allianz.sd.core.jpa.repository.SysYearDataRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiParam;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-06-03T07:26:06.626Z")

@RestController
@RequestMapping("/Configuration")
public class ConfigurationApiController implements ConfigurationApi {

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationApiController.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private DateService dateService;

    @Autowired
    private GoalService goalService;

    @Autowired
    private AgentTableSyncService agentTableSyncService;

    @Autowired
    private OrganizationService organizationalUnit;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    @Validated
    public ConfigurationGetResponse pullConfiguration(@ApiParam(value = "Bearer oauth2_access_token" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization, @ApiParam(value = "[RFC 7231 Date/Time Formats](https://tools.ietf.org/html/rfc7231#section-7.1.1.1) - e.g : Sun, 06 Nov 1994 08:49:37 GMT " ,required=true) @RequestHeader(value="X-Date", required=true) String xDate, @ApiParam(value = "Unique request ID provided by the partner for logging and tracing transactions." ,required=true) @RequestHeader(value="X-Request-ID", required=true) String xRequestID, @ApiParam(value = "the body content type" ,required=true) @RequestHeader(value="Content-Type", required=true) String contentType, @NotNull @ApiParam(value = "Device last updatetime e.g. 2018-02-27T00:00:00+0800", required = true) @Valid @RequestParam(value = "lastUpdateTime", required = true) String lastUpdateTime, @ApiParam(value = "The identifier of the partner organization" ) @RequestHeader(value="X-Organization", required=false) String xOrganization, @ApiParam(value = "The identifier of the partner sub-organization" ) @RequestHeader(value="X-Organization-Branch", required=false) String xOrganizationBranch, @ApiParam(value = "The identifier of the requester of the resource. The agents ID" ) @RequestHeader(value="X-Organization-User", required=false) BigDecimal xOrganizationUser, @ApiParam(value = "The version of the resource" ) @RequestHeader(value="X-API-Version", required=false) String xAPIVersion) {
        String accept = request.getHeader("Content-Type");
        if (accept != null && accept.contains("application/json")) {
            String agentID = tokenService.getAgentIdFromToken(authorization);

            try{
                return getConfigurationResponse(agentID, lastUpdateTime);
            }catch(SnDException e) {
                logger.error("pullConfiguration fail!!["+agentID+"]",e);
                throw e;
            }catch(Exception e) {
                logger.error("pullConfiguration fail!!["+agentID+"]",e);
                throw new SystemError(e);
            }
        } else throw new NotFoundContentType();

    }

    @RequestMapping(value="/{agentID}",method = RequestMethod.GET)
    @ResponseBody
    @Validated
    public ConfigurationGetResponse pullConfigurationByAgentID(@ApiParam(value = "Bearer oauth2_access_token" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization,@ApiParam(value = "[RFC 7231 Date/Time Formats](https://tools.ietf.org/html/rfc7231#section-7.1.1.1) - e.g : Sun, 06 Nov 1994 08:49:37 GMT " ,required=true) @RequestHeader(value="X-Date", required=true) String xDate,@ApiParam(value = "Unique request ID provided by the partner for logging and tracing transactions." ,required=true) @RequestHeader(value="X-Request-ID", required=true) String xRequestID,@ApiParam(value = "the body content type" ,required=true) @RequestHeader(value="Content-Type", required=true) String contentType,@ApiParam(value = "Device last updatetime e.g. 2018-02-27T00:00:00+0800",required=true) @PathVariable("agentID") String agentID,@ApiParam(value = "The identifier of the partner organization" ) @RequestHeader(value="X-Organization", required=false) String xOrganization,@ApiParam(value = "The identifier of the partner sub-organization" ) @RequestHeader(value="X-Organization-Branch", required=false) String xOrganizationBranch,@ApiParam(value = "The identifier of the requester of the resource. The agents ID" ) @RequestHeader(value="X-Organization-User", required=false) BigDecimal xOrganizationUser,@ApiParam(value = "The version of the resource" ) @RequestHeader(value="X-API-Version", required=false) String xAPIVersion) {
        String accept = request.getHeader("Content-Type");
        if (accept != null && accept.contains("application/json")) {

            try{
                return getConfigurationResponse(agentID, null);
            }catch(SnDException e) {
                logger.error("pullConfiguration fail!!["+agentID+"]",e);
                throw e;
            }catch(Exception e) {
                logger.error("pullConfiguration fail!!["+agentID+"]",e);
                throw new SystemError(e);
            }
        } else throw new NotFoundContentType();

    }

    private ConfigurationGetResponse getConfigurationResponse(String agentID,String lastUpdateTime) throws Exception{
        ConfigurationGetResponse response = new ConfigurationGetResponse();

        List<ModelConfiguration> configurations = new ArrayList<>();
        Date lastUpdateDate = pullData(agentID,lastUpdateTime,configurations);

        response.setLastUpdateTime(dateService.toDateTimeFormatString(lastUpdateDate));
        response.setConfigurations(configurations);

        //判斷是否為第一次使用
        if(configurations.size() != 0) {
            boolean firstUseApp = goalService.firstUseApp(organizationalUnit.getOrganizationalUnit(), agentID);
            response.setFirstUseAPP(firstUseApp);
        }

        return response;
    }

    private Date pullData(String agentID,String lastUpdateTime,List<ModelConfiguration> configurations) throws Exception{
        //realtime fetch data
        if(StringUtils.isEmpty(lastUpdateTime)) {
            AgentTableDataSync agentTableDataSync = agentTableSyncService.getAgentTableDatasync(DataCategory.CONFIGURATION);
            agentTableDataSync.pullData(agentID,configurations);
            return dateService.getTodayDate();
        }
        //catch data
        else return agentTableSyncService.pull(DataCategory.CONFIGURATION, configurations, agentID, lastUpdateTime);
    }

}
