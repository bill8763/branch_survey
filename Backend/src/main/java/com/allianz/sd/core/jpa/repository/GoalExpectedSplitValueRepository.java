package com.allianz.sd.core.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.allianz.sd.core.jpa.model.GoalExpectedSplitValue;
import com.allianz.sd.core.jpa.model.GoalExpectedSplitValueIdentity;
import com.allianz.sd.core.jpa.model.GoalExpectedValue;


/**
 */
public interface GoalExpectedSplitValueRepository extends JpaRepository<GoalExpectedSplitValue,GoalExpectedSplitValueIdentity> {

	@Query(nativeQuery =  true , value = "select NVL(sum(c.SetValue),0) from\n" +
			"TW_LH_SD_Goal_Expected_Version a ,TW_LH_SD_Goal_Expected_Split_Value c\n" +
			"where 1=1  and a.GoalExpectedSeq = c.GoalExpectedSeq \n" +
			"and a.AgentID = :AgentID\n" +
			"and a.DataYear = :TodayYear\n" +
			"and a.OrganizationalUnit=:Unit\n" +
			"and c.MappingID='Recruitment' \n" +
			"and upper(c.TimeBase)='QUARTER' ")
	public int getYearGoalExpectedTotal(
			@Param("TodayYear") int TodayYear,
			@Param("AgentID") String AgentID,
			@Param("Unit") String Unit);


	//取得Leader底下InDirect的GoalExpected每季加總
	@Query("select c from\n" +
			"GoalExpectedVersion b , GoalExpectedSplitValue c\n" +
			"where 1=1  \n" +
			"and b.goalExpectedSeq = c.identity.goalExpectedSeq \n" +
			"and b.agentID in (select distinct a.identity.agentID from AgentYearData a , AgencyPlanDetail b\n" +
			"where 1=1\n" +
			"and a.goalSigningSupervisor = :AgentID\n" +
			"and a.identity.dataYear = :TodayYear\n" +
			"and a.identity.agentID = b.identity.agentID\n" +
			"and b.identity.dataYear = :TodayYear\n" +
			"and b.identity.displayBlock = 'INDIRECT')\n" +
			"and b.dataYear = :TodayYear\n" +
			"and UPPER(c.identity.timeBase) = 'QUARTER'\n" +
			"and c.identity.mappingID='Recruitment' \n")
	public List<GoalExpectedSplitValue> getYearSubordinateGoalExpected(
			@Param("TodayYear") int TodayYear,
			@Param("AgentID") String agentID);

	@Query("select distinct a.goalSigningSupervisor from AgentYearData a , AgencyPlanDetail b\n" +
			"where 1=1\n" +
			"and a.identity.agentID = :AgentID\n" +
			"and a.identity.dataYear = :TodayYear\n" +
			"and a.identity.organizationalUnit = :organizationalUnit\n" +
			"and a.goalSigningSupervisor = b.identity.agentID\n" +
			"and b.identity.dataYear = :TodayYear\n" +
			"and b.identity.displayBlock = 'INDIRECT'\n" +
			"and b.identity.organizationalUnit = :organizationalUnit")
	public List<String> getBottomupYearSubordinateLeaders(
			@Param("TodayYear") int TodayYear,
			@Param("AgentID") String AgentID,
			@Param("organizationalUnit") String organizationalUnit);
	
	//取得Leader自己的GoalExpected每季加總
	@Query("select c from\n" +
			"GoalExpectedVersion b , GoalExpectedSplitValue c\n" +
			"where 1=1  \n" +
			"and b.goalExpectedSeq = c.identity.goalExpectedSeq \n" +
			"and b.agentID =:agentID \n" +
			"and b.dataYear = :TodayYear\n" +
			"and UPPER(c.identity.timeBase) = 'QUARTER'\n" +
			"and c.identity.mappingID='Recruitment' \n" +
			"order by c.identity.timeBaseSeq")
	public List<GoalExpectedSplitValue> getYearMasterGoalExpected(@Param("TodayYear") int TodayYear,@Param("agentID") String agentID);

	
	public List<GoalExpectedSplitValue> findByIdentityGoalExpectedSeq(Integer goalExpectedSeq);

}
