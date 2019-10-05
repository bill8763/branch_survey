package com.allianz.sd.core.jpa.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.allianz.sd.core.jpa.model.ProgressIdentity;
import com.allianz.sd.core.jpa.model.TeamProgress;
import org.springframework.transaction.annotation.Transactional;


/**
 */
public interface TeamProgressRepository extends JpaRepository<TeamProgress,ProgressIdentity> {

	@Transactional
	@Modifying
	@Query("delete from TeamProgress b \n"+
			"where 1=1 \n"+
			"and b.identity.dataYear =:dataYear\n" +
			"and b.identity.organizationalUnit =:organizationalUnit")
	public int deleteTeamProgress(
			@Param("dataYear")int dataYear,
			@Param("organizationalUnit") String organizationalUnit
	);

	//query personal progress by organizationUnit,agentID,year
    @Query("SELECT a from TeamProgress a "
			+ "where 1=1 "
			+ "and agentID = :agentID "
			+ "and organizationalUnit = :organizationalUnit "
			+ "and dataYear = :dataYear "
			+ "order by dataYear desc")
	public List<TeamProgress> getTeamProgress(@Param("agentID") String agentID ,
			@Param("organizationalUnit") String organizationalUnit,
			@Param("dataYear") Integer dataYear);

//	@Transactional
//	@Modifying
//	@Query("update TeamProgress a \n"+
//			"set forecast = forecast + :difference \n"+
//			"where a.identity.agentID in(:subordinateAgentID) \n"+
//			"and a.identity.dataType =:dataType \n"+
//			"and a.identity.organizationalUnit =:organizationalUnit \n"+
//			"and a.identity.dataYear =:dataYear \n"+
//			"and a.identity.timeBase =:timeBase ")
//	public int updateForecast(
//			@Param("difference")long difference,
//			@Param("subordinateAgentID")List<String> subordinateAgentID,
//			@Param("dataType")String dataType,
//			@Param("organizationalUnit")String organizationalUnit,
//			@Param("dataYear")int dataYear,
//			@Param("timeBase")String timeBase);
//
//	@Transactional
//	@Modifying
//	@Query(nativeQuery = true , value="update TW_LH_SD_TEAM_PROGRESS a \n"+
//			"set shortfall = CASE WHEN forecast-actual <0 THEN 0 \n" +
//			" ELSE forecast-actual\r\n" +
//			" END \n"+
//			"where a.agentID in(:subordinateAgentID) \n"+
//			"and a.dataType in('FYFC','ANP') \n"+
//			"and a.timeBase in ('Month','Quarter')\n" +
//			"and a.organizationalUnit =:organizationalUnit \n"+
//			"and a.dataYear =:dataYear")
//	public int updateShartfallByForecast(
//			@Param("subordinateAgentID")List<String> subordinateAgentID,
//			@Param("organizationalUnit")String organizationalUnit,
//			@Param("dataYear")int dataYear);
//
//	@Transactional
//	@Modifying
//	@Query("update TeamProgress a set goal =:goal \n"+
//			"where a.identity.agentID = :agentID \n"+
//			"and a.identity.dataType =:dataType \n"+
//			"and a.identity.organizationalUnit =:organizationalUnit \n"+
//			"and a.identity.timeBase = 'Year'\n" +
//			"and a.identity.dataYear =:dataYear")
//	public int updateYearGoal(@Param("goal") long goal,
//							  @Param("agentID")String agentID,
//							  @Param("dataType")String dataType,
//							  @Param("organizationalUnit")String organizationalUnit,
//							  @Param("dataYear")int dataYear);
//
//	@Transactional
//	@Modifying
//	@Query(nativeQuery = true , value="update TW_LH_SD_TEAM_PROGRESS a \n"+
//			"set shortfall = CASE WHEN goal - Actual <0 THEN 0 \n" +
//			" ELSE goal - Actual \n" +
//			" END \n"+
//			"where a.agentID in(:subordinateAgentID) \n"+
//			"and a.dataType in('FYFC','ANP','Manpower','Recruitment') \n"+
//			"and a.timeBase = 'Year'\n" +
//			"and a.organizationalUnit =:organizationalUnit \n"+
//			"and a.dataYear =:dataYear")
//	public int updateShartfallByGoal(
//			@Param("subordinateAgentID")List<String> subordinateAgentID,
//			@Param("organizationalUnit")String organizationalUnit,
//			@Param("dataYear")int dataYear);


	@Transactional
	@Modifying
	@Query(nativeQuery = true , value = "update TW_LH_SD_TEAM_PROGRESS \n" +
			"set shortfall = CASE WHEN forecast - Actual <0 THEN 0 else forecast - Actual end\n" +
			"\t\t\twhere agentID = :subordinateAgentID \n" +
			"\t\t\tand organizationalUnit =:organizationalUnit \n" +
			"\t\t\tand dataType = 'Recruitment' \n" +
			"\t\t\tand timeBase in ('Month','Quarter')\n" +
			"\t\t\tand dataYear =:dataYear")
	public int updateShartfallRecruitment(
			@Param("subordinateAgentID")String subordinateAgentID,
			@Param("organizationalUnit")String organizationalUnit,
			@Param("dataYear")int dataYear);

	@Transactional
	@Modifying
	@Query("update TeamProgress a set forecast = :forecast \n"+
			"where a.identity.agentID = :subordinateAgentID \n"+
			"and a.identity.organizationalUnit =:organizationalUnit \n"+
			"and a.identity.dataType = 'Recruitment' \n" +
			"and a.identity.timeBase = :timeBase\n" +
			"and a.identity.dataYear =:dataYear")
	public int updateForecastRecruitment(
			@Param("forecast")long forecast,
			@Param("subordinateAgentID")String subordinateAgentID,
			@Param("organizationalUnit")String organizationalUnit,
			@Param("dataYear")int dataYear,
			@Param("timeBase")String timeBase);


}
