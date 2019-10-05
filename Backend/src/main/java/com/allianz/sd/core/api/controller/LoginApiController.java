package com.allianz.sd.core.api.controller;

import com.allianz.sd.core.InstanceCode;
import com.allianz.sd.core.authorization.bean.DeviceInfo;
import com.allianz.sd.core.authorization.bean.RegisterDeviceResponse;
import com.allianz.sd.core.api.model.LoginRequest;
import com.allianz.sd.core.api.model.LoginResponse;
import com.allianz.sd.core.exception.*;
import com.allianz.sd.core.jpa.model.*;
import com.allianz.sd.core.jpa.repository.AgentDeviceRepository;
import com.allianz.sd.core.jpa.repository.AgentLoginTokenRepository;
import com.allianz.sd.core.jpa.repository.LoginLogRepository;
import com.allianz.sd.core.service.AgentDataService;
import com.allianz.sd.core.service.AgentDeviceService;
import com.allianz.sd.core.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-03-05T03:30:18.434Z")

@RestController
public class LoginApiController implements LoginApi {

    private static final Logger logger = LoggerFactory.getLogger(LoginApiController.class);

    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    private final BeanFactory beanFactory;
    private final TokenService tokenService;
    private final LoginLogRepository loginLogRepository;
    private final AgentDataService agentDataService;
    private final AgentLoginTokenRepository agentLoginTokenRepository;
    private final AgentDeviceService agentDeviceService;

    @org.springframework.beans.factory.annotation.Autowired
    public LoginApiController(ObjectMapper objectMapper,
                              HttpServletRequest request,
                              AgentDeviceRepository agentDeviceRepository,
                              TokenService tokenService,
                              org.springframework.beans.factory.BeanFactory beanFactory,
                              LoginLogRepository loginLogRepository,
                              AgentDataService agentDataService,
                              AgentLoginTokenRepository agentLoginTokenRepository,
                              AgentDeviceService agentDeviceService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.tokenService = tokenService;
        this.beanFactory = beanFactory;
        this.loginLogRepository = loginLogRepository;
        this.agentDataService = agentDataService;
        this.agentLoginTokenRepository = agentLoginTokenRepository;
        this.agentDeviceService = agentDeviceService;
    }

    @RequestMapping("/login")
    @ResponseBody
    @Validated
    @Transactional
    public LoginResponse login(@ApiParam(value = "[RFC 7231 Date/Time Formats](https://tools.ietf.org/html/rfc7231#section-7.1.1.1) - e.g : Sun, 06 Nov 1994 08:49:37 GMT ", required = true) @RequestHeader(value = "X-Date", required = true) String xDate, @ApiParam(value = "Unique request ID provided by the partner for logging and tracing transactions.", required = true) @RequestHeader(value = "X-Request-ID", required = true) String xRequestID, @ApiParam(value = "the body content type", required = true) @RequestHeader(value = "Content-Type", required = true) String contentType, @ApiParam(value = "", required = true) @Valid @RequestBody LoginRequest loginInfo, @ApiParam(value = "The identifier of the partner organization") @RequestHeader(value = "X-Organization", required = false) String xOrganization, @ApiParam(value = "The identifier of the partner sub-organization") @RequestHeader(value = "X-Organization-Branch", required = false) String xOrganizationBranch, @ApiParam(value = "The version of the resource") @RequestHeader(value = "X-API-Version", required = false) String xAPIVersion) {
        String accept = request.getHeader("Content-Type");

        if (accept != null && accept.contains("application/json")) {
            String username = loginInfo.getUsername();
            String password = loginInfo.getPassword();

            DeviceInfo deviceInfo = new DeviceInfo(
                    loginInfo.getDeviceId(),
                    loginInfo.getDeviceModel(),
                    loginInfo.getDeviceSystem(),
                    loginInfo.getPushId(),
                    loginInfo.getDeviceType());

            String deviceID = deviceInfo.getDeviceId();

            try{
                //valid usernamd & password is valid
                com.allianz.sd.core.authorization.Authorization authorization = (com.allianz.sd.core.authorization.Authorization) beanFactory.getBean(InstanceCode.Authorization);

                //login check is success or not
                com.allianz.sd.core.authorization.bean.LoginResponse loginResponse = authorization.login(username,password,deviceInfo);

                //save log
                LoginLog loginLog = new LoginLog();
                loginLog.setDeviceID(deviceID);
                loginLog.setLoginTime(new Date());
                loginLog.setUsername(username);
                loginLog.setSuccess(loginResponse.isSuccess() ? "Y" : "N");
                loginLogRepository.save(loginLog);

                if(loginResponse.isSuccess()) {

                    String logintoken = loginResponse.getToken();
                    String agentID = loginResponse.getAgentID();

                    logger.trace("AgentID = "+agentID+", Token : " + logintoken);

                    //get agent data from database
                    AgentYearData agentYearData = agentDataService.getCurrentAgentYearData(agentID);

                    if(agentYearData != null) {

                        AgentDevice agentDevice = agentDeviceService.isNewDevice(deviceInfo);

                        //判斷是否為新裝置(未綁定)
                        if(agentDevice == null) {
                            //判斷是否還允許註冊裝置
                            boolean allowMoreDevice = agentDeviceService.checkAllowMoreDevice(agentYearData,deviceInfo);

                            if(allowMoreDevice) {
                                //register device
                                RegisterDeviceResponse registerDeviceResponse = agentDeviceService.registeredDevice(agentYearData,deviceInfo,loginResponse);

                                if(registerDeviceResponse.isSuccess()) {
                                    //直接登入
                                    LoginResponse response = loginSuccessProcess(agentYearData,deviceInfo,loginResponse);

                                    return response;
                                }
                                else {
                                    throw new RegisterDeviceException(registerDeviceResponse.getErrorMsg(),registerDeviceResponse.getErrorCode());
                                }
                            }
                            else {
                                //FAIL
                                throw new DeviceIsLimit(deviceInfo);
                            }
                        }
                        else {

                            //判斷裝置是否支援跨使用者使用
                            boolean isSupportDeviceOverAgent = agentDeviceService.isSupportDeviceOverAgent(agentYearData,agentDevice);

                            if(isSupportDeviceOverAgent) {
                                //直接登入
                                LoginResponse response = loginSuccessProcess(agentYearData,deviceInfo,loginResponse);

                                return response;
                            }
                            else {
                                //判斷該裝置是否屬於該agent
                                boolean isBelongAgent = agentDeviceService.isBelongAgent(agentYearData,agentDevice);

                                if(isBelongAgent) {
                                    //直接登入
                                    LoginResponse response = loginSuccessProcess(agentYearData,deviceInfo,loginResponse);

                                    return response;
                                }
                                else {
                                    //登入失敗
                                    throw new AgentDeviceTypeDuplicate(agentYearData.getAgentData(),deviceInfo);
                                }
                            }


                        }
                    }
                    else {
                        throw new NotFoundAgentException();
                    }

                }
                else {
                    throw new LoginError(loginResponse.getErrorCode(),loginResponse.getErrorMsg());
                }
            }catch(SnDException e) {
                logger.error("login fail!!",e);
                throw e;
            }
            catch(Exception e) {
                logger.error("login fail!!",e);
                throw new SystemError(e);
            }

        }
        else throw new NotFoundContentType();

    }

