package com.allianz.sd.core.jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/22
 * Time: 下午 12:06
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="TW_LH_SD_TableUpdateVersion")
public class TableUpdateVersion {

    @Id
    @Column(name = "TableName")
    private String tableName = null;

    @Column(name = "DataTime")
    private Date dataTime = null;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Date getDataTime() {
        return dataTime;
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }
}
