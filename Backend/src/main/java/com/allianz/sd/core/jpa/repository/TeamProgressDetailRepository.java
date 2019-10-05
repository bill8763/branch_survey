package com.allianz.sd.core.jpa.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.allianz.sd.core.jpa.model.ProgressIdentity;
import com.allianz.sd.core.jpa.model.TeamProgressDetail;
import org.springframework.transaction.annotation.Transactional;


/**
 */
public interface TeamProgressDetailRepository extends JpaRepository<TeamProgressDetail,ProgressIdentity> {

	@Transactional
	@Modifying
	@Query("delete from TeamProgressDetail b \n"+
			"where 1=1 \n"+
			"and b.identity.dataYear =:dataYear\n" +
			"and b.identity.organizationalUnit =:organizationalUnit")
	public int deleteTeamProgressDetail(
			@Param("dataYear")int dataYear,
			@Param("organizationalUnit") String organizationalUnit
	);

	//query personal progress by organizationUnit,agentID,year
    @Query("SELECT a from TeamProgressDetail a "
			+ "where 1=1 "
			+ "and agentID = :agentID "
			+ "and organizationalUnit = :organizationalUnit "
			+ "and dataYear = :dataYear "
			+ "order by dataYear , displayOrder")
	public List<TeamProgressDetail> getTeamProgressDetail(@Param("agentID") String agentID ,
			@Param("organizationalUnit") String organizationalUnit,
			@Param("dataYear") Integer dataYear);

//	@Transactional
//	@Modifying
//	@Query("update TeamProgressDetail a set goal =:goal \n"+
//			"where a.identity.subordinateAgentID = :agentID \n"+
//			"and a.identity.dataType =:dataType \n"+
//			"and a.identity.organizationalUnit =:organizationalUnit \n"+
//			"and a.identity.timeBase = 'Year'\n" +
//			"and a.identity.directUnitType =:displayBlock \n" +
//			"and a.identity.dataYear =:dataYear")
//	public int updateYearGoal(@Param("goal") long goal,
//						  @Param("agentID")String agentID,
//						  @Param("dataType")String dataType,
//						  @Param("displayBlock")String displayBlock,
//						  @Param("organizationalUnit")String organizationalUnit,
//						  @Param("dataYear")int dataYear);
//
//
//	@Transactional
//	@Modifying
//	@Query("update TeamProgressDetail a \n"+
//			"set a.forecast = a.forecast + :difference \n"+
//			"where a.identity.subordinateAgentID in(:subordinateAgentID) \n"+
//			"and a.identity.dataType =:dataType \n"+
//			"and a.identity.organizationalUnit =:organizationalUnit \n"+
//			"and a.identity.directUnitType = 'INDIRECT' \n" +
//			"and a.identity.dataYear =:dataYear \n"+
//			"and a.identity.timeBase =:timeBase ")
//	public int updateInDirectForecast(
//			@Param("difference")long difference,
//			@Param("subordinateAgentID")List<String> subordinateAgentID,
//			@Param("dataType")String dataType,
//			@Param("organizationalUnit")String organizationalUnit,
//			@Param("dataYear")int dataYear,
//			@Param("timeBase")String timeBase);
//
//
//
//	@Transactional
//	@Modifying
//	@Query("update TeamProgressDetail a \n"+
//			"set a.forecast = a.forecast + :difference \n"+
//			"where a.identity.subordinateAgentID = :agentID \n"+
//			"and a.identity.dataType =:dataType \n"+
//			"and a.identity.organizationalUnit =:organizationalUnit \n"+
//			"and a.identity.directUnitType = 'DIRECT' \n" +
//			"and a.identity.dataYear =:dataYear \n"+
//			"and a.identity.timeBase =:timeBase ")
//	public int updateDirectForecast(
//			@Param("difference")long difference,
//			@Param("agentID")String agentID,
//			@Param("dataType")String dataType,
//			@Param("organizationalUnit")String organizationalUnit,
//			@Param("dataYear")int dataYear,
//			@Param("timeBase")String timeBase);
//
//
//	@Transactional
//	@Modifying
//	@Query(nativeQuery = true , value="update TW_LH_SD_TEAM_PROGRESS_DETAIL a \n"+
//			"set shortfall = CASE WHEN forecast-actual <0 THEN 0 \n" +
//			" ELSE forecast-actual \n" +
//			" END \n"+
//			"where a.subordinateAgentID in(:subordinateAgentID) \n"+
//			"and a.dataType in('FYFC','ANP') \n"+
//			"and a.timeBase in ('Month','Quarter')\n" +
//			"and a.organizationalUnit =:organizationalUnit \n"+
//			"and a.dataYear =:dataYear")
//	public int updateShartfallByForecast(
//			@Param("subordinateAgentID")List<String> subordinateAgentID,
//			@Param("organizationalUnit")String organizationalUnit,
//			@Param("dataYear")int dataYear);
//
//
//	@Transactional
//	@Modifying
//	@Query(nativeQuery = true , value="update TW_LH_SD_TEAM_PROGRESS_DETAIL a \n"+
//			"set shortfall = CASE WHEN goal - Actual <0 THEN 0 \n" +
//			"  ELSE goal - Actual \n" +
//			"  END \n"+
//			"where a.subordinateAgentID in(:subordinateAgentID) \n"+
//			"and a.dataType in('FYFC','ANP','Manpower','Recruitment') \n"+
//			"and a.timeBase = 'Year'\n" +
//			"and a.organizationalUnit =:organizationalUnit \n"+
//			"and a.dataYear =:dataYear")
//	public int updateShartfallByGoal(
//			@Param("subordinateAgentID")List<String> subordinateAgentID,
//			@Param("organizationalUnit")String organizationalUnit,
//			@Param("dataYear")int dataYear);
//
//	@Transactional
//	@Modifying
//	@Query("update TeamProgressDetail a set goal =:manpower \n"+
//			"where a.identity.subordinateAgentID = :subordinateAgentID \n"+
//			"and a.identity.organizationalUnit =:organizationalUnit \n"+
//			"and a.identity.dataType = 'Manpower' \n" +
//			"and a.identity.timeBase = 'Year'\n" +
//			"and a.identity.dataYear =:dataYear")
//	public int updateYearManpowerGoal(
//			@Param("manpower")long manpower,
//			@Param("subordinateAgentID")String subordinateAgentID,
//			@Param("organizationalUnit")String organizationalUnit,
//			@Param("dataYear")int dataYear);
//
//	@Transactional
//	@Modifying
//	@Query("update TeamProgressDetail a set goal =:recruitment \n"+
//			"where a.identity.subordinateAgentID = :subordinateAgentID \n"+
//			"and a.identity.organizationalUnit =:organizationalUnit \n"+
//			"and a.identity.dataType = 'Recruitment' \n" +
//			"and a.identity.timeBase = 'Year'\n" +
//			"and a.identity.dataYear =:dataYear")
//	public int updateYearRecruitmentGoal(
//			@Param("recruitment")long manpower,
//			@Param("subordinateAgentID")String subordinateAgentID,
//			@Param("organizationalUnit")String organizationalUnit,
//			@Param("dataYear")int dataYear);


