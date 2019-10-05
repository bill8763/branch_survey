package com.allianz.sd.application.extension.authorization;

import com.allianz.sd.application.extension.esb.ESBHandler;
import com.allianz.sd.application.extension.esb.ESBResponse;
import com.allianz.sd.core.authorization.Authorization;
import com.allianz.sd.core.authorization.DeviceBindingRule;
import com.allianz.sd.core.authorization.bean.DeviceInfo;
import com.allianz.sd.core.authorization.bean.LoginResponse;
import com.allianz.sd.core.authorization.bean.RegisterDeviceResponse;
import com.allianz.sd.core.exception.AgentDeviceTypeDuplicate;
import com.allianz.sd.core.handler.RestfulHandler;
import com.allianz.sd.core.handler.bean.RestfulParam;
import com.allianz.sd.core.impl.DefaultDeviceBindingRule;
import com.allianz.sd.core.jpa.model.AgentData;
import com.allianz.sd.core.jpa.model.AgentDevice;
import com.allianz.sd.core.jpa.model.AgentLoginToken;
import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.jpa.repository.AgentDeviceRepository;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/3/5
 * Time: 上午 11:45
 * To change this template use File | Settings | File Templates.
 */
@Component
public class TWDeviceBinding extends DefaultDeviceBindingRule {

    private static final Logger logger = LoggerFactory.getLogger(TWDeviceBinding.class);

    @Value("${SND.WebService.Login.URL}")
    private String loginUrl;

    @Value("${SND.WebService.RegisterDevice.URL}")
    private String registerDeviceUrl;

    @Autowired
    private AgentDeviceRepository agentDeviceRepository;

    @Autowired
    private ESBHandler esbHandler;

    @Override
    public boolean checkAllowMoreDevice(AgentYearData agentYearData, DeviceInfo deviceInfo) {

        String agentID = agentYearData.getIdentity().getAgentID();
        String deviceType = deviceInfo.getDeviceType();

        //check agent only have one mobile & one pad
        String padDeviceId = null;
        String phoneDeviceId = null;

        List<AgentDevice> agentDevices = agentDeviceRepository.findByAgentID(agentID);
        for(AgentDevice agentDevice : agentDevices) {

            if("Pad".equalsIgnoreCase(agentDevice.getDeviceType())) padDeviceId = agentDevice.getDeviceID();
            else if("Phone".equalsIgnoreCase(agentDevice.getDeviceType())) phoneDeviceId = agentDevice.getDeviceID();
        }

        boolean moreDevice = "Pad".equalsIgnoreCase(deviceType) ? StringUtils.isEmpty(padDeviceId) : StringUtils.isEmpty(phoneDeviceId);

        return !(StringUtils.isNotEmpty(padDeviceId) && StringUtils.isNotEmpty(phoneDeviceId)) &&
                (moreDevice);
    }

//    @Override
//    public boolean isSupportDeviceOverAgent(AgentYearData agentYearData, AgentDevice agentDevice) {
//        return false;
//    }

    @Override
    public RegisterDeviceResponse registerDevice(AgentYearData agentYearData, DeviceInfo deviceInfo, LoginResponse loginResponse) {
        RegisterDeviceResponse response = new RegisterDeviceResponse();

        AgentData agentData = agentYearData.getAgentData();

        ESBResponse esbResponse = null;

        RestfulParam param = new RestfulParam();
        param.setUrl(registerDeviceUrl);

        param.addParam("ReferenceToken",loginResponse.getToken());
        param.addParam("AgentID",agentData.getAgentID());
        param.addParam("DeviceID",deviceInfo.getDeviceId());
        param.addParam("PushID",deviceInfo.getPushId());
        param.addParam("DeviceSystem",deviceInfo.getDeviceSystem());
        param.addParam("DeviceModel",deviceInfo.getDeviceModel());

        param.addParam("EditMode","A");//A：新增,U：修改,D：刪除


        esbResponse = esbHandler.call(param,"RegisterDevice");
        response.setSuccess(esbResponse.isSuccess());
        response.setErrorCode(esbResponse.getErrorCode());
        response.setErrorMsg(esbResponse.getErrorMsg());

        response.setSuccess(true);

        return response;
    }


//    @Override
//    public boolean onPushIdChange(AgentYearData agentYearData, DeviceInfo deviceInfo, AgentLoginToken agentLoginToken) {
//        boolean isSuccess = false;
//
//        AgentData agentData = agentYearData.getAgentData();
//        String agentId = agentData.getAgentID();
//        String pushId = deviceInfo.getPushId();
//
//        //TODO 多重裝置確認作法
//        List<AgentDevice> agentDevices = agentDeviceRepository.findByAgentID(agentId);
//        AgentDevice agentDevice = agentDevices.size()>0 ?  agentDevices.get(0):null;
//
//        if(agentDevice != null) {
//
//            RestfulHandler restfulHandler = new RestfulHandler(registerDeviceUrl);
//            restfulHandler.addParam("ReferenceToken",agentLoginToken.getToken());
//            restfulHandler.addParam("AgentID",agentData.getAgentID().toString());
//            restfulHandler.addParam("DeviceID",agentDevice.getDeviceID());
//            restfulHandler.addParam("PushID",pushId);
//            restfulHandler.addParam("DeviceSystem",agentDevice.getDeviceSystem());
//            restfulHandler.addParam("DeviceModel",agentDevice.getDeviceModel());
//            restfulHandler.addParam("EditMode","U");//A：新增,U：修改,D：刪除
//
//            ESBHandler esbHandler = new ESBHandler();
//            ESBResponse esbResponse = esbHandler.call(restfulHandler,"RegisterDevice");
//
//            agentDevice.setRegisterKernel(esbResponse.isSuccess() ? "Y" : "N");
//            agentDevice.setPushId(pushId);
//            agentDeviceRepository.save(agentDevice);
//        }
//
//        return isSuccess;
//    }
}
