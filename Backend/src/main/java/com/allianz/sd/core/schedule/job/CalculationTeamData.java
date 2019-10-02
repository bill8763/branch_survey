package com.allianz.sd.core.schedule.job;

import com.allianz.sd.core.InstanceCode;
import com.allianz.sd.core.agencyplan.DrilldownRule;
import com.allianz.sd.core.jpa.bean.DataCategory;
import com.allianz.sd.core.jpa.model.*;
import com.allianz.sd.core.service.*;
import com.allianz.sd.core.service.bean.AgencyPlanMaster;
import com.allianz.sd.core.service.bean.Goal;
import com.allianz.sd.core.service.bean.GoalSettingValue;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 依照資料交換後的資料計算每個業務員畫面所需的欄位及資料存入Temp Table
 * Calculate the fields and data required for each salesperson's screen according to the data after data exchange and deposit them into Temp Table.
 */

@Component
public class CalculationTeamData extends QuartzJobBean {

	private static final Logger logger = LoggerFactory.getLogger(CalculationTeamData.class);

	@Autowired
	private GoalService goalService;

	@Autowired
	private DateService dateService;

	@Autowired
	private AgencyPlanService agencyPlanService;

	@Autowired
	private ProgressService progressService;

	@Autowired
	private SysDataService sysDataService;

	@Autowired
	private AgencyPlanSyncService agencyPlanSyncService;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private AgentUpdateVersionService agentUpdateVersionService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		long currentTimeMillis = System.currentTimeMillis();
		logger.info("CalculationTeamData Start");

		logger.info("Clear Progress Cache");
		agentUpdateVersionService.clearAgentTableCache(DataCategory.PROGRESS);
		logger.info("Clear Progress Cache Success!!");

		String organizationalUnit = organizationService.getOrganizationalUnit();

		Date today = dateService.getTodayDate();
		Set<Integer> years = sysDataService.getAPPDisplayYears(today,organizationalUnit);

		logger.info("Year size: " + years.size());

		//for cache approve list by year
		Map<Integer , Map<String,String>> cachePendingApproveMap = new LinkedHashMap<>();
		for(int dataYear : years) {

			logger.info("Delete AgencyPlan & TeamProgress Year["+dataYear+"]");
			agencyPlanSyncService.clearSyncTable(organizationalUnit,dataYear);
			progressService.clearTeamProgressSyncTable(organizationalUnit,dataYear);
			logger.info("Delete AgencyPlan & TeamProgress Year["+dataYear+"] Success");

			//for performance tuning-(calc AgencyPlan)
			Map<String,String> pendingApproveMap = cachePendingApproveMap.get(dataYear);
			if(pendingApproveMap == null) {
				logger.debug("Get all pending approve dataYear = " + dataYear);
				pendingApproveMap = goalService.getPendingApproveMap(dataYear);

				cachePendingApproveMap.put(dataYear,pendingApproveMap);
			}

			List<AgencyPlanDetail> agencyPlanDetails = agencyPlanService.getDetails(dataYear,organizationalUnit);
			List<AgencyPlan> agencyPlans = agencyPlanService.getMasters(dataYear,organizationalUnit);

			//Calc AgencyPlan
			logger.info("CalcAgencyPlan ["+dataYear+"]");
			agencyPlanSyncService.calcAgencyPlan(agencyPlanDetails,dataYear,pendingApproveMap);
			logger.info("CalcAgencyPlan ["+dataYear+"] finish!!");

			//Calc TeamProgress
			logger.info("CalcTeamProgress ["+dataYear+"]");
			progressService.calcTeamProgress(agencyPlans,agencyPlanDetails);
			logger.info("CalcTeamProgress ["+dataYear+"] finish!!");
		}

		logger.info("CalculationTeamData Finish time["+((System.currentTimeMillis() - currentTimeMillis) / 1000)+" sec]");

	}


}
