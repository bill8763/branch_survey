package com.allianz.sd.core.jpa.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 12:20
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="TW_LH_SD_Customer_Tel")
public class CustomerTel extends CreateUpdateInfo{

    @Column(name = "OrganizationalUnit")
    private String organizationalUnit = null;

    @Id
    @Column(name = "TelID")
    @GeneratedValue(generator = "idSequence")
    @SequenceGenerator(name = "idSequence", sequenceName = "TW_LH_SD_Sequence_Customer_Tel", allocationSize = 1)
    private Integer telID = null;

    @Column(name = "CustomerID")
    private Integer customerID = null;

    @Column(name = "TelType")
    private String telType = null;

    @Column(name = "Tel")
    private String tel = null;

    @Column(name = "ContractID")
    private Integer contractID = null;

    @Column(name = "AddID")
    private Integer addID = null;

    @Column(name = "DataSource")
    private String dataSource = null;

    public String getOrganizationalUnit() {
        return organizationalUnit;
    }

    public void setOrganizationalUnit(String organizationalUnit) {
        this.organizationalUnit = organizationalUnit;
    }

    public Integer getTelID() {
        return telID;
    }

    public void setTelID(Integer telID) {
        this.telID = telID;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public String getTelType() {
        return telType;
    }

    public void setTelType(String telType) {
        this.telType = telType;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getContractID() {
        return contractID;
    }

    public void setContractID(Integer contractID) {
        this.contractID = contractID;
    }

    public Integer getAddID() {
        return addID;
    }

    public void setAddID(Integer addID) {
        this.addID = addID;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

}
