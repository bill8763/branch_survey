package com.allianz.sd.core.jpa.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/3/16
 * Time: 下午 5:12
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="TW_LH_SD_BatchLog")
public class BatchLog {

    @Id
    @Column(name = "BatchLogID")
    private Integer batchLogID = null;

    @Column(name = "OrganizationalUnit")
    private String organizationalUnit = null;

    @Column(name = "BatchName")
    private String batchName = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "StartTime")
    private Date startTime = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EndTime")
    private Date endTime = null;

    @Column(name = "Result")
    private String result = null;

    @Column(name = "ErrorMessage")
    private String errorMessage = null;

    @Column(name = "ErrorCount")
    private Integer errorCount = null;

    public Integer getBatchLogID() {
        return batchLogID;
    }

    public void setBatchLogID(Integer batchLogID) {
        this.batchLogID = batchLogID;
    }

    public String getOrganizationalUnit() {
        return organizationalUnit;
    }

    public void setOrganizationalUnit(String organizationalUnit) {
        this.organizationalUnit = organizationalUnit;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }
}
