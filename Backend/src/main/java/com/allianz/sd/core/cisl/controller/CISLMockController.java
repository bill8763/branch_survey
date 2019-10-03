package com.allianz.sd.core.cisl.controller;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CISLMockController {

    @Value("${CISL.API.MockDir}")
    private String mockDir;

    @RequestMapping(method = RequestMethod.GET, value = "/MockCISL")
    @ResponseBody
    @Validated
    public String mockByPage(@RequestParam("usageModel") String usageModel,
                           @RequestParam("pageNumber") String pageNumber) throws Exception{

        File tempJSONFolder = new File(mockDir + "/" + usageModel + "/"+usageModel+"_page_"+pageNumber+".json");

        return FileUtils.readFileToString(tempJSONFolder,"utf-8");
    }

}
