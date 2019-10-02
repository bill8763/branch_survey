package com.allianz.sd.core.jpa.repository;

import com.allianz.sd.core.jpa.model.CISLBatchLogDetail;
import com.allianz.sd.core.jpa.model.CISLBatchLogDetailIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 12:41
 * To change this template use File | Settings | File Templates.
 */
public interface CISLBatchLogDetailRepository extends JpaRepository<CISLBatchLogDetail, CISLBatchLogDetailIdentity> {

    @Query("select a from CISLBatchLogDetail a\n" +
            "where a.identity.batchLogID = :batchID")
    public List<CISLBatchLogDetail> getCISLDetailByBatchID(
            @Param("batchID") Integer batchID
    );
}
