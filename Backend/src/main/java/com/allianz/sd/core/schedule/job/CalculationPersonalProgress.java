package com.allianz.sd.core.schedule.job;

import com.allianz.sd.core.InstanceCode;
import com.allianz.sd.core.jpa.model.*;
import com.allianz.sd.core.jpa.repository.*;
import com.allianz.sd.core.progress.CalcPersonalProgress;
import com.allianz.sd.core.service.*;
import com.allianz.sd.core.service.bean.GoalSettingValue;
import com.allianz.sd.core.service.bean.*;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class CalculationPersonalProgress extends QuartzJobBean {

	private static final Logger logger = LoggerFactory.getLogger(CalculationPersonalProgress.class);

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private ProgressService progressService;

	@Autowired
	private DateService dateService;

	@Autowired
	private GoalService goalService;

	@Autowired
	private SysDataService sysDataService;

	@Autowired
	private BeanFactory beanFactory;


	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		logger.info("CalculationPersonalProgress Start");

		CalcPersonalProgress calcPersonalProgress = (CalcPersonalProgress)beanFactory.getBean(InstanceCode.CalcPersonalProgress);

		String organizationalUnit = organizationService.getOrganizationalUnit();
		Date date = dateService.getTodayDate();

		//取得APP畫面會顯示的年度
		Set<Integer> years = sysDataService.getAPPDisplayYears(date,organizationalUnit);

		logger.info("sysYearDatas size " + years.size());

		for (int year : years) {

			logger.info("calc Progress ["+year+"]");

			logger.info("Delete PrersonalProgress ["+year+"]");
			progressService.clearPersonalProgressSyncTable(organizationalUnit,year);
			logger.info("Delete PrersonalProgress ["+year+"] Success!!");

			//用年度帶出當年度的AG業務員
			List<AgentYearData> agentYearDataList = calcPersonalProgress.getCalcPersonalAgentYearDatas(year,organizationalUnit);

			logger.info("agentYearDataList size " + agentYearDataList.size());

			for (AgentYearData agentYearData : agentYearDataList) {
				String agentID = agentYearData.getIdentity().getAgentID();
				String appUseMode = agentYearData.getAppUseMode();
				String performanceTable = agentYearData.getPerformanceTable();

				try{

					logger.debug("["+agentID+"]" + ",appUseMode = [" + appUseMode + "],performanceTable = ["+performanceTable+"]");

					SysYearData sysYearData = sysDataService.getSysYearData(organizationalUnit,agentID,year);
					PerformanceTime performanceTime = progressService.getPerformanceTime(date, organizationalUnit,performanceTable);

					if(sysYearData == null || performanceTime == null) {
						progressService.saveEmptyPersonalProgress(agentYearData);
					}
					else {

						//如果當下年度已超過計算年度，改變PerforamnceTime的屬性
						if(performanceTime.getYear() > year) {
							logger.trace("performance year over current calc year");
							List<Integer> monthOfQuarterList = new ArrayList<Integer>();
							monthOfQuarterList.add(10);
							monthOfQuarterList.add(11);
							monthOfQuarterList.add(12);
							performanceTime.setYear(year);
							performanceTime.setQuarter(4);
							performanceTime.setMonth(12);
							performanceTime.setEndMonthOfYear(12);
							performanceTime.setMonthOfQuarter(monthOfQuarterList);
							performanceTime.setActualCalcEndMonth(12);
							performanceTime.setDays(0);
							performanceTime.setPlanClacStartMonth(13);
						}

						Goal approveGoal = goalService.getYearGoal(agentID, year);
						progressService.calcPersonalProgress(agentYearData,approveGoal,performanceTime,sysYearData);
					}

				}catch(Exception e) {
					logger.error("Calc PersonalProgress Exception!!",e);
				}

			}

		}

		logger.info("CalculationPersonalProgress Finish");
	}

}
