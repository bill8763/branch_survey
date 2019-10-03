package com.allianz.sd.core.service;

import com.allianz.sd.core.datasync.AgentTableDataSync;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AgentTableSyncService<T> {

    private Logger logger = LoggerFactory.getLogger(AgentTableSyncService.class);

    @Autowired
    private DateService dateService;

    @Autowired
    private AgentUpdateVersionService agentUpdateVersionService;

    @Autowired
    private BeanFactory beanFactory;

    public Date pull(String dataCategory,List<T> pullData ,String agentID , String lastUpdateTime) throws Exception {

        logger.trace("DataSync["+dataCategory+"] pull agentID = " + agentID + ",lastUpdateTime = " + lastUpdateTime);

        AgentTableDataSync agentTableDataSync = getAgentTableDatasync(dataCategory);
        if(agentTableDataSync != null) {

            Date today = dateService.getTodayDate();
            Date appSyncDate = dateService.toDateTimeFormatDate(lastUpdateTime);

            //first check this agent has more sync data
            Date dataLastSyncDate = agentUpdateVersionService.getAgentLastSyncData(agentID, dataCategory);
            logger.trace("["+agentID+"] sync dataLastSyncDate = " + dataLastSyncDate + ", appSyncDate = " + appSyncDate);

            //if no cache , add to cache table
            if(dataLastSyncDate == null) agentUpdateVersionService.addAgentTableSyncCache(today,agentID,dataCategory);

            //if no cache or datasyncdate more than appsyncdate
            if(dataLastSyncDate == null || dataLastSyncDate.getTime() > appSyncDate.getTime()) {
                logger.trace("["+agentID+"] sync dataLastSyncDate realtime");
                agentTableDataSync.pullData(agentID,pullData);
            }

            logger.trace("["+agentID+"] sync dataLastSyncDate finish");

            return today;
        }
        else {
            logger.error("Cannot find " + dataCategory + "DataSync object from Spring");
            return null;
        }
    }

    public AgentTableDataSync getAgentTableDatasync(String dataCategory) {
        return (AgentTableDataSync) beanFactory.getBean(dataCategory + "DataSync");
    }
}
