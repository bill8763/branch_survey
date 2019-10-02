package com.allianz.sd.core.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.allianz.sd.core.jpa.model.GoalExpectedValue;
import com.allianz.sd.core.jpa.model.GoalExpectedValueIdentity;


/**
 */
public interface GoalExpectedValueRepository extends JpaRepository<GoalExpectedValue,GoalExpectedValueIdentity> {

	@Query("SELECT b "
			+ "from GoalExpectedVersion a join GoalExpectedValue b on (a.goalExpectedSeq = b.identity.goalExpectedSeq) "
			+ "where 1=1 "
			+ "and a.organizationalUnit = :organizationalUnit \r\n" 
			+ "and a.agentID = :agentID \r\n" 
			+ "and a.dataYear = :dataYear \r\n"
			+ " order by a.dataTime desc ")
	public List<GoalExpectedValue> getGoalExpectedValue(
			@Param("agentID") String agentID ,
			@Param("organizationalUnit") String organizationalUnit,
			@Param("dataYear") Integer dataYear
		);
	

	public List<GoalExpectedValue> findByIdentityGoalExpectedSeq(Integer goalExpectedSeq);
	
}
