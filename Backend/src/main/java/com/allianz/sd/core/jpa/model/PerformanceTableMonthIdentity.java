package com.allianz.sd.core.jpa.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 6:00
 * To change this template use File | Settings | File Templates.
 */
@Embeddable
public class PerformanceTableMonthIdentity implements Serializable {

    @Column(name = "OrganizationalUnit")
    private String organizationalUnit = null;

    @Column(name = "PerformanceTable")
    private String performanceTable = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "StartDate")
    private Date startDate = null;

	public String getOrganizationalUnit() {
		return organizationalUnit;
	}

	public void setOrganizationalUnit(String organizationalUnit) {
		this.organizationalUnit = organizationalUnit;
	}

	public String getPerformanceTable() {
		return performanceTable;
	}

	public void setPerformanceTable(String performanceTable) {
		this.performanceTable = performanceTable;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}



}
