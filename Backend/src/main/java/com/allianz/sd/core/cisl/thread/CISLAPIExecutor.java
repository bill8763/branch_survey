package com.allianz.sd.core.cisl.thread;

import com.allianz.sd.core.cisl.CISLAPIFetch;
import com.allianz.sd.core.cisl.CISLAPIQueue;
import com.allianz.sd.core.cisl.bean.CISLAPITask;
import com.allianz.sd.core.cisl.bean.ProcessData;
import com.allianz.sd.core.jpa.model.CISLBatchLogDetail;
import com.allianz.sd.core.jpa.model.CISLBatchLogDetailIdentity;
import com.allianz.sd.core.service.BatchLogService;
import com.allianz.sd.core.service.bean.CISLToken;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CISLAPIExecutor implements Runnable {

    private Logger logger = LoggerFactory.getLogger(CISLAPIExecutor.class);
    private CISLAPIQueue cislapiQueue;
    private Integer batchLogID = null;
    private BatchLogService batchLogService;
    private List<String> errorMessageList;

    public CISLAPIExecutor(CISLAPIQueue cislapiQueue, Integer batchLogID , BatchLogService batchLogService,List<String> errorMessageList) {
        this.cislapiQueue = cislapiQueue;
        this.batchLogID = batchLogID;
        this.batchLogService = batchLogService;
        this.errorMessageList = errorMessageList;
    }

    @Override
    public void run() {

        logger.debug("CISLAPIExecutor["+Thread.currentThread().getId()+"] running");
        try{
            Thread.sleep(1000);
        }catch(Exception e) {
            e.printStackTrace();
        }

        while (!cislapiQueue.isEmpty()) {

            try{
                CISLAPITask task = (CISLAPITask) cislapiQueue.get();

                if(task != null) {
                    int pageNo = task.getPageNo();
                    CISLAPIFetch cislapiFetch = task.getCislapiFetch();

                    String apiName = cislapiFetch.getAPIName();
                    logger.debug(apiName + " fetch pageNo = " + pageNo);

                    JSONObject jsonObject = cislapiFetch.fetchData(pageNo);

                    if(jsonObject != null) {

                        //check content field is exist
                        if(!jsonObject.has("content")) {
                            writeDetailLog(apiName,"miss content Array["+pageNo+"]",pageNo);
                            continue;
                        }

                        JSONArray contents = jsonObject.getJSONArray("content");
                        for(int i=0;i<contents.length();i++) {
                            JSONObject content = contents.getJSONObject(i);

                            ProcessData processData = null;
                            try{
                                processData = cislapiFetch.parse2ProcessData(content);
                            }catch(Exception e) {
                                logger.error(apiName + " parse CISL JSON fail!pageNo["+pageNo+"],record["+(i+1)+"]",e);
                                writeDetailLog(apiName," parse CISL JSON fail!pageNo["+pageNo+"],record["+(i+1)+"]["+ e.getMessage()+"]",pageNo);
                            }

                            if(processData != null) {
                                try{
                                    cislapiFetch.saveToSND(processData);
                                }catch(Exception ex) {
                                    logger.error(apiName + " insert to S&D Database error!pageNo["+pageNo+"],record["+(i+1)+"]",ex);
                                    writeDetailLog(apiName," insert to S&D Database error!pageNo["+pageNo+"],record["+(i+1)+"]["+ ex.getMessage()+"]",pageNo);
                                }
                            }

                        }

                    }
                    else {
                        writeDetailLog(apiName," get response error!",pageNo);
                    }
                }
                else {
                    break;
                }

            }catch(Exception e) {
                logger.error("Get API fail!!",e);
                writeDetailLog("Unknow","APIExecutor fail["+e.getMessage()+"]",0);
            }
        }

        logger.trace("CISLAPIExecutor["+Thread.currentThread().getId()+"] is done!");
    }


    private void writeDetailLog(String detailCategory , String errorMsg , Integer pageNo) {
        CISLBatchLogDetail cislBatchLogDetail = new CISLBatchLogDetail();

        CISLBatchLogDetailIdentity identity = new CISLBatchLogDetailIdentity();

        identity.setBatchLogID(batchLogID);
        identity.setDetailCategory(detailCategory);
        identity.setPageNumber(pageNo);

        cislBatchLogDetail.setIdentity(identity);

        errorMessageList.add(detailCategory + ":" + errorMsg);

        this.batchLogService.saveCISLBatchDetailLog(cislBatchLogDetail);
    }

}
