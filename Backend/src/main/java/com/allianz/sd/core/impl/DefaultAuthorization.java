package com.allianz.sd.core.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.allianz.sd.core.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;

import com.allianz.sd.core.authorization.Authorization;
import com.allianz.sd.core.authorization.bean.DeviceInfo;
import com.allianz.sd.core.authorization.bean.LoginResponse;
import com.allianz.sd.core.jpa.model.AgentData;
import com.allianz.sd.core.jpa.repository.AgentDataRepository;
import com.allianz.sd.core.jpa.repository.AgentDeviceRepository;
import com.allianz.sd.core.service.AgentDeviceService;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/3/16
 * Time: 下午 6:04
 * To change this template use File | Settings | File Templates.
 */

public class DefaultAuthorization implements Authorization {

    @Autowired
    private AgentDataRepository agentDataRepository;

    @Autowired
    private OrganizationService organizationService;
    
    private static Map<String,String> mockAgentMap = new HashMap<>();
    static{
        mockAgentMap.put("titan","1");
        mockAgentMap.put("peggy","2");

        mockAgentMap.put("AGCAO","AGCAO");
        mockAgentMap.put("AGZH","AGZH");
        mockAgentMap.put("AG2","AG2");
        mockAgentMap.put("AG3","AG3");
        mockAgentMap.put("AG13","AG13");
        mockAgentMap.put("AG151","AG151");
        mockAgentMap.put("AG1521","AG1521");
        mockAgentMap.put("AG1522","AG1522");
        mockAgentMap.put("AG211","AG211");
        mockAgentMap.put("AG311","AG311");
        mockAgentMap.put("AG2132","AG2132");
        mockAgentMap.put("AG21332","AG21332");
        mockAgentMap.put("AG152","AG152");
        mockAgentMap.put("AG123","AG123");
        mockAgentMap.put("AG213","AG213");
        mockAgentMap.put("AG2133","AG2133");
        mockAgentMap.put("AG31","AG31");
        mockAgentMap.put("AG121","AG121");
        mockAgentMap.put("AG15","AG15");
        mockAgentMap.put("AG21","AG21");
        mockAgentMap.put("AG11","AG11");
        mockAgentMap.put("AG12","AG12");
        mockAgentMap.put("AG01","AG01");

        //RD TEST
        mockAgentMap.put("RAG2","RAG2");
        mockAgentMap.put("RAG3","RAG3");
        mockAgentMap.put("RAG13","RAG13");
        mockAgentMap.put("RAG151","RAG151");
        mockAgentMap.put("RAG1521","RAG1521");
        mockAgentMap.put("RAG1522","RAG1522");
        mockAgentMap.put("RAG211","RAG211");
        mockAgentMap.put("RAG311","RAG311");
        mockAgentMap.put("RAG2132","RAG2132");
        mockAgentMap.put("RAG21332","RAG21332");
        mockAgentMap.put("RAG152","RAG152");
        mockAgentMap.put("RAG123","RAG123");
        mockAgentMap.put("RAG213","RAG213");
        mockAgentMap.put("RAG2133","RAG2133");
        mockAgentMap.put("RAG31","RAG31");
        mockAgentMap.put("RAG121","RAG121");
        mockAgentMap.put("RAG15","RAG15");
        mockAgentMap.put("RAG21","RAG21");
        mockAgentMap.put("RAG11","RAG11");
        mockAgentMap.put("RAG12","RAG12");
        mockAgentMap.put("RAG01","RAG01");
    }

    @Override
    public LoginResponse login(String username, String password,DeviceInfo deviceInfo) {
        LoginResponse response = new LoginResponse();

        AgentData agentData = agentDataRepository.findByAgentIDAndOrganizationalUnit(username,organizationService.getOrganizationalUnit());

        response.setSuccess(agentData != null);
        response.setAgentID(response.isSuccess() ? agentData.getAgentID() : null);
        response.setErrorCode(response.isSuccess() ? "000" : "999");
        response.setErrorMsg(response.isSuccess() ? "" : "Agent Not Found!!");
        response.setToken(UUID.randomUUID().toString());
        
        return response;
    }


}
