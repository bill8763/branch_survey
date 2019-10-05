package com.allianz.sd.core.jpa.repository;

import java.util.List;

import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.allianz.sd.core.jpa.model.GoalSettingSplitValue;
import com.allianz.sd.core.jpa.model.GoalSettingSplitValueIdentity;


/**
 */
public interface GoalSettingSplitValueRepository extends JpaRepository<GoalSettingSplitValue,GoalSettingSplitValueIdentity> {


	@Query("select c from\r\n" +
			"        GoalSettingVersion a , GoalSettingSplit b , GoalSettingSplitValue c\r\n" +
			"        where 1=1\r\n" +
			"        and a.goalSettingSeq = b.identity.goalSettingSeq\r\n" +
			"        and a.goalSettingSeq = c.identity.goalSettingSeq\r\n" +
			"        and b.identity.splitVersion = c.identity.splitVersion\r\n" +
			"        and a.organizationalUnit = :organizationalUnit\r\n" +
			"        and a.agentID = :agentID \r\n" +
			"        and a.dataYear = :dataYear \r\n" +
			"        and a.topVersion = 'Y'\r\n" +
			"        and b.topVersion = 'Y'\r\n" +
			"        and UPPER(c.identity.timeBase) = 'MONTH'\r\n" +
			"        order by c.identity.timeBaseSeq ")
	public List<GoalSettingSplitValue> getPlanByMonth(
			@Param("organizationalUnit") String organizationalUnit,
			@Param("agentID") String agentID,
			@Param("dataYear") Integer dataYear);


	@Query(nativeQuery=true,value="select c.TimeBaseSeq,c.SetValue from\n" +
			"TW_LH_SD_Goal_Setting_Version a ,  ( select * from \r\n" + 
			"        (\r\n" + 
			"        select b1.GoalSettingSeq,b1.SplitVersion, ROW_NUMBER() Over (Partition By b1.GoalSettingSeq Order By b1.splitVersion Desc) As row_num \r\n" + 
			"        from TW_LH_SD_Goal_Setting_Split  b1\r\n" + 
			"        ) b2\r\n" + 
			"    ) b , TW_LH_SD_Goal_Setting_Split_Value c\n" +
			"where 1=1\n" +
			"and a.GoalSettingSeq = b.GoalSettingSeq\n" +
			"and a.GoalSettingSeq = c.GoalSettingSeq\n" +
			"and b.SplitVersion = c.SplitVersion\n" +
			"and c.goalsettingseq = :goalsettingseq\n" +
			"and c.mappingID = :mappingID\n" +
			"and b.row_num = 1\n"+
			"and UPPER(c.TimeBase) = 'MONTH'\n" +
			"order by c.TimeBaseSeq")
	public List<Object[]> getPlanByMonth(@Param("goalsettingseq") Integer goalsettingseq , String mappingID);

	@Query(nativeQuery=true, value="select d.TimeBaseSeq,NVL(sum(d.SetValue),0) from\r\n" +
			"      TW_LH_SD_Goal_Setting_Version b , TW_LH_SD_Goal_Setting_Split c , TW_LH_SD_Goal_Setting_Split_Value d\r\n" + 
			"      where 1=1\r\n" + 
			"      and b.GoalSettingSeq = c.GoalSettingSeq\r\n" + 
			"      and b.GoalSettingSeq = d.GoalSettingSeq\r\n" + 
			"      and b.AgentID in (\r\n" + 
			"    		select AgentID from\r\n" +
			"      		TW_LH_SD_Agency_Plan_Pile_Data a \r\n" + 
			"      		where 1=1\r\n" + 
			"      		and a.LeaderAgentID = :agentID \r\n" +
			"           and a.AgentID <> :agentID \r\n" +
			"           and a.DataYear = :dataYear\r\n" +
			"    	)\r\n" + 
			"      and b.DataYear = :dataYear\r\n" +
			"      and b.TopVersion = 'Y'\r\n" +
			"	   and c.TopVersion = 'Y'\r\n" +
			"      and c.SplitVersion = d.SplitVersion\r\n" +
			"      and d.mappingID = :MappingID\r\n" +
			"      and UPPER(d.TimeBase) = 'MONTH'\r\n" +
			"      group by d.MappingID,   d.TimeBase,   d.TimeBaseSeq")
	public List<Object[]> getYearSubordinatePlanExceptSelf(
			@Param("agentID") String agentID,
			@Param("dataYear") Integer dataYear,
			@Param("MappingID") String mappingID);


	@Query("select c.setValue from \n"
			+ " GoalSettingVersion a , GoalSettingSplit b , GoalSettingSplitValue c \n"
			+ " where 1=1 \n"
			+ " and a.goalSettingSeq = b.identity.goalSettingSeq \n"
			+ " and a.goalSettingSeq = c.identity.goalSettingSeq \n"
			+ " and a.agentID in (:AgentID) \n"
			+ " and a.dataYear = :TodayYear \n"
			+ " and a.topVersion = 'Y' \n"
			+ " and b.identity.splitVersion = c.identity.splitVersion \n"
			+ " and UPPER(c.identity.timeBase) = 'MONTH' \n"
			+ " and c.identity.mappingID = :MappingID \n"
			+ " and c.identity.timeBaseSeq >= :planCalcStartMonth \n"
			+ " and c.identity.timeBaseSeq <= :planCalcEndMonth \n")
	public List<String> getTotalPlan(@Param("TodayYear") int TodayYear,
							@Param("planCalcStartMonth") int planCalcStartMonth,
							@Param("planCalcEndMonth") int planCalcEndMonth,
							@Param("AgentID") List<String> AgentID,
							@Param("MappingID") String mappingID);

}
