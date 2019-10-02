package com.allianz.sd.core.jpa.model;

import com.allianz.sd.core.jpa.listener.CreateTimeListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/4/5
 * Time: 下午 5:27
 * To change this template use File | Settings | File Templates.
 */
@EntityListeners(CreateTimeListener.class)
@MappedSuperclass
public class CreateInfo {
    @Column(name = "CreateBy")
    private String createBy = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CreateTime")
    private Date createTime = null;

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

}
