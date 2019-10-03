package com.allianz.sd.core.jpa.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/3/16
 * Time: 下午 5:12
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="TW_LH_SD_OPUS_CUSTOMER")
public class OPUS extends CreateInfo {
	@EmbeddedId()
    private OPUSIdentity identity = null;

    @Column(name = "CustomerName")
    private String customerName = null;

	public OPUSIdentity getIdentity() {
		return identity;
	}

	public void setIdentity(OPUSIdentity identity) {
		this.identity = identity;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


}
