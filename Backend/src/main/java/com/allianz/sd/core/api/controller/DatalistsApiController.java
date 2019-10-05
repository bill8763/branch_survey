package com.allianz.sd.core.api.controller;

import java.math.BigDecimal;

import com.allianz.sd.core.api.model.*;
import com.allianz.sd.core.exception.NotFoundContentType;
import com.allianz.sd.core.exception.SnDException;
import com.allianz.sd.core.exception.SystemError;
import com.allianz.sd.core.jpa.bean.DataCategory;
import com.allianz.sd.core.jpa.model.Code;
import com.allianz.sd.core.jpa.model.CodeType;
import com.allianz.sd.core.jpa.repository.CodeRepository;
import com.allianz.sd.core.jpa.repository.CodeTypeRepository;
import com.allianz.sd.core.service.DateService;
import com.allianz.sd.core.service.TableUpdateVersionService;
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
import java.util.Date;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-04-27T12:43:26.347Z")

@RestController
public class DatalistsApiController implements DatalistsApi {

    private static final Logger log = LoggerFactory.getLogger(DatalistsApiController.class);

    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    private final CodeRepository codeRepository;
    private final CodeTypeRepository codeTypeRepository;
    private final DateService dateService;
    private final TableUpdateVersionService tableUpdateVersionService;

    @org.springframework.beans.factory.annotation.Autowired
    public DatalistsApiController(ObjectMapper objectMapper, HttpServletRequest request,CodeRepository codeRepository,CodeTypeRepository codeTypeRepository,DateService dateService,TableUpdateVersionService tableUpdateVersionService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.codeRepository = codeRepository;
        this.codeTypeRepository = codeTypeRepository;
        this.dateService = dateService;
        this.tableUpdateVersionService = tableUpdateVersionService;
    }

