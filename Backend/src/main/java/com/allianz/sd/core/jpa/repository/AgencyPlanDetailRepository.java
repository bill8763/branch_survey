package com.allianz.sd.core.jpa.repository;

import com.allianz.sd.core.jpa.model.AgencyPlan;
import com.allianz.sd.core.jpa.model.AgencyPlanDetail;
import com.allianz.sd.core.jpa.model.AgencyPlanDetailIdentity;
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
public interface AgencyPlanDetailRepository extends JpaRepository<AgencyPlanDetail, AgencyPlanDetailIdentity> {


    @Query("select a from AgencyPlanDetail a " +
            "where 1=1 " +
            "and a.identity.dataYear = :TodayYear " +
            "and a.identity.organizationalUnit = :OrganizationalUnit ")
    public List<AgencyPlanDetail> findDetailAgents(@Param("TodayYear") int TodayYear,
                                                   @Param("OrganizationalUnit") String OrganizationalUnit);

    @Query("select a from AgencyPlanDetail a\n" +
            "where 1=1\n" +
            "and a.identity.dataYear = :dataYear\n" +
            "and a.identity.agentID = :agentID")
    public List<AgencyPlanDetail> findDetailByAgentID(
            @Param("dataYear") int dataYear,
            @Param("agentID") String agentID
    );

    @Query("select count(*)" +
            "   from AgencyPlanDetail" +
            "   where 1=1\n" +
            "   and dataYear = :TodayYear" +
            "   and agentID = :AgentID" +
            "   and displayBlock = 'DIRECT'")
    public int getManPowerCount(
            @Param("TodayYear") int TodayYear,
            @Param("AgentID") String AgentID);

    @Query("select a from AgencyPlanDetail a , AgencyPlanPile b\n" +
            "where a.identity.organizationalUnit = b.identity.organizationalUnit\n" +
            "and a.identity.dataYear = b.identity.dataYear\n" +
            "and a.subAgentID = b.identity.leaderAgentID\n" +
            "and b.identity.agentID = :agentID\n" +
            "and b.identity.dataYear = :dataYear\n" +
            "")
    public List<AgencyPlanDetail> findBottomupAgencyPlanDetail(
            @Param("agentID") String agentID,
            @Param("dataYear") int dataYear
    );


    @Query(value = "select NVL(sum(c.SetValue),0) from\n" +
            "TW_LH_SD_Goal_Setting_Version a , TW_LH_SD_Goal_Setting_Split b , TW_LH_SD_Goal_Setting_Split_Value c\n" +
            "where 1=1\n" +
            "and a.GoalSettingSeq = b.GoalSettingSeq\n" +
            "and a.AgentID in (select distinct a.AgentID from\n" +
            "            TW_LH_SD_Agency_Plan_Pile_Data a , TW_LH_SD_Agency_Plan_Detail b\n" +
            "            where 1=1\n" +
            "            and a.DataYear = b.DataYear\n" +
            "            and a.LeaderAgentID = b.AgentID\n" +
            "            and a.DataYear = :TodayYear\n" +
            "            and b.AgentId = :AgentID\n" +
            "            and b.CumulativeProc = 'Y')\n" +
            "and a.DataYear = :TodayYear\n" +
            "and a.TopVersion = 'Y'\n" +
            "and b.GoalSettingSeq = c.GoalSettingSeq\n" +
            "and b.SplitVersion = c.SplitVersion\n" +
            "and b.TopVersion = 'Y'\n" +
            "and UPPER(c.TimeBase) = 'MONTH'\n" +
            "and c.TimeBaseSeq >= :StartMonth\n" +
            "and c.MappingID = :MappingID",
            nativeQuery = true)
    public long getAgencyPlanMasterBottomupPlan(
            @Param("TodayYear") int TodayYear,
            @Param("AgentID") String AgentID,
            @Param("StartMonth") int startMonth,
            @Param("MappingID") String MappingID);
    
    
    //get ZH 底下人的 ZH List 
    @Query(value = "select DISTINCT a.subAgentID from AgencyPlanDetail a \n" + 
    		"where a.identity.dataYear = :dataYear \n" + 
    		"and a.identity.agentID = :agentID \n" + 
    		"and a.identity.displayBlock = 'INDIRECT' \n" + 
    		"and (a.subAgentJobGrade like '%GM%' or a.subAgentJobGrade like '%AVP%')")
    public List<String> getZHBottomList(@Param("dataYear")int dataYear,@Param("agentID")String agentID);
    
    //get CAO 底下人的 ZH List 
    @Query(value = "select DISTINCT a.subAgentID from AgencyPlanDetail a \n" + 
    		"where a.identity.dataYear = :dataYear \n" + 
    		"and a.identity.agentID = :agentID \n" + 
    		"and a.identity.displayBlock = 'INDIRECT' \n" + 
    		"and (a.subAgentJobGrade like '%ZH%')")
    public List<String> getCAOBottomList(@Param("dataYear")int dataYear,@Param("agentID")String agentID);
    
    //帶List get CAO 底下的 GM or AVG List 
    @Query(value = "select DISTINCT a.subAgentID from AgencyPlanDetail a \r\n" + 
    		"where a.identity.dataYear = :dataYear \n" + 
    		"and a.identity.agentID in (:agentList) \n" + 
    		"and a.identity.displayBlock = 'INDIRECT' \n" + 
    		"and (a.subAgentJobGrade like '%GM%' or a.subAgentJobGrade like '%AVP%') ")
    public List<String> getZHBottomListByList(@Param("dataYear")int dataYear,
    									@Param("agentList")List<String> agentList);
    
    
}
