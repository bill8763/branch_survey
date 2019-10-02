package com.allianz.sd.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allianz.sd.core.InstanceCode;
import com.allianz.sd.core.authorization.DeviceBindingRule;
import com.allianz.sd.core.authorization.bean.DeviceInfo;
import com.allianz.sd.core.authorization.bean.LoginResponse;
import com.allianz.sd.core.authorization.bean.RegisterDeviceResponse;
import com.allianz.sd.core.jpa.model.AgentData;
import com.allianz.sd.core.jpa.model.AgentDevice;
import com.allianz.sd.core.jpa.model.AgentLoginToken;
import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.jpa.repository.AgentDeviceRepository;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AgentDeviceService {

	private static final Logger logger = LoggerFactory.getLogger(AgentDeviceService.class);

	@Autowired
    private AgentDeviceRepository agentDeviceRepository;

	@Autowired
    private BeanFactory beanFactory;

	public AgentDevice isNewDevice(DeviceInfo deviceInfo) {
	    String deviceId = deviceInfo.getDeviceId();

	    return agentDeviceRepository.findByDeviceID(deviceId);

    }

    public boolean isSupportDeviceOverAgent(AgentYearData agentYearData,AgentDevice agentDevice) {
        DeviceBindingRule deviceBindingRule = (DeviceBindingRule) beanFactory.getBean(InstanceCode.DeviceBindingRule);
        return deviceBindingRule.isSupportDeviceOverAgent(agentYearData,agentDevice);
    }

    public boolean isBelongAgent(AgentYearData agentYearData,AgentDevice agentDevice) {

	    return agentDevice.getAgentID().equalsIgnoreCase(agentYearData.getIdentity().getAgentID());
    }

	public boolean checkAllowMoreDevice(AgentYearData agentYearData,DeviceInfo deviceInfo) {
        DeviceBindingRule deviceBindingRule = (DeviceBindingRule) beanFactory.getBean(InstanceCode.DeviceBindingRule);

        return deviceBindingRule.checkAllowMoreDevice(agentYearData,deviceInfo);
    }

    @Transactional
	public RegisterDeviceResponse registeredDevice(AgentYearData agentYearData, DeviceInfo deviceInfo, LoginResponse loginResponse) {

	    AgentData agentData = agentYearData.getAgentData();
		String agentID = agentData.getAgentID();
		String deviceID = deviceInfo.getDeviceId();

        DeviceBindingRule deviceBindingRule = (DeviceBindingRule) beanFactory.getBean(InstanceCode.DeviceBindingRule);

        RegisterDeviceResponse registerDeviceResponse = deviceBindingRule.registerDevice(agentYearData,deviceInfo,loginResponse);

        //register device
        AgentDevice agentDevice = new AgentDevice();
        agentDevice.setAgentID(agentID);
        agentDevice.setCreateBy(agentData.getAgentID());
        agentDevice.setUpdateBy(agentData.getAgentID());
        agentDevice.setDeviceCategory(deviceInfo.getDeviceSystem());
        agentDevice.setDeviceID(deviceID);
        agentDevice.setDeviceModel(deviceInfo.getDeviceModel());
        agentDevice.setDeviceSystem(deviceInfo.getDeviceSystem());
        agentDevice.setPushId(deviceInfo.getPushId());
        agentDevice.setRegisterKernel(registerDeviceResponse.isSuccess() ? "Y" : "N");
        agentDevice.setDeviceType(deviceInfo.getDeviceType());

        agentDeviceRepository.save(agentDevice);

        return registerDeviceResponse;

	}

	public boolean onPushIdChange(AgentYearData agentYearData, DeviceInfo deviceInfo, AgentLoginToken agentLoginToken) {
        DeviceBindingRule deviceBindingRule = (DeviceBindingRule) beanFactory.getBean(InstanceCode.DeviceBindingRule);

        String deviceId = deviceInfo.getDeviceId();
        String pushId = deviceInfo.getPushId();

        AgentDevice agentDevice = agentDeviceRepository.findByDeviceID(deviceId);
        if(agentDevice != null) {
            agentDevice.setPushId(pushId);

            saveAgentDevice(agentDevice);

            return deviceBindingRule.onPushIdChange(agentYearData,deviceInfo,agentLoginToken);
        }
        else {
            return false;
        }

    }

    @Transactional
    public void saveAgentDevice(AgentDevice agentDevice) {
        agentDeviceRepository.save(agentDevice);
    }

    public AgentDevice getAgentDeviceByDeviceId(String deviceID) {
	    return agentDeviceRepository.findByDeviceID(deviceID);
    }

    public List<AgentDevice> getAgentDevice(String agentID) {
        return agentDeviceRepository.findByAgentID(agentID);
    }
}
