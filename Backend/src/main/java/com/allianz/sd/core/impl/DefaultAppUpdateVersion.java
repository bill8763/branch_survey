package com.allianz.sd.core.impl;

import com.allianz.sd.core.jpa.model.VersionControl;
import com.allianz.sd.core.jpa.repository.VersionControlRepository;
import com.allianz.sd.core.service.bean.APPUpdateVersion;
import com.allianz.sd.core.version.APPUpdateVersionMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/4/18
 * Time: 上午 9:51
 * To change this template use File | Settings | File Templates.
 */
public class DefaultAppUpdateVersion implements APPUpdateVersionMgr {

    @Autowired
    private VersionControlRepository versionControlRepository;

    @Override
    public APPUpdateVersion getAppUpdateVersion(String version,String agentID,String deviceSystem) {
        APPUpdateVersion appUpdateVersion = new APPUpdateVersion();

        List<Object[]> objects = versionControlRepository.getLastVersion(version, deviceSystem);
        if(objects.size() == 0) {
            appUpdateVersion.setVersion(version);
        }
        else {
            Object[] obj = objects.get(0);
            String ver = String.valueOf(obj[0]);
            String updatetype = String.valueOf(obj[1]);
            String applink = String.valueOf(obj[2]);

            appUpdateVersion.setVersion(ver);
            appUpdateVersion.setApplink(applink);
            appUpdateVersion.setUpdateType(updatetype);
        }

        return appUpdateVersion;
    }
}
