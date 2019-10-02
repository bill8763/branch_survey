package com.allianz.sd.core.jpa.model;

import com.allianz.sd.core.jpa.listener.CreateUpdateTimeListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 1:43
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name="TW_LH_SD_Code")
public class Code extends CreateUpdateInfo{

    @EmbeddedId()
    private CodeIdentity identity = null;

    @Column(name = "Orders")
    private Integer orders = null;

    @Column(name = "Arguments")
    private String arguments = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ValidityPeriod")
    private Date validityPeriod = null;

    public Code() {
    }

    public CodeIdentity getIdentity() {
        return identity;
    }

    public void setIdentity(CodeIdentity identity) {
        this.identity = identity;
    }

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }

    public String getArguments() {
        return arguments;
    }

    public void setArguments(String arguments) {
        this.arguments = arguments;
    }

    public Date getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(Date validityPeriod) {
        this.validityPeriod = validityPeriod;
    }
}
