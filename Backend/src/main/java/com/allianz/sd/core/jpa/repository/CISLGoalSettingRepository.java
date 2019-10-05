package com.allianz.sd.core.jpa.repository;

import com.allianz.sd.core.jpa.model.AgentYearDataIdentity;
import com.allianz.sd.core.jpa.model.CISLGoalSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CISLGoalSettingRepository extends JpaRepository<CISLGoalSetting,AgentYearDataIdentity> {

    @Query(nativeQuery = true,value = "\n" +
            "select distinct a.* from tw_lh_sd_CISL_goal_setting a , tw_lh_sd_goal_setting b\n" +
            "where 1=1\n" +
            "and a.organizationalunit = b.organizationalunit\n" +
            "and a.datayear = b.datayear\n" +
            "and a.goalsettingstartdate <> b.goalsettingstartdate\n" +
            "\n" +
            "union\n" +
            "\n" +
            "select * from tw_lh_sd_CISL_goal_setting\n" +
            "where 1=1\n" +
            "and organizationalunit = :organizationalunit\n" +
            "and datayear = :dataYear\n" +
            "and agentid not in (select agentid from tw_lh_sd_goal_setting where 1=1 and organizationalunit = :organizationalunit and datayear = :dataYear)\n")
    public List<CISLGoalSetting> findAdjustGoalData(
            @Param("dataYear") int dataYear,
            @Param("organizationalunit") String organizationalunit
    );
}
