package com.allianz.sd.core.jpa.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/3/16
 * Time: 下午 5:12
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="TW_LH_SD_Agent_Login_Token")
public class AgentLoginToken extends CreateUpdateInfo{
	
	
	
	@EmbeddedId()
    private AgentLoginTokenIdentity identity = null;

	@Column(name = "Token")
	private String token = null;

	@Column(name = "JwtToken")
	private String jwtToken = null;


	public AgentLoginTokenIdentity getIdentity() {
		return identity;
	}

	public void setIdentity(AgentLoginTokenIdentity identity) {
		this.identity = identity;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
}
