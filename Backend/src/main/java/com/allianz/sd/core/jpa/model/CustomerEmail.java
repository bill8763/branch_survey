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
@Table(name="TW_LH_SD_Customer_Email")
public class CustomerEmail extends CreateUpdateInfo{

    @Column(name = "OrganizationalUnit")
    private String organizationalUnit = null;

    @Id
    @Column(name = "MailID")
    @GeneratedValue(generator = "idSequence")
    @SequenceGenerator(name = "idSequence", sequenceName = "TW_LH_SD_Sequence_Customer_Email", allocationSize = 1)
    private Integer mailID = null;

    @Column(name = "CustomerID")
    private Integer customerID = null;

    @Column(name = "EmailType")
    private String emailType = null;

    @Column(name = "Email")
    private String email = null;

    public String getOrganizationalUnit() {
        return organizationalUnit;
    }

    public void setOrganizationalUnit(String organizationalUnit) {
        this.organizationalUnit = organizationalUnit;
    }

    public Integer getMailID() {
        return mailID;
    }

    public void setMailID(Integer mailID) {
        this.mailID = mailID;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public String getEmailType() {
        return emailType;
    }

    public void setEmailType(String emailType) {
        this.emailType = emailType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
