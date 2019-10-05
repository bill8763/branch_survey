package com.allianz.sd.core.service;

import com.allianz.sd.core.jpa.model.SysCtrl;
import com.allianz.sd.core.jpa.model.SysCtrlIdentity;
import com.allianz.sd.core.jpa.repository.SysCtrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysCtrlService {

    public static final String CISLSyncToSNDTime = "CISLSyncToSNDTime";
    public static final String CISLBatchSyncTime = "CISLBatchSyncTime";
    public static final String CalcAgencyProgressBatchSyncTime = "CalcAgencyProgressBatchSyncTime";
    public static final String CISLGoalStatusMethod = "CISLGoalStatusMethod";


    @Autowired
    private SysCtrlRepository sysCtrlRepository;

    @Autowired
    private OrganizationService organizationService;

    public SysCtrl getSysCtrl(String sysCtrl) {
        SysCtrlIdentity sysCtrlIdentity = new SysCtrlIdentity();
        sysCtrlIdentity.setSysCtrl(sysCtrl);
        sysCtrlIdentity.setOrganizationalUnit(organizationService.getOrganizationalUnit());

        return sysCtrlRepository.findById(sysCtrlIdentity).orElse(null);
    }

    public void updateSysCtrl(SysCtrl sysCtrl) {
        sysCtrlRepository.save(sysCtrl);
    }


}
