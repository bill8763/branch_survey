package com.allianz.sd.core.schedule.job;

import com.allianz.sd.core.cisl.CISLAPIFetch;
import com.allianz.sd.core.cisl.CISLAPIManager;
import com.allianz.sd.core.cisl.CISLAPIQueue;
import com.allianz.sd.core.cisl.bean.CISLAPITask;
import com.allianz.sd.core.cisl.bean.CISLPageInfo;
import com.allianz.sd.core.cisl.thread.CISLAPIExecutor;
import com.allianz.sd.core.jpa.model.BatchLog;
import com.allianz.sd.core.jpa.model.CISLBatchLogDetail;
import com.allianz.sd.core.jpa.model.SysCtrl;
import com.allianz.sd.core.service.BatchLogService;
import com.allianz.sd.core.service.CISLSyncToSNDService;
import com.allianz.sd.core.service.DateService;
import com.allianz.sd.core.service.SysCtrlService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CISLSyncBatch extends QuartzJobBean {

    private static final Logger logger = LoggerFactory.getLogger(CISLSyncBatch.class);

    @Autowired
    private SysCtrlService sysCtrlService;

    @Autowired
    private DateService dateService;

    @Autowired
    private BatchLogService batchLogService;

    @Autowired
    private CISLSyncToSNDService cislSyncToSNDService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        //read sys-ctrl get CISLBatchTime
        SysCtrl cislSyncToSNDTimeSysCtrl = sysCtrlService.getSysCtrl(SysCtrlService.CISLSyncToSNDTime);
        SysCtrl cislBatchSYncTimeSysCtrl = sysCtrlService.getSysCtrl(SysCtrlService.CISLBatchSyncTime);

        if(cislSyncToSNDTimeSysCtrl != null && cislBatchSYncTimeSysCtrl != null) {
            String cislSyncToSndTime = cislSyncToSNDTimeSysCtrl.getSysValue();
            String cislBatchSyncTime = cislBatchSYncTimeSysCtrl.getSysValue();

            logger.debug("cislSyncToSndTime = " + cislSyncToSndTime);
            logger.debug("cislBatchSyncTime = " + cislBatchSyncTime);

            Date cislSyncToSndTimeDate = dateService.getDate(cislSyncToSndTime);
            Date cislBatchSyncTimeDate = dateService.getDate(cislBatchSyncTime);

            if(cislSyncToSndTimeDate == null || cislBatchSyncTimeDate == null) {
                writeErrorLog("cislSyncToSndTimeDate & cislBatchSyncTimeDate is not date date format!");
            }
            else {

                //check cislSyncToSndTimeDate & cislBatchSyncTimeDate is not match
                if(cislSyncToSndTimeDate.getTime() > cislBatchSyncTimeDate.getTime()) {

                    //update CISLBatchSyncTime
                    cislBatchSYncTimeSysCtrl.setSysValue(cislSyncToSndTime);
                    sysCtrlService.updateSysCtrl(cislBatchSYncTimeSysCtrl);

                    //call Service to sync
                    cislSyncToSNDService.CISLSyncToSND();

                }
            }
        }
        else {
            writeErrorLog("Can't found SysCtrl CISLBatchSyncTime and CISLSyncToSNDTime param");
        }
    }

    private void writeErrorLog(String message) {
        batchLogService.createBatchLog(getClass().getSimpleName(),message);
    }
}
