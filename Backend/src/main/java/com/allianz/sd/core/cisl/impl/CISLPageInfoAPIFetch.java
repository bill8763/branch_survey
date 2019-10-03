package com.allianz.sd.core.cisl.impl;

import com.allianz.sd.core.cisl.CISLAPIFetch;
import com.allianz.sd.core.cisl.bean.CISLPageInfo;
import com.allianz.sd.core.handler.RestfulHandler;
import com.allianz.sd.core.handler.bean.RestfulParam;
import com.allianz.sd.core.service.bean.CISLToken;
import com.sun.corba.se.impl.oa.toa.TOA;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public abstract class CISLPageInfoAPIFetch implements CISLAPIFetch {

    protected CISLToken token = null;

    @Override
    public void setToken(CISLToken cislToken) {
        token = cislToken;
    }

    @Override
    public CISLPageInfo getCISLPageInfo() throws Exception{
        RestfulParam restfulParam = getPageNumberParam();

        JSONObject jsonObject = fetchJSON(restfulParam);

        return parseJSON2PageInfo(jsonObject);
    }

    @Override
    public CISLPageInfo getCISLPageInfoByAgent(String agentID) throws Exception{
        RestfulParam restfulParam = getPageNumberParam();
        restfulParam.addParam("agentNumber",agentID);

        JSONObject jsonObject = fetchJSON(restfulParam);

        return parseJSON2PageInfo(jsonObject);
    }

    @Override
    public JSONObject fetchData(int pageNo) throws Exception {
        RestfulParam restfulParam = getPageNumberParam(pageNo);

        return fetchJSON(restfulParam);
    }

    protected JSONObject fetchJSON(RestfulParam restfulParam) throws Exception {
        RestfulHandler restfulHandler = new RestfulHandler();
        JSONObject jsonObject = restfulHandler.callRestfulAPIToObjectByGet(restfulParam);

        if(jsonObject == null) throw new Exception("Can't fetch CISL API result["+restfulParam.getUrl()+"] , jsonObject is null");
        return jsonObject;
    }


    private CISLPageInfo parseJSON2PageInfo(JSONObject jsonObject) {

        if(jsonObject != null && jsonObject.has("pageCount") && jsonObject.has("pageSize") && jsonObject.has("pageNumber")) {
            CISLPageInfo cislPageInfo = new CISLPageInfo();

            cislPageInfo.setPageCount(jsonObject.getInt("pageCount"));
            cislPageInfo.setPageSize(jsonObject.getInt("pageSize"));
            cislPageInfo.setPageNumber(jsonObject.getInt("pageNumber"));

            return cislPageInfo;
        }
        else return null;

    }

    private RestfulParam getPageNumberParam() {
        return getPageNumberParam(1);
    }

    protected RestfulParam getPageNumberParam(int pageNo) {
        RestfulParam restfulParam = getAPIRestfulParam();
        restfulParam.addParam("pageNumber",String.valueOf(pageNo));

        restfulParam.addHeaderParam("Authorization","bearer " + token.getToken());

        return restfulParam;
    }


    protected abstract RestfulParam getAPIRestfulParam();
}
