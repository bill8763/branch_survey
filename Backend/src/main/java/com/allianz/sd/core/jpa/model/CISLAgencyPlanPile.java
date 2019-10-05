package com.allianz.sd.core.jpa.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 */
@Entity

@Table(name="TW_LH_SD_CISL_Agency_Plan_Pile_Data")
public class CISLAgencyPlanPile extends CreateInfo{

	@EmbeddedId()
    private AgencyPlanPileIdentity identity = null;

	public AgencyPlanPileIdentity getIdentity() {
		return identity;
	}

	public void setIdentity(AgencyPlanPileIdentity identity) {
		this.identity = identity;
	}
}
