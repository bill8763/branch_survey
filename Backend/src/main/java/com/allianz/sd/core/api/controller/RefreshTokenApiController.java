package com.allianz.sd.core.api.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.allianz.sd.core.exception.SnDException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.allianz.sd.core.api.model.LoginResponse;
import com.allianz.sd.core.exception.NotFoundAgentYearDataException;
import com.allianz.sd.core.exception.NotFoundContentType;
import com.allianz.sd.core.exception.SystemError;
import com.allianz.sd.core.jpa.model.AgentLoginToken;
import com.allianz.sd.core.jpa.model.AgentLoginTokenIdentity;
import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.jpa.model.AgentYearDataIdentity;
import com.allianz.sd.core.jpa.repository.AgentLoginTokenRepository;
import com.allianz.sd.core.jpa.repository.AgentYearDataRepository;
import com.allianz.sd.core.service.DateService;
import com.allianz.sd.core.service.OrganizationService;
import com.allianz.sd.core.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiParam;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-06T03:16:57.779Z")

@RestController
public class RefreshTokenApiController implements RefreshTokenApi {

    private static final Logger logger = LoggerFactory.getLogger(RefreshTokenApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    
    private final TokenService tokenService;
    
    private final AgentYearDataRepository agentYearDataRepository;
    
    private final DateService dateService;
    
    private final OrganizationService organizationService;
    
    private final AgentLoginTokenRepository agentLoginTokenRepository;

    @org.springframework.beans.factory.annotation.Autowired
    public RefreshTokenApiController(OrganizationService organizationService,DateService dateService,
    			AgentYearDataRepository agentYearDataRepository,TokenService tokenService,
    			ObjectMapper objectMapper, HttpServletRequest request,
    			AgentLoginTokenRepository agentLoginTokenRepository) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.tokenService = tokenService;
        this.agentYearDataRepository = agentYearDataRepository;
        this.organizationService = organizationService;
        this.dateService = dateService;
        this.agentLoginTokenRepository = agentLoginTokenRepository;
    }
    
    
    @RequestMapping(value="/refreshToken",method=RequestMethod.POST)
    @ResponseBody
    @Validated
    public LoginResponse refreshToken(@ApiParam(value = "Bearer oauth2_access_token" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization,@ApiParam(value = "[RFC 7231 Date/Time Formats](https://tools.ietf.org/html/rfc7231#section-7.1.1.1) - e.g : Sun, 06 Nov 1994 08:49:37 GMT " ,required=true) @RequestHeader(value="X-Date", required=true) String xDate,@ApiParam(value = "Unique request ID provided by the partner for logging and tracing transactions." ,required=true) @RequestHeader(value="X-Request-ID", required=true) String xRequestID,@ApiParam(value = "the body content type" ,required=true) @RequestHeader(value="Content-Type", required=true) String contentType,@ApiParam(value = "The identifier of the partner organization" ) @RequestHeader(value="X-Organization", required=false) String xOrganization,@ApiParam(value = "The identifier of the partner sub-organization" ) @RequestHeader(value="X-Organization-Branch", required=false) String xOrganizationBranch,@ApiParam(value = "The version of the resource" ) @RequestHeader(value="X-API-Version", required=false) String xAPIVersion) {
        String accept = request.getHeader("Content-Type");
        if (accept != null && accept.contains("application/json")) {
            try {
            	
            	String agentID = tokenService.getAgentIdFromToken(authorization);
            	String organizationalUnit = organizationService.getOrganizationalUnit();
            	int dataYear = dateService.getCurrentYear();
            	
            	//get this agent Latest Data
            	AgentLoginToken agentLoginToken = agentLoginTokenRepository.getTheLatestData(agentID);
            	String token = agentLoginToken.getToken();
            	
            	AgentYearDataIdentity identity = new AgentYearDataIdentity();
            	identity.setOrganizationalUnit(organizationalUnit);
            	identity.setAgentID(agentID);
            	identity.setDataYear(dataYear);
            	
            	Optional<AgentYearData> agentYearDataOptional = agentYearDataRepository.findById(identity);
            	AgentYearData agentYearData = agentYearDataOptional.orElse(null);
            	if(agentYearData != null) {
            		  //create new jwt token
                    Map<String, Object> claims = new HashMap<>();
                    claims.put("AgentID",agentID);
                    claims.put("AgentName",agentYearData.getAgentData().getAgentName());
                    claims.put("Gender",agentYearData.getAgentData().getGender());
                    claims.put("AppUseMode",agentYearData.getAppUseMode());
                    claims.put("OfficeName",agentYearData.getAgentData().getOfficeName());
                    claims.put("CurrentJobSeniorityMonth",agentYearData.getCurrentJobSeniorityMonth());
                    claims.put("JobGrade",agentYearData.getJobGrade() );
                    claims.put("ReferenceToken",token);
                    
                    //重新取得jwtToken
                    String jwtToken = tokenService.generateToken(claims);
                    
                    //set JwtToken
                    agentLoginToken.setJwtToken(jwtToken);
                    
                    agentLoginTokenRepository.save(agentLoginToken);
                    
                    LoginResponse response = new LoginResponse();
                    response.setSuccess(true);
                    response.setToken(jwtToken);
                    
                    return response;

            	}else {
            		throw new NotFoundAgentYearDataException(organizationalUnit,agentID,dataYear);
            	}
            } catch(SnDException e) {
                logger.error("RefreshTokenApiController Fail!!", e);
                throw e;
            } catch (Exception e) {
                logger.error("RefreshTokenApiController Fail!!", e);
                throw new SystemError(e);
            }
        }

        else throw new NotFoundContentType();
    }

}
