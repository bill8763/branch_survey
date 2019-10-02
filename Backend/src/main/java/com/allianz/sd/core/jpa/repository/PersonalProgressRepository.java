package com.allianz.sd.core.jpa.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.allianz.sd.core.jpa.model.PersonalProgress;
import com.allianz.sd.core.jpa.model.ProgressIdentity;


/**
 */
public interface PersonalProgressRepository extends JpaRepository<PersonalProgress,ProgressIdentity> {

	@Transactional
	@Modifying
	@Query("delete from PersonalProgress b \n"+
			"where 1=1 \n"+
			"and b.identity.dataYear =:dataYear\n" +
			"and b.identity.organizationalUnit =:organizationalUnit")
	public int deletePersonalProgress(
			@Param("dataYear")int dataYear,
			@Param("organizationalUnit") String organizationalUnit
	);

	
	//query personal progress by organizationUnit,agentID,year
	@Query("SELECT a from PersonalProgress a "
			+ "where 1=1 "
			+ "and agentID = :agentID "
			+ "and organizationalUnit = :organizationalUnit "
			+ "and dataYear = :dataYear "
			+ "order by dataYear desc")
	public List<PersonalProgress> getPersonalProgressSync(@Param("agentID") String agentID ,
														  @Param("organizationalUnit") String organizationalUnit,
														  @Param("dataYear") Integer dataYear);

	@Query("SELECT a from PersonalProgress a "
			+ "where 1=1 "
			+ "and agentID = :agentID "
			+ "and organizationalUnit = :organizationalUnit "
			+ "and dataYear = :dataYear "
			+ "and identity.timeBase = :timeBase "
			+ "order by dataYear desc")
	public List<PersonalProgress> getPersonalProgressSync(@Param("agentID") String agentID ,
														  @Param("organizationalUnit") String organizationalUnit,
														  @Param("dataYear") Integer dataYear,
														  @Param("timeBase") String timeBase);
    
    /*
     * get total point by agentID and date
     * NVL(col,0): col isnull then 0
     */
    @Query(nativeQuery = true,value="\n" +
			"select (NVL(ProgressFind,0) + NVL(ProgressSchedule,0)) as sum  from tw_lh_sd_agent_daily_app_data\n" +
			"where agentid = :agentID\n" +
			"and to_char(datadate,'yyyy-MM-dd') = to_char(:dataDate,'yyyy-MM-dd')\n" +
			"and OrganizationalUnit = :organizationalUnit\n" +
			"\n" +
			"union\n" +
			"\n" +
			"select (NVL(ProgressPointMeet,0) + NVL(ProgressPointSubmit,0) + NVL(ProgressPointInforce,0)) as sum  from TW_LH_SD_Agent_Daily_Data\n" +
			"where agentid = :agentID\n" +
			"and to_char(datadate,'yyyy-MM-dd') = to_char(:dataDate,'yyyy-MM-dd')\n" +
			"and OrganizationalUnit = :organizationalUnit\n")
	 public List<BigDecimal> getActivityPointByDate(
			 @Param("agentID") String agentID , 
			 @Param("organizationalUnit") String organizationalUnit, 
			 @Param("dataDate") Date dataDate);
    
