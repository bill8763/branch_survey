package com.allianz.sd.core.jpa.repository;

import com.allianz.sd.core.jpa.model.VersionControl;
import com.allianz.sd.core.jpa.model.VersionControlAgent;
import com.allianz.sd.core.jpa.model.VersionControlAgentIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;


/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 12:41
 * To change this template use File | Settings | File Templates.
 */
public interface VersionControlAgentRepository extends JpaRepository<VersionControlAgent,VersionControlAgentIdentity> {

    @Modifying()
    @Transactional
    @Query(nativeQuery = true , value = "DELETE from TW_LH_SD_VersionControlAgent where Version = :version")
    public int deleteVersion(@Param("version") String version );
}
