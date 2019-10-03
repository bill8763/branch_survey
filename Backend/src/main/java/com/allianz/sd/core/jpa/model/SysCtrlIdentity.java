package com.allianz.sd.core.jpa.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 6:00
 * To change this template use File | Settings | File Templates.
 */
@Embeddable
public class SysCtrlIdentity implements Serializable {

    @Column(name = "OrganizationalUnit")
    private String organizationalUnit = null;

    @Column(name = "SysCtrl")
    private String sysCtrl = null;

	public String getOrganizationalUnit() {
		return organizationalUnit;
	}

	public void setOrganizationalUnit(String organizationalUnit) {
		this.organizationalUnit = organizationalUnit;
	}

	public String getSysCtrl() {
		return sysCtrl;
	}

	public void setSysCtrl(String sysCtrl) {
		this.sysCtrl = sysCtrl;
	}
}