    @Query(nativeQuery = true,value="select b.agentid from \n" +
			"\t\t\t(\n" +
			"\t\t\tselect agentid , sum(sum) as total\n" +
			"\t\t\tfrom (\n" +
			"\t\t\tselect agentid,(NVL(ProgressFind,0) + NVL(ProgressSchedule,0)) as sum  from tw_lh_sd_agent_daily_app_data\n" +
			"\t\t\twhere 1=1\n" +
			"            and OrganizationalUnit = :organizationalUnit\n" +
			"\t\t\tand to_char(datadate,'yyyy-MM-dd') = to_char(:dataDate,'yyyy-MM-dd')\n" +
			"\t\t\tunion\n" +
			"\t\t\t\n" +
			"\t\t\tselect agentid,(NVL(ProgressPointMeet,0) + NVL(ProgressPointSubmit,0) + NVL(ProgressPointInforce,0)) as sum  from TW_LH_SD_Agent_Daily_Data\n" +
			"\t\t\twhere 1=1\n" +
			"\t\t\tand OrganizationalUnit = :organizationalUnit\n" +
			"\t\t\tand to_char(datadate,'yyyy-MM-dd') = to_char(:dataDate,'yyyy-MM-dd')\n" +
			"\t\t\t) a\n" +
			"\t\t\tgroup by a.agentid\n" +
			"\t\t\t) b , tw_lh_sd_agent_year_data c\n" +
			"\t\t\twhere b.total < :lessPoint\n" +
			"            and b.agentid = c.agentid\n" +
			"            and c.datayear = 2019\n" +
			"            and c.appusemode in ('AG','AL')")
    public List<String> getAgentIdMiniPoint(@Param("organizationalUnit")String organizationalUnit,
    										@Param("dataDate")Date dataDate,
    										@Param("lessPoint")int lessPoint);
    @Transactional
    @Modifying
    @Query("update PersonalProgress a \n"+
    		"set find = find + :value \n"+    
    		"where a.identity.agentID =:agentID \n" + 
    		" and a.identity.organizationalUnit =:organizationalUnit \n" + 
    		" and a.identity.dataYear =:dataYear \n" + 
    		" and a.identity.dataType = 'Actual' \n")
    public int updateFind(@Param("value")BigDecimal value,
						 @Param("agentID")String agentID,
						 @Param("organizationalUnit")String organizationalUnit,
						 @Param("dataYear")int dataYear);
    @Transactional
    @Modifying
    @Query("update PersonalProgress a \n"+
    		"set schedule = schedule + :value \n"+
    		"where a.identity.agentID =:agentID \n" + 
    		" and a.identity.organizationalUnit =:organizationalUnit \n" + 
    		" and a.identity.dataYear =:dataYear \n" + 
    		" and a.identity.dataType = 'Actual' \n")
    public int updateSchedule(@Param("value")BigDecimal value,
							 @Param("agentID")String agentID,
							 @Param("organizationalUnit")String organizationalUnit,
							 @Param("dataYear")int dataYear);
    @Transactional
    @Modifying
    @Query("update PersonalProgress a \n"+
    		"set meet = meet + :value \n"+
    		"where a.identity.agentID =:agentID \n" + 
    		" and a.identity.organizationalUnit =:organizationalUnit \n" + 
    		" and a.identity.dataYear =:dataYear \n" + 
    		" and a.identity.dataType = 'Actual' \n")
    public int updateMeet(@Param("value")BigDecimal value,
						 @Param("agentID")String agentID,
						 @Param("organizationalUnit")String organizationalUnit,
						 @Param("dataYear")int dataYear);
    @Transactional
    @Modifying
    @Query("update PersonalProgress a \n"+
    		"set a.submit = submit + :value \n"+
    		"where a.identity.agentID =:agentID \n" + 
    		" and a.identity.organizationalUnit =:organizationalUnit \n" + 
    		" and a.identity.dataYear =:dataYear \n" + 
    		" and a.identity.dataType = 'Actual' \n")
    public int updateSubmit(@Param("value")BigDecimal value,
							 @Param("agentID")String agentID,
							 @Param("organizationalUnit")String organizationalUnit,
							 @Param("dataYear")int dataYear);
    @Transactional
    @Modifying
    @Query( "update PersonalProgress a \n"+
    		"set inforce = inforce +:value \n"+
    		"where a.identity.agentID =:agentID \n" + 
    		" and a.identity.organizationalUnit =:organizationalUnit \n" + 
    		" and a.identity.dataYear =:dataYear \n" + 
    		" and a.identity.dataType = 'Actual' \n")
    public int updateInforce(@Param("value")BigDecimal value,
    						 @Param("agentID")String agentID,
    						 @Param("organizationalUnit")String organizationalUnit,
    						 @Param("dataYear")int dataYear);

}
