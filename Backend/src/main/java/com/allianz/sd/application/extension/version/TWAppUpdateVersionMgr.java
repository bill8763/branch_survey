package com.allianz.sd.application.extension.version;

import com.allianz.sd.core.jpa.repository.VersionControlRepository;
import com.allianz.sd.core.service.bean.APPUpdateVersion;
import com.allianz.sd.core.version.APPUpdateVersionMgr;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/4/18
 * Time: 上午 10:17
 * To change this template use File | Settings | File Templates.
 */

public class TWAppUpdateVersionMgr implements APPUpdateVersionMgr {

    @Autowired
    private VersionControlRepository versionControlRepository;

    @Override
    public APPUpdateVersion getAppUpdateVersion(String version, String agentID, String deviceSystem) {
        APPUpdateVersion appUpdateVersion = new APPUpdateVersion();

        List<Object[]> objects = versionControlRepository.getAgentLastSyncData(version,agentID,deviceSystem);

        if(objects.size() != 0) {

            Object[] obj = objects.get(0);
            String ver = String.valueOf(obj[0]);
            String updatetype = String.valueOf(obj[1]);
            String applink = String.valueOf(obj[2]);

            appUpdateVersion.setVersion(ver);
            appUpdateVersion.setUpdateType(updatetype);
            appUpdateVersion.setApplink(applink);
        }
        else {
            appUpdateVersion.setVersion(version);
        }


        return appUpdateVersion;
    }
}
