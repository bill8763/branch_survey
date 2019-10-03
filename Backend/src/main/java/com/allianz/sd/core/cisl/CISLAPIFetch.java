package com.allianz.sd.core.cisl;

import com.allianz.sd.core.cisl.bean.CISLPageInfo;
import com.allianz.sd.core.cisl.bean.ProcessData;
import com.allianz.sd.core.service.bean.CISLToken;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public interface CISLAPIFetch {

    public void setToken(CISLToken cislToken);

    public String getAPIName();

    public CISLPageInfo getCISLPageInfo() throws Exception;

    public CISLPageInfo getCISLPageInfoByAgent(String agentID) throws Exception;

    public JSONObject fetchData(int pageNo) throws Exception;

    public JSONObject fetchByAgent(String agentID,int pageNo) throws Exception;

    public ProcessData parse2ProcessData(JSONObject content) throws Exception;

    public void saveToSND(ProcessData processData) throws Exception;
}