    @RequestMapping(value = "/datalists/all" , method = RequestMethod.GET)
    @ResponseBody
    @Validated
    public DataListAllResponse datalistsAllGet(@ApiParam(value = "Bearer oauth2_access_token" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization,@ApiParam(value = "[RFC 7231 Date/Time Formats](https://tools.ietf.org/html/rfc7231#section-7.1.1.1) - e.g : Sun, 06 Nov 1994 08:49:37 GMT " ,required=true) @RequestHeader(value="X-Date", required=true) String xDate,@ApiParam(value = "Unique request ID provided by the partner for logging and tracing transactions." ,required=true) @RequestHeader(value="X-Request-ID", required=true) String xRequestID,@ApiParam(value = "the body content type" ,required=true) @RequestHeader(value="Content-Type", required=true) String contentType,@NotNull @ApiParam(value = "Device last updatetime e.g. 2018-02-27T00:00:00+0800", required = true) @Valid @RequestParam(value = "lastUpdateTime", required = true) String lastUpdateTime,@ApiParam(value = "The identifier of the partner organization" ) @RequestHeader(value="X-Organization", required=false) String xOrganization,@ApiParam(value = "The identifier of the partner sub-organization" ) @RequestHeader(value="X-Organization-Branch", required=false) String xOrganizationBranch,@ApiParam(value = "The identifier of the requester of the resource. The agents ID" ) @RequestHeader(value="X-Organization-User", required=false) BigDecimal xOrganizationUser,@ApiParam(value = "The version of the resource" ) @RequestHeader(value="X-API-Version", required=false) String xAPIVersion) {
        String accept = request.getHeader("Content-Type");
        if (accept != null && accept.contains("application/json")) {
            try {

                DataListAllResponse dataListAllResponse = new DataListAllResponse();

                Date appSyncDate = dateService.toDateTimeFormatDate(lastUpdateTime);
                if(tableUpdateVersionService.haveNewVersion(appSyncDate, DataCategory.CODE)) {

                    List<CodeType> codeTypes = this.codeTypeRepository.findAll();
                    for(CodeType codeType : codeTypes) {

                        DataListResponse dataListResponse = new DataListResponse();
                        String typeID = codeType.getCodeTypeIdentity().getTypeID();
                        dataListResponse.setDatalistId(typeID);

                        List<Code> codeList = this.codeRepository.findByIdentityTypeID(typeID);
                        for(Code code : codeList) {
                            dataListResponse.addValuesItem(toDatalistValue(code));
                        }

                        dataListAllResponse.addDatalistItem(dataListResponse);
                    }
                }

                dataListAllResponse.setLastUpdateTime(dateService.toDateTimeFormatString(dateService.getTodayDate()));
                return dataListAllResponse;
            } catch(SnDException e) {
                log.error("datalistsAllGet fail!!", e);
                throw e;
            } catch (Exception e) {
                log.error("datalistsAllGet fail!!", e);
                throw new SystemError(e);
            }
        }
        else throw new NotFoundContentType();
    }

    @RequestMapping(value = "/datalists" , method = RequestMethod.GET)
    @ResponseBody
    @Validated
    public InlineResponse200 datalistsGet(@ApiParam(value = "Bearer oauth2_access_token" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization,@ApiParam(value = "[RFC 7231 Date/Time Formats](https://tools.ietf.org/html/rfc7231#section-7.1.1.1) - e.g : Sun, 06 Nov 1994 08:49:37 GMT " ,required=true) @RequestHeader(value="X-Date", required=true) String xDate,@ApiParam(value = "Unique request ID provided by the partner for logging and tracing transactions." ,required=true) @RequestHeader(value="X-Request-ID", required=true) String xRequestID,@ApiParam(value = "the body content type" ,required=true) @RequestHeader(value="Content-Type", required=true) String contentType,@ApiParam(value = "The identifier of the partner organization" ) @RequestHeader(value="X-Organization", required=false) String xOrganization,@ApiParam(value = "The identifier of the partner sub-organization" ) @RequestHeader(value="X-Organization-Branch", required=false) String xOrganizationBranch,@ApiParam(value = "The identifier of the requester of the resource. The agents ID" ) @RequestHeader(value="X-Organization-User", required=false) BigDecimal xOrganizationUser,@ApiParam(value = "The version of the resource" ) @RequestHeader(value="X-API-Version", required=false) String xAPIVersion) {
        String accept = request.getHeader("Content-Type");
        if (accept != null && accept.contains("application/json")) {
            try {

                InlineResponse200 response200 = new InlineResponse200();

                List<CodeType> codeTypes = this.codeTypeRepository.findAll();
                for(CodeType codeType : codeTypes) {
                    response200.addDatalistIdItem(codeType.getCodeTypeIdentity().getTypeID());
                }

                return response200;
            } catch(SnDException e) {
                log.error("Datalist API Error!!", e);
                throw e;
            } catch (Exception e) {
                log.error("Datalist API Error!!", e);
                throw new SystemError(e);
            }
        }else throw new NotFoundContentType();
    }

    @RequestMapping(value = "/datalists/{datalistId}" , method = RequestMethod.GET)
    @ResponseBody
    @Validated
    public DataListResponse datalistsDatalistIdGet(@ApiParam(value = "Bearer oauth2_access_token" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization, @ApiParam(value = "[RFC 7231 Date/Time Formats](https://tools.ietf.org/html/rfc7231#section-7.1.1.1) - e.g : Sun, 06 Nov 1994 08:49:37 GMT " ,required=true) @RequestHeader(value="X-Date", required=true) String xDate, @ApiParam(value = "Unique request ID provided by the partner for logging and tracing transactions." ,required=true) @RequestHeader(value="X-Request-ID", required=true) String xRequestID, @ApiParam(value = "the body content type" ,required=true) @RequestHeader(value="Content-Type", required=true) String contentType, @ApiParam(value = "The unique datalist id to retrieve datalist corresponding to the ID",required=true) @PathVariable("datalistId") String datalistId, @ApiParam(value = "The identifier of the partner organization" ) @RequestHeader(value="X-Organization", required=false) String xOrganization, @ApiParam(value = "The identifier of the partner sub-organization" ) @RequestHeader(value="X-Organization-Branch", required=false) String xOrganizationBranch, @ApiParam(value = "The identifier of the requester of the resource. The agents ID" ) @RequestHeader(value="X-Organization-User", required=false) BigDecimal xOrganizationUser, @ApiParam(value = "The version of the resource" ) @RequestHeader(value="X-API-Version", required=false) String xAPIVersion) {
        String accept = request.getHeader("Content-Type");
        if (accept != null && accept.contains("application/json")) {
            try {
                DataListResponse dataListResponse = new DataListResponse();

                dataListResponse.setDatalistId(datalistId);

                List<Code> codeList = this.codeRepository.findByIdentityTypeID(datalistId);
                for(Code code : codeList) {
                    dataListResponse.addValuesItem(toDatalistValue(code));
                }

                return dataListResponse;
            } catch(SnDException e) {
                log.error("datalistsDatalistIdGet fail!!", e);
                throw e;
            } catch (Exception e) {
                log.error("datalistsDatalistIdGet fail!!", e);
                throw new SystemError(e);
            }
        }else throw new NotFoundContentType();
    }

    @RequestMapping(value = "/datalists/publish" , method = RequestMethod.PUT)
    @ResponseBody
    @Validated
    public GenerationResponse datalistsPublishPut(@ApiParam(value = "[RFC 7231 Date/Time Formats](https://tools.ietf.org/html/rfc7231#section-7.1.1.1) - e.g : Sun, 06 Nov 1994 08:49:37 GMT " ,required=true) @RequestHeader(value="X-Date", required=true) String xDate, @ApiParam(value = "Unique request ID provided by the partner for logging and tracing transactions." ,required=true) @RequestHeader(value="X-Request-ID", required=true) String xRequestID, @ApiParam(value = "the body content type" ,required=true) @RequestHeader(value="Content-Type", required=true) String contentType, @NotNull @ApiParam(value = "Code publishTime e.g. 2018-02-27T00:00:00+0800", required = true) @Valid @RequestParam(value = "publishTime", required = true) String publishTime, @ApiParam(value = "The identifier of the partner organization" ) @RequestHeader(value="X-Organization", required=false) String xOrganization, @ApiParam(value = "The identifier of the partner sub-organization" ) @RequestHeader(value="X-Organization-Branch", required=false) String xOrganizationBranch, @ApiParam(value = "The identifier of the requester of the resource. The agents ID" ) @RequestHeader(value="X-Organization-User", required=false) BigDecimal xOrganizationUser, @ApiParam(value = "The version of the resource" ) @RequestHeader(value="X-API-Version", required=false) String xAPIVersion) {
        String accept = request.getHeader("Content-Type");
        if (accept != null && accept.contains("application/json")) {

            GenerationResponse generationResponse = new GenerationResponse();

            try {
                Date publishDate = dateService.toDateTimeFormatDate(publishTime);
                tableUpdateVersionService.updateTableVersion(DataCategory.CODE,publishDate);

                generationResponse.setSuccess(true);
                return generationResponse;
            } catch(SnDException e) {
                log.error("datalistsPublishPut fail!!", e);
                throw e;
            } catch (Exception e) {
                generationResponse.setSuccess(false);
                log.error("datalistsPublishPut fail!!", e);
                throw new SystemError(e);
            }
        }else throw new NotFoundContentType();
    }

    private DataListValue toDatalistValue(Code code) {
        DataListValue dataListValue = new DataListValue();

        dataListValue.setLabel(code.getIdentity().getMappingId());
        dataListValue.setValue(code.getIdentity().getCode());
        dataListValue.setOrders(new BigDecimal(code.getOrders()));
        dataListValue.setArguments(code.getArguments());
        dataListValue.setValidityPeriod(dateService.toDateString(code.getValidityPeriod(),"yyyy-MM-dd"));

        return dataListValue;
    }
}
