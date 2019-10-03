package com.allianz.sd.core.jpa.model;

import com.allianz.sd.core.jpa.listener.CreateUpdateTimeListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/4/5
 * Time: 下午 5:27
 * To change this template use File | Settings | File Templates.
 */
@EntityListeners(CreateUpdateTimeListener.class)
@MappedSuperclass
public class CreateUpdateInfo {
    @Column(name = "CreateBy")
    private String createBy = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CreateTime")
    private Date createTime = null;

    @Column(name = "UpdateBy")
    private String updateBy = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UpdateTime")
    private Date updateTime = null;

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
