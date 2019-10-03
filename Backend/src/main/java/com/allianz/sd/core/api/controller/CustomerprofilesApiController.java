package com.allianz.sd.core.api.controller;

import java.math.BigDecimal;

import com.allianz.sd.core.api.model.CustomerProfileGetResponse;
import com.allianz.sd.core.api.model.CustomerProfilePutResponse;
import com.allianz.sd.core.api.model.Message;
import com.allianz.sd.core.api.model.Person;
import com.allianz.sd.core.exception.NotFoundContentType;
import com.allianz.sd.core.exception.SnDException;
import com.allianz.sd.core.exception.SystemError;
import com.allianz.sd.core.jpa.bean.DataCategory;
import com.allianz.sd.core.service.ActivityService;
import com.allianz.sd.core.service.DataSyncService;
import com.allianz.sd.core.service.DateService;
import com.allianz.sd.core.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-04-27T12:43:26.347Z")

@RestController
@RequestMapping("/customerprofiles")
public class CustomerprofilesApiController implements CustomerprofilesApi {

    private static final Logger logger = LoggerFactory.getLogger(CustomerprofilesApiController.class);

    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    private final DataSyncService dataSyncService;
    private final TokenService tokenService;
    private final DateService dateService;
    private final ActivityService activityService;

    @org.springframework.beans.factory.annotation.Autowired
    public CustomerprofilesApiController(ObjectMapper objectMapper, HttpServletRequest request,DataSyncService dataSyncService,TokenService tokenService,DateService dateService,ActivityService activityService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.dataSyncService = dataSyncService;
        this.tokenService = tokenService;
        this.dateService = dateService;
        this.activityService = activityService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    @Validated
    public CustomerProfileGetResponse pullCustomer(@ApiParam(value = "Bearer oauth2_access_token" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization, @ApiParam(value = "[RFC 7231 Date/Time Formats](https://tools.ietf.org/html/rfc7231#section-7.1.1.1) - e.g : Sun, 06 Nov 1994 08:49:37 GMT " ,required=true) @RequestHeader(value="X-Date", required=true) String xDate, @ApiParam(value = "Unique request ID provided by the partner for logging and tracing transactions." ,required=true) @RequestHeader(value="X-Request-ID", required=true) String xRequestID, @ApiParam(value = "the body content type" ,required=true) @RequestHeader(value="Content-Type", required=true) String contentType, @NotNull @ApiParam(value = "Device last updatetime e.g. 2018-02-27T00:00:00+08:00", required = true) @Valid @RequestParam(value = "lastUpdateTime", required = true) String lastUpdateTime, @ApiParam(value = "The identifier of the partner organization" ) @RequestHeader(value="X-Organization", required=false) String xOrganization, @ApiParam(value = "The identifier of the partner sub-organization" ) @RequestHeader(value="X-Organization-Branch", required=false) String xOrganizationBranch, @ApiParam(value = "The identifier of the requester of the resource. The agents ID" ) @RequestHeader(value="X-Organization-User", required=false) BigDecimal xOrganizationUser, @ApiParam(value = "The version of the resource" ) @RequestHeader(value="X-API-Version", required=false) String xAPIVersion) {
        String accept = request.getHeader("Content-Type");
        if (accept != null && accept.contains("application/json")) {

            String agentID = tokenService.getAgentIdFromToken(authorization);

            try {
                List<Person> result = new ArrayList<Person>();
                List<BigDecimal> deleteList = new ArrayList<>();

                Date pullDate = dataSyncService.pull(DataCategory.CUSTOMER,result,deleteList,agentID,lastUpdateTime);
                CustomerProfileGetResponse customerProfileGetResponse = new CustomerProfileGetResponse();
                customerProfileGetResponse.setCustomerInfos(result);
                customerProfileGetResponse.setDeletedPersonIds(deleteList);
                customerProfileGetResponse.setLastUpdateTime(dateService.toDateTimeFormatString(pullDate));

                return customerProfileGetResponse;
            } catch(SnDException e) {
                logger.error("CustomerProfileAPI Sync fail!!["+agentID+"]", e);
                throw e;
            } catch (Exception e) {
                logger.error("CustomerProfileAPI Sync fail!!["+agentID+"]", e);
                throw new SystemError(e);
            }
        }
        else throw new NotFoundContentType();
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    @Validated
    @Transactional
    public CustomerProfilePutResponse pushCustomer(@ApiParam(value = "Bearer oauth2_access_token" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization, @ApiParam(value = "[RFC 7231 Date/Time Formats](https://tools.ietf.org/html/rfc7231#section-7.1.1.1) - e.g : Sun, 06 Nov 1994 08:49:37 GMT " ,required=true) @RequestHeader(value="X-Date", required=true) String xDate, @ApiParam(value = "Unique request ID provided by the partner for logging and tracing transactions." ,required=true) @RequestHeader(value="X-Request-ID", required=true) String xRequestID, @ApiParam(value = "the body content type" ,required=true) @RequestHeader(value="Content-Type", required=true) String contentType, @ApiParam(value = "" ,required=true )  @Valid @RequestBody List<Person> customerInfos, @ApiParam(value = "The identifier of the partner organization" ) @RequestHeader(value="X-Organization", required=false) String xOrganization, @ApiParam(value = "The identifier of the partner sub-organization" ) @RequestHeader(value="X-Organization-Branch", required=false) String xOrganizationBranch, @ApiParam(value = "The identifier of the requester of the resource. The agents ID" ) @RequestHeader(value="X-Organization-User", required=false) BigDecimal xOrganizationUser, @ApiParam(value = "The version of the resource" ) @RequestHeader(value="X-API-Version", required=false) String xAPIVersion) {
        String accept = request.getHeader("Content-Type");
        if (accept != null && accept.contains("application/json")) {

            String agentID = tokenService.getAgentIdFromToken(authorization);

            try {

//            	for(Person list : customerInfos) {
//            		logger.debug("Familiarity = " + list.getFamiliarity());
//            		logger.debug("EmailContacts = " + list.getEmailContacts());
//            		logger.debug("AnnualIncomeRange = " + list.getAnnualIncomeRange());
//            		logger.debug("Gender = " + list.getGender());
//            		logger.debug("Marry = " + list.getMarritalStatus());
//            		logger.debug("Phone = " + list.getPhoneChannels());
//            		logger.debug("AgeRange = " + list.getAgeRange());
//            		logger.debug("Addresses = " + list.getAddresses());
//            		logger.debug("NumberOfChildren = " + list.getNumberOfChildren());
//            		logger.debug("Occupation = " + list.getOccupation());
//            	}
                int updateRecords = dataSyncService.push(DataCategory.CUSTOMER,agentID,customerInfos);

                
                CustomerProfilePutResponse customerProfilePutResponse = new CustomerProfilePutResponse();
                customerProfilePutResponse.setUpdateRecordsNumber(updateRecords);
                customerProfilePutResponse.setSynchStatus(updateRecords != -1);

                //check is send message
                activityService.calcPushProgressActivityMessage(agentID,dateService.getTodayDate());

                return customerProfilePutResponse;
            } catch(SnDException e) {
                logger.error("CustomerProfileAPI Sync fail!!["+agentID+"]", e);
                throw e;
            } catch (Exception e) {
                logger.error("CustomerProfileAPI Sync fail!!["+agentID+"]", e);
                throw new SystemError(e);
            }
        }
        else throw new NotFoundContentType();
    }

}
