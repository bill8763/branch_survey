package com.allianz.sd.core.jpa.repository;

import com.allianz.sd.core.jpa.model.AgencyPlanDetail;
import com.allianz.sd.core.jpa.model.AgencyPlanDetailIdentity;
import com.allianz.sd.core.jpa.model.CISLAgencyPlanDetail;
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
public interface CISLAgencyPlanDetailRepository extends JpaRepository<CISLAgencyPlanDetail, AgencyPlanDetailIdentity> {

}
