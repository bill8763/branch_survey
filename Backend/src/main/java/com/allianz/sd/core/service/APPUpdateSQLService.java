package com.allianz.sd.core.service;

import com.allianz.sd.core.InstanceCode;
import com.allianz.sd.core.service.bean.APPUpdateVersion;
import com.allianz.sd.core.version.APPUpdateVersionMgr;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/4/10
 * Time: 下午 8:36
 * To change this template use File | Settings | File Templates.
 */
@Service
public class APPUpdateSQLService {

//    @Value("${SND.APPUpdate.sql-dir}")
//    private String appUpdateSqlDir = null;
//
    @Autowired
    private BeanFactory beanFactory;
//
//    public void storeSQLFile(String version , File sqlFile) throws IOException {
//        File destFile = new File(appUpdateSqlDir + "/" + version + ".sql");
//        FileUtils.copyFile(sqlFile, destFile);
//    }
//
    public APPUpdateVersion getAppUpdateVersion(String version,String agentID,String deviceSystem) {
        APPUpdateVersionMgr appUpdateVersionMgr = (APPUpdateVersionMgr) beanFactory.getBean(InstanceCode.AppUpdateVersionMgr);

        return appUpdateVersionMgr.getAppUpdateVersion(version,agentID,deviceSystem);
    }
//
//    public List<String> loadSQLFiles(String version) throws IOException {
//        List<String> sqlLines = new ArrayList<String>();
//
//        File sqlFile = loadSQLFile(version);
//        if(sqlFile != null && sqlFile.exists()) {
//            String file = FileUtils.readFileToString(sqlFile);
//
//            String[] sqls = file.split(";\n");
//            for(String sql : sqls) {
//                sqlLines.add(sql);
//            }
//
//        }
//
//        return sqlLines;
//    }
//
//    public File loadSQLFile(String version) throws IOException {
//        File sqlFile = new File(appUpdateSqlDir + "/" + version + ".sql");
//
//        return sqlFile;
//    }
}
