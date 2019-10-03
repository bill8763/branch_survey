package com.allianz.sd.core.console;

import com.allianz.sd.core.controller.UpdateCodeTable;
import com.allianz.sd.core.controller.UpdateLanguage;
import com.allianz.sd.core.controller.UpdateSysTable;
import com.allianz.sd.core.jpa.model.LanguageTextMapping;
import com.allianz.sd.core.jpa.repository.CodeRepository;
import com.allianz.sd.core.jpa.repository.LanguageTextMappingRepository;
import com.allianz.sd.core.jpa.repository.SysDataRepository;
import com.allianz.sd.core.service.FileStorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.List;

@Controller
@RequestMapping("/setup")
public class SetupConsoleController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private UpdateLanguage updateLanguage = null;

    @Autowired
    private UpdateCodeTable updateCodeTable;

    @Autowired
    private LanguageTextMappingRepository languageTextMappingRepository;

    @Autowired
    private CodeRepository codeRepository;

    @Autowired
    private SysDataRepository sysDataRepository;

    @Autowired
    private UpdateSysTable updateSysTable = null;

    @RequestMapping("/index")
    public String index(Model model) {

        model.addAttribute("languageSize",languageTextMappingRepository.count());
        model.addAttribute("codeSize",codeRepository.count());
        model.addAttribute("sysDataSize",sysDataRepository.count());

        return "setup-index";
    }

    @PostMapping("/uploadLanguage")
    public String uploadLanguage(@RequestParam("file") MultipartFile file , Model model) {

        String fileName = fileStorageService.storeFile(file);
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject = updateLanguage.saveLanguage(resource.getFile());
        }catch(Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("jsonObject",jsonObject);

        return "uploadLanguage";
    }

    @RequestMapping("/downloadLanguage")
    public void uploadLanguage(HttpServletResponse response) {

        String fileName = "language.json";

        response.setContentType("application/json");
        response.addHeader("Content-Disposition", "attachment; filename="+fileName);
        OutputStream os = null;
        ByteArrayInputStream bais = null;
        try {
            os = response.getOutputStream();

            JSONObject jsonObject = new JSONObject();

            List<LanguageTextMapping> mappingList = languageTextMappingRepository.findAll();
            for(LanguageTextMapping languageTextMapping : mappingList) {

                String languageID = languageTextMapping.getIdentity().getLanguageID();
                String mappingID = languageTextMapping.getIdentity().getMappingId();
                String text = languageTextMapping.getText();

                if(StringUtils.isEmpty(text)) text = "";

                JSONObject languageObj = new JSONObject();

                if(jsonObject.has(languageID)) {
                    languageObj = jsonObject.getJSONObject(languageID);
                }

                languageObj.put(mappingID, text);

                jsonObject.put(languageID, languageObj);
            }


            String json = jsonObject.toString();
            ObjectMapper mapper = new ObjectMapper();
            Object obj = mapper.readValue(json, Object.class);
            String outputJSON = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);

            byte[] test = outputJSON.getBytes("utf-8");
            bais = new ByteArrayInputStream(test);
            byte[] data = new byte[1024];
            int data_size;
            while ((data_size = bais.read(data)) > 0) {
                os.write(data, 0, data_size);
            }

            os.flush();
        } catch(Exception e) {
            System.out.println("downLoadFile fail! exception:"+e.getMessage());
        } finally{
            if(bais!=null) {
                try {
                    bais.close();
                } catch(Exception e) {
                    ;
                }
            }

            if(os!=null) {
                try {
                    os.flush();
                    os.close();
                } catch(Exception e) {
                    ;
                }
            }
        }
    }

    @RequestMapping("/uploadSysData")
    public String uploadSysData(@RequestParam("file") MultipartFile file,Model model) {
        boolean result = false;
        try{

            String fileName = fileStorageService.storeFile(file);
            Resource resource = fileStorageService.loadFileAsResource(fileName);

            result = updateSysTable.update(resource.getFile());
        }catch(Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("result",result);

        return "uploadSysData";
    }

    @RequestMapping("/uploadCode")
    public String codeUpdate(@RequestParam("file") MultipartFile file , Model model) {

        boolean result = false;
        try{

            String fileName = fileStorageService.storeFile(file);
            Resource resource = fileStorageService.loadFileAsResource(fileName);

            File resourceFile = resource.getFile();

            updateCodeTable.updateType(resourceFile);
            result = updateCodeTable.update(resourceFile);
        }catch(Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("result",result);

        return "uploadCode";
    }
}
