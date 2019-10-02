package com.allianz.sd.core.jpa.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.allianz.sd.core.jpa.model.SysYearData;
import com.allianz.sd.core.jpa.model.SysYearDataIdentity;


/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 12:41
 * To change this template use File | Settings | File Templates.
 */
public interface SysYearDataRepository extends JpaRepository<SysYearData,SysYearDataIdentity> {

	@Query("select b from AgentYearData  a , SysYearData  b \n" +
			"	WHERE a.appSysCtrl=b.identity.appSysCtrl \n" +
			"	and  a.identity.organizationalUnit=b.identity.organizationalUnit \n" +
			"	and  a.identity.dataYear=b.identity.dataYear \n" +
			"	and  a.appSysCtrl=b.identity.appSysCtrl \n" +
			"   and  a.identity.organizationalUnit = :OrganizationalUnit\n" +
			"	and  a.identity.dataYear=:TodayYear\n" +
			"	and  a.identity.agentID=:AgentID")
	public SysYearData getAgentSysYearData(@Param("OrganizationalUnit") String organizationalUnit,@Param("TodayYear") int TodayYear,@Param("AgentID") String AgentID);

	
	@Query("select a from SysYearData a \n" +
			"where a.appDisplayStartDate <=:datetimeStart \n" +
			"and a.appDisplayEndDate >=:datetimeEnd \n" +
			"and a.identity.appSysCtrl =:appSysCtrl \n" +
			"order by a.identity.dataYear")
	public List<SysYearData> getYear(
			@Param("datetimeStart") Date datetimeStart ,
			@Param("datetimeEnd") Date datetimeEnd,  
			@Param("appSysCtrl") String appSysCtrl);

	
	@Query("select a.identity.dataYear from SysYearData a \n" +
			"where a.appDisplayStartDate <=:datetimeStart \n" +
			"and a.appDisplayEndDate >=:datetimeEnd \n" +
			"and a.identity.organizationalUnit = :organizationalUnit \n" +
			"group by a.identity.dataYear")
	public List<Integer> getAPPDisplayYears(@Param("datetimeStart") Date datetimeStart,
												  @Param("datetimeEnd") Date datetimeEnd ,
												  @Param("organizationalUnit") String organizationalUnit);


}
