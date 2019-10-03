package com.allianz.sd.core.schedule.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.allianz.sd.core.jpa.model.SysData;
import com.allianz.sd.core.jpa.model.SysDataIdentity;
import com.allianz.sd.core.jpa.model.SysDataMonth;
import com.allianz.sd.core.jpa.repository.SysDataMonthRepository;
import com.allianz.sd.core.jpa.repository.SysDataRepository;
import com.allianz.sd.core.service.DateService;
import com.allianz.sd.core.service.OrganizationService;

@Component
public class SysDataSaleMonthPlanMonth extends QuartzJobBean{
	
	private static final Logger logger = LoggerFactory.getLogger(SysDataSaleMonthPlanMonth.class);
	
	@Autowired
	private DateService dateService;
	

	@Autowired
	private SysDataMonthRepository sysDataMonthRepository;
	
	@Autowired
	private SysDataRepository sysDataRepository;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.trace("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< QuartzJob SysDataSaleMonthPlanMonth Start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//		String organizationalUnit = organizationService.getOrganizationalUnit();
		Date date = dateService.getTodayDate();
		List<Object[]> sysDataAppSysCtrlUnit = sysDataRepository.getAppSysCtrlAndOrganizationalUnit();
		
		for(Object[] appSysCtrlUnit : sysDataAppSysCtrlUnit) {
			String appSysCtrl = (String)appSysCtrlUnit[0];
			String organizationalUnit = (String)appSysCtrlUnit[1];
			logger.trace("organizationalUnit = "+organizationalUnit + "  appSysCtrl = "+appSysCtrl);
			
			//query SysDataMonth 
			SysDataMonth sysDataMonth = sysDataMonthRepository.getMonth(organizationalUnit, appSysCtrl, date, date);
			if(sysDataMonth != null) {
				//find planMonth & saleMonth
				Date planMonth = sysDataMonth.getPlanMonth();
				Date saleMonth = sysDataMonth.getSaleMonth();
				//update SysData PerformanceSettlementMonth & RemainingPlanCalcStartMonth
				SysDataIdentity sysDataIdentity = new SysDataIdentity();
				sysDataIdentity.setAppSysCtrl(appSysCtrl);
				sysDataIdentity.setOrganizationalUnit(organizationalUnit);
				Optional<SysData> sysDataOptional = sysDataRepository.findById(sysDataIdentity);
				SysData sysData = sysDataOptional.orElse(null);
				if(sysData != null) {
					sysData.setPerformanceSettlementMonth(saleMonth);
					sysData.setRemainingPlanCalcStartMonth(planMonth);
//					sysData.setUpdateBy("SD");
					SysData updateSysData = sysDataRepository.save(sysData);
					boolean isSuccess = updateSysData != null ;
					logger.trace("updateSysData : " + isSuccess);
				}
			}
		}
			logger.trace("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< QuartzJob SysDataSaleMonthPlanMonth Finish >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}

	

}
