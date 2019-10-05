package com.allianz.sd.core.jpa.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/4/10
 * Time: 上午 9:49
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name="TW_LH_SD_ESB_Log")
public class ESBLog {
    @Id
    @Column(name = "LogID")
    @GeneratedValue(generator = "esbLogIDSequence")
    @SequenceGenerator(name = "esbLogIDSequence", sequenceName = "TW_LH_SD_Sequence_ESB_Log", allocationSize = 1)
    private Integer logID = null;

    @Column(name = "TxId")
    private String txId = null;

    @Column(name = "Request")
    private String request = null;

    @Column(name = "ResponseCode")
    private String responseCode = null;

    @Column(name = "Response")
    private String response = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CreateTime")
    private Date createTime = null;
    
    public Integer getLogID() {
        return logID;
    }

    public void setLogID(Integer logID) {
        this.logID = logID;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
    
}
