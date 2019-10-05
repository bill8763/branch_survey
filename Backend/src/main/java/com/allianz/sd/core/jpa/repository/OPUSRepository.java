package com.allianz.sd.core.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.allianz.sd.core.jpa.model.OPUS;
import com.allianz.sd.core.jpa.model.OPUSIdentity;


/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 12:41
 * To change this template use File | Settings | File Templates.
 */
public interface OPUSRepository extends JpaRepository<OPUS, OPUSIdentity> {

}
