package com.allianz.sd.core.cisl;

import com.allianz.sd.core.service.bean.CISLToken;

public interface CISLTokenProvider {
    public CISLToken getCISLToken() throws Exception;
}
