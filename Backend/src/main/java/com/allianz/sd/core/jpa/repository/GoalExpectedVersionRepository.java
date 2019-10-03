package com.allianz.sd.core.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.allianz.sd.core.jpa.model.GoalExpectedVersion;


/**
 */
public interface GoalExpectedVersionRepository extends JpaRepository<GoalExpectedVersion,Integer> {

	public Optional<GoalExpectedVersion> findByOrganizationalUnitAndAgentIDAndDataYear(String organizationUnit, String agentID, Integer dataYear);
	
}
