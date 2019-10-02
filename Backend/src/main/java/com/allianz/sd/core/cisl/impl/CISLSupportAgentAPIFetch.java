package com.allianz.sd.core.cisl.impl;

import com.allianz.sd.core.handler.bean.RestfulParam;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public abstract class CISLSupportAgentAPIFetch extends CISLUsageModelAPIFetch {
    @Override
    public JSONObject fetchByAgent(String agentID, int pageNo) throws Exception {
        RestfulParam restfulParam = getPageNumberParam(pageNo);

        restfulParam.addParam("agentNumber",agentID);

        return fetchJSON(restfulParam);
    }
}
