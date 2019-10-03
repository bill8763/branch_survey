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
@Table(name="TW_LH_SD_Customer_Address")
public class CustomerAddress extends CreateUpdateInfo{

    @Column(name = "OrganizationalUnit")
    private String organizationalUnit = null;

    @Id
    @Column(name = "AddrID")
    @GeneratedValue(generator = "idSequence")
    @SequenceGenerator(name = "idSequence", sequenceName = "TW_LH_SD_Sequence_Customer_Address", allocationSize = 1)
    private Integer addrID = null;

    @Column(name = "CustomerID")
    private Integer customerID = null;

    @Column(name = "AddressType")
    private String addressType = null;

    @Column(name = "AddID")
    private Integer addID = null;

    @Column(name = "Country")
    private String country = null;

    @Column(name = "City")
    private String city = null;

    @Column(name = "Area")
    private String area = null;

    @Column(name = "Zipcode")
    private String zipcode = null;

    @Column(name = "Address1")
    private String address1 = null;

    @Column(name = "Address2")
    private String address2 = null;

    @Column(name = "Address3")
    private String address3 = null;

    @Column(name = "Address4")
    private String address4 = null;

    public String getOrganizationalUnit() {
        return organizationalUnit;
    }

    public void setOrganizationalUnit(String organizationalUnit) {
        this.organizationalUnit = organizationalUnit;
    }

    public Integer getAddrID() {
        return addrID;
    }

    public void setAddrID(Integer addrID) {
        this.addrID = addrID;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public Integer getAddID() {
        return addID;
    }

    public void setAddID(Integer addID) {
        this.addID = addID;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getAddress4() {
        return address4;
    }

    public void setAddress4(String address4) {
        this.address4 = address4;
    }
}
