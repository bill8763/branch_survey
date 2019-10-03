package com.allianz.sd.core.jpa.repository;

import com.allianz.sd.core.jpa.model.AgencyPlan;
import com.allianz.sd.core.jpa.model.AgencyPlanIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
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
public interface AgencyPlanRepository extends JpaRepository<AgencyPlan, AgencyPlanIdentity> {

    @Query("select a from AgencyPlan a , AgencyPlanPile b\n" +
            "where a.identity.organizationalUnit = b.identity.organizationalUnit\n" +
            "and a.identity.dataYear = b.identity.dataYear\n" +
            "and a.identity.agentID = b.identity.leaderAgentID\n" +
            "and b.identity.agentID = :agentID\n" +
            "and b.identity.dataYear = :dataYear\n" +
            "")
    public List<AgencyPlan> findBottomupAgencyPlan(
            @Param("agentID") String agentID,
            @Param("dataYear") int dataYear
    );

    @Query("select a from AgencyPlan a " +
            "where 1=1 " +
            "and a.identity.agentID = :AgentID " +
            "and a.identity.dataYear = :TodayYear " +
            "and a.identity.organizationalUnit = :OrganizationalUnit ")
    public AgencyPlan getAgencyPlan(
            @Param("AgentID") String AgentID,
            @Param("TodayYear") int TodayYear,
            @Param("OrganizationalUnit") String OrganizationalUnit);

    @Query("select a from AgencyPlan a " +
            "where 1=1 " +
            "and a.identity.dataYear = :TodayYear " +
            "and a.identity.organizationalUnit = :OrganizationalUnit ")
    public List<AgencyPlan> getAgencyPlans(@Param("TodayYear") int TodayYear,
                                                   @Param("OrganizationalUnit") String OrganizationalUnit);
}
