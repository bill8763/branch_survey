package com.allianz.sd.core.exception;

import com.allianz.sd.core.authorization.bean.DeviceInfo;
import com.allianz.sd.core.jpa.model.AgentData;

/*
 *
 */

public class DeviceIsLimit extends SnDException  {

	public DeviceIsLimit(DeviceInfo deviceInfo) {
		super("B0015","Device has reached the upper limit["+deviceInfo.getDeviceId()+"]");
	}



}
