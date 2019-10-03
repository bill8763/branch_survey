package com.allianz.sd.core.jpa.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 6:00
 * To change this template use File | Settings | File Templates.
 */
@Embeddable
public class SysYearDataIdentity implements Serializable {

    @Column(name = "OrganizationalUnit")
    private String organizationalUnit = null;

    @Column(name = "AppSysCtrl")
    private String appSysCtrl = null;

    @Column(name = "DataYear")
    private Integer dataYear = null;

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

	public Integer getDataYear() {
		return dataYear;
	}

	public void setDataYear(Integer dataYear) {
		this.dataYear = dataYear;
	}
}
