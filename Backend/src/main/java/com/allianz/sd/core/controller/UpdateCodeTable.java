package com.allianz.sd.core.controller;

import com.allianz.sd.core.jpa.model.Code;
import com.allianz.sd.core.jpa.model.CodeIdentity;
import com.allianz.sd.core.jpa.model.CodeType;
import com.allianz.sd.core.jpa.model.CodeTypeIdentity;
import com.allianz.sd.core.jpa.repository.CodeRepository;
import com.allianz.sd.core.jpa.repository.CodeTypeRepository;
import com.allianz.sd.core.service.DateService;
import com.allianz.sd.core.service.OrganizationService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 6:39
 * To change this template use File | Settings | File Templates.
 */
@Component
public class UpdateCodeTable {

    @Autowired
    private CodeRepository codeRepository = null;

    @Autowired
    private CodeTypeRepository codeTypeRepository = null;

    @Autowired
    private OrganizationService organizationService = null;

    @Autowired
    private DateService dateService;

    public boolean updateType(File file) throws Exception{
        InputStream is = new FileInputStream(file);
        HSSFWorkbook wb = new HSSFWorkbook(is);

        Date date = dateService.parseDateString2Date("99991231235959");

        //delete code
        codeTypeRepository.deleteAll();

        HSSFSheet codeSheet = wb.getSheetAt(2);
        int codeRows = codeSheet.getPhysicalNumberOfRows();
        for(int i=2;i<codeRows;i++) {
            HSSFRow row = codeSheet.getRow(i);

            String typeId = row.getCell(0).getStringCellValue();

            if(StringUtils.isEmpty(typeId)) continue;

            String typeName = row.getCell(1).getStringCellValue();
            String unit = row.getCell(2).getStringCellValue();


            CodeTypeIdentity id = new CodeTypeIdentity();
            id.setTypeID(typeId);
            id.setOrganizationalUnit(unit);

            CodeType codeObj = new CodeType();
            codeObj.setCodeTypeIdentity(id);
            codeObj.setTypeName(typeName);
            codeObj.setCreateBy("SD");
            codeObj.setUpdateBy("SD");
            codeTypeRepository.save(codeObj);


        }

        return true;
    }
    public boolean update(File file) throws Exception{
        InputStream is = new FileInputStream(file);
        HSSFWorkbook wb = new HSSFWorkbook(is);

        Date date = dateService.parseDateString2Date("99991231235959");

        //delete code
        codeRepository.deleteAll();

        HSSFSheet codeSheet = wb.getSheetAt(1);
        int codeRows = codeSheet.getPhysicalNumberOfRows();
        for(int i=2;i<codeRows;i++) {
            HSSFRow row = codeSheet.getRow(i);

            if(row.getCell(0) == null) continue;
            String typeId = row.getCell(0).getStringCellValue();
            String code = "";

            if(StringUtils.isEmpty(typeId)) continue;

            switch (row.getCell(1).getCellType()) {
                case BOOLEAN :
                    //To-do
                    break;
                case NUMERIC:
                    code = ((int) row.getCell(1).getNumericCellValue()) + "";
                    break;
                case STRING:
                    code = row.getCell(1).getStringCellValue();
                    break;
            }


            HSSFCell orderCell = row.getCell(3);

            String mappingID = row.getCell(2).getStringCellValue();
            String orders = orderCell.getCellType() == CellType.STRING ? orderCell.getStringCellValue() : ((int)orderCell.getNumericCellValue()) + "";
            String arguments = row.getCell(4).getStringCellValue();

            CodeIdentity id = new CodeIdentity();
            id.setCode(code);
            id.setMappingId(mappingID);
            id.setTypeID(typeId);
            id.setOrganizationalUnit(organizationService.getOrganizationalUnit());

            Code codeObj = new Code();
            codeObj.setIdentity(id);
            codeObj.setArguments(arguments);
            codeObj.setOrders(Integer.parseInt(orders));
            codeObj.setValidityPeriod(date);
            codeObj.setCreateBy("SD");
            codeObj.setUpdateBy("SD");
            codeRepository.save(codeObj);


        }

        return true;
    }
}
