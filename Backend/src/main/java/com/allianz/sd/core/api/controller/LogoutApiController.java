package com.allianz.sd.core.api.controller;

import com.allianz.sd.core.api.model.LogoutResponse;
import com.allianz.sd.core.exception.NotFoundContentType;
import com.allianz.sd.core.exception.SystemError;
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

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-03-05T03:18:57.627Z")

@RestController
public class LogoutApiController implements LogoutApi {

    private static final Logger log = LoggerFactory.getLogger(LogoutApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public LogoutApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @ResponseBody
    @Validated
    @Transactional
    public LogoutResponse logout(@ApiParam(value = "[RFC 7231 Date/Time Formats](https://tools.ietf.org/html/rfc7231#section-7.1.1.1) - e.g : Sun, 06 Nov 1994 08:49:37 GMT " ,required=true) @RequestHeader(value="X-Date", required=true) String xDate,@ApiParam(value = "Unique request ID provided by the partner for logging and tracing transactions." ,required=true) @RequestHeader(value="X-Request-ID", required=true) String xRequestID,@ApiParam(value = "the body content type" ,required=true) @RequestHeader(value="Content-Type", required=true) String contentType,@ApiParam(value = "The identifier of the partner organization" ,required=true) @RequestHeader(value="X-Organization", required=true) String xOrganization,@ApiParam(value = "The identifier of the partner sub-organization" ,required=true) @RequestHeader(value="X-Organization-Branch", required=true) String xOrganizationBranch,@ApiParam(value = "The version of the resource" ,required=true) @RequestHeader(value="X-API-Version", required=true) String xAPIVersion,@ApiParam(value = "" ,required=true )  @Valid @RequestBody String token) {
        String accept = request.getHeader("Content-Type");
        if (accept != null && accept.contains("application/json")) {
            try {
                return objectMapper.readValue("{  \"success\" : true}", LogoutResponse.class);
            } catch (IOException e) {
                log.error("logout fail!!", e);
                throw new SystemError(e);
            }
        }
        else throw new NotFoundContentType();
    }

}
