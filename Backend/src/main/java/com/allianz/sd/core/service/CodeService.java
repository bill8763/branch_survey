package com.allianz.sd.core.service;

import com.allianz.sd.core.jpa.model.Code;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 6:28
 * To change this template use File | Settings | File Templates.
 */
@Service
public class CodeService {
    private static final Logger logger = LoggerFactory.getLogger(CodeService.class);
    private Map<String,Map<String,Code>> codeTypeMap = new LinkedHashMap<>();

    public void addCode(Code code) {
        if(code != null) {
            String typeId = code.getIdentity().getTypeID();

            Map<String,Code> codeMap = codeTypeMap.get(typeId);
            if(codeMap == null) codeMap = new LinkedHashMap<>();

            codeMap.put(code.getIdentity().getCode(),code);

            codeTypeMap.put(typeId,codeMap);
        }
    }

    public Map<String,Code> getCodeMap(String typeId) {
        return this.codeTypeMap.get(typeId);
    }

    public JSONObject getCodeArguments(String typeId , String code) {
        Map<String,Code> codeMap = codeTypeMap.get(typeId);
        if(codeMap != null) {
            Code codeObj = codeMap.get(code);

            if(codeObj != null) {
                String arg = codeObj.getArguments();

                if(StringUtils.isNotEmpty(arg)) {
                    try{
                        return new JSONObject(arg);
                    }catch(Exception e) {
                        logger.error("InstanceCode Arguments is not JSON format",e);
                    }
                }
            }
        }

        return null;
    }
}
