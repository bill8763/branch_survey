package com.allianz.sd.core.datasync.impl;

import com.allianz.sd.core.api.model.Actual;
import com.allianz.sd.core.api.model.ActualValue;
import com.allianz.sd.core.datasync.AgentTableDataSync;
import com.allianz.sd.core.exception.NotFoundAgentYearDataException;
import com.allianz.sd.core.exception.NotFoundSysYearDataException;
import com.allianz.sd.core.jpa.model.AgentSalesData;
import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.jpa.model.SysYearData;
import com.allianz.sd.core.service.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class ActualAgentTableDataSync implements AgentTableDataSync {

    @Autowired
    private SysDataService sysDataService;

    @Autowired
    private SalesDataService salesDataService;

    @Autowired
    private DateService dateService;

    @Autowired
    private AgentDataService agentDataService;

    @Autowired
    private OrganizationService organizationService;

    @Override
    public void pullData(String agentID, List pullData) throws Exception{
        String organizationalUnit = organizationService.getOrganizationalUnit();

        //新增日期
        Date today = dateService.getTodayDate();
        int dataYear = dateService.getCurrentYear();

        //查詢SysYearData 起訖時間 取得年份
        AgentYearData agentYearData = agentDataService.getAgentYearData(agentID, dataYear);
        if(agentYearData != null) {
            List<SysYearData> yearList = sysDataService.getAPPDisplaySysYearData(agentYearData.getAppSysCtrl(), today);

            if(yearList.size() != 0) {
                for(SysYearData sysYearData : yearList) {
                    List<AgentSalesData> agentSalesData = salesDataService.getAgentSalesData(agentID, sysYearData.getIdentity().getDataYear());

                    for(AgentSalesData result : agentSalesData) {
                        String month = new SimpleDateFormat("M").format(result.getIdentity().getDataYearMonth());
                        String type = result.getIdentity().getPerformanceType();

                        //塞Actual
                        Actual actual = new Actual();
                        actual.setDataYear(new BigDecimal(sysYearData.getIdentity().getDataYear()));
                        actual.setTimeBase(Actual.TimeBaseEnum.MONTH);

                        //塞Value
                        List<ActualValue> actualValueList = new ArrayList<ActualValue>();

                        Map<ActualValue.DataTypeEnum,BigDecimal> actualMap = new LinkedHashMap<>();
                        actualMap.put(ActualValue.DataTypeEnum.ANP,new BigDecimal(result.getAnp()));
                        actualMap.put(ActualValue.DataTypeEnum.FYFC,new BigDecimal(result.getFyfc()));
                        actualMap.put(ActualValue.DataTypeEnum.NEWBUSINESSCASE,new BigDecimal(result.getNewBusinessCase()));
                        actualMap.put(ActualValue.DataTypeEnum.MANPOWER,new BigDecimal(result.getManpower()));
                        actualMap.put(ActualValue.DataTypeEnum.RECRUITMENT,new BigDecimal(result.getRecruitment()));

                        for(ActualValue.DataTypeEnum dataType : actualMap.keySet()){
                            ActualValue actualValue = new ActualValue();
                            actualValue.setMonth(new BigDecimal(month));
                            actualValue.setPerformanceType(type);
                            actualValue.setValue(actualMap.get(dataType));
                            actualValue.setDataType(dataType);
                            actualValueList.add(actualValue);//存一筆Value到ValueList
                        }

                        //整個Values存給Actual
                        actual.setValues(actualValueList);

                        //存一筆Actual 給 ActualList
                        pullData.add(actual);
                    }

                }
            }else{
                throw new NotFoundSysYearDataException(organizationalUnit,agentID,dataYear);
            }
        }
        else {
            throw new NotFoundAgentYearDataException(organizationalUnit,agentID,dataYear);
        }
    }
}
