package com.allianz.sd.core.exception;

import com.allianz.sd.core.authorization.bean.DeviceInfo;
import com.allianz.sd.core.jpa.model.AgentData;

/*
 * 
 */

public class AgentDeviceTypeDuplicate extends SnDException  {

	public AgentDeviceTypeDuplicate(AgentData agentData, DeviceInfo deviceInfo) {
		super("B0012","Device Type Duplicate. agentID:" + agentData.getAgentID() + "deviceType:" + deviceInfo.getDeviceType());
	}

}
