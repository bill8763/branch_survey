package com.allianz.sd.core.version;

import com.allianz.sd.core.service.bean.APPUpdateVersion;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/4/18
 * Time: 上午 9:50
 * To change this template use File | Settings | File Templates.
 */
public interface APPUpdateVersionMgr {
    public APPUpdateVersion getAppUpdateVersion(String version,String agentID,String deviceSystem);
}
