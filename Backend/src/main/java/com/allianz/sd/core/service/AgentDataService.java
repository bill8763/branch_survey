package com.allianz.sd.core.service;

import com.allianz.sd.core.exception.NotFoundAgentYearDataException;
import com.allianz.sd.core.jpa.model.AgentData;
import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.jpa.model.AgentYearDataIdentity;
import com.allianz.sd.core.jpa.repository.AgentDataRepository;
import com.allianz.sd.core.jpa.repository.AgentYearDataRepository;
import com.allianz.sd.core.service.bean.AppUseMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class AgentDataService {

    private static Logger logger = LoggerFactory.getLogger(AgentDataService.class);
    
    @Autowired
    private AgentYearDataRepository agentYearDataRepository;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private DateService dateService;

    @Autowired
    private AgentDataRepository agentDataRepository;

    public int getCalcPersonalActualStartMonth(AgentYearData agentYearData) {
        return calcActualStartMonth(agentYearData.getAgentData().getOBMonth());
    }

    public int getCalcTeamActualStartMonth(AgentYearData agentYearData) {
        return calcActualStartMonth(agentYearData.getCurrentJobOBMonth());
    }

    private int calcActualStartMonth(Date tmp) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(tmp);
        int year = calendar.get(Calendar.YEAR);

        if(year == dateService.getCurrentYear()) return tmp.getMonth() + 1;
        else return 1;
    }


    public List<AgentYearData> getAllIsAgentYearData(int dataYear , String organizationalUnit) {
        return agentYearDataRepository.findIsAgents(dataYear,organizationalUnit);
    }

    public List<AgentYearData> getAllAgentYearData(int dataYear , String organizationalUnit) {
        return agentYearDataRepository.findByIdentityDataYearAndIdentityOrganizationalUnit(dataYear, organizationalUnit);
    }

    public AgentData getAgentData(String agentID) {
        return agentDataRepository.findByAgentIDAndOrganizationalUnit(agentID,organizationService.getOrganizationalUnit());
    }

    public AgentYearData getAgentYearData(String agentID , int year) {
        return getAgentYearData(agentID,year,organizationService.getOrganizationalUnit());
    }

    public AgentYearData getAgentYearData(String agentID , int year , String organizationalUnit) {
        logger.trace("[getAgentYearData] agentID = " + agentID + " , year = " + year + " , unit = " + organizationalUnit);

        AgentYearDataIdentity agentYearDataIdentity = new AgentYearDataIdentity();
        agentYearDataIdentity.setAgentID(agentID);
        agentYearDataIdentity.setDataYear(year);
        agentYearDataIdentity.setOrganizationalUnit(organizationalUnit);
        Optional<AgentYearData> agentDataOptional = agentYearDataRepository.findById(agentYearDataIdentity);

        return agentDataOptional.orElse(null);
    }

    public AgentYearData getCurrentAgentYearData(String agentID) {
        return getAgentYearData(agentID,dateService.getCurrentYear());
    }



    public String getAppUseMode(String agentID , int year) {
    	AgentYearData agentYearData = getAgentYearData(agentID, year);
    	if(agentYearData!=null) {
        	return agentYearData.getAppUseMode();
    	}else {
    		return null;
    	}
    }
}
