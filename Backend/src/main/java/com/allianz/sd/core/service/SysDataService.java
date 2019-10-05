package com.allianz.sd.core.service;

import com.allianz.sd.core.jpa.model.SysData;
import com.allianz.sd.core.jpa.model.SysDataIdentity;
import com.allianz.sd.core.jpa.model.SysYearData;
import com.allianz.sd.core.jpa.model.SysYearDataIdentity;
import com.allianz.sd.core.jpa.repository.SysDataRepository;
import com.allianz.sd.core.jpa.repository.SysYearDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SysDataService {

    private static final Logger logger = LoggerFactory.getLogger(SysDataService.class);

    @Autowired
    private SysDataRepository sysDataRepository;

    @Autowired
    private SysYearDataRepository sysYearDataRepository;

    public List<SysData> getAllSysData() {
        return sysDataRepository.findAll();
    }

    public SysData getSysData(String organizationalUnit , String appSysCtrl) {
        SysDataIdentity sysDataIdentity = new SysDataIdentity();
        sysDataIdentity.setOrganizationalUnit(organizationalUnit);
        sysDataIdentity.setAppSysCtrl(appSysCtrl);
        Optional<SysData> sysDataOptional = sysDataRepository.findById(sysDataIdentity);

        return sysDataOptional.orElse(null);
    }


    public Set<Integer> getAPPDisplayYears(Date date,String organizationalUnit) {

        Set<Integer> yearSet = new HashSet<Integer>();

        List<Integer> sysYearDatas = sysYearDataRepository.getAPPDisplayYears(date,date,organizationalUnit);

        for(Integer year : sysYearDatas) {
            yearSet.add(year);
        }

        return yearSet;
    }

    public SysYearData getSysYearDataBySysCtrl(String organizationalUnit , String appSysCtrl , int dateYear) {
        SysYearDataIdentity sysYearDataIdentity = new SysYearDataIdentity();
        sysYearDataIdentity.setAppSysCtrl(appSysCtrl);
        sysYearDataIdentity.setDataYear(dateYear);
        sysYearDataIdentity.setOrganizationalUnit(organizationalUnit);
        Optional<SysYearData> sysYearDataOption = sysYearDataRepository.findById(sysYearDataIdentity);

        return sysYearDataOption.orElse(null);
    }

    public SysYearData getSysYearData(String organizationalUnit , String agentID , int year) {
        return sysYearDataRepository.getAgentSysYearData(organizationalUnit,year,agentID);
    }

    public List<SysYearData> getAPPDisplaySysYearData(String appSysCtrl, Date datetime) {
        return sysYearDataRepository.getYear(datetime, datetime, appSysCtrl);
    }

    /**
     * 取得sys_data已結績跟plan起算月-For Goal & AgencyPlan使用
     * @param sysYearData
     * @return
     */
    public int getRemainingPlanCalcStartMonth(SysYearData sysYearData) {

        if(sysYearData == null) return 1;

        SysData sysData = sysYearData.getSysData();
        Date startDate = sysData.getRemainingPlanCalcStartMonth();
        
        int dataYear = sysYearData.getIdentity().getDataYear();

        //TODO
        Calendar calendar = new GregorianCalendar();
        try {
        	calendar.setTime(startDate);
	    }catch (Exception e) {
	    	return 7;
		}
        int year = calendar.get(Calendar.YEAR);

        logger.debug("SysDataService getPlanCalcStartMonth ["+year+"],["+dataYear+"] , ["+(year == dataYear)+"]");

        int calcMonth = calendar.get(Calendar.MONTH) + 1;

        //如果系統年度大於原本plan結績年，代表還沒到，月份統一用1月
        if(dataYear > year)  calcMonth = 1;
        //如果原本plan結績年大於系統年，代表是看過往年度，月份統一用99月(因為永遠算不到plan了)
        else if(dataYear < year) calcMonth = 99;

    	return calcMonth;
    }

}
