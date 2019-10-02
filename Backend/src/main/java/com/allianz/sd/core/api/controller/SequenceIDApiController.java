package com.allianz.sd.core.api.controller;

import java.math.BigDecimal;

import com.allianz.sd.core.api.model.SequenceIDGetResponse;
import com.allianz.sd.core.exception.NotFoundContentType;
import com.allianz.sd.core.exception.SnDException;
import com.allianz.sd.core.exception.SystemError;
import com.allianz.sd.core.service.SequenceIDService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-04-18T05:16:13.196Z")

@Controller
public class SequenceIDApiController implements SequenceIDApi {

    private static final Logger log = LoggerFactory.getLogger(SequenceIDApiController.class);

    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    private final SequenceIDService sequenceIDService;


    @org.springframework.beans.factory.annotation.Autowired
    public SequenceIDApiController(ObjectMapper objectMapper, HttpServletRequest request,SequenceIDService sequenceIDService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.sequenceIDService = sequenceIDService;
    }

    @ResponseBody
    @Validated
    public SequenceIDGetResponse getSequenceID(@ApiParam(value = "Bearer oauth2_access_token" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization,@ApiParam(value = "[RFC 7231 Date/Time Formats](https://tools.ietf.org/html/rfc7231#section-7.1.1.1) - e.g : Sun, 06 Nov 1994 08:49:37 GMT " ,required=true) @RequestHeader(value="X-Date", required=true) String xDate,@ApiParam(value = "Unique request ID provided by the partner for logging and tracing transactions." ,required=true) @RequestHeader(value="X-Request-ID", required=true) String xRequestID,@ApiParam(value = "the body content type" ,required=true) @RequestHeader(value="Content-Type", required=true) String contentType,@NotNull @ApiParam(value = "The type of ID", required = true) @Valid @RequestParam(value = "category", required = true) String category,@NotNull @ApiParam(value = "Need how many the ID", required = true) @Valid @RequestParam(value = "idNums", required = true) BigDecimal idNums,@ApiParam(value = "The identifier of the partner organization" ) @RequestHeader(value="X-Organization", required=false) String xOrganization,@ApiParam(value = "The identifier of the partner sub-organization" ) @RequestHeader(value="X-Organization-Branch", required=false) String xOrganizationBranch,@ApiParam(value = "The identifier of the requester of the resource. The agents ID" ) @RequestHeader(value="X-Organization-User", required=false) BigDecimal xOrganizationUser,@ApiParam(value = "The version of the resource" ) @RequestHeader(value="X-API-Version", required=false) String xAPIVersion) {
        String accept = request.getHeader("Content-Type");
        if (accept != null && accept.contains("application/json")) {
            try {
                List<BigDecimal> list = sequenceIDService.getId(category,idNums);

                SequenceIDGetResponse sequenceIDGetResponse = new SequenceIDGetResponse();
                sequenceIDGetResponse.setIds(list);
                return sequenceIDGetResponse;
            } catch(SnDException e) {
                log.error("Get SequenceID fail!!category["+category+"]", e);
                throw e;
            } catch (Exception e) {
                log.error("Get SequenceID fail!!category["+category+"]", e);
                throw new SystemError(e);
            }
        }
        else throw new NotFoundContentType();
    }

}
