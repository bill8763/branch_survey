package com.allianz.sd.core.jpa.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 */
@Embeddable
public class CISLBatchLogDetailIdentity implements Serializable {

	@Column(name = "BatchLogID")
	private Integer batchLogID = null;

	@Column(name = "PageNumber")
	private Integer pageNumber = null;

	@Column(name = "DetailCategory")
	private String detailCategory = null;

	public Integer getBatchLogID() {
		return batchLogID;
	}

	public void setBatchLogID(Integer batchLogID) {
		this.batchLogID = batchLogID;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getDetailCategory() {
		return detailCategory;
	}

	public void setDetailCategory(String detailCategory) {
		this.detailCategory = detailCategory;
	}
}