	@Transactional
	@Modifying
	@Query("update TeamProgressDetail a set forecast = :forecast \n"+
			"where a.identity.subordinateAgentID in (:subordinateAgentID) \n"+
			"and a.identity.organizationalUnit =:organizationalUnit \n"+
			"and a.identity.directUnitType = 'INDIRECT' \n" +
			"and a.identity.dataType = 'Recruitment' \n" +
			"and a.identity.timeBase = :timeBase\n" +
			"and a.identity.dataYear =:dataYear")
	public int updateForecastRecruitment(
			@Param("forecast")long forecast,
			@Param("subordinateAgentID")String subordinateAgentID,
			@Param("organizationalUnit")String organizationalUnit,
			@Param("dataYear")int dataYear,
			@Param("timeBase")String timeBase);

	@Transactional
	@Modifying
	@Query(nativeQuery = true , value = "update TW_LH_SD_Team_Progress_Detail \n" +
			"set shortfall = CASE WHEN forecast - Actual <0 THEN 0 else forecast - Actual end\n" +
			"where subordinateAgentID = :subordinateAgentID \n" +
			"and organizationalUnit =:organizationalUnit \n" +
			"and dataType = 'Recruitment' \n" +
			"and timeBase in ('Month','Quarter')\n" +
			"and dataYear =:dataYear")
	public int updateShartfallRecruitment(
			@Param("subordinateAgentID")String subordinateAgentID,
			@Param("organizationalUnit")String organizationalUnit,
			@Param("dataYear")int dataYear);
}
