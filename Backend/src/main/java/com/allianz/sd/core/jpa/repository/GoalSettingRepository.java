package com.allianz.sd.core.jpa.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.allianz.sd.core.jpa.model.AgentYearDataIdentity;
import com.allianz.sd.core.jpa.model.GoalSetting;
import com.allianz.sd.core.jpa.model.GoalSettingVersion;


/**
 */
public interface GoalSettingRepository extends JpaRepository<GoalSetting,AgentYearDataIdentity> {

	
	@Query("SELECT a from GoalSetting a "
			+ "where 1=1 "
			+ "and agentID = :agentID "
			+ "and organizationalUnit = :organizationalUnit "
			+ "and dataYear = :dataYear "
			+ "order by dataYear desc")
	public GoalSetting getGoalSetting(@Param("agentID") String agentID ,
			@Param("organizationalUnit") String organizationalUnit,
			@Param("dataYear") Integer dataYear);
	
	
	@Query(nativeQuery = true, value = "select a.GoalSettingFlag\r\n" + 
			"from TW_LH_SD_Goal_Setting a ,  ( \r\n" + 
			"    select * from (\r\n" + 
			"        select * from TW_LH_SD_Goal_Setting_Version " +
			"			where 1=1 "+
			"			and agentID = :agentID " +
			"			and organizationalUnit = :organizationalUnit " +
			"			and dataYear = :dataYear " +
			" 		order by GoalSettingSeq desc )\r\n" + 
			"    where rownum = 1) b\r\n" + 
			"where 1=1\r\n" + 
			"and a.AgentID = b.AgentID\r\n" + 
			"and a.DataYear = b.DataYear\r\n" + 
			"and :todayDate between a.GoalSettingStartDate and a.GoalSettingDeadline " +
			"and (a.GoalSettingStartDate != b.GoalSettingStartDate or b.GoalSettingStartDate is null) \r\n" + 
			"")
	public List<String> goalSettingFlag(@Param("agentID") String agentID ,
			@Param("organizationalUnit") String organizationalUnit,
			@Param("dataYear") Integer dataYear,
			@Param("todayDate") Date todayDate
			);
	@Query(nativeQuery = true,value="select a.AgentID , a.GoalSettingDeadline , a.GoalSettingFlag \n" +
			"\t\t\tfrom TW_LH_SD_Goal_Setting a ,  ( \n" +
			"\t\t\tselect * from ( \n" +
			"\t\t\t\tselect d.GoalSettingStartDate,d.DataYear,d.agentID,ROW_NUMBER() Over (Partition By d.AgentID Order By d.GoalSettingSeq Desc) As Sort \n" +
			"\t\t\t\tfrom TW_LH_SD_Goal_Setting_Version d , TW_LH_SD_Agent_Year_Data e\n" +
			"\t\t\t\t\twhere 1=1  \n" +
			"\t\t\t\t\tand d.organizationalUnit =:organizationalUnit  \n" +
			"\t\t\t\t\tand d.dataYear = :dataYear  \n" +
			"                    and d.dataYear = e.dataYear\n" +
			"                    and d.agentid = e.agentid\n" +
			"                    and e.appusemode not in ('Manager','SuperVistor')\n" +
			"\t\t\t\t\torder by d.GoalSettingSeq desc )  \n" +
			"\t\t\t\t\t) b  \n" +
			"\t\t\twhere 1=1  \n" +
			"\t\t\tand a.AgentID = b.AgentID  \n" +
			"\t\t\tand a.DataYear = b.DataYear  \n" +
			"            and :todayDate between a.GoalSettingStartDate and a.GoalSettingDeadline   \n" +
			"\t\t\tand (a.GoalSettingStartDate != b.GoalSettingStartDate or b.GoalSettingStartDate is null) \n" +
			"\t\t\tand sort = 1")
	public List<Object[]> getAllNeedSetting(@Param("organizationalUnit") String organizationalUnit,
										@Param("dataYear") String dataYear,
										@Param("todayDate") Date todayDate);
	
	
	
}
