package com.allianz.sd.core.impl;

import com.allianz.sd.core.jpa.model.AgentDevice;
import org.springframework.beans.factory.annotation.Autowired;

import com.allianz.sd.core.authorization.DeviceBindingRule;
import com.allianz.sd.core.authorization.bean.DeviceInfo;
import com.allianz.sd.core.authorization.bean.LoginResponse;
import com.allianz.sd.core.authorization.bean.RegisterDeviceResponse;
import com.allianz.sd.core.jpa.model.AgentLoginToken;
import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.service.AgentDeviceService;


public class DefaultDeviceBindingRule implements DeviceBindingRule {

    @Autowired
    private AgentDeviceService agentDeviceService;

    @Override
    public boolean checkAllowMoreDevice(AgentYearData agentYearData, DeviceInfo deviceInfo) {
        return true;
    }

    @Override
    public boolean isSupportDeviceOverAgent(AgentYearData agentYearData, AgentDevice agentDevice) {
        return true;
    }

    @Override
    public RegisterDeviceResponse registerDevice(AgentYearData agentYearData, DeviceInfo deviceInfo, LoginResponse loginResponse) {
        RegisterDeviceResponse response = new RegisterDeviceResponse();

        response.setSuccess(true);

        return response;
    }

    @Override
    public boolean onPushIdChange(AgentYearData agentYearData,DeviceInfo deviceInfo, AgentLoginToken agentLoginToken) {
        return true;
    }
}
