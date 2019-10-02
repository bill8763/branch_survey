package com.allianz.sd.core.api.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.allianz.sd.core.api.model.Message;
import com.allianz.sd.core.api.model.MessageGetResponse;
import com.allianz.sd.core.api.model.MessagePutResponse;
import com.allianz.sd.core.exception.NotFoundContentType;
import com.allianz.sd.core.exception.SnDException;
import com.allianz.sd.core.exception.SystemError;
import com.allianz.sd.core.jpa.bean.DataCategory;
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
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-04-18T07:36:06.067Z")

@RestController
@RequestMapping("/message")
public class MessageApiController implements MessageApi {

    private static final Logger log = LoggerFactory.getLogger(MessageApiController.class);

    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    private final DataSyncService dataSyncService;
    private final TokenService tokenService;
    private final DateService dateService;

    @org.springframework.beans.factory.annotation.Autowired
    public MessageApiController(ObjectMapper objectMapper, HttpServletRequest request,DataSyncService dataSyncService,TokenService tokenService,DateService dateService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.dataSyncService = dataSyncService;
        this.tokenService = tokenService;
        this.dateService = dateService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    @Validated
    public MessageGetResponse pullMessage(@ApiParam(value = "Bearer oauth2_access_token" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization,@ApiParam(value = "[RFC 7231 Date/Time Formats](https://tools.ietf.org/html/rfc7231#section-7.1.1.1) - e.g : Sun, 06 Nov 1994 08:49:37 GMT " ,required=true) @RequestHeader(value="X-Date", required=true) String xDate,@ApiParam(value = "Unique request ID provided by the partner for logging and tracing transactions." ,required=true) @RequestHeader(value="X-Request-ID", required=true) String xRequestID,@ApiParam(value = "the body content type" ,required=true) @RequestHeader(value="Content-Type", required=true) String contentType,@NotNull @ApiParam(value = "Device last updatetime e.g. 2018-02-27T00:00:00+08:00", required = true) @Valid @RequestParam(value = "lastUpdateTime", required = true) String lastUpdateTime,@ApiParam(value = "The identifier of the partner organization" ) @RequestHeader(value="X-Organization", required=false) String xOrganization,@ApiParam(value = "The identifier of the partner sub-organization" ) @RequestHeader(value="X-Organization-Branch", required=false) String xOrganizationBranch,@ApiParam(value = "The identifier of the requester of the resource. The agents ID" ) @RequestHeader(value="X-Organization-User", required=false) BigDecimal xOrganizationUser,@ApiParam(value = "The version of the resource" ) @RequestHeader(value="X-API-Version", required=false) String xAPIVersion) {
        String accept = request.getHeader("Content-Type");
        if (accept != null && accept.contains("application/json")) {
            String agentID = tokenService.getAgentIdFromToken(authorization);

            try {

                List<Message> result = new ArrayList<Message>();
                List<BigDecimal> deleteList = new ArrayList<>();

                Date pullDate = dataSyncService.pull(DataCategory.MESSAGE,result,deleteList,agentID,lastUpdateTime);

                MessageGetResponse messageGetResponse = new MessageGetResponse();
                messageGetResponse.setDeletedMessageIds(deleteList);
                messageGetResponse.setMessageInfos(result);
                messageGetResponse.setSynchStatus(pullDate != null);
                messageGetResponse.setUpdateRecordsNumber(result.size());
                messageGetResponse.setLastUpdateTime(dateService.toDateTimeFormatString(pullDate));


                return messageGetResponse;
            } catch(SnDException e) {
                log.error("MessageAPI Pull fail!!["+agentID+"]", e);
                throw e;
            } catch (Exception e) {
                log.error("MessageAPI Pull fail!!["+agentID+"]", e);
                throw new SystemError(e);
            }
        }
        else throw new NotFoundContentType();
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    @Validated
    @Transactional
    public MessagePutResponse pushMessage(@ApiParam(value = "Bearer oauth2_access_token" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization,@ApiParam(value = "[RFC 7231 Date/Time Formats](https://tools.ietf.org/html/rfc7231#section-7.1.1.1) - e.g : Sun, 06 Nov 1994 08:49:37 GMT " ,required=true) @RequestHeader(value="X-Date", required=true) String xDate,@ApiParam(value = "Unique request ID provided by the partner for logging and tracing transactions." ,required=true) @RequestHeader(value="X-Request-ID", required=true) String xRequestID,@ApiParam(value = "the body content type" ,required=true) @RequestHeader(value="Content-Type", required=true) String contentType,@ApiParam(value = "" ,required=true )  @Valid @RequestBody List<BigDecimal> readMessageInfos,@ApiParam(value = "The identifier of the partner organization" ) @RequestHeader(value="X-Organization", required=false) String xOrganization,@ApiParam(value = "The identifier of the partner sub-organization" ) @RequestHeader(value="X-Organization-Branch", required=false) String xOrganizationBranch,@ApiParam(value = "The identifier of the requester of the resource. The agents ID" ) @RequestHeader(value="X-Organization-User", required=false) BigDecimal xOrganizationUser,@ApiParam(value = "The version of the resource" ) @RequestHeader(value="X-API-Version", required=false) String xAPIVersion) {
        String accept = request.getHeader("Content-Type");
        if (accept != null && accept.contains("application/json")) {

            String agentID = tokenService.getAgentIdFromToken(authorization);

            try {

                int updateRecords = dataSyncService.push(DataCategory.MESSAGE,agentID,readMessageInfos);

                MessagePutResponse messagePutResponse = new MessagePutResponse();
                messagePutResponse.setUpdateRecordsNumber(updateRecords);
                messagePutResponse.setSynchStatus(updateRecords != -1);

                return messagePutResponse;
            } catch(SnDException e) {
                log.error("MessageAPI Pull fail!!["+agentID+"]", e);
                throw e;
            } catch (Exception e) {
                log.error("pushMessage fail!!["+agentID+"]", e);
                throw new SystemError(e);
            }
        }
        else throw new NotFoundContentType();
    }

}
