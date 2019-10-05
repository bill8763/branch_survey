package com.allianz.sd.core.service;

import com.allianz.sd.core.InstanceCode;
import com.allianz.sd.core.cisl.*;
import com.allianz.sd.core.cisl.bean.CISLAPITask;
import com.allianz.sd.core.cisl.bean.CISLPageInfo;
import com.allianz.sd.core.cisl.thread.CISLAPIExecutor;
import com.allianz.sd.core.jpa.model.*;
import com.allianz.sd.core.jpa.repository.CISLGoalSettingRepository;
import com.allianz.sd.core.service.bean.CISLToken;
import com.allianz.sd.core.service.bean.Goal;
import com.allianz.sd.core.service.bean.GoalSettingStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class CISLSyncToSNDService {

    private Logger logger = LoggerFactory.getLogger(CISLSyncToSNDService.class);

    @Value("${CISL.ThreadNum}")
    private String cislThreadNum;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CISLGoalSettingRepository cislGoalSettingRepository;

    @Autowired
    private DateService dateService;

    @Autowired
    private SysDataService sysDataService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private SysCtrlService sysCtrlService;

    @Autowired
    private GoalService goalService;

    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private BatchLogService batchLogService;

    @Async
    public void handlingErrorCISLToSND(Integer batchID) {

        List<String> errorMessageList = new ArrayList<>();
        BatchLog batchLog = batchLogService.getBatchLog(batchID);

        if(batchLog != null) {

            //reset batchlog
            batchLog.setErrorCount(0);
            batchLog.setErrorMessage("");
            batchLog.setStartTime(dateService.getTodayDate());
            batchLog.setEndTime(null);

            //get CISL Token
            CISLTokenProvider cislTokenProvider = (CISLTokenProvider) beanFactory.getBean(InstanceCode.CISLTokenProvider);

            CISLToken cislToken = null;
            try{
                cislToken = cislTokenProvider.getCISLToken();
            }catch(Exception e) {
                logger.error("get CISLToken fail!!",e);
                errorMessageList.add("Can't get CISL token");
            }

            if(cislToken != null) {
                CISLAPIQueue cislapiQueue = new CISLAPIQueue();

                List<CISLBatchLogDetail> details = batchLogService.getCISLErrorDetails(batchID);
                for(CISLBatchLogDetail cislBatchLogDetail : details) {

                    Integer pageNo = cislBatchLogDetail.getIdentity().getPageNumber();
                    CISLAPIFetch cislapiFetch = CISLAPIManager.getInstance().getCislAPIFetch(cislBatchLogDetail.getIdentity().getDetailCategory());

                    if(cislapiFetch != null) {

                        cislapiFetch.setToken(cislToken);

                        CISLAPITask task = new CISLAPITask();
                        task.setPageNo(pageNo);
                        task.setCislapiFetch(cislapiFetch);
                        cislapiQueue.add(task);
                    }

                }

                executeCISLSyncProcess(cislapiQueue,batchID,errorMessageList);
            }

            //update batchlog
            batchLog.setErrorMessage(String.join("\n" , errorMessageList));
            batchLog.setErrorCount(errorMessageList.size());

            batchLogService.saveBatchLog(batchLog);
            logger.trace("Update BatchLog");
        }


    }

    public void CISLSyncToSND() {

        //create batchlog
        BatchLog batchLog = batchLogService.createBatchLog(getClass().getSimpleName());
        List<String> errorMessageList = new ArrayList<>();

        //get CISL Token
        CISLTokenProvider cislTokenProvider = (CISLTokenProvider) beanFactory.getBean(InstanceCode.CISLTokenProvider);

        CISLToken cislToken = null;
        try{
            cislToken = cislTokenProvider.getCISLToken();
        }catch(Exception e) {
            logger.error("get CISLToken fail!!",e);
            errorMessageList.add("Can't get CISL token");
        }

        if(cislToken != null) {
            //execute CISL API & get PageInfo
            CISLAPIQueue cislapiQueue = new CISLAPIQueue();

            List<CISLAPIFetch> cislapiFetches = CISLAPIManager.getInstance().getCislapiFetchList();
            for(CISLAPIFetch cislapiFetch : cislapiFetches) {

                cislapiFetch.setToken(cislToken);

                try{
                    CISLPageInfo cislPageInfo = cislapiFetch.getCISLPageInfo();

                    //get totalpage
                    int pageCount = cislPageInfo.getPageCount();
                    logger.debug(cislapiFetch.getClass().getSimpleName() + " pageCount = " + pageCount);

                    //Add to CISLAPQueue
                    for(int i=0;i<pageCount;i++) {
                        CISLAPITask task = new CISLAPITask();
                        task.setPageNo(i+1);
                        task.setCislapiFetch(cislapiFetch);

                        cislapiQueue.add(task);
                    }
                }catch(Exception e) {
                    logger.error("getCISLPageInfo fail!!",e);
                    errorMessageList.add("["+cislapiFetch.getClass().getSimpleName()+"] getCISLPageInfo fail!!");
                }
            }

            executeCISLSyncProcess(cislapiQueue,batchLog.getBatchLogID(),errorMessageList);
        }


        //update batchlog
        batchLog.setErrorMessage(String.join("\n" , errorMessageList));
        batchLog.setErrorCount(errorMessageList.size());

        batchLogService.saveBatchLog(batchLog);
        logger.trace("Update BatchLog");
    }

    private void executeCISLSyncProcess(CISLAPIQueue cislapiQueue,Integer batchID , List<String> errorMessageList) {
        logger.debug("Total cislapiQueue size = "+cislapiQueue.size());

        //new Thread to Execute CISL API
        int threadNum = Integer.parseInt(cislThreadNum);
        for(int i=0;i<threadNum;i++) {
            CISLAPIExecutor executor = new CISLAPIExecutor(cislapiQueue, batchID,batchLogService,errorMessageList);
            Thread thread = new Thread(executor);
            thread.start();
        }

        //waiting queue is clear
        while (!cislapiQueue.isEmpty()) {
            try{
                logger.trace("CISLSyncBatch cislapiQueue size = "+cislapiQueue.size());
                Thread.sleep(10000);
            }catch(Exception ex) {
                logger.error("MainThread sleep fail!!",ex);
                break;
            }
        }

        try{
            //waiting last thread is finish
            Thread.sleep(30000);
        }catch(Exception e) {
            logger.error("MainThread sleep fail!!",e);
        }

        if(errorMessageList.size() == 0) {
            try{
                syncToSND();
            }catch(Exception e) {
                logger.error("CISLSyncToSND fail!!",e);
                errorMessageList.add("CISLSyncToSND fail!!");
            }
        }
    }

    @Transactional
    public void syncToSND() throws Exception {

        CISLGoalValueProcesser cislGoalValueProcesser = (CISLGoalValueProcesser) beanFactory.getBean(InstanceCode.CISLGoalValueProcesser);
        String cislGoalStatusMethod = sysCtrlService.getSysCtrl(SysCtrlService.CISLGoalStatusMethod).getSysValue();

        Date today = dateService.getTodayDate();
        String organizationalUnit = organizationService.getOrganizationalUnit();

        Set<Integer> dataYears = sysDataService.getAPPDisplayYears(today,organizationalUnit);

        //把Temp的Goal寫到GoalVersion & GoalValue & GoalPlan
        for(Integer dataYear : dataYears) {

            logger.trace("get CISLGoalSetting adjust data["+dataYear+"]");
            List<CISLGoalSetting> cislGoalSettings = cislGoalSettingRepository.findAdjustGoalData(dataYear,organizationalUnit);
            logger.trace("get CISLGoalSetting adjust data["+dataYear+"] size = " + cislGoalSettings.size());

            for(CISLGoalSetting cislGoalSetting : cislGoalSettings) {

                String agentID = cislGoalSetting.getIdentity().getAgentID();

                //撈GoalSettingVersion
                GoalSettingVersion goalSettingVersion = goalService.getLastGoalVersionModel(agentID,organizationalUnit,dataYear);

                //如果沒有Version代表是新的業務員，要幫他建version
                if(goalSettingVersion == null) {
                    cislGoalValueProcesser.initGoalValue(cislGoalSetting);
                }
                //判斷目前最後一版的goal是不是pending_approve
                else {

                    if(GoalSettingStatus.PENDING_APPROVAL.toString().equalsIgnoreCase(goalSettingVersion.getStatus())) {
                        if("OVERRIDE".equalsIgnoreCase(cislGoalStatusMethod)) {

                            //如果是採用覆蓋的方式，就先把上一版goal改成reject
                            goalSettingVersion.setStatus(GoalSettingStatus.REJECT.toString());
                            goalService.updateGoalVersion(goalSettingVersion);

                            cislGoalValueProcesser.createNewGoalValue(cislGoalSetting,goalSettingVersion);
                        }
                    }
                    else {
                        cislGoalValueProcesser.createNewGoalValue(cislGoalSetting,goalSettingVersion);
                    }
                }
            }
        }


        //呼叫StoredProducted寫到正式table
        int result = entityManager.createStoredProcedureQuery("CISL_SYNC_SND_PROCEDURE").executeUpdate();
    }
}
