package com.allianz.sd.core.jpa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 6:00
 * To change this template use File | Settings | File Templates.
 */
@Embeddable
public class AgentLoginTokenIdentity implements Serializable {

    @Column(name = "DeviceID")
    private String deviceID = null;

    @Column(name = "AgentID")
    private String agentID = null;

    

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public String getAgentID() {
		return agentID;
	}

	public void setAgentID(String agentID) {
		this.agentID = agentID;
	}


	

}
