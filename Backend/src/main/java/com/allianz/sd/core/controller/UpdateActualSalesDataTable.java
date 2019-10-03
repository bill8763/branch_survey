package com.allianz.sd.core.controller;

import com.allianz.sd.core.jpa.model.*;
import com.allianz.sd.core.jpa.repository.*;
import com.allianz.sd.core.service.DateService;
import com.allianz.sd.core.service.NumberService;
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
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 6:39
 * To change this template use File | Settings | File Templates.
 */
@Component
public class UpdateActualSalesDataTable {

    @Autowired
    private OrganizationService organizationService = null;

    @Autowired
    private DateService dateService;

    @Autowired
    private AgentSalesDataRepository agentSalesDataRepository;

    @Autowired
    private NumberService numberService;


    public boolean update(File file) throws Exception{
        InputStream is = new FileInputStream(file);
        HSSFWorkbook wb = new HSSFWorkbook(is);

        agentSalesDataRepository.deleteAll();

        HSSFSheet codeSheet = wb.getSheetAt(2);
        int codeRows = codeSheet.getPhysicalNumberOfRows();
        for(int i=1;i<codeRows;i++) {
            HSSFRow row = codeSheet.getRow(i);

            String agentID = row.getCell(0).getStringCellValue();
            if(StringUtils.isEmpty(agentID)) continue;

            double year = row.getCell(1).getNumericCellValue();
            double month = row.getCell(2).getNumericCellValue();
            String PerformanceType = row.getCell(3).getStringCellValue();
            double Anp = row.getCell(4).getNumericCellValue();
            double Fyfc = row.getCell(5).getNumericCellValue();
            double NewBusinessCase = row.getCell(6).getNumericCellValue();
            double Manpower = row.getCell(7).getNumericCellValue();
            double Recruitment = row.getCell(8).getNumericCellValue();

            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR,(int)year);
            c.set(Calendar.MONTH,(int)month-1);

            AgentSalesData agentSalesData = new AgentSalesData();
            AgentSalesDataIdentity agentSalesDataIdentity = new AgentSalesDataIdentity();
            agentSalesDataIdentity.setOrganizationalUnit(organizationService.getOrganizationalUnit());
            agentSalesDataIdentity.setAgentID(agentID);
            agentSalesDataIdentity.setDataYearMonth(c.getTime());
            agentSalesDataIdentity.setPerformanceType(PerformanceType);

            agentSalesData.setIdentity(agentSalesDataIdentity);
            agentSalesData.setAnp(numberService.round(Anp));
            agentSalesData.setFyfc((long)Fyfc);
            agentSalesData.setNewBusinessCase((long)NewBusinessCase);
            agentSalesData.setManpower((long)Manpower);
            agentSalesData.setRecruitment((long)Recruitment);
            agentSalesData.setDataTime(dateService.getTodayDate());
            agentSalesData.setCreateBy("SD");
            agentSalesData.setUpdateBy("SD");
            agentSalesDataRepository.save(agentSalesData);


        }

        return true;
    }
}
