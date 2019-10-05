package com.allianz.sd.core.service;

import com.allianz.sd.core.jpa.model.AgentData;
import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.jpa.model.AgentYearDataIdentity;
import com.allianz.sd.core.jpa.model.CISLAgentYearData;
import com.allianz.sd.core.jpa.repository.AgentDataRepository;
import com.allianz.sd.core.jpa.repository.AgentYearDataRepository;
import com.allianz.sd.core.jpa.repository.CISLAgentYearDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CISLAgentDataService {

    private static Logger logger = LoggerFactory.getLogger(CISLAgentDataService.class);
    
    @Autowired
    private CISLAgentYearDataRepository agentYearDataRepository;

    public CISLAgentYearData getAgentYearData(String agentID , int year , String organizationalUnit) {
        logger.trace("[getAgentYearData] agentID = " + agentID + " , year = " + year + " , unit = " + organizationalUnit);

        AgentYearDataIdentity agentYearDataIdentity = new AgentYearDataIdentity();
        agentYearDataIdentity.setAgentID(agentID);
        agentYearDataIdentity.setDataYear(year);
        agentYearDataIdentity.setOrganizationalUnit(organizationalUnit);
        Optional<CISLAgentYearData> agentDataOptional = agentYearDataRepository.findById(agentYearDataIdentity);

        return agentDataOptional.orElse(null);
    }
}
