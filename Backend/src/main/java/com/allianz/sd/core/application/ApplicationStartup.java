package com.allianz.sd.core.application;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

import com.allianz.sd.core.InstanceCode;
import com.allianz.sd.core.cisl.CISLAPIFetch;
import com.allianz.sd.core.cisl.CISLAPIManager;
import com.allianz.sd.core.cisl.impl.*;
import com.allianz.sd.core.goalsetting.GoalStatusListener;
import com.allianz.sd.core.goalsetting.impl.*;
import com.allianz.sd.core.service.bean.GoalSettingStatus;
import org.hibernate.metamodel.internal.EntityTypeImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.allianz.sd.core.jpa.model.Code;
import com.allianz.sd.core.jpa.model.LanguageTextMapping;
import com.allianz.sd.core.jpa.repository.CodeRepository;
import com.allianz.sd.core.jpa.repository.LanguageTextMappingRepository;
import com.allianz.sd.core.service.CodeService;
import com.allianz.sd.core.service.LanguageService;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 6:24
 * To change this template use File | Settings | File Templates.
 */
@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationStartup.class);

    @Value("${SND.LanguageID}")
    private String SND_LANGUAGE_ID = null;

    @Autowired
    private CodeService codeService = null;

    @Autowired
    private CodeRepository codeRepository = null;

    @Autowired
    private LanguageTextMappingRepository languageTextMappingRepository = null;

    @Autowired
    private LanguageService languageService = null;

    @Autowired
    private GoalStatusListener goalStatusListener;

    @Autowired
    private CalcAgencyPlanValue calcAgencyPlanValue;

    @Autowired
    private CalcProgressValue calcProgressValue;

    @Autowired
    private SendApproveOrRejectGoalNotification sendApproveOrRejectGoalNotification;

    @Autowired
    private SendOverdueGoalNotification sendOverdueGoalNotification;

    @Autowired
    private SendPendingGoalNotification sendPendingGoalNotification;

    @Autowired
    private UpdateAgencyPlanApproveStatus updateAgencyPlanApproveStatus;

    @Autowired
    private UpdateRejectStatus updateRejectStatus;

    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private CISLAgentDataAPIFetch cislAgentDataAPIFetch;

    @Autowired
    private CISLAgentYearAPIFetch cislAgentYearAPIFetch;

    @Autowired
    private CISLAgencyPlanAPIFetch cislAgencyPlanAPIFetch;

    @Autowired
    private CISLAgencyPlanDetailAPIFetch cislAgencyPlanDetailAPIFetch;

    @Autowired
    private CISLAgencyPlanPileAPIFetch cislAgencyPlanPileAPIFetch;

    @Autowired
    private CISLSalesAPIFetch cislSalesAPIFetch;

    @Autowired
    private CISLGoalSettingAPIFetch cislGoalSettingAPIFetch;

    /**
     * This event is executed as late as conceivably possible to indicate that
     * the application is ready to service requests.
     */
    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {

        logger.info("Read all TW_LH_SD_Code");

        //read all InstanceCode
        List<Code> codeList = codeRepository.findAll();
        for(Code code : codeList) {
            codeService.addCode(code);
        }

        logger.info("Read all TW_LH_SD_Code Finish!!");

        // read language mapping to Service
        logger.info("Read all TW_LH_SD_LANGUAGE_TEXT_MAPPING");
        List<LanguageTextMapping> languageTextMappings  = languageTextMappingRepository.findByIdentityLanguageID(SND_LANGUAGE_ID);
        logger.info("languageTextMappings size:"+languageTextMappings.size());
        for(LanguageTextMapping languageTextMapping:languageTextMappings) {
            languageService.addCodeLanguage(languageTextMapping.getIdentity().getMappingId(), languageTextMapping.getText());
        }
        logger.info("Read all TW_LH_SD_LANGUAGE_TEXT_MAPPING Finish!!");


        //Register GoalStatusListener Listener
        goalStatusListener.addObserver(GoalSettingStatus.APPROVED,GoalSettingStatus.APPROVED,calcAgencyPlanValue);
        goalStatusListener.addObserver(GoalSettingStatus.APPROVED,GoalSettingStatus.APPROVED,calcProgressValue);

        goalStatusListener.addObserver(GoalSettingStatus.APPROVED,GoalSettingStatus.PENDING_APPROVAL,sendPendingGoalNotification);
        goalStatusListener.addObserver(GoalSettingStatus.APPROVED,GoalSettingStatus.PENDING_APPROVAL,updateAgencyPlanApproveStatus);

        goalStatusListener.addObserver(GoalSettingStatus.PENDING_APPROVAL,GoalSettingStatus.REJECT,sendApproveOrRejectGoalNotification);
        goalStatusListener.addObserver(GoalSettingStatus.PENDING_APPROVAL,GoalSettingStatus.REJECT,updateRejectStatus);

        goalStatusListener.addObserver(GoalSettingStatus.PENDING_APPROVAL,GoalSettingStatus.APPROVED,sendApproveOrRejectGoalNotification);
        goalStatusListener.addObserver(GoalSettingStatus.PENDING_APPROVAL,GoalSettingStatus.APPROVED,calcAgencyPlanValue);
        goalStatusListener.addObserver(GoalSettingStatus.PENDING_APPROVAL,GoalSettingStatus.APPROVED,calcProgressValue);

        goalStatusListener.addObserver(GoalSettingStatus.PENDING_APPROVAL,GoalSettingStatus.OVERDUE,sendOverdueGoalNotification);

        goalStatusListener.addObserver(GoalSettingStatus.REJECT,GoalSettingStatus.APPROVED,calcAgencyPlanValue);
        goalStatusListener.addObserver(GoalSettingStatus.REJECT,GoalSettingStatus.APPROVED,calcProgressValue);

        goalStatusListener.addObserver(GoalSettingStatus.REJECT,GoalSettingStatus.PENDING_APPROVAL,sendPendingGoalNotification);
        goalStatusListener.addObserver(GoalSettingStatus.REJECT,GoalSettingStatus.PENDING_APPROVAL,updateAgencyPlanApproveStatus);


        CISLAPIManager.getInstance().addCISLAPIFetch((CISLAPIFetch) beanFactory.getBean(InstanceCode.AgentDataAPIFetch));
        CISLAPIManager.getInstance().addCISLAPIFetch((CISLAPIFetch) beanFactory.getBean(InstanceCode.AgentYearAPIFetch));
        CISLAPIManager.getInstance().addCISLAPIFetch((CISLAPIFetch) beanFactory.getBean(InstanceCode.AgencyPlanAPIFetch));
        CISLAPIManager.getInstance().addCISLAPIFetch((CISLAPIFetch) beanFactory.getBean(InstanceCode.AgencyPlanDetailAPIFetch));
        CISLAPIManager.getInstance().addCISLAPIFetch((CISLAPIFetch) beanFactory.getBean(InstanceCode.AgencyPlanPileAPIFetch));
        CISLAPIManager.getInstance().addCISLAPIFetch((CISLAPIFetch) beanFactory.getBean(InstanceCode.SalesAPIFetch));
        CISLAPIManager.getInstance().addCISLAPIFetch((CISLAPIFetch) beanFactory.getBean(InstanceCode.GoalSettingAPIFetch));
//        CISLAPIManager.getInstance().addCISLAPIFetch((CISLAPIFetch) beanFactory.getBean(InstanceCode.CustomerAPIFetch));

    }


}