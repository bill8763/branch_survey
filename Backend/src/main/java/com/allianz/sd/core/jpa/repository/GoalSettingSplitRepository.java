package com.allianz.sd.core.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.allianz.sd.core.jpa.model.GoalSettingSplit;
import com.allianz.sd.core.jpa.model.GoalSettingSplitIdentity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 */
public interface GoalSettingSplitRepository extends JpaRepository<GoalSettingSplit,GoalSettingSplitIdentity> {

    @Query("SELECT a from GoalSettingSplit a "
            + "where 1=1 "
            + "and goalSettingSeq = :goalSettingSeq "
            + "order by SplitVersion desc")
    public List<GoalSettingSplit> getGoalSettingSplitVersion(@Param("goalSettingSeq") Integer goalSettingSeq);

    
    @Modifying()
    @Query("UPDATE GoalSettingSplit a SET topVersion = 'N' "
    		+ "Where 1=1 "
    		+ "and goalSettingSeq = :goalSettingSeq "
    		)
    public int updateTopVersion2N(@Param("goalSettingSeq") Integer goalSettingSeq);
}