    @Transactional
    private LoginResponse loginSuccessProcess(AgentYearData agentYearData , DeviceInfo deviceInfo , com.allianz.sd.core.authorization.bean.LoginResponse loginResponse) {

        String agentID = agentYearData.getIdentity().getAgentID();
        String deviceID = deviceInfo.getDeviceId();
        String logintoken = loginResponse.getToken();
        String pushID = deviceInfo.getPushId();

        //更新PushID
        AgentDevice agentDevice = agentDeviceService.getAgentDeviceByDeviceId(deviceID);
        agentDevice.setPushId(pushID);
        agentDeviceService.saveAgentDevice(agentDevice);

        //create jwt token
        Map<String, Object> claims = new HashMap<>();
        claims.put("AgentID",agentID);
        claims.put("AgentName",agentYearData.getAgentData().getAgentName());
        claims.put("Gender",agentYearData.getAgentData().getGender());
        claims.put("AppUseMode",agentYearData.getAppUseMode());
        claims.put("OfficeName",agentYearData.getAgentData().getOfficeName());
        claims.put("CurrentJobSeniorityMonth",agentYearData.getCurrentJobSeniorityMonth());
        claims.put("JobGrade",agentYearData.getJobGrade() );
        claims.put("ReferenceToken",loginResponse.getToken());

        //add extension info
        Map<String,String> extensionInfo = loginResponse.getExtensionInfo();
        for(String key : extensionInfo.keySet()) {
            claims.put(key,extensionInfo.get(key));
        }

        String jwtToken = tokenService.generateToken(claims);

        //把agentID、deviceID、token 存到AgentLoginToken
        AgentLoginTokenIdentity agentLoginTokenIdentity = new AgentLoginTokenIdentity() ;
        agentLoginTokenIdentity.setAgentID(agentID);
        agentLoginTokenIdentity.setDeviceID(deviceID);
        AgentLoginToken agentLoginToken = new AgentLoginToken();
        agentLoginToken.setIdentity(agentLoginTokenIdentity);
        agentLoginToken.setToken(logintoken);
        agentLoginToken.setJwtToken(jwtToken);
        agentLoginTokenRepository.save(agentLoginToken);

        LoginResponse response = new LoginResponse();
        response.setSuccess(true);
        response.setToken(jwtToken);

        return response;
    }

}
