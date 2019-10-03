package com.allianz.sd.core.controller;

import com.allianz.sd.core.jpa.model.*;
import com.allianz.sd.core.jpa.repository.CodeRepository;
import com.allianz.sd.core.jpa.repository.CodeTypeRepository;
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
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 6:39
 * To change this template use File | Settings | File Templates.
 */
@Component
public class UpdateSysTable {

    @Autowired
    private OrganizationService organizationService = null;

    @Autowired
    private DateService dateService;

    @Autowired
    private SysDataRepository sysDataRepository;

    @Autowired
    private SysYearDataRepository sysYearDataRepository;


    public boolean update(File file) throws Exception{
        InputStream is = new FileInputStream(file);
        HSSFWorkbook wb = new HSSFWorkbook(is);

        sysYearDataRepository.deleteAll();
        sysDataRepository.deleteAll();


        Set<String> appSysCtrlSet = new HashSet<>();
        HSSFSheet codeSheet = wb.getSheetAt(0);
        int codeRows = codeSheet.getPhysicalNumberOfRows();
        for(int i=1;i<codeRows;i++) {
            HSSFRow row = codeSheet.getRow(i);

            String appSysCtrl = row.getCell(0).getStringCellValue();
            if(StringUtils.isEmpty(appSysCtrl) || row.getCell(1) == null) continue;

            double dataYear = row.getCell(1).getNumericCellValue();
            double GoalSettingTimeLimit = row.getCell(2).getNumericCellValue();
            double GoalApproveTimeLimit = row.getCell(3).getNumericCellValue();
            Date AppDisplayStartDate = row.getCell(4).getDateCellValue();
            Date AppDisplayEndDate = row.getCell(5).getDateCellValue();
            Date GoalSettingLastDate = row.getCell(7).getDateCellValue();
            double GoalAndPlanDifferenceLimit = row.getCell(8).getNumericCellValue();
            double FyfcCovertAnpRate = row.getCell(9).getNumericCellValue();
            double ProgressDayPointsLimit = row.getCell(10).getNumericCellValue();
            double FindConvertPointBase = row.getCell(11).getNumericCellValue();
            double ScheduleConvertPointBase = row.getCell(12).getNumericCellValue();
            double MeetConvertPointBase = row.getCell(13).getNumericCellValue();
            double SubmitConvertPointBase = row.getCell(14).getNumericCellValue();
            double InforceConvertPointBase = row.getCell(15).getNumericCellValue();
            double InforceConvertFindRate = row.getCell(16).getNumericCellValue();
            double InforceConvertScheduleRate = row.getCell(17).getNumericCellValue();
            double InforceConvertMeetRate = row.getCell(18).getNumericCellValue();
            double InforceConvertSubmitRate = row.getCell(19).getNumericCellValue();
            Date performanceSettlementMonth = row.getCell(20).getDateCellValue();

            if(!appSysCtrlSet.contains(appSysCtrl)) {
                appSysCtrlSet.add(appSysCtrl);

                SysData sysData = new SysData();
                SysDataIdentity sysDataIdentity = new SysDataIdentity();
                sysDataIdentity.setAppSysCtrl(appSysCtrl);
                sysDataIdentity.setOrganizationalUnit(organizationService.getOrganizationalUnit());
                sysData.setCustomerDataDeleteLimit(12 * 30);
                sysData.setCustomerDataTrackingLimit(7);
                sysData.setGoalApproveTimeLimit((int)GoalApproveTimeLimit);
                sysData.setGoalSettingTimeLimit((int)GoalSettingTimeLimit);
                sysData.setIdentity(sysDataIdentity);
                sysData.setPerformanceSettlementMonth(performanceSettlementMonth);
                sysData.setRemainingPlanCalcStartMonth(dateService.addDate(sysData.getPerformanceSettlementMonth(), Calendar.MONDAY,1));
                sysData.setDataTime(dateService.getTodayDate());
                sysData.setCreateBy("SD");
                sysData.setUpdateBy("SD");
                sysDataRepository.save(sysData);
            }

            SysYearData sysYearData = new SysYearData();
            SysYearDataIdentity sysYearDataIdentity = new SysYearDataIdentity();
            sysYearDataIdentity.setAppSysCtrl(appSysCtrl);
            sysYearDataIdentity.setDataYear((int)dataYear);
            sysYearDataIdentity.setOrganizationalUnit(organizationService.getOrganizationalUnit());
            sysYearData.setIdentity(sysYearDataIdentity);
            sysYearData.setAppDisplayStartDate(AppDisplayStartDate);
            sysYearData.setAppDisplayEndDate(AppDisplayEndDate);
            sysYearData.setGoalSettingLastDate(GoalSettingLastDate);
            sysYearData.setFyfcCovertAnpRate(FyfcCovertAnpRate);
            sysYearData.setProgressDayPointsLimit((int)ProgressDayPointsLimit);
            sysYearData.setGoalSettingActivityProcMode("TWMode");
            sysYearData.setGoalAndPlanDifferenceLimit(GoalAndPlanDifferenceLimit);
            sysYearData.setFindConvertPointBase(FindConvertPointBase);
            sysYearData.setScheduleConvertPointBase(ScheduleConvertPointBase);
            sysYearData.setMeetConvertPointBase(MeetConvertPointBase);
            sysYearData.setSubmitConvertPointBase(SubmitConvertPointBase);
            sysYearData.setInforceConvertPointBase(InforceConvertPointBase);
            sysYearData.setInforceConvertFindRate(InforceConvertFindRate);
            sysYearData.setInforceConvertScheduleRate(InforceConvertScheduleRate);
            sysYearData.setInforceConvertMeetRate(InforceConvertMeetRate);
            sysYearData.setInforceConvertSubmitRate(InforceConvertSubmitRate);
            sysYearData.setProgressBarControlMode("TWMode");
            sysYearData.setDataTime(dateService.getTodayDate());
            sysYearData.setCreateBy("SD");
            sysYearData.setUpdateBy("SD");
            sysYearDataRepository.save(sysYearData);

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
