package com.allianz.sd.core.jpa.listener;

import com.allianz.sd.core.jpa.model.CreateUpdateInfo;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/4/5
 * Time: 下午 5:26
 * To change this template use File | Settings | File Templates.
 */
public class CreateUpdateTimeListener {

    @PrePersist
    public void addCreateTime(CreateUpdateInfo info) {
        info.setCreateTime(new Date(new java.util.Date().getTime()));
        info.setUpdateTime(new Date(new java.util.Date().getTime()));
    }
    @PreUpdate
    public void addUpdateTime(CreateUpdateInfo info) {
        if(info.getCreateTime() == null) info.setCreateTime(new Date(new java.util.Date().getTime()));
        info.setUpdateTime(new Date(new java.util.Date().getTime()));
    }
}
