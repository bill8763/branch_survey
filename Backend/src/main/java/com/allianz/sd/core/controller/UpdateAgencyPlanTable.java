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
import org.quartz.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 6:39
 * To change this template use File | Settings | File Templates.
 */
@Component
public class UpdateAgencyPlanTable {

    @Autowired
    private OrganizationService organizationService = null;

    @Autowired
    private DateService dateService;

    @Autowired
    private AgencyPlanRepository agencyPlanRepository;

    @Autowired
    private AgencyPlanDetailRepository agencyPlanDetailRepository;

    @Autowired
    private AgencyPlanPileRepository agencyPlanPileRepository;

    @Autowired
    private NumberService numberService;


    public boolean update(File file) throws Exception {
        InputStream is = new FileInputStream(file);
        HSSFWorkbook wb = new HSSFWorkbook(is);

        agencyPlanPileRepository.deleteAll();
        agencyPlanDetailRepository.deleteAll();
        agencyPlanRepository.deleteAll();


        HSSFSheet agencyPlanSheet = wb.getSheetAt(4);
        HSSFSheet agencyPlanDetailSheet = wb.getSheetAt(5);
        HSSFSheet agencyPlanPileDataSheet = wb.getSheetAt(6);
        int agencyPlanRows = agencyPlanSheet.getPhysicalNumberOfRows();
        int agencyPlanDetailRows = agencyPlanDetailSheet.getPhysicalNumberOfRows();
        int agencyPlanPileDataRows = agencyPlanPileDataSheet.getPhysicalNumberOfRows();
        for(int i=1;i<agencyPlanRows;i++) {
            HSSFRow row = agencyPlanSheet.getRow(i);

            if(row != null) {
                String agentID = row.getCell(1).getStringCellValue();
                if(StringUtils.isEmpty(agentID)) continue;

                double DataYear = row.getCell(0).getNumericCellValue();
                String agentName = row.getCell(2).getStringCellValue();
                String JobGrade = row.getCell(3).getStringCellValue();
                double Anp = row.getCell(4).getNumericCellValue();
                double Fyfc = row.getCell(5).getNumericCellValue();

                AgencyPlan agencyPlan = new AgencyPlan();
                AgencyPlanIdentity agencyPlanIdentity = new AgencyPlanIdentity();
                agencyPlanIdentity.setOrganizationalUnit(organizationService.getOrganizationalUnit());
                agencyPlanIdentity.setAgentID(agentID);
                agencyPlanIdentity.setDataYear((int)DataYear);

                agencyPlan.setIdentity(agencyPlanIdentity);
                agencyPlan.setAgentName(agentName);
                agencyPlan.setJobGrade(JobGrade);
                agencyPlan.setAnp(numberService.round(Anp));
                agencyPlan.setFyfc((int)Fyfc);

                agencyPlan.setCreateBy("SD");

                agencyPlanRepository.save(agencyPlan);
            }

        }

        for(int i=1;i<agencyPlanDetailRows;i++) {
            HSSFRow row = agencyPlanDetailSheet.getRow(i);

            if(row != null) {
                String agentID = row.getCell(1).getStringCellValue();
                if(StringUtils.isEmpty(agentID)) continue;

                double DataYear = row.getCell(0).getNumericCellValue();

                String DisplayBlock = row.getCell(2).getStringCellValue();
                double DisplayOrder = row.getCell(3).getNumericCellValue();

                String SubordinateAgentID = row.getCell(4).getStringCellValue();
                String SubordinateAgentAgentName = row.getCell(5).getStringCellValue();
                String SubordinateAgentJobGrade = row.getCell(6).getStringCellValue();
                String CumulativeProc = row.getCell(7).getStringCellValue();
                Date PerformanceCalcStartMonth = row.getCell(8).getDateCellValue();

                double ANP = row.getCell(9).getNumericCellValue();
                double FYFC = row.getCell(10).getNumericCellValue();
                double NewBusinessCase = row.getCell(11).getNumericCellValue();
                double Recruitment = row.getCell(12).getNumericCellValue();
                double Manpower = row.getCell(13).getNumericCellValue();


                AgencyPlanDetail agencyPlanDetail = new AgencyPlanDetail();
                AgencyPlanDetailIdentity agencyPlanDetailIdentity = new AgencyPlanDetailIdentity();
                agencyPlanDetailIdentity.setOrganizationalUnit(organizationService.getOrganizationalUnit());
                agencyPlanDetailIdentity.setAgentID(agentID);
                agencyPlanDetailIdentity.setDataYear((int)DataYear);
                agencyPlanDetailIdentity.setDisplayBlock(DisplayBlock);
                agencyPlanDetailIdentity.setDisplayOrder((int)DisplayOrder);


                agencyPlanDetail.setIdentity(agencyPlanDetailIdentity);

                agencyPlanDetail.setCumulativeProc(CumulativeProc);
                agencyPlanDetail.setPerformanceCalcStartMonth(PerformanceCalcStartMonth);
                agencyPlanDetail.setSubAgentID(SubordinateAgentID);
                agencyPlanDetail.setSubAgentName(SubordinateAgentAgentName);
                agencyPlanDetail.setSubAgentJobGrade(SubordinateAgentJobGrade);

                agencyPlanDetail.setAnp(numberService.round(ANP));
                agencyPlanDetail.setFyfc((long)FYFC);
                agencyPlanDetail.setNewBusinessCase((long)NewBusinessCase);
                agencyPlanDetail.setRecruitment((long)Recruitment);
                agencyPlanDetail.setManpower((long)Manpower);

                agencyPlanDetail.setCreateBy("SD");

                agencyPlanDetailRepository.save(agencyPlanDetail);
            }

        }

        for(int i=1;i<agencyPlanPileDataRows;i++) {
            HSSFRow row = agencyPlanPileDataSheet.getRow(i);

            if (row != null) {
                String LeaderAgentID = row.getCell(1).getStringCellValue();
                if (StringUtils.isEmpty(LeaderAgentID)) continue;

                double DataYear = row.getCell(0).getNumericCellValue();
                String agentId = row.getCell(2).getStringCellValue();

                AgencyPlanPile planPile = new AgencyPlanPile();
                AgencyPlanPileIdentity planPileIdentity = new AgencyPlanPileIdentity();
                planPileIdentity.setAgentID(agentId);
                planPileIdentity.setLeaderAgentID(LeaderAgentID);
                planPileIdentity.setDataYear((int)DataYear);
                planPileIdentity.setOrganizationalUnit(organizationService.getOrganizationalUnit());

                planPile.setIdentity(planPileIdentity);
                planPile.setCreateBy("SD");

                agencyPlanPileRepository.save(planPile);
            }
        }

        return true;
    }
}
