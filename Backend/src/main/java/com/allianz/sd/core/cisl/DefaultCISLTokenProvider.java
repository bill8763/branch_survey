package com.allianz.sd.core.cisl;

import com.allianz.sd.core.service.bean.CISLToken;

public class DefaultCISLTokenProvider implements CISLTokenProvider {
    @Override
    public CISLToken getCISLToken() throws Exception {
        CISLToken cislToken = new CISLToken();

        cislToken.setToken("");

        return cislToken;
    }
}
