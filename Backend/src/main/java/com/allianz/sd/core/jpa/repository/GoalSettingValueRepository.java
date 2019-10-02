package com.allianz.sd.core.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.allianz.sd.core.jpa.model.GoalSettingValue;
import com.allianz.sd.core.jpa.model.GoalSettingValueIdentity;

public interface GoalSettingValueRepository extends JpaRepository<GoalSettingValue,GoalSettingValueIdentity> {

	@Query("SELECT a from GoalSettingValue a "
			+ "where 1=1 "
			+ "and goalSettingSeq = :goalSettingSeq ")
	public List<GoalSettingValue> getGoalSettingValue(	@Param("goalSettingSeq") Integer goalSettingSeq);

	
	@Query("SELECT a from GoalSettingValue a join GoalSettingVersion b on a.identity.goalSettingSeq = b.goalSettingSeq "
			+ "where 1=1 "
			+ "and b.topVersion = 'Y' "
			+ "and b.agentID = :agentID "
			+ "and b.organizationalUnit = :organizationalUnit "
			+ "and b.dataYear = :dataYear ")
	public List<GoalSettingValue> getApprovedGoalSettingValue(@Param("agentID") String agentID ,
			@Param("organizationalUnit") String organizationalUnit,
			@Param("dataYear") Integer dataYear);

	@Query("select b from \n"
			+ " GoalSettingVersion a , GoalSettingValue b  where 1=1 \n"
			+ " and a.goalSettingSeq = b.identity.goalSettingSeq \n"
			+ " and a.organizationalUnit = :Unit \n"
			+ " and a.agentID = :AgentID \n"
			+ " and a.dataYear = :TodayYear \n"
			+ " and a.topVersion = 'Y' ")
	public  List<GoalSettingValue> getYearGoal(@Param("TodayYear") int TodayYear, @Param("Unit") String Unit,
											   @Param("AgentID") String AgentID);

}
