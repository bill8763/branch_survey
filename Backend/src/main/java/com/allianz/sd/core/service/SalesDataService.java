package com.allianz.sd.core.service;

import com.allianz.sd.core.jpa.model.*;
import com.allianz.sd.core.jpa.repository.*;
import com.allianz.sd.core.service.bean.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class SalesDataService {

    private static final Logger logger = LoggerFactory.getLogger(SalesDataService.class);

    @Autowired
    private AgentSalesDataRepository agentSalesDataRepository;

    //透過每月的業績資料計算該月/該季/該年的加總
    public SumSalesData calcSalesData(Map<Integer, AgentSalesData> salesMap , int actualStartMonth,  PerformanceTime performanceTime) {
        SumSalesData sumSalesData = new SumSalesData();

        SalesData salesDataMonth = new SalesData();

        List<Integer> monthOfQuarter = performanceTime.getMonthOfQuarter();
        int startMonthOfQuarter = monthOfQuarter.get(0);
        int endMonthOfQuarter = monthOfQuarter.get(monthOfQuarter.size()-1);

        int endMonthOfYear = performanceTime.getEndMonthOfYear();
        int currentMonth = performanceTime.getMonth();

        int actualCalcEndMonth = performanceTime.getActualCalcEndMonth();
        int calcQuarterEndMonth = currentMonth < endMonthOfQuarter ? currentMonth : endMonthOfQuarter;

        logger.trace("salesMap size = " + salesMap.size() + ",currentMonth = " + currentMonth + ",actualStartMonth = " + actualStartMonth + ",endMonthOfYear = " + endMonthOfYear + ",startMonthOfQuarter = "+startMonthOfQuarter+",endMonthOfQuarter = " + endMonthOfQuarter + ",calcQuarterEndMonth = " + calcQuarterEndMonth + ",actualCalcEndMonth = " + actualCalcEndMonth);

        if(salesMap.size() != 0) {

            //先算月
            AgentSalesData monthAgentSalesData = salesMap.get(currentMonth);
            if(monthAgentSalesData != null) {
                salesDataMonth.setFyfc(monthAgentSalesData.getFyfc());
                salesDataMonth.setAnp(monthAgentSalesData.getAnp());
                salesDataMonth.setManPower(monthAgentSalesData.getManpower());
                salesDataMonth.setRecruitment(monthAgentSalesData.getRecruitment());
                salesDataMonth.setNewBusinessCase(monthAgentSalesData.getNewBusinessCase());
            }

            //再算季
            SalesData salesDataQuarter = calcActual(salesMap,startMonthOfQuarter,calcQuarterEndMonth);

            //先算年
            SalesData salesDataYear = calcActual(salesMap,actualStartMonth,actualCalcEndMonth);

            sumSalesData.setMonthSalesData(salesDataMonth);
            sumSalesData.setQuarterSalesData(salesDataQuarter);
            sumSalesData.setYearSalesData(salesDataYear);
        }


        return sumSalesData;
    }

    //取得業務員年度每月業績
    public Map<Integer, AgentSalesData> getAgentPersonalSalesDataByYear(AgentYearData agentYearData) {
        int year = agentYearData.getIdentity().getDataYear();
        String agentID = agentYearData.getIdentity().getAgentID();
        String organizationalUnit = agentYearData.getIdentity().getOrganizationalUnit();
        String performanceType = agentYearData.getPersonnelPerformanceType();

        return getAgentSalesDataByYear(year,agentID,organizationalUnit,performanceType);
    }

    public List<AgentSalesData> getAgentSalesData(String agentID , int dataYear) {
        List<AgentSalesData> agentSalesDataList = agentSalesDataRepository.getActual(agentID, ("%" + dataYear + "%"));

        _fillNumber(agentSalesDataList);

        return agentSalesDataList;
    }

    private void _fillNumber(List<AgentSalesData> agentSalesDataList) {
        for(AgentSalesData agentSalesData : agentSalesDataList) {
            if(agentSalesData.getFyfc() == null) agentSalesData.setFyfc(new Long(0));
            if(agentSalesData.getAnp() == null) agentSalesData.setAnp(new Long(0));
            if(agentSalesData.getManpower() == null) agentSalesData.setManpower(new Long(0));
            if(agentSalesData.getRecruitment() == null) agentSalesData.setRecruitment(new Long(0));
            if(agentSalesData.getNewBusinessCase() == null) agentSalesData.setNewBusinessCase(new Long(0));
        }
    }

    //取得業務員年度每月業績
    public Map<Integer, AgentSalesData> getAgentSalesDataByYear(int year , String agentID ,
                                                                String organizationalUnit , String performanceType) {
        Map<Integer,AgentSalesData> salesDataMap = new LinkedHashMap<>();

        logger.trace("getAgentSalesDataByYear year = " + year + ",agentID = " + agentID + ",organizationalUnit = " + organizationalUnit + ",performanceType = " + performanceType);
        List<AgentSalesData> agentSalesDatas = agentSalesDataRepository.getAgentSalesData(organizationalUnit,agentID,year + "%",performanceType);

        _fillNumber(agentSalesDatas);

        logger.trace("getAgentSalesDataByYear size = " + agentSalesDatas.size());

        for(AgentSalesData agentSalesData : agentSalesDatas) {
            Date month = agentSalesData.getIdentity().getDataYearMonth();

            logger.trace("[Month="+(month.getMonth() + 1)+"] Value = " + agentSalesData);
            salesDataMap.put(month.getMonth() + 1 , agentSalesData);
        }

        return salesDataMap;
    }

    //傳入每月業績，依照業績起算月累加到結束月
    public SalesData calcActual(Map<Integer, AgentSalesData> monthMap , int startMonth , int endMonth) {
        SalesData salesData = new SalesData();

        long fyfc = 0;
        long anp = 0;
        long manpower = 0;
        long recruitment = 0;
        long newBusiness = 0;

        if(endMonth == -1) endMonth = 12;

        logger.trace("calcActual startMonth = " + startMonth + ",endMonth = " + endMonth);

        if(monthMap.size() != 0) {
            for(int i = startMonth ;i <= endMonth ; i++) {
                AgentSalesData agentSalesData = monthMap.get(i);

                if(agentSalesData != null) {
                    fyfc += agentSalesData.getFyfc() != null ? agentSalesData.getFyfc() : 0;
                    anp += agentSalesData.getAnp() != null ? agentSalesData.getAnp() : 0;
                    manpower += agentSalesData.getManpower() != null ? agentSalesData.getManpower() : 0;
                    recruitment += agentSalesData.getRecruitment() != null ? agentSalesData.getRecruitment() : 0;
                    newBusiness += agentSalesData.getNewBusinessCase() != null ? agentSalesData.getNewBusinessCase() : 0;

                    logger.info("fyfc = "+fyfc+"  , anp = "+anp );
                }

            }
        }


        salesData.setFyfc(fyfc);
        salesData.setAnp(anp);
        salesData.setManPower(manpower);
        salesData.setRecruitment(recruitment);
        salesData.setNewBusinessCase(newBusiness);

        return salesData;
    }

    //傳入每月業績，依照業績起算月累加
    public SalesData calcActual(Map<Integer, AgentSalesData> monthMap , int startMonth) {
        return calcActual(monthMap,startMonth,-1);
    }


}
