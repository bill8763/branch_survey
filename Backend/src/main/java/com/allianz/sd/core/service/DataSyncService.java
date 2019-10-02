package com.allianz.sd.core.service;

import com.allianz.sd.core.datasync.DataMatch;
import com.allianz.sd.core.datasync.DataSync;
import com.allianz.sd.core.jpa.model.AgentUpdateVersion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/4/18
 * Time: 下午 4:36
 * To change this template use File | Settings | File Templates.
 */
@Service
public class DataSyncService<T> {

    private Logger logger = LoggerFactory.getLogger(DataSyncService.class);

    private final DateService dateService;
    private final AgentUpdateVersionService agentUpdateVersionService;
    private final BeanFactory beanFactory;

    @Autowired
    public DataSyncService(DateService dateService,BeanFactory beanFactory,AgentUpdateVersionService agentUpdateVersionService) {
        this.dateService = dateService;
        this.beanFactory = beanFactory;
        this.agentUpdateVersionService = agentUpdateVersionService;
    }

    public Date pull(String dataCategory,List result , List<BigDecimal> deleteList , String agentID , String lastUpdateTime) throws Exception {

        logger.trace("DataSync["+dataCategory+"] pull agentID = " + agentID + ",lastUpdateTime = " + lastUpdateTime);

        DataSync dataSync = (DataSync) beanFactory.getBean(dataCategory + "DataSync");
        if(dataSync != null)  {

            Date appSyncDate = dateService.toDateTimeFormatDate(lastUpdateTime);

            //get current time
            Date responseDate = dateService.getTodayDate();
            
            //set agentID
            dataSync.setAgentID(agentID);

            //first check this agent has more sync data
            List<Integer> idList = agentUpdateVersionService.getAgentLastSyncData(agentID, dataCategory,appSyncDate);
            logger.trace("["+agentID+"] sync data size = " + idList.size());

            //get sync raw data
            Map<Integer,T> moreDataList = dataSync.findPullData(appSyncDate);

            if(moreDataList != null) {
                logger.trace("["+agentID+"] moreData size = " + moreDataList.size());

                for(Integer id : idList) {

                    T data = moreDataList.get(id);

                    logger.trace("UpdateMessageID = " + id + " , data = ["+data+"]");

                    if(data != null) {

                        Object c = dataSync.data2SyncObj(data);

                        if(c != null) result.add(c);
                    }
                    else {
                        deleteList.add(new BigDecimal(id));
                    }
                }

            }

            return responseDate;
        }
        else {
            logger.error("Cannot find " + dataCategory + "DataSync object from Spring");
            return null;
        }

    }

    @Transactional
    public int push(String dataCategory,String agentID, List<T> pushList) throws  Exception{

        logger.trace("DataSync["+dataCategory+"] push agentID = " + agentID + ",pushList size = " + pushList.size());


        DataSync dataSync = (DataSync) beanFactory.getBean(dataCategory + "DataSync");
        if(dataSync != null)  {
            DataMatch dataMatch = dataSync.getDataMatchPolicy();
            Date currentDate = dateService.getTodayDate();
            int updateRec = 0;

            for(Object pushData : pushList) {

                logger.trace("pushData = " + pushData.toString());
                
                //set agentID
                dataSync.setAgentID(agentID);

                Object dbData = dataSync.findPushData(pushData);
                boolean isDeleteData = dataSync.isDeleteData(pushData);
                logger.trace("isDeleteData = " + isDeleteData);

                if(dbData != null) {

                    boolean isDifference = dataMatch.isDifference(dbData,pushData);

                    logger.trace("isDifference = " + isDifference);

                    //check is difference
                    if(!isDifference) {

                        if(isDeleteData) {
                            dataSync.onDeleteData(dbData);
                        }
                        else {
                            dbData = dataSync.onSaveData(dbData,pushData);
                        }

                        //紀錄log到異動檔
                        Integer identityID = dataSync.getIdentityID(dbData);
                        if(identityID != null) {
                            agentUpdateVersionService.updateAgentLastTme(currentDate, agentID, dataCategory,identityID);
                        }
                        else {
                            throw new Exception("can't find identityID on Update ["+agentID+"]");
                        }

                        updateRec++;
                    }
                }
                else {

                    //if backend no data & app send delete , skip this process
                    if(!isDeleteData) {
                        dbData = dataSync.onSaveData(dbData,pushData);

                        //紀錄log到異動檔
                        Integer identityID = dataSync.getIdentityID(dbData);
                        if(identityID != null) {
                            agentUpdateVersionService.insertAgentLastTme(currentDate, agentID, dataCategory,identityID );
                        }
                        else {
                            throw new Exception("can't find identityID on Insert ["+agentID+"]");
                        }
                        updateRec++;
                    }

                }


            }

            return updateRec;
        }else {
            logger.error("Cannot find " + dataCategory + "DataSync object from Spring");
            return -1;
        }

    }

}
