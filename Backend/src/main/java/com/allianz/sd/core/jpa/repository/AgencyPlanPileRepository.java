package com.allianz.sd.core.jpa.repository;

import com.allianz.sd.core.jpa.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 12:41
 * To change this template use File | Settings | File Templates.
 */
public interface AgencyPlanPileRepository extends JpaRepository<AgencyPlanPile, AgencyPlanPileIdentity> {

    @Query("select identity.agentID from AgencyPlanPile where identity.leaderAgentID = :AgentID and identity.dataYear=:TodayYear ")
    public List<String> getAgentIDList(@Param("AgentID") String AgentID,
                                        @Param("TodayYear") int TodayYear);

    @Query("select a.identity.leaderAgentID from AgencyPlanPile a \n" +
    	   "where 1 = 1 \n"+
    	   "and a.identity.organizationalUnit =:organizationalUnit \n"+
    	   "and a.identity.dataYear =:dataYear \n"+
    	   "and a.identity.agentID =:agentID ")
    public List<String> getLeader(@Param("organizationalUnit")String organizationalUnit,
    							  @Param("dataYear")int dataYear,
    							  @Param("agentID")String agentID);
    
}
