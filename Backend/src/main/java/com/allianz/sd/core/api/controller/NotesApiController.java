package com.allianz.sd.core.api.controller;

import java.math.BigDecimal;

import com.allianz.sd.core.api.model.Note;
import com.allianz.sd.core.api.model.NotePutResponse;
import com.allianz.sd.core.api.model.NotesGetResponse;
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
@RequestMapping("/notes")
public class NotesApiController implements NotesApi {

    private static final Logger log = LoggerFactory.getLogger(NotesApiController.class);

    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    private final DataSyncService dataSyncService;
    private final TokenService tokenService;
    private final DateService dateService;

    @org.springframework.beans.factory.annotation.Autowired
    public NotesApiController(ObjectMapper objectMapper, HttpServletRequest request,DataSyncService dataSyncService,TokenService tokenService,DateService dateService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.dataSyncService = dataSyncService;
        this.tokenService = tokenService;
        this.dateService = dateService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    @Validated
    public NotesGetResponse getNotesByLastupdateDateTime(@ApiParam(value = "Bearer oauth2_access_token" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization, @ApiParam(value = "[RFC 7231 Date/Time Formats](https://tools.ietf.org/html/rfc7231#section-7.1.1.1) - e.g : Sun, 06 Nov 1994 08:49:37 GMT " ,required=true) @RequestHeader(value="X-Date", required=true) String xDate, @ApiParam(value = "Unique request ID provided by the partner for logging and tracing transactions." ,required=true) @RequestHeader(value="X-Request-ID", required=true) String xRequestID, @ApiParam(value = "the body content type" ,required=true) @RequestHeader(value="Content-Type", required=true) String contentType, @NotNull @ApiParam(value = "Device last updatetime e.g. 2018-02-27T00:00:00+08:00", required = true) @Valid @RequestParam(value = "lastUpdateTime", required = true) String lastUpdateTime, @ApiParam(value = "The identifier of the partner organization" ) @RequestHeader(value="X-Organization", required=false) String xOrganization, @ApiParam(value = "The identifier of the partner sub-organization" ) @RequestHeader(value="X-Organization-Branch", required=false) String xOrganizationBranch, @ApiParam(value = "The identifier of the requester of the resource. The agents ID" ) @RequestHeader(value="X-Organization-User", required=false) BigDecimal xOrganizationUser, @ApiParam(value = "The version of the resource" ) @RequestHeader(value="X-API-Version", required=false) String xAPIVersion) {
        String accept = request.getHeader("Content-Type");
        if (accept != null && accept.contains("application/json")) {

            String agentID = tokenService.getAgentIdFromToken(authorization);

            try {
                List<Note> result = new ArrayList<Note>();
                List<BigDecimal> deleteList = new ArrayList<>();

                Date pullDate = dataSyncService.pull(DataCategory.CUSTOMERCONTACT,result,deleteList,agentID,lastUpdateTime);
                NotesGetResponse notesGetResponse = new NotesGetResponse();
                notesGetResponse.setNotes(result);
                notesGetResponse.setDeletedNoteIds(deleteList);
                notesGetResponse.setLastUpdateTime(dateService.toDateTimeFormatString(pullDate));

                return notesGetResponse;
            } catch(SnDException e) {
                log.error("NotesAPI Sync Excetion["+agentID+"]", e);
                throw e;
            } catch (Exception e) {
                log.error("NotesAPI Sync Excetion["+agentID+"]", e);
                throw new SystemError(e);
            }
        }
        else throw new NotFoundContentType();
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    @Validated
    @Transactional
    public NotePutResponse noteSynchronization(@ApiParam(value = "Bearer oauth2_access_token" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization, @ApiParam(value = "[RFC 7231 Date/Time Formats](https://tools.ietf.org/html/rfc7231#section-7.1.1.1) - e.g : Sun, 06 Nov 1994 08:49:37 GMT " ,required=true) @RequestHeader(value="X-Date", required=true) String xDate, @ApiParam(value = "Unique request ID provided by the partner for logging and tracing transactions." ,required=true) @RequestHeader(value="X-Request-ID", required=true) String xRequestID, @ApiParam(value = "the body content type" ,required=true) @RequestHeader(value="Content-Type", required=true) String contentType, @ApiParam(value = "" ,required=true )  @Valid @RequestBody List<Note> notes, @ApiParam(value = "The identifier of the partner organization" ) @RequestHeader(value="X-Organization", required=false) String xOrganization, @ApiParam(value = "The identifier of the partner sub-organization" ) @RequestHeader(value="X-Organization-Branch", required=false) String xOrganizationBranch, @ApiParam(value = "The identifier of the requester of the resource. The agents ID" ) @RequestHeader(value="X-Organization-User", required=false) BigDecimal xOrganizationUser, @ApiParam(value = "The version of the resource" ) @RequestHeader(value="X-API-Version", required=false) String xAPIVersion) {
        String accept = request.getHeader("Content-Type");
        if (accept != null && accept.contains("application/json")) {

            String agentID = tokenService.getAgentIdFromToken(authorization);

            try {

                int updateRecords = dataSyncService.push(DataCategory.CUSTOMERCONTACT,agentID,notes);
                NotePutResponse notePutResponse = new NotePutResponse();
                notePutResponse.setSynchStatus(updateRecords != -1);
                notePutResponse.setUpdateRecordsNumber(updateRecords);

                return notePutResponse;
            } catch(SnDException e) {
                log.error("NotesAPI Sync Excetion["+agentID+"]", e);
                throw e;
            } catch (Exception e) {
                log.error("NotesAPI Sync Excetion["+agentID+"]", e);
                throw new SystemError(e);
            }
        }
        else throw new NotFoundContentType();
    }

}
