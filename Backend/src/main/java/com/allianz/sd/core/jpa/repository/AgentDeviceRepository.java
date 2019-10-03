package com.allianz.sd.core.jpa.repository;

import com.allianz.sd.core.jpa.model.AgentDevice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 12:41
 * To change this template use File | Settings | File Templates.
 */
public interface AgentDeviceRepository extends JpaRepository<AgentDevice,Long> {

    public List<AgentDevice> findByAgentID(String agentID);
    
    public AgentDevice findByDeviceID(String deviceID);
    
    public AgentDevice findByAgentIDAndDeviceID(String agentID,String deviceID);

}
