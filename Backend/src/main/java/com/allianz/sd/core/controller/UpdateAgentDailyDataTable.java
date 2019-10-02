package com.allianz.sd.core.controller;

import com.allianz.sd.core.jpa.model.*;
import com.allianz.sd.core.jpa.repository.*;
import com.allianz.sd.core.service.DateService;
import com.allianz.sd.core.service.OrganizationService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 6:39
 * To change this template use File | Settings | File Templates.
 */
@Component
public class UpdateAgentDailyDataTable {

    @Autowired
    private OrganizationService organizationService = null;

    @Autowired
    private DateService dateService;

    @Autowired
    private AgentDailyDataRepository agentDailyDataRepository;

    @Autowired
    private AgentYearDataRepository agentYearDataRepository;

    @Autowired
    private SysYearDataRepository sysYearDataRepository;


    public boolean update(File file) throws Exception{
        InputStream is = new FileInputStream(file);
        HSSFWorkbook wb = new HSSFWorkbook(is);

        agentDailyDataRepository.deleteAll();

        HSSFSheet codeSheet = wb.getSheetAt(3);
        int codeRows = codeSheet.getPhysicalNumberOfRows();
        for(int i=1;i<codeRows;i++) {
            HSSFRow row = codeSheet.getRow(i);

            String agentID = row.getCell(0).getStringCellValue();
            if(StringUtils.isEmpty(agentID)) continue;

            Date DataDate = row.getCell(1).getDateCellValue();
            double NumberOfMeet = row.getCell(2).getNumericCellValue();
            double NumberOfSubmit = row.getCell(3).getNumericCellValue();
            double NumberOfInforce = row.getCell(4).getNumericCellValue();
            double DataYear = row.getCell(5).getNumericCellValue();
            double DataMonth = row.getCell(6).getNumericCellValue();
            double DataQuarter = row.getCell(7).getNumericCellValue();

            AgentYearDataIdentity agentYearDataIdentity = new AgentYearDataIdentity();
            agentYearDataIdentity.setOrganizationalUnit(organizationService.getOrganizationalUnit());
            agentYearDataIdentity.setAgentID(agentID);
            agentYearDataIdentity.setDataYear((int)DataYear);
            Optional<AgentYearData> optionalAgentYearData = agentYearDataRepository.findById(agentYearDataIdentity);
            AgentYearData agentYearData = optionalAgentYearData.orElse(null);

            if(agentYearData == null) continue;

            SysYearDataIdentity sysYearDataIdentity = new SysYearDataIdentity();
            sysYearDataIdentity.setOrganizationalUnit(organizationService.getOrganizationalUnit());
            sysYearDataIdentity.setDataYear((int)DataYear);
            sysYearDataIdentity.setAppSysCtrl(agentYearData.getAppSysCtrl());
            Optional<SysYearData> sysYearDataOptional = sysYearDataRepository.findById(sysYearDataIdentity);
            SysYearData sysYearData = sysYearDataOptional.orElse(null);

            if(sysYearData == null) continue;


            AgentDailyData agentDailyData = new AgentDailyData();
            AgentDailyDataIdentity agentSalesDataIdentity = new AgentDailyDataIdentity();
            agentSalesDataIdentity.setOrganizationalUnit(organizationService.getOrganizationalUnit());
            agentSalesDataIdentity.setAgentID(agentID);
            agentSalesDataIdentity.setDataDate(DataDate);

            agentDailyData.setIdentity(agentSalesDataIdentity);
            agentDailyData.setDataMonth((int)DataMonth);
            agentDailyData.setDataQuarter((int)DataQuarter);
            agentDailyData.setDataYear((int)DataYear);
            agentDailyData.setNumberOfInforce((int)NumberOfInforce);
            agentDailyData.setNumberOfMeet((int)NumberOfMeet);
            agentDailyData.setNumberOfSubmit((int)NumberOfSubmit);
            agentDailyData.setProgressPointInforce((int)(agentDailyData.getNumberOfInforce() * sysYearData.getInforceConvertPointBase()));
            agentDailyData.setProgressPointMeet((int)(agentDailyData.getNumberOfMeet() * sysYearData.getMeetConvertPointBase()));
            agentDailyData.setProgressPointSubmit((int)(agentDailyData.getNumberOfSubmit() * sysYearData.getSubmitConvertPointBase()));


            agentDailyData.setDataTime(dateService.getTodayDate());
            agentDailyData.setCreateBy("SD");
            agentDailyData.setUpdateBy("SD");
            agentDailyDataRepository.save(agentDailyData);


        }

        return true;
    }
}
