package com.allianz.sd.core.jpa.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.allianz.sd.core.jpa.model.PerformanceTableMonth;
import com.allianz.sd.core.jpa.model.PerformanceTableMonthIdentity;

import javax.persistence.TemporalType;


/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 12:41
 * To change this template use File | Settings | File Templates.
 */
public interface PerformanceTableMonthRepository extends JpaRepository<PerformanceTableMonth,PerformanceTableMonthIdentity> {

	
	@Query("select b.performanceMonth from PerformanceTableMonth b \n" +
			"where b.performanceQuarter = :quarter \n" +
			"and b.performanceYear = :year \n" +
			"and b.identity.performanceTable = :performanceTable \n" +
			"order by b.performanceMonth ")
	public List<Integer> findQuarterMonths(
			@Param("year") int year ,
			@Param("quarter") int quarter ,
			@Param("performanceTable") String performanceTable);
	
	@Query(" select b.performanceMonth from PerformanceTableMonth b " +
			"where b.performanceYear = :Year \n" +
			"and b.identity.performanceTable = :performanceTable \n" +
			"order by b.performanceMonth ")
	public List<Integer> findYearMonths(
			@Param("Year") int year ,
			@Param("performanceTable") String performanceTable);

	//取該季的月份區間
	@Query(value="select b.performanceMonth from PerformanceTableMonth b \n" +
			"where 1=1 " +
			"and b.performanceQuarter = :Quarter \n" +
			" and b.performanceYear =:Year \n"+
			"order by b.performanceMonth")
	public List<Integer> getMonthByQuarter(@Param("Quarter") int quarter,
										   @Param("Year")int year);

	@Query(value="select a from PerformanceTableMonth a \n" +
			"where a.identity.performanceTable =:performancetable  \n" +
			"and a.identity.organizationalUnit =:organizationalunit \n" +
			"and a.identity.startDate <=:dateStart \n" +
			"and a.endDate >=:dateEnd")
	public PerformanceTableMonth findDataByOrganizationalUnitPerformanceTableDate(@Param("organizationalunit")String organizationalunit,@Param("performancetable")String performancetable
			,@Param("dateStart")Date dateStart,@Param("dateEnd")Date dateEnd);


	@Query(value="select count(a.performanceMonth) from PerformanceTableMonth a \n" +
			"where a.performanceYear =:performanceYear \n"+
			"and a.identity.performanceTable =:performanceTable")
	public int getMonthQuantityOfYear(@Param("performanceYear")int performanceYear,@Param("performanceTable")String performanceTable);

	@Query(value="select a.endDate from PerformanceTableMonth a \n" +
			"where a.identity.performanceTable =:performancetable \n" +
			"and a.identity.organizationalUnit =:organizationalunit \n"+
			"and  a.performanceYear =:performanceyear \n" +
			"and a.performanceMonth = '12'")
	public Date findEndDate(@Param("performancetable")String performancetable,@Param("organizationalunit")String organizationalunit,@Param("performanceyear")int performanceyear);

	@Query(value="select a from PerformanceTableMonth a \n" +
			"where 1=1  \n" +
			"and a.identity.organizationalUnit =:organizationalunit \n" +
			"and a.identity.performanceTable =:performanceTable \n" +
			"and to_char(a.identity.startDate,'yyyy-MM-dd') <= to_char(:TodayDate,'yyyy-MM-dd') \n" +
			"and to_char(a.endDate,'yyyy-MM-dd') >= to_char(:TodayDate,'yyyy-MM-dd') \n")
	public PerformanceTableMonth getPerformanceTime(
			@Param("TodayDate") @Temporal(TemporalType.DATE) Date TodayDate
			,@Param("organizationalunit") String organizationalunit
			,@Param("performanceTable") String performanceTable);


}
