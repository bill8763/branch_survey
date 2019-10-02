package com.allianz.sd.core.jpa.repository;

import com.allianz.sd.core.jpa.model.VersionControl;
import com.allianz.sd.core.jpa.model.VersionControlIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 12:41
 * To change this template use File | Settings | File Templates.
 */
public interface VersionControlRepository extends JpaRepository<VersionControl,VersionControlIdentity> {

    @Query(nativeQuery = true,value = "select a.version,a.updatetype,a.applink from tw_lh_sd_versioncontrol a , TW_LH_SD_VersionControlAgent b\n" +
            "where 1=1\n" +
            "and a.version = b.version\n" +
            "and a.version >= :version\n" +
            "and b.agentID = :agentId\n" +
            "and a.isenabled = 'Y'\n" +
            "and a.devicesystem = :devicesystem\n" +
            "and ROWNUM < 2 \n" +
            "order by a.version desc")
    public List<Object[]> getAgentLastSyncData(@Param("version") String version ,
                                                     @Param("agentId") String agentId ,
                                              @Param("devicesystem") String devicesystem);

    @Query(nativeQuery = true,value = "select a.version,a.updatetype,a.applink from tw_lh_sd_versioncontrol a\n" +
            "where 1=1\n" +
            "and a.isenabled = 'Y'\n" +
            "and a.version >= :version\n" +
            "and a.devicesystem = :devicesystem\n" +
            "and ROWNUM < 2 \n" +
            "order by a.version desc")
    public List<Object[]> getLastVersion(@Param("version") String version,
                                                 @Param("devicesystem") String deviceSystem);
}
