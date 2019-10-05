package com.allianz.sd.core.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.allianz.sd.core.jpa.model.Code;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class LanguageService {


	private Logger logger = LoggerFactory.getLogger(LanguageService.class);

	//add data when startup
	private Map<String,String> codeLanguageTextMap = null;

	/**
	 * @param code
	 * @param languageText
	 */
	public void addCodeLanguage(String code, String languageText) {
		if(StringUtils.isNotEmpty(code)) {
            if(codeLanguageTextMap == null) codeLanguageTextMap = new LinkedHashMap<>();
            codeLanguageTextMap.put(code, languageText);
        }
	}
    public Map<String, String> getCodeLanguageTextMap() {
		return codeLanguageTextMap;
	}

	public void setCodeLanguageTextMap(Map<String, String> codeLanguageTextMap) {
		this.codeLanguageTextMap = codeLanguageTextMap;
	}

	
	/**
	 * @param mappingID
	 * @return mapping language text
	 */
	public String convertText(String mappingID) {
        return convertText(mappingID,null);
    }

    /**
     * @param mappingID
     * @param paramMap
     * @return mapping language text
     */
    public String convertText(String mappingID , Map<String,String> paramMap) {

        String text = mappingID;

        if(codeLanguageTextMap.containsKey(mappingID)) {
        	String mappingText = codeLanguageTextMap.get(mappingID);
        	if(StringUtils.isNotEmpty(mappingText)) {
        		text = mappingText;
        	}
        }

        // replace parameters
        if(paramMap != null) {

//			logger.debug("paramMap size = " + paramMap.size());
			for(String key : paramMap.keySet()) {
				logger.debug("["+key+"] , ["+paramMap.get(key)+"]");
			}

            for(String key : paramMap.keySet()) {
            	String value = paramMap.get(key);
				text = StringUtils.replace(text,"${"+key+"}",value);
            }
        }

        return text;

    }

}
