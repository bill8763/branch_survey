package com.allianz.sd.core.controller;

import com.allianz.sd.core.jpa.model.*;
import com.allianz.sd.core.jpa.repository.PerformanceTableMonthRepository;
import com.allianz.sd.core.jpa.repository.SysDataRepository;
import com.allianz.sd.core.jpa.repository.SysYearDataRepository;
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
public class UpdatePormanceTable {

    @Autowired
    private OrganizationService organizationService = null;

    @Autowired
    private DateService dateService;

    @Autowired
    private PerformanceTableMonthRepository performanceTableMonthRepository;

    public boolean update(File file) throws Exception{
        InputStream is = new FileInputStream(file);
        HSSFWorkbook wb = new HSSFWorkbook(is);

        performanceTableMonthRepository.deleteAll();

        HSSFSheet codeSheet = wb.getSheetAt(1);
        int codeRows = codeSheet.getPhysicalNumberOfRows();
        for(int i=1;i<codeRows;i++) {
            HSSFRow row = codeSheet.getRow(i);

            Date startDate = row.getCell(1).getDateCellValue();
            if(startDate == null) continue;

            Date endDate = row.getCell(2).getDateCellValue();

            String performanceTable = row.getCell(0).getStringCellValue();
            double PerformanceYear = row.getCell(3).getNumericCellValue();
            double PerformanceMonth = row.getCell(4).getNumericCellValue();
            double PerformanceQuarter = row.getCell(5).getNumericCellValue();
            double Days = row.getCell(7).getNumericCellValue();

            if((int) Days == 0) continue;
            PerformanceTableMonth performanceTableMonth = new PerformanceTableMonth();

            PerformanceTableMonthIdentity performanceTableMonthIdentity = new PerformanceTableMonthIdentity();
            performanceTableMonthIdentity.setOrganizationalUnit(organizationService.getOrganizationalUnit());
            performanceTableMonthIdentity.setPerformanceTable(performanceTable);
            performanceTableMonthIdentity.setStartDate(startDate);

            performanceTableMonth.setDays((int)Days);
            performanceTableMonth.setPerformanceYear((int)PerformanceYear);
            performanceTableMonth.setPerformanceMonth((int)PerformanceMonth);
            performanceTableMonth.setPerformanceQuarter((int)PerformanceQuarter);
            performanceTableMonth.setEndDate(endDate);
            performanceTableMonth.setIdentity(performanceTableMonthIdentity);
            performanceTableMonth.setCreateBy("SD");
            performanceTableMonth.setUpdateBy("SD");

            performanceTableMonthRepository.save(performanceTableMonth);
//            switch (row.getCell(1).getCellType()) {
//                case BOOLEAN :
//                    //To-do
//                    break;
//                case NUMERIC:
//                    code = ((int) row.getCell(1).getNumericCellValue()) + "";
//                    break;
//                case STRING:
//                    code = row.getCell(1).getStringCellValue();
//                    break;
//            }


        }

        return true;
    }
}
