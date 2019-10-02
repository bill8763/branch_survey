package com.allianz.sd.core.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allianz.sd.core.jpa.repository.AgentUpdateVersionRepository;

@Service
public class AgentUpdateVersionService {

    private Logger logger = LoggerFactory.getLogger(AgentUpdateVersionService.class);

    @Autowired
    private AgentUpdateVersionRepository agentUpdateVersionRepository;

    public boolean insertAgentLastTme(
            Date dataTime ,
            String agentID ,
            String dataCategory,
            Integer uniqueID) {
        return agentUpdateVersionRepository.insertAgentLastTme(dataTime,agentID,dataCategory,uniqueID) != -1;
    }

    public boolean addAgentTableSyncCache(
            Date dataTime ,
            String agentID ,
            String dataCategory) {
        return agentUpdateVersionRepository.insertAgentLastTme(dataTime,agentID,dataCategory,-1) != -1;
    }

    public boolean updateAgentLastTme(
            Date dataTime ,
            String agentID ,
            String dataCategory,
            Integer uniqueID) {
        return agentUpdateVersionRepository.updateAgentLastTme(dataTime,agentID,dataCategory,uniqueID) != -1;
    }

    public boolean clearAgentTableCache(
            Set<String> agentIDs,
            String dataCategory) {
        return agentUpdateVersionRepository.clearAgentTableCache(agentIDs,dataCategory) != -1;
    }

    public boolean clearAgentTableCache(
            String dataCategory) {
        return agentUpdateVersionRepository.clearAgentTableCache(dataCategory) != -1;
    }

    public List<Integer> getAgentLastSyncData(String agentID ,
                                              String dataCategory,
                                              Date dataTime) {
        return agentUpdateVersionRepository.getAgentLastSyncData(agentID,dataCategory,dataTime);
    }

    public Date getAgentLastSyncData(String agentID ,
                                              String dataCategory) {
        return agentUpdateVersionRepository.getAgentLastSyncData(agentID,dataCategory);
    }
}
