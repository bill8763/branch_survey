package com.allianz.sd.core.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.allianz.sd.core.jpa.model.GoalSettingVersion;
import org.springframework.transaction.annotation.Transactional;


/**
 */
public interface GoalSettingVersionRepository extends JpaRepository<GoalSettingVersion,Integer> {

	@Query("SELECT count(a.goalSettingSeq) from GoalSettingVersion a "
			+ "where 1=1 "
			+ "and agentID = :agentID "
			+ "and organizationalUnit = :organizationalUnit "
			+ "and submitStatus = 'Y' "
			+ "order by GoalSettingSeq desc")
	public Integer getFirstUseAPP(@Param("agentID") String agentID ,
												   @Param("organizationalUnit") String organizationalUnit);

	@Query("SELECT count(a.goalSettingSeq) from GoalSettingVersion a "
			+ "where 1=1 "
			+ "and agentID = :agentID "
			+ "and organizationalUnit = :organizationalUnit "
			+ "and submitStatus = 'Y' "
			+ "and dataYear = :dataYear "
			+ "order by GoalSettingSeq desc")
	public Integer isFirstTime(@Param("agentID") String agentID ,
												   @Param("organizationalUnit") String organizationalUnit,
												   @Param("dataYear") int dataYear);

	@Query("SELECT a from GoalSettingVersion a "
			+ "where 1=1 "
			+ "and agentID = :agentID "
			+ "and organizationalUnit = :organizationalUnit "
			+ "and dataYear = :dataYear "
			+ "order by GoalSettingSeq desc")
	public List<GoalSettingVersion> getGoalSettingVersion(@Param("agentID") String agentID ,
														  @Param("organizationalUnit") String organizationalUnit,
														  @Param("dataYear") Integer dataYear);

	@Query("SELECT a from GoalSettingVersion a "
			+ "where 1=1 "
			+ "and goalSettingSeq = :GoalSettingSeq "
			+ "order by GoalSettingSeq desc")
	public GoalSettingVersion getGoalSettingVersion(@Param("GoalSettingSeq") int GoalSettingSeq);


	@Query("SELECT a from GoalSettingVersion a "
			+ "where 1=1 "
			+ "and agentID = :agentID "
			+ "and organizationalUnit = :organizationalUnit "
			+ "and dataYear = :dataYear "
			+ "and TopVersion = 'Y' "
			+ "order by GoalSettingSeq desc")
	public GoalSettingVersion getGoalSettingIsTopVersion(@Param("agentID") String agentID ,
														 @Param("organizationalUnit") String organizationalUnit,
														 @Param("dataYear")  Integer dataYear);


	@Modifying()
	@Query("UPDATE GoalSettingVersion a SET a.topVersion = 'N' "
			+ "Where 1=1 "
			+ "and a.agentID = :agentID "
			+ "and a.organizationalUnit = :organizationalUnit "
			+ "and a.dataYear = :dataYear")
	public int updateTopVersion2N(
			@Param("agentID") String agentID ,
			@Param("organizationalUnit") String organizationalUnit,
			@Param("dataYear")  Integer dataYear);

	@Query(value = " SELECT b "
			+ " FROM GoalSetting a, GoalSettingVersion b "
			+ " WHERE 1=1 "
			+ " AND a.identity.agentID = b.agentID "
			+ " AND a.identity.dataYear = b.dataYear "
			+ " AND b.topVersion = 'Y' "
			+ " AND (a.goalSettingStartDate != b.goalSettingStartDate or b.goalSettingStartDate is null) "
			+ " AND b.status != 'OVERDUE' "
			+ " AND a.goalSettingDeadline < current_date ")
	public List<GoalSettingVersion> findOverDeadlineGoal();

	public List<GoalSettingVersion> findByStatus(String status);

	public List<GoalSettingVersion> findByStatusAndDataYear(String status,int year);

	public List<GoalSettingVersion> findByStatusAndDataYearAndAgentID(String status,int year, String agentID);

}
