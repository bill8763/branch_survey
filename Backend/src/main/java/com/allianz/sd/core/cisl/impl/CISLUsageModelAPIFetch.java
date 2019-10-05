package com.allianz.sd.core.cisl.impl;

import com.allianz.sd.core.handler.bean.RestfulParam;
import org.springframework.stereotype.Component;

@Component
public abstract class CISLUsageModelAPIFetch extends CISLPageInfoAPIFetch {
    @Override
    protected RestfulParam getAPIRestfulParam() {
        RestfulParam restfulParam = new RestfulParam();

        restfulParam.setUrl(getCISLAPIURL());
        restfulParam.addParam("usageMode",getUsageModel());

        return restfulParam;
    }

    @Override
    public String getAPIName() {return getUsageModel();}

    protected abstract String getCISLAPIURL();
    protected abstract String getUsageModel();
}
