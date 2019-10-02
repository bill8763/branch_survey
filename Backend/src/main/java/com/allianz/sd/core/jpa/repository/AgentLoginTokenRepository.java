package com.allianz.sd.core.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.allianz.sd.core.jpa.model.AgentLoginToken;
import com.allianz.sd.core.jpa.model.AgentLoginTokenIdentity;


/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 12:41
 * To change this template use File | Settings | File Templates.
 */
public interface AgentLoginTokenRepository extends JpaRepository<AgentLoginToken,AgentLoginTokenIdentity> {

	
	@Query("select a from AgentLoginToken a  \n" + 
			"where a.identity.agentID =:agentID \n" + 
			"and a.updateTime = (select Max(updateTime) from AgentLoginToken )")
	public AgentLoginToken getTheLatestData(@Param("agentID")String agentID);


}
