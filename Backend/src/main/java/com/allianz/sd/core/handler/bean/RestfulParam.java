package com.allianz.sd.core.handler.bean;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RestfulParam {
    private String url = null;
    private MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
    private Map<String,String> headerMap = new LinkedHashMap<>();

    public String request2JSONFormat() {
        JSONObject jsonObject = new JSONObject();

        for(String key : map.keySet()) {
            List<String> values = map.get(key);
            jsonObject.put(key,new JSONArray(values));
        }

        return jsonObject.toString();
    }

    public void addParam(String key , String value) {
        this.map.add(key, value);
    }

    public void addHeaderParam(String key , String value) {
        this.headerMap.put(key,value);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public MultiValueMap<String, String> getMap() {
        return map;
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public String toGetURL() {
        StringBuilder getURL = new StringBuilder();
        getURL.append(url);

        if(map != null) {
            for(String paramName : map.keySet()) {
                List<String> values = map.get(paramName);
                for(String value : values) {
                    if(getURL.toString().contains("?")) getURL.append("&" + paramName + "=" + value);
                    else getURL.append("?" + paramName + "=" + value);
                }
            }
        }

        return getURL.toString();
    }
}
