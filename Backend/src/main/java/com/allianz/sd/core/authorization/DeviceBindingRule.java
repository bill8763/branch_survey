package com.allianz.sd.core.authorization;

import com.allianz.sd.core.jpa.model.AgentDevice;
import org.springframework.stereotype.Component;

import com.allianz.sd.core.authorization.bean.DeviceInfo;
import com.allianz.sd.core.authorization.bean.LoginResponse;
import com.allianz.sd.core.authorization.bean.RegisterDeviceResponse;
import com.allianz.sd.core.jpa.model.AgentLoginToken;
import com.allianz.sd.core.jpa.model.AgentYearData;

@Component
public interface DeviceBindingRule {

    //check this device is binding
    public boolean checkAllowMoreDevice(AgentYearData agentYearData,DeviceInfo deviceInfo);

    //check this device is binding
    public boolean isSupportDeviceOverAgent(AgentYearData agentYearData, AgentDevice agentDevice);

    //register this agent mapping device
    public RegisterDeviceResponse registerDevice(AgentYearData agentYearData , DeviceInfo deviceInfo, LoginResponse loginResponse);

    public boolean onPushIdChange(AgentYearData agentYearData,DeviceInfo deviceInfo, AgentLoginToken agentLoginToken);
}
