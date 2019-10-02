package com.allianz.sd.core.jpa.repository;

import com.allianz.sd.core.jpa.model.AgentData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 12:41
 * To change this template use File | Settings | File Templates.
 */
public interface AgentDataRepository extends JpaRepository<AgentData,String> {

    public AgentData findByAgentIDAndOrganizationalUnit(String agentID , String organizationalUnit);

    @Query("select a from AgentData a\n" +
            "where 1=1\n" +
            "and leavingDate is null")
    public List<AgentData> findAvailableAgentData();
}
