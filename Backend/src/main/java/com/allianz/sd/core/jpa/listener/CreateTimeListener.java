package com.allianz.sd.core.jpa.listener;

import com.allianz.sd.core.jpa.model.CreateInfo;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
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
public class CreateTimeListener {

    @PrePersist
    public void addCreateTime(CreateInfo info) {
        info.setCreateTime(new Date(new java.util.Date().getTime()));
    }

    @PreUpdate
    public void addUpdateTime(CreateInfo info) {
        if(info.getCreateTime() == null) info.setCreateTime(new Date(new java.util.Date().getTime()));
    }

}
