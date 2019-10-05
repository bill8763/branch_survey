package com.allianz.sd.core.service;

import com.allianz.sd.core.jpa.model.BatchLog;
import com.allianz.sd.core.jpa.model.CISLBatchLogDetail;
import com.allianz.sd.core.jpa.repository.CISLBatchLogDetailRepository;
import com.allianz.sd.core.jpa.repository.BatchLogRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BatchLogService {

    @Autowired
    private BatchLogRepository batchLogRepository;

    @Autowired
    private CISLBatchLogDetailRepository cislBatchLogDetailRepository;

    @Autowired
    private SequenceIDService sequenceIDService;

    @Autowired
    private DateService dateService;

    @Autowired
    private OrganizationService organizationService;

    public BatchLog getBatchLog(Integer batchID) {
        return batchLogRepository.findById(batchID).orElse(null);
    }

    public BatchLog createBatchLog(String batchName) {
        return createBatchLog(batchName,null);
    }

    public BatchLog createBatchLog(String batchName, String erorrMsg) {

        BatchLog batchLog = new BatchLog();

        int batchLogID = sequenceIDService.getId("batchlog",new BigDecimal(1)).get(0).intValue();
        batchLog.setBatchLogID(batchLogID);
        batchLog.setBatchName(batchName);
        batchLog.setOrganizationalUnit(organizationService.getOrganizationalUnit());
        batchLog.setStartTime(dateService.getTodayDate());

        if(StringUtils.isNotEmpty(erorrMsg)) {
            batchLog.setErrorCount(1);
            batchLog.setErrorMessage(erorrMsg);
        }

        batchLogRepository.save(batchLog);

        return batchLog;
    }

    public void saveCISLBatchDetailLog(CISLBatchLogDetail cislBatchLogDetail) {
        cislBatchLogDetailRepository.save(cislBatchLogDetail);
    }

    public void saveBatchLog(BatchLog batchLog) {

        Integer errorCount = batchLog.getErrorCount();
        String result = "N";
        if(errorCount == null || errorCount == 0) result = "Y";

        batchLog.setEndTime(dateService.getTodayDate());
        batchLog.setResult(result);
        batchLogRepository.save(batchLog);

        //Get BatchDetail update to batchlog

    }

    public List<CISLBatchLogDetail> getCISLErrorDetails(Integer batchID) {
        return cislBatchLogDetailRepository.getCISLDetailByBatchID(batchID);
    }
}
