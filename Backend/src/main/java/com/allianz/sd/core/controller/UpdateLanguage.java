package com.allianz.sd.core.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allianz.sd.core.jpa.model.LanguageTextMapping;
import com.allianz.sd.core.jpa.model.LanguageTextMappingIdentity;
import com.allianz.sd.core.jpa.repository.LanguageTextMappingRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/26
 * Time: 上午 11:05
 * To change this template use File | Settings | File Templates.
 */
@Component
public class UpdateLanguage {

	@Autowired
    private LanguageTextMappingRepository languageTextMappingRepository;
	
    public JSONObject getLanguage(File file) {

        JSONObject jsonObject = new JSONObject();

        try{
            InputStream is = new FileInputStream(file);
            HSSFWorkbook wb = new HSSFWorkbook(is);

            int sheetCount = wb.getNumberOfSheets();
            for(int z=0;z<sheetCount;z++) {
                HSSFSheet languageTextMappingSheet = wb.getSheetAt(z);

                int mappingRows = languageTextMappingSheet.getPhysicalNumberOfRows();
                for(int i=1;i<mappingRows;i++) {
                    HSSFRow row = languageTextMappingSheet.getRow(i);

                    if(row == null || row.getCell(0) == null) continue;
                    String languageID = row.getCell(0).getStringCellValue();
                    String mappingID = "";

                    if(StringUtils.isEmpty(languageID)) continue;

                    switch (row.getCell(1).getCellType()) {
                        case BOOLEAN :
                            //To-do
                            break;
                        case NUMERIC:
                            mappingID = ((int) row.getCell(1).getNumericCellValue()) + "";
                            break;
                        case STRING:
                            mappingID = row.getCell(1).getStringCellValue();
                            break;
                    }

                    String text = "";
                    if(row.getCell(2) != null) {
                        switch (row.getCell(2).getCellType()) {
                            case BOOLEAN :
                                //To-do
                                break;
                            case NUMERIC:
                                text = ((int) row.getCell(2).getNumericCellValue()) + "";
                                break;
                            case STRING:
                                text = row.getCell(2).getStringCellValue();
                                break;
                        }
                    }


                    JSONObject languageObj = new JSONObject();
                    if(jsonObject.has(languageID)) {
                        languageObj = jsonObject.getJSONObject(languageID);
                    }

                    languageObj.put(mappingID, text);

                    jsonObject.put(languageID, languageObj);
                }
            }


        }catch(Exception e) {
            e.printStackTrace();
        }
        return jsonObject;

    }

	public JSONObject saveLanguage(File file) throws Exception {

		languageTextMappingRepository.deleteAll();

        InputStream is = new FileInputStream(file);
        HSSFWorkbook wb = new HSSFWorkbook(is);

        int sheetCount = wb.getNumberOfSheets();
        for(int z=0;z<sheetCount;z++) {
            HSSFSheet mySheet = wb.getSheetAt(z);
            String pageName = mySheet.getSheetName();
            int totalRec = mySheet.getPhysicalNumberOfRows();
            for(int i=1;i<totalRec;i++){

                Row row = mySheet.getRow(i);
                if(row == null || row.getCell(0) == null ||row.getCell(1) == null || row.getCell(2)==null) continue;

                String languageID = row.getCell(0).toString();
                String mappingID = row.getCell(1).toString();
                String text = row.getCell(2).toString();

                if(StringUtils.isEmpty(languageID)||StringUtils.isEmpty(mappingID)) continue;

                LanguageTextMapping mapping = new LanguageTextMapping();
                LanguageTextMappingIdentity identity = new LanguageTextMappingIdentity();
                identity.setLanguageID(languageID);
                identity.setMappingId(mappingID);
                mapping.setIdentity(identity);
                mapping.setText(text);
//                mapping.setPageName(pageName);
                languageTextMappingRepository.save(mapping);
            }
        }


        JSONObject response =new JSONObject();
        response.put("success", true);
		return response;
	}
}
