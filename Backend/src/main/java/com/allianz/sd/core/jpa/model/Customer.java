package com.allianz.sd.core.jpa.model;

import javax.persistence.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 12:20
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="TW_LH_SD_Customer")
public class Customer extends CreateUpdateInfo {
    @Column(name = "OrganizationalUnit")
    private String organizationalUnit = null;

    @Id
    @Column(name = "CustomerID")
    private Integer customerID = null;

    @Column(name = "FirstName")
    private String firstName = null;

    @Column(name = "MiddleName")
    private String middleName = null;

    @Column(name = "LastName")
    private String lastName = null;

    @Column(name = "Occupation")
    private String occupation = null;

    @Column(name = "Company")
    private String company = null;

    @Column(name = "BirthdayYear")
    private String birthdayYear = null;

    @Column(name = "BirthdayMonth")
    private String birthdayMonth = null;

    @Column(name = "BirthdayDate")
    private String birthdayDate = null;

    @Column(name = "Age")
    private Integer age = null;

    @Column(name = "AgeRange")
    private String ageRange = null;

    @Column(name = "Gender")
    private String gender = null;

    @Column(name = "Income")
    private String income = null;

    @Column(name = "Source")
    private String source = null;

    @Column(name = "Marriage")
    private String marriage = null;

    @Column(name = "Children")
    private String children = null;

    @Column(name = "Familiarity")
    private String familiarity = null;

    @Column(name = "ContactFrequancy")
    private String contactFrequancy = null;

    @Column(name = "Possibility")
    private String possibility = null;

    @Column(name = "IsFollow")
    private String isFollow = null;

    @Column(name = "AgentID")
    private String agentId = null;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DataTime")
    private Date dataTime = null;

    @Column(name = "DataSource")
    private String dataSource = null;

    @Column(name = "PartID")
    private Integer partID = null;

    @Column(name = "Completeness")
    private Float completeness = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "OverTimeAlertTime")
    private Date overTimeAlertTime = null;

    @Column(name = "APPUpdateTime")
    private Date appUpdateTime = null;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "CustomerID")
    private Set<CustomerTel> tels = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "CustomerID")
    private Set<CustomerEmail> emails = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "CustomerID")
    private Set<CustomerAddress> addresses = new HashSet<>();

    public String getOrganizationalUnit() {
        return organizationalUnit;
    }

    public void setOrganizationalUnit(String organizationalUnit) {
        this.organizationalUnit = organizationalUnit;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(String isFollow) {
        this.isFollow = isFollow;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBirthdayYear() {
        return birthdayYear;
    }

    public void setBirthdayYear(String birthdayYear) {
        this.birthdayYear = birthdayYear;
    }

    public String getBirthdayMonth() {
        return birthdayMonth;
    }

    public void setBirthdayMonth(String birthdayMonth) {
        this.birthdayMonth = birthdayMonth;
    }

    public String getBirthdayDate() {
        return birthdayDate;
    }

    public void setBirthdayDate(String birthdayDate) {
        this.birthdayDate = birthdayDate;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(String ageRange) {
        this.ageRange = ageRange;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public String getFamiliarity() {
        return familiarity;
    }

    public void setFamiliarity(String familiarity) {
        this.familiarity = familiarity;
    }

    public String getContactFrequancy() {
        return contactFrequancy;
    }

    public void setContactFrequancy(String contactFrequancy) {
        this.contactFrequancy = contactFrequancy;
    }

    public String getPossibility() {
        return possibility;
    }

    public void setPossibility(String possibility) {
        this.possibility = possibility;
    }

    public String getFollow() {
        return isFollow;
    }

    public void setFollow(String follow) {
        isFollow = follow;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }


    public Date getDataTime() {
        return dataTime;
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public Integer getPartID() {
        return partID;
    }

    public void setPartID(Integer partID) {
        this.partID = partID;
    }

    public Float getCompleteness() {
        return completeness;
    }

    public void setCompleteness(Float completeness) {
        this.completeness = completeness;
    }

    public Date getOverTimeAlertTime() {
        return overTimeAlertTime;
    }

    public void setOverTimeAlertTime(Date overTimeAlertTime) {
        this.overTimeAlertTime = overTimeAlertTime;
    }

    public Date getAppUpdateTime() {
        return appUpdateTime;
    }

    public void setAppUpdateTime(Date appUpdateTime) {
        this.appUpdateTime = appUpdateTime;
    }

    public void addTel(CustomerTel tel) {
        this.tels.add(tel);
    }

    public void addEmail(CustomerEmail email) {
        this.emails.add(email);
    }

    public void addAddress(CustomerAddress address) {
        this.addresses.add(address);
    }

    public Set<CustomerTel> getTels() {
        return tels;
    }

    public void setTels(Set<CustomerTel> tels) {
        this.tels = tels;
    }

    public Set<CustomerEmail> getEmails() {
        return emails;
    }

    public void setEmails(Set<CustomerEmail> emails) {
        this.emails = emails;
    }

    public Set<CustomerAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<CustomerAddress> addresses) {
        this.addresses = addresses;
    }
}