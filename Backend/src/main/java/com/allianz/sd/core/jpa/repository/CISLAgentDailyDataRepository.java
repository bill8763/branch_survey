package com.allianz.sd.core.jpa.repository;

import com.allianz.sd.core.jpa.model.AgentDailyDataIdentity;
import com.allianz.sd.core.jpa.model.CISLAgentDailyData;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 12:41
 * To change this template use File | Settings | File Templates.
 */
public interface CISLAgentDailyDataRepository extends JpaRepository<CISLAgentDailyData,AgentDailyDataIdentity> {

}
