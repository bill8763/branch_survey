package com.allianz.sd.core.controller;

import com.allianz.sd.core.jpa.model.*;
import com.allianz.sd.core.jpa.repository.*;
import com.allianz.sd.core.service.DateService;
import com.allianz.sd.core.service.OrganizationService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
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
public class UpdateAgentDataTable {

    @Autowired
    private OrganizationService organizationService ;

    @Autowired
    private DateService dateService;

    @Autowired
    private AgentDataRepository agentDataRepository;
    
    @Autowired
    private AgentYearDataRepository agentYearDataRepository;


    public boolean update(File file) throws Exception{
        InputStream is = new FileInputStream(file);
        HSSFWorkbook wb = new HSSFWorkbook(is);

        agentYearDataRepository.deleteAll();
        agentDataRepository.deleteAll();

        HSSFSheet agentDataSheet = wb.getSheetAt(7);
        HSSFSheet agentYearDataSheet = wb.getSheetAt(8);
        int agentDataRows = agentDataSheet.getPhysicalNumberOfRows();
        int agentYearDataRows = agentYearDataSheet.getPhysicalNumberOfRows();
        
        for(int i=1;i<agentDataRows;i++) {
            HSSFRow row = agentDataSheet.getRow(i);

            String agentID = row.getCell(0).getStringCellValue();
            if(StringUtils.isEmpty(agentID)) continue;
            String agentName = row.getCell(1).getStringCellValue();
            String officeName = row.getCell(2).getStringCellValue();
            String gender = row.getCell(3).getStringCellValue();
            Date oBMonth = row.getCell(4).getDateCellValue();

            AgentData agentData = new AgentData();
            agentData.setAgentID(agentID);
            agentData.setAgentName(agentName);
            agentData.setOfficeName(officeName);
            agentData.setGender(gender);
            agentData.setOBMonth(oBMonth);
            agentData.setOrganizationalUnit(organizationService.getOrganizationalUnit());
            agentData.setDataTime(dateService.getTodayDate());
            agentData.setCreateBy("SD");
            agentData.setUpdateBy("SD");
            agentDataRepository.save(agentData);
        }
        for(int i=1;i<agentYearDataRows;i++) {
        	HSSFRow row = agentYearDataSheet.getRow(i);
        	
        	String agentID = row.getCell(0).getStringCellValue();
        	if(StringUtils.isEmpty(agentID)) continue;
        	Double dataYear = row.getCell(1).getNumericCellValue();
        	String appUseMode = row.getCell(2).getStringCellValue();
        	String appSysCtrl = row.getCell(3).getStringCellValue();
        	String performanceTable = row.getCell(4).getStringCellValue();
        	String jobGrade = row.getCell(5).getStringCellValue();
        	Date currentJobOBMonth = row.getCell(6).getDateCellValue();
        	Double currentJobSeniorityMonth = row.getCell(7).getNumericCellValue();
        	String goalSigningSupervisor = row.getCell(8).getStringCellValue();
        	String personnelPerformanceType = row.getCell(9).getStringCellValue();
        	String teamPerformanceType = row.getCell(10).getStringCellValue();
        	Double initialPreCaseFyfc = row.getCell(11).getNumericCellValue();
        	
        	
        	AgentYearDataIdentity identity = new AgentYearDataIdentity();
        	identity.setAgentID(agentID);
        	identity.setDataYear(dataYear.intValue());
        	identity.setOrganizationalUnit(organizationService.getOrganizationalUnit());
        	AgentYearData agentYearData = new AgentYearData();
			agentYearData.setIdentity(identity);
			agentYearData.setAppUseMode(appUseMode);
			agentYearData.setAppSysCtrl(appSysCtrl);
			agentYearData.setPerformanceTable(performanceTable);
			agentYearData.setJobGrade(jobGrade);
			agentYearData.setCurrentJobOBMonth(currentJobOBMonth);
			agentYearData.setCurrentJobSeniorityMonth(currentJobSeniorityMonth.intValue());
			agentYearData.setGoalSigningSupervisor(goalSigningSupervisor);
			agentYearData.setPersonnelPerformanceType(personnelPerformanceType);
			agentYearData.setTeamPerformanceType(teamPerformanceType);
			agentYearData.setInitialPreCaseFyfc(initialPreCaseFyfc.intValue());
			agentYearData.setDataTime(dateService.getTodayDate());
			agentYearData.setCreateBy("SD");
			agentYearData.setUpdateBy("SD");
        	agentYearDataRepository.save(agentYearData);
        }

        return true;
    }
}
