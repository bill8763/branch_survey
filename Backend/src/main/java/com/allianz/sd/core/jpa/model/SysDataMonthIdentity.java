package com.allianz.sd.core.jpa.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 2019/07/03
 * Time: 下午 6:00
 * To change this template use File | Settings | File Templates.
 */
@Embeddable
public class SysDataMonthIdentity implements Serializable {

    @Column(name = "OrganizationalUnit")
    private String organizationalUnit = null;

    @Column(name = "AppSysCtrl")
    private String appSysCtrl = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "StartDate")
    private Date startDate = null;

	public String getOrganizationalUnit() {
		return organizationalUnit;
	}

	public void setOrganizationalUnit(String organizationalUnit) {
		this.organizationalUnit = organizationalUnit;
	}

	public String getAppSysCtrl() {
		return appSysCtrl;
	}

	public void setAppSysCtrl(String appSysCtrl) {
		this.appSysCtrl = appSysCtrl;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


}
