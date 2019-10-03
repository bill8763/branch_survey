package com.allianz.sd.core.jpa.repository;

import com.allianz.sd.core.jpa.model.CodeType;
import com.allianz.sd.core.jpa.model.CodeTypeIdentity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 3:53
 * To change this template use File | Settings | File Templates.
 */
public interface CodeTypeRepository extends JpaRepository<CodeType, CodeTypeIdentity> {

}
