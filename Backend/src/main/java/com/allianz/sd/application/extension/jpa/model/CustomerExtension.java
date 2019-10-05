package com.allianz.sd.application.extension.jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/4/5
 * Time: 下午 5:36
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name="TW_LH_SD_Customer_Extension")
public class CustomerExtension {

    @Column(name = "Ex_OrganizationalUnit")
    private String exOrganizationalUnit = null;

    @Id
    @Column(name = "Ex_CustomerID")
    private Integer exCustomerID = null;

    @Column(name = "RecentStatus")
    private String recentStatus = null;

    @Column(name = "MANPA")
    private String manpa = null;

    public String getExOrganizationalUnit() {
        return exOrganizationalUnit;
    }

    public void setExOrganizationalUnit(String exOrganizationalUnit) {
        this.exOrganizationalUnit = exOrganizationalUnit;
    }

    public Integer getExCustomerID() {
        return exCustomerID;
    }

    public void setExCustomerID(Integer exCustomerID) {
        this.exCustomerID = exCustomerID;
    }

    public String getRecentStatus() {
        return recentStatus;
    }

    public void setRecentStatus(String recentStatus) {
        this.recentStatus = recentStatus;
    }

    public String getManpa() {
        return manpa;
    }

    public void setManpa(String manpa) {
        this.manpa = manpa;
    }
}
