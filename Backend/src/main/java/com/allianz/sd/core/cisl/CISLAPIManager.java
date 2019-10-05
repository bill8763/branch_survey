package com.allianz.sd.core.cisl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class CISLAPIManager {

    private Logger logger = LoggerFactory.getLogger(CISLAPIManager.class);

    private static CISLAPIManager ourInstance = new CISLAPIManager();

    private List<CISLAPIFetch> cislapiFetchList = new ArrayList<>();

    public static CISLAPIManager getInstance() {
        return ourInstance;
    }

    private CISLAPIManager() {
    }

    public void addCISLAPIFetch(CISLAPIFetch cislapiFetch) {
        logger.info("Register CISLAPIManager["+cislapiFetch.getClass().getSimpleName()+"]");
        this.cislapiFetchList.add(cislapiFetch);
    }

    public CISLAPIFetch getCislAPIFetch(String apiName) {
        for(CISLAPIFetch cislapiFetch : cislapiFetchList) {
            if(apiName.equalsIgnoreCase(cislapiFetch.getAPIName())) return cislapiFetch;
        }

        return null;
    }

    public List<CISLAPIFetch> getCislapiFetchList() {
        return cislapiFetchList;
    }
}
