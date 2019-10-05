package com.allianz.sd.core.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.allianz.sd.core.jpa.model.SysData;
import com.allianz.sd.core.jpa.model.SysDataIdentity;
import org.springframework.data.repository.query.Param;


/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 12:41
 * To change this template use File | Settings | File Templates.
 */
public interface SysDataRepository extends JpaRepository<SysData,SysDataIdentity> {

	@Query("SELECT a FROM SysData a WHERE AppSysCtrl=:appSysCtrl")
	public SysData findByAppSysCtrl(@Param("appSysCtrl") String appSysCtrl);


	@Query("select DISTINCT a.identity.appSysCtrl ,a.identity.organizationalUnit from SysData a \n" + 
			"group by a.identity.appSysCtrl , a.identity.organizationalUnit ")
	public List<Object[]> getAppSysCtrlAndOrganizationalUnit();

}
