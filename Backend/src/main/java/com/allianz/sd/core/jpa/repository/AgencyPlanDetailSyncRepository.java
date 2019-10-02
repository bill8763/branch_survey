package com.allianz.sd.core.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.allianz.sd.core.jpa.model.AgencyPlanDetailSync;
import com.allianz.sd.core.jpa.model.AgencyPlanSyncIdentity;


/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 12:41
 * To change this template use File | Settings | File Templates.
 */
public interface AgencyPlanDetailSyncRepository extends JpaRepository<AgencyPlanDetailSync,AgencyPlanSyncIdentity> {

	@Transactional
	@Modifying
	@Query("delete from AgencyPlanDetailSync b \n"+
			"where 1=1 \n"+
			"and b.identity.dataYear =:dataYear\n" +
			"and b.identity.organizationalUnit =:organizationalUnit")
	public int deleteAgencyPlanDetailSync(
			@Param("dataYear")int dataYear,
			@Param("organizationalUnit") String organizationalUnit
	);

	@Query("select NVL(sum(manpower),0),NVL(sum(recruitment),0) "+
			" from AgencyPlanDetailSync "+
			" where 1=1 "+
			" and organizationalUnit = :Unit "+
			" and dataYear = :TodayYear "+
			" and agentID = :AgentID "+
			" and dataType = 'FYFC' and isDrillDown ='Y' ")
	public List<Object[]> getManpowerAndRecruitment(
			@Param("Unit") String Unit,
			@Param("TodayYear") int TodayYear,
			@Param("AgentID") String AgentID);

	
	@Query("SELECT a from AgencyPlanDetailSync a "
			+ "where 1=1 "
			+ "and agentID = :agentID "
			+ "and organizationalUnit = :organizationalUnit "
			+ "and dataYear = :dataYear "
			+ "order by dataYear , displayOrder")
	public List<AgencyPlanDetailSync> getAgencyPlanDetailSync(@Param("agentID") String agentID ,
			@Param("organizationalUnit") String organizationalUnit,
			@Param("dataYear") Integer dataYear);

	@Transactional
	@Modifying
	@Query(nativeQuery =  true , value = "update tw_lh_sd_agency_plan_detail_sync \n" +
			"set isapprove = :isApprove \n" +
			"where datayear = :dataYear \n" +
			"and organizationalUnit = :organizationalUnit " +
			"and agentid = :agentID\n" +
			"and subordinateagentid = :subAgentID")
	public int updateAgencyPlanDetailSyncApproveStatus(
			@Param("isApprove") String isApprove,
			@Param("dataYear")int dataYear,
			@Param("organizationalUnit")String organizationalUnit,
			@Param("agentID")String agentID,
			@Param("subAgentID")String subAgentID
	);
	

//	@Transactional
//	@Modifying
//	@Query("update AgencyPlanDetailSync a set goal =:goal \n"+
//			"where a.subordinateAgentID = :agentID \n"+
//			"and a.identity.dataType =:dataType \n"+
//			"and a.identity.organizationalUnit =:organizationalUnit \n"+
//			"and a.identity.displayBlock ='INDIRECT' \n" +
//			"and a.identity.dataYear =:dataYear")
//	public int updateInDirectGoal(@Param("goal")long goal,
//									  @Param("agentID")String agentID,
//									  @Param("dataType")String dataType,
//									  @Param("organizationalUnit")String organizationalUnit,
//									  @Param("dataYear")int dataYear);
//
//	@Transactional
//	@Modifying
//	@Query("update AgencyPlanDetailSync a set goal =:goal \n"+
//			"where a.subordinateAgentID = :agentID \n"+
//			"and a.identity.dataType =:dataType \n"+
//			"and a.identity.organizationalUnit =:organizationalUnit \n"+
//			"and a.identity.displayBlock = 'DIRECT' \n" +
//			"and a.identity.dataYear =:dataYear")
//	public int updateSelfGoal(@Param("goal")long goal,
//						  @Param("agentID")String agentID,
//						  @Param("dataType")String dataType,
//						  @Param("organizationalUnit")String organizationalUnit,
//						  @Param("dataYear")int dataYear);
//
//	@Transactional
//	@Modifying
//	@Query("update AgencyPlanDetailSync a set manpower = manpower + :manpowerDiff, recruitment = recruitment + :recruitmentDiff \n"+
//			"where a.subordinateAgentID = :agentID \n"+
//			"and a.identity.organizationalUnit =:organizationalUnit \n"+
//			"and a.identity.displayBlock = 'INDIRECT' \n" +
//			"and a.identity.dataYear =:dataYear")
//	public int updateManpowerAndRecruitment(
//									  @Param("manpowerDiff")long manpowerDiff,
//									  @Param("recruitmentDiff")long recruitmentDiff,
//									  @Param("agentID")String agentID,
//									  @Param("organizationalUnit")String organizationalUnit,
//									  @Param("dataYear")int dataYear);
//
//
//	@Transactional
//	@Modifying
//	@Query("update AgencyPlanDetailSync a \n"+
//			"set forecast = forecast + :difference , plan = plan + :difference \n"+
//			"	,caseCount = ROUND( NVL( (plan + :difference)/NULLIF(percase,0),0)) "+
//			"where a.subordinateAgentID in(:subordinateAgentID) \n"+
//			"and a.identity.dataType =:dataType \n"+
//			"and a.identity.organizationalUnit =:organizationalUnit \n"+
//			"and a.identity.displayBlock =:displayBlock \n"+
//			"and a.identity.dataYear =:dataYear")
//	public int updateForecastPlanByDisPlayBlock(
//			@Param("difference")long difference,
//			@Param("subordinateAgentID")List<String> subordinateAgentID,
//			@Param("dataType")String dataType,
//			@Param("organizationalUnit")String organizationalUnit,
//			@Param("displayBlock")String displayBlock,
//			@Param("dataYear")int dataYear);
//
//	/**
//	 * ROUND: 四捨五入
//	 * NVL: null then value
//	 * nullif: if 0 then null
//	 * caseCount = plan / percase
//	 */
//	@Transactional
//	@Modifying
//	@Query("update AgencyPlanDetailSync a \n"+
//			"set forecast = forecast + :difference , plan = plan + :difference \n"+
//			"	,percase = :percase \n"+
//			"	,caseCount = ROUND( NVL( (plan + :difference)/NULLIF(:percase,0),0)) "+
//			"where a.subordinateAgentID =(:subordinateAgentID) \n"+
//			"and a.identity.dataType =:dataType \n"+
//			"and a.identity.organizationalUnit =:organizationalUnit \n"+
//			"and a.identity.displayBlock =:displayBlock \n"+
//			"and a.identity.dataYear =:dataYear")
//	public int updateForecastAndPercase(
//			@Param("difference")long different,
//			@Param("percase")long percase,
//			@Param("subordinateAgentID")String agentID,
//			@Param("dataType")String dataType,
//			@Param("organizationalUnit")String organizationalUnit,
//			@Param("displayBlock")String displayBlock,
//			@Param("dataYear")int dataYear);
	


	
}
