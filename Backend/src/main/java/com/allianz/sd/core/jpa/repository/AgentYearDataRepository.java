package com.allianz.sd.core.jpa.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.jpa.model.AgentYearDataIdentity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 12:41
 * To change this template use File | Settings | File Templates.
 */
public interface AgentYearDataRepository extends JpaRepository<AgentYearData, AgentYearDataIdentity> {

    public List<AgentYearData> findByIdentityDataYearAndIdentityOrganizationalUnit(int DataYear, String organizationalUnit);

    @Query("select a from AgentYearData a where 1=1 " +
            "and a.identity.dataYear = :Year " +
            "and a.identity.organizationalUnit = :OrganizationalUnit " +
            "and a.appUseMode in ('AG','AL')")
    public List<AgentYearData> findIsAgents(
            @Param("Year") int year ,
            @Param("OrganizationalUnit") String organizationalUnit);
}
