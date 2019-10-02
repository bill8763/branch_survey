package com.allianz.sd.core.jpa.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.allianz.sd.core.jpa.model.MessageGoalStatus;

import java.util.List;


/**
 * @author bill8
 *
 */
public interface MessageGoalStatusRepository extends JpaRepository<MessageGoalStatus,Integer> {

	public MessageGoalStatus findByGoalSettingSeq(Integer goalSettingSeq);
}
