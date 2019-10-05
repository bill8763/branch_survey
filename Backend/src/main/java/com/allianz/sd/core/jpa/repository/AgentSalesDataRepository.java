package com.allianz.sd.core.jpa.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.allianz.sd.core.jpa.model.AgentSalesData;
import com.allianz.sd.core.jpa.model.AgentSalesDataIdentity;


/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 12:41
 * To change this template use File | Settings | File Templates.
 */
public interface AgentSalesDataRepository extends JpaRepository<AgentSalesData,AgentSalesDataIdentity> {


	@Query(nativeQuery = true ,value="select * from TW_LH_SD_Agent_Sales_Data \n" +
			"where agentid =:agentID \n" +
			"and to_char(datayearmonth,'yyyy-mm-dd') like :dataYear ")
	public List<AgentSalesData> getActual(@Param("agentID") String agentID , @Param("dataYear") String dataYear);

	@Query(" select a from AgentSalesData a\r\n" +
			"            			where 1 = 1 \n" +
			"            			and a.identity.organizationalUnit =:organizationalUnit \n" +
			"            			and a.identity.agentID =:agentID \n" +
			"            			and to_char(a.identity.dataYearMonth,'yyyy-mm-dd') like :dataYear \n" +
			"            			and a.identity.performanceType =:performancetype")
	public List<AgentSalesData> getAgentSalesData(@Param("organizationalUnit")String organizationalUnit,
												  @Param("agentID")String agentID,
												  @Param("dataYear")String dataYear,
												  @Param("performancetype")String performancetype);

	
	@Query("select sum(a.fyfc) ,sum(a.anp) ,sum(a.manpower) ,sum(a.recruitment) "
			+"from AgentSalesData a \n" + 
			"where to_char(a.identity.dataYearMonth,'yyyy') =:dataYear \n" + 
			"and a.identity.organizationalUnit =:organizationalUnit \n" + 
			"and a.identity.agentID =:agentID \n" + 
			"and a.identity.performanceType = 'T' ")
	public List<Object[]> getSumSales(@Param("organizationalUnit")String organizationalUnit ,
							 @Param("dataYear")String dataYear,
							 @Param("agentID")String agentID);

}
