package com.allianz.sd.core.datasync;

import com.allianz.sd.core.jpa.bean.DataCategory;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/4/18
 * Time: 下午 3:20
 * To change this template use File | Settings | File Templates.
 */
public interface DataSync {
	
	public void setAgentID(String agentID);

    /**
     * find database data from RESTFul Identity IDs
     * @param appSyncDate
     * @return
     */
    public Map findPullData(Date appSyncDate);

    /**
     * find database data from RESTFul Object
     * @param data
     * @return
     */
    public Object findPushData(Object data);

    /**
     * Database Object convert to RESTFul Object
     * @param data
     * @return
     */
    public Object data2SyncObj(Object data);

    /**
     * get Database identity ID
     * @param data
     * @return
     */
    public Integer getIdentityID(Object data);

    /**
     * Check RESTFul Object is Delete status
     * @param data
     * @return
     */
    public boolean isDeleteData(Object data);

    /**
     * Delete Database data
     * @param data
     */
    public void onDeleteData(Object data);

    /**
     * Save database data
     * @param data
     */
    public Object onSaveData(Object dbData , Object data);

    /**
     * Get Data Compare Policy
     * @return
     */
    public DataMatch getDataMatchPolicy();
}
