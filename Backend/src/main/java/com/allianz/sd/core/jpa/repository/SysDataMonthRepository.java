package com.allianz.sd.core.jpa.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.allianz.sd.core.jpa.model.SysDataMonth;
import com.allianz.sd.core.jpa.model.SysDataMonthIdentity;


/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 2019/07/03
 * Time: 下午 12:41
 * To change this template use File | Settings | File Templates.
 */
public interface SysDataMonthRepository extends JpaRepository<SysDataMonth, SysDataMonthIdentity> {

	
	@Query("select a from SysDataMonth a \n" + 
			"where a.identity.organizationalUnit =:organizationalUnit \n" + 
			"and a.identity.appSysCtrl =:appSysCtrl \n" + 
			"and a.identity.startDate <=:startDate \n" + 
			"and a.endDate >=:endDate ")
	public SysDataMonth getMonth(@Param("organizationalUnit")String organizationalUnit,
								@Param("appSysCtrl")String appSysCtrl,
								@Param("startDate")Date startDate,
								@Param("endDate")Date endDate);
}
