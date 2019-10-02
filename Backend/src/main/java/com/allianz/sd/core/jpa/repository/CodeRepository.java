package com.allianz.sd.core.jpa.repository;

import com.allianz.sd.core.jpa.model.Code;
import com.allianz.sd.core.jpa.model.CodeIdentity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 3:53
 * To change this template use File | Settings | File Templates.
 */
public interface CodeRepository extends JpaRepository<Code,CodeIdentity> {
    public List<Code> findByIdentityTypeID(String typeId);
}
