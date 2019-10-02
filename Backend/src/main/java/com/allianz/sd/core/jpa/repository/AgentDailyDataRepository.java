package com.allianz.sd.core.jpa.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.allianz.sd.core.jpa.model.AgentDailyData;
import com.allianz.sd.core.jpa.model.AgentDailyDataIdentity;


/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 12:41
 * To change this template use File | Settings | File Templates.
 */
public interface AgentDailyDataRepository extends JpaRepository<AgentDailyData,AgentDailyDataIdentity> {

	@Query("select NVL(SUM(numberOfMeet),0),NVL(SUM(numberOfSubmit),0),NVL(SUM(numberOfInforce),0) " +
			"from AgentDailyData  " +
			"where 1=1 " +
			"and to_char(dataDate,'yyyy-MM-dd') >= :StartDate " +
			"and to_char(dataDate,'yyyy-MM-dd') <= :EndDate " +
			"and agentID=:AgentID")
	public List<Object[]> getMeetAndSubmitAndInforce(
			@Param("AgentID") String AgentID,
			@Param("StartDate") String StartDate,
			@Param("EndDate") String EndDate);


	@Query("select NVL(SUM(numberOfMeet),0),NVL(SUM(numberOfSubmit),0),NVL(SUM(numberOfInforce),0) " +
			"from AgentDailyData a  " +
			"where 1=1 " +
			"and dataYear=:DataYear " +
			"and dataMonth=:DataMonth " +
			"and agentID=:AgentID")
	public List<Object[]> getMonthMeetAndSubmitAndInforce(@Param("AgentID") String AgentID,
														  @Param("DataYear") int DataYear,@Param("DataMonth") int DataMonth);
	@Query("select NVL(SUM(numberOfMeet),0),NVL(SUM(numberOfSubmit),0),NVL(SUM(numberOfInforce),0) " +
			"from AgentDailyData a  " +
			"where 1=1 " +
			"and dataYear=:DataYear a" +
			"nd dataQuarter=:DataQuarter " +
			"and agentID=:AgentID")
	public List<Object[]> getQuarterMeetAndSubmitAndInforce(@Param("AgentID") String AgentID,@Param("DataYear") int DataYear,
															@Param("DataQuarter") int DataQuarter);

	@Query("select NVL(SUM(numberOfMeet),0),NVL(SUM(numberOfSubmit),0),NVL(SUM(numberOfInforce),0) from AgentDailyData a  where 1=1 and dataYear=:DataYear  and agentID=:AgentID")
	public List<Object[]> getYearMeetAndSubmitAndInforce(@Param("AgentID") String AgentID,@Param("DataYear") int DataYear);


	@Query(value="select a from AgentDailyData a \n" +
			"where 1=1 \n" +
			"and a.identity.agentID =:agentId \n" +
			"and a.identity.organizationalUnit =:organizationalunit \n" +
			"and a.identity.dataDate =:dataDate")
	public AgentDailyData findData(@Param("agentId")String agentId, @Param("organizationalunit")String organizationalunit,@Param("dataDate")Date dataDate);
}
