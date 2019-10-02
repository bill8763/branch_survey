package com.allianz.sd.core.cisl.bean;

import com.allianz.sd.core.cisl.CISLAPIFetch;
import com.allianz.sd.core.service.bean.CISLToken;

public class CISLAPITask {

    private int pageNo = -1;
    private CISLAPIFetch cislapiFetch = null;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public CISLAPIFetch getCislapiFetch() {
        return cislapiFetch;
    }

    public void setCislapiFetch(CISLAPIFetch cislapiFetch) {
        this.cislapiFetch = cislapiFetch;
    }

}
