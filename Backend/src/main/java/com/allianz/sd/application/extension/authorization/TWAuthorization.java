package com.allianz.sd.application.extension.authorization;

import com.allianz.sd.core.handler.bean.RestfulParam;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.allianz.sd.application.extension.esb.ESBHandler;
import com.allianz.sd.application.extension.esb.ESBResponse;
import com.allianz.sd.core.authorization.Authorization;
import com.allianz.sd.core.authorization.bean.DeviceInfo;
import com.allianz.sd.core.authorization.bean.LoginResponse;
import com.allianz.sd.core.handler.RestfulHandler;
import com.allianz.sd.core.jpa.repository.AgentDeviceRepository;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/3/5
 * Time: 上午 11:45
 * To change this template use File | Settings | File Templates.
 */
@Component
public class TWAuthorization implements Authorization {

    private static final Logger logger = LoggerFactory.getLogger(TWAuthorization.class);

    @Value("${SND.WebService.Login.URL}")
    private String loginUrl;

    @Value("${SND.WebService.RegisterDevice.URL}")
    private String registerDeviceUrl;

    @Autowired
    private AgentDeviceRepository agentDeviceRepository;

    @Autowired
    private ESBHandler esbHandler;
    
    @Override
    public LoginResponse login(String username , String password, DeviceInfo deviceInfo) {
        LoginResponse loginResponse = new LoginResponse();

        try{
            RestfulParam restfulParam = new RestfulParam();
            restfulParam.setUrl(loginUrl);
            restfulParam.addParam("Account",username);
            restfulParam.addParam("Password",password);
            restfulParam.addParam("DeviceID",deviceInfo.getDeviceId());

            ESBResponse response = esbHandler.call(restfulParam,"Login");

            if(response.isSuccess()) {
                JSONObject body = response.getBody();
                JSONObject tokenData = body.getJSONObject("Response_Data");
                loginResponse.setSuccess(true);
                loginResponse.setAgentID(tokenData.getString("AgentID"));
                loginResponse.setToken(tokenData.getString("ReferenceToken"));
            }
            else {
                loginResponse.setErrorCode(response.getErrorCode());
                loginResponse.setErrorMsg(response.getErrorMsg());
            }

        }catch(Exception e) {
            logger.error("call Login WebService is exception!!",e);
        }

        return loginResponse;
    }

}
