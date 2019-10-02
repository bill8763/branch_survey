package com.allianz.sd.core.jpa.repository;

import com.allianz.sd.core.jpa.model.AgentUpdateVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;


/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 12:41
 * To change this template use File | Settings | File Templates.
 */
public interface AgentUpdateVersionRepository extends JpaRepository<AgentUpdateVersion,Integer> {

    @Query("SELECT a.uniqueID from AgentUpdateVersion a where 1=1 and a.agentID = :agentID and a.dataCategory = :dataCategory and a.dataTime > :dataTime order by a.dataTime desc")
    public List<Integer> getAgentLastSyncData(@Param("agentID") String agentID ,
                                              @Param("dataCategory") String dataCategory,
                                              @Param("dataTime") Date dataTime);

    @Query("SELECT a.dataTime from AgentUpdateVersion a where 1=1 and a.agentID = :agentID and a.dataCategory = :dataCategory")
    public Date getAgentLastSyncData(@Param("agentID") String agentID ,
                                              @Param("dataCategory") String dataCategory);

    @Modifying()
    @Transactional
    @Query("UPDATE AgentUpdateVersion a SET a.dataTime = :dataTime Where 1=1 and a.agentID = :agentID and a.dataCategory = :dataCategory and a.uniqueID = :uniqueID")
    public int updateAgentLastTme(
            @Param("dataTime") Date dataTime ,
            @Param("agentID") String agentID ,
            @Param("dataCategory") String dataCategory,
            @Param("uniqueID") Integer uniqueID);

    @Modifying()
    @Transactional
    @Query("delete from AgentUpdateVersion a Where 1=1 and a.agentID in :agentID and a.dataCategory = :dataCategory")
    public int clearAgentTableCache(
            @Param("agentID") Set<String> agentID ,
            @Param("dataCategory") String dataCategory);

    @Modifying()
    @Transactional
    @Query("delete from AgentUpdateVersion a Where 1=1 and a.dataCategory = :dataCategory")
    public int clearAgentTableCache(
            @Param("dataCategory") String dataCategory);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO TW_LH_SD_AgentUpdateVersion (DataTime,AgentID,DataCategory,UniqueID) VALUES (:dataTime,:agentID,:dataCategory,:uniqueID)",nativeQuery = true)
    public int insertAgentLastTme(
            @Param("dataTime") Date dataTime ,
            @Param("agentID") String agentID ,
            @Param("dataCategory") String dataCategory,
            @Param("uniqueID") Integer uniqueID);


}
