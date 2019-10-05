package com.allianz.sd.core.jpa.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.allianz.sd.core.jpa.model.AgentDailyAppData;
import com.allianz.sd.core.jpa.model.AgentDailyAppDataIdentity;


/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 12:41
 * To change this template use File | Settings | File Templates.
 */
public interface AgentDailyAppDataRepository extends JpaRepository<AgentDailyAppData,AgentDailyAppDataIdentity> {


	@Query("select NVL(SUM(numberOfFind),0),NVL(SUM(numberOfSchedule),0) " +
			"from AgentDailyAppData  " +
			"where 1=1 " +
			"and to_char(identity.dataDate,'yyyy-MM-dd') >=:StartDate " +
			"and to_char(identity.dataDate,'yyyy-MM-dd') <=:EndDate " +
			"and identity.agentID=:AgentID")
	public List<Object[]> getFindAndSchedule(
			@Param("AgentID") String AgentID,
			@Param("StartDate") String StartDate,
			@Param("EndDate") String EndDate);


	@Query("select NVL(SUM(numberOfFind),0),NVL(SUM(numberOfSchedule),0) from AgentDailyAppData  where 1=1 and dataYear=:DataYear and dataMonth=:DataMonth  and agentID=:AgentID")
	public  List<Object[]> getMonthFindAndSchedule(@Param("AgentID") String AgentID,
												   @Param("DataYear") int DataYear,@Param("DataMonth") int DataMonth);

	@Query("select NVL(SUM(numberOfFind),0),NVL(SUM(numberOfSchedule),0) from AgentDailyAppData  where 1=1 and dataYear=:DataYear and dataQuarter=:DataQuarter  and agentID=:AgentID")
	public List<Object[]> getQuarterFindAndSchedule(@Param("AgentID") String AgentID,
													@Param("DataYear") int DataYear,@Param("DataQuarter") int DataQuarter);

	@Query("select NVL(SUM(numberOfFind),0),NVL(SUM(numberOfSchedule),0)  from AgentDailyAppData  where 1=1 and dataYear=:DataYear  and agentID=:AgentID")
	public List<Object[]> getYearFindAndSchedule(@Param("AgentID") String AgentID,	@Param("DataYear") int DataYear);

	@Query(value="select a from AgentDailyAppData a \n" +
	 		"where 1=1 \n" + 
	 		"and a.identity.agentID =:agentId \n" + 
	 		"and a.identity.organizationalUnit =:organizationalunit \n" + 
	 		"and a.identity.dataDate =:dataDate")
	    public AgentDailyAppData findData(@Param("agentId") String agentId , @Param("organizationalunit") String organiztionalunit
	    										,@Param("dataDate") Date dataDate);

	
}
