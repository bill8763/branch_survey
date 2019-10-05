package com.allianz.sd.application;



import com.allianz.sd.application.extension.authorization.TWAuthorization;
import com.allianz.sd.application.extension.notification.TWNotification;
import com.allianz.sd.core.cisl.CISLAPIFetch;
import com.allianz.sd.core.cisl.CISLGoalValueProcesser;
import com.allianz.sd.core.cisl.impl.*;
import com.allianz.sd.core.datasync.*;
import com.allianz.sd.core.datasync.impl.*;
import com.allianz.sd.core.impl.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.allianz.sd.application.extension.authorization.TWDeviceBinding;
import com.allianz.sd.application.extension.datasync.CustomerDataSyncExtension;
import com.allianz.sd.application.extension.datasync.TestOtherExtension;
import com.allianz.sd.application.extension.version.TWAppUpdateVersionMgr;
import com.allianz.sd.core.agencyplan.ApproveRule;
import com.allianz.sd.core.agencyplan.CalcAgencyPlanRule;
import com.allianz.sd.core.agencyplan.DrilldownRule;
import com.allianz.sd.core.customer.CustomerFullNameConvert;
import com.allianz.sd.core.datasync.AgencyPlanDetailSyncListener;
import com.allianz.sd.core.datasync.AgencyPlanMasterSyncListener;
import com.allianz.sd.core.datasync.CalendarDataSyncListener;
import com.allianz.sd.core.datasync.ConfigurationSyncListener;
import com.allianz.sd.core.datasync.CustomerContactDataSyncListener;
import com.allianz.sd.core.datasync.CustomerDataSyncListener;
import com.allianz.sd.core.datasync.DataSync;
import com.allianz.sd.core.datasync.GoalSettingDataSyncListener;
import com.allianz.sd.core.datasync.GoalSettingPlanSyncListener;
import com.allianz.sd.core.datasync.GoalSettingSyncListener;
import com.allianz.sd.core.datasync.GoalSettingValueSyncListener;
import com.allianz.sd.core.datasync.GoalSyncListener;
import com.allianz.sd.core.datasync.PersonalProgressSyncListener;
import com.allianz.sd.core.datasync.TeamProgressDetailSyncListener;
import com.allianz.sd.core.datasync.TeamProgressMasterSyncListener;
import com.allianz.sd.core.datasync.impl.AppointmentDataSync;
import com.allianz.sd.core.datasync.impl.CustomerDataSync;
import com.allianz.sd.core.datasync.impl.GoalSettingDataSync;
import com.allianz.sd.core.datasync.impl.MessageDataSync;
import com.allianz.sd.core.datasync.impl.NoteDataSync;
import com.allianz.sd.core.impl.CalcSchedulePointRule;
import com.allianz.sd.core.impl.DefaultApproveRule;
import com.allianz.sd.core.impl.DefaultAuthorization;
import com.allianz.sd.core.impl.DefaultCalcAgencyPlanRule;
import com.allianz.sd.core.impl.DefaultCalcPersonalProgress;
import com.allianz.sd.core.impl.DefaultCalcTeamProgress;
import com.allianz.sd.core.impl.DefaultCalcWorkingMonth;
import com.allianz.sd.core.impl.DefaultCustomerFullNameConvert;
import com.allianz.sd.core.impl.DefaultDeviceBindingRule;
import com.allianz.sd.core.impl.DefaultDrilldownRule;
import com.allianz.sd.core.impl.DefaultProgressActivity;
import com.allianz.sd.core.impl.FirebasePushNotification;
import com.allianz.sd.core.notification.PushNotification;
import com.allianz.sd.core.progress.CalcPersonalProgress;
import com.allianz.sd.core.progress.CalcTeamProgress;
import com.allianz.sd.core.progress.IsCalcSchedulePoint;
import com.allianz.sd.core.progress.ProgressActivity;
import com.allianz.sd.core.version.APPUpdateVersionMgr;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/3/5
 * Time: 上午 11:51
 * To change this template use File | Settings | File Templates.
 */
@Configuration
public class ExtensionConfiguration {

    @Value("${spring.profiles.active}")
    private String profileID;


    @Bean
    @Primary
    public CISLGoalValueProcesser CISLGoalValueProcesser() {
        return new DefaultCISLGoalValueProcesser();
    }

    @Bean
    @Primary
    public CISLAPIFetch AgentDataAPIFetch() {
        return new CISLAgentDataAPIFetch();
    }

    @Bean
    @Primary
    public CISLAPIFetch AgentYearAPIFetch() {
        return new CISLAgentYearAPIFetch();
    }

    @Bean
    @Primary
    public CISLAPIFetch AgencyPlanAPIFetch() {
        return new CISLAgencyPlanAPIFetch();
    }

    @Bean
    @Primary
    public CISLAPIFetch AgencyPlanDetailAPIFetch() {
        return new CISLAgencyPlanDetailAPIFetch();
    }

    @Bean
    @Primary
    public CISLAPIFetch AgencyPlanPileAPIFetch() {
        return new CISLAgencyPlanPileAPIFetch();
    }

    @Bean
    @Primary
    public CISLAPIFetch SalesAPIFetch() {
        return new CISLSalesAPIFetch();
    }

    @Bean
    @Primary
    public CISLAPIFetch GoalSettingAPIFetch() {
        return new CISLGoalSettingAPIFetch();
    }

	@Bean
	@Primary
	public DrilldownRule drilldownRule() {
		return new DefaultDrilldownRule();
	}

    @Bean
    @Primary
    public ApproveRule approveRule() {
        return new DefaultApproveRule();
    }

    @Bean
    @Primary
    public CalcAgencyPlanRule calcAgencyPlanDetailPlan() {
        return new DefaultCalcAgencyPlanRule();
    }

    @Bean
    @Primary
    public CalcPersonalProgress calcPersonalProgress() {
        return new DefaultCalcPersonalProgress();
    }


    @Bean
    @Primary
    public CalcTeamProgress calcTeamProgress() {
        return new DefaultCalcTeamProgress();
    }


	@Bean
	@Primary
	public IsCalcSchedulePoint isCalcSchedulePoint() {
		return new CalcSchedulePointRule();
	}
	
	@Bean
	@Primary
	public ProgressActivity progressActivity() {
		return new DefaultProgressActivity();
	}

    @Bean
    @Primary
    public com.allianz.sd.core.authorization.DeviceBindingRule deviceBindingRule() {

        if("DEV".equalsIgnoreCase(profileID) || "SIT".equalsIgnoreCase(profileID)) return new DefaultDeviceBindingRule();
        else return new TWDeviceBinding();
    }

    @Bean
    @Primary
    public com.allianz.sd.core.authorization.Authorization authorization() {

        if("DEV".equalsIgnoreCase(profileID) || "SIT".equalsIgnoreCase(profileID)) return new DefaultAuthorization();
        else return new TWAuthorization();
	}

    @Bean
    @Primary
    public PushNotification pushNotification() {
	    if("DEV".equalsIgnoreCase(profileID) || "SIT".equalsIgnoreCase(profileID)) return new FirebasePushNotification();
        else return new TWNotification();
    }

    @Bean
    @Primary
    public APPUpdateVersionMgr appUpdateVersionMgr() {
        return new TWAppUpdateVersionMgr();
    }

    @Bean
    @Primary
    public AgentTableDataSync ProgressDataSync() {
        return new ProgressAgentTableDataSync();
    }

    @Bean
    @Primary
    public AgentTableDataSync ActualDataSync() {
        return new ActualAgentTableDataSync();
    }

    @Bean
    @Primary
    public AgentTableDataSync ConfigurationDataSync() {
        return new ConfigurationAgentTableDataSync();
    }

    @Bean
    @Primary
    public DataSync MessageDataSync() {
        return new MessageDataSync();
    }

    @Bean
    @Primary
    public DataSync CalendarDataSync() {
        return new AppointmentDataSync();
    }

    @Bean
    @Primary
    public DataSync CustomerDataSync() {
        return new CustomerDataSync();
    }
    
    @Bean
    @Primary
    public DataSync GoalSettingDataSync() {
    	return new GoalSettingDataSync();
    }

    @Bean
    @Primary
    public DataSync CustomerContactDataSync() {
        return new NoteDataSync();
    }

    @Bean
    @Primary
    public CustomerFullNameConvert customerFullNameConvert() {
        return new DefaultCustomerFullNameConvert();
    }

    @Bean
    @Primary
    public CustomerDataSyncListener customerExtensionListener() {return new CustomerDataSyncExtension();}

    @Bean
    @Primary
    public CalendarDataSyncListener calendarExtensionListener() {return null;}

    @Bean
    @Primary
    public CustomerContactDataSyncListener customerContactExtensionListener() {return null;}
    
    @Bean
    @Primary
    public GoalSettingDataSyncListener goalExpectedListener() {return null;}

    @Bean
    @Primary
    public ConfigurationSyncListener configurationExtension() {return new TestOtherExtension();}

    @Bean
    @Primary
    public GoalSyncListener goalExtension() {return new TestOtherExtension();}

    @Bean
    @Primary
    public GoalSettingSyncListener goalSettingExtension() {return new TestOtherExtension();}

    @Bean
    @Primary
    public GoalSettingValueSyncListener goalSettingValueExtension() {return new TestOtherExtension();}

    @Bean
    @Primary
    public GoalSettingPlanSyncListener goalSettingPlanExtension() {return new TestOtherExtension();}

    @Bean
    @Primary
    public AgencyPlanMasterSyncListener agencyPlanMasterExtension() {return new TestOtherExtension();}

    @Bean
    @Primary
    public AgencyPlanDetailSyncListener agencyPlanDetailExtension() {return new TestOtherExtension();}

    @Bean
    @Primary
    public PersonalProgressSyncListener personalProgressExtension() {return new TestOtherExtension();}

    @Bean
    @Primary
    public TeamProgressMasterSyncListener teamProgressMasterExtension() {return new TestOtherExtension();}

    @Bean
    @Primary
    public TeamProgressDetailSyncListener teamProgressDetailExtension() {return new TestOtherExtension();}

    @Bean
    @Primary
    public com.allianz.sd.core.sysdata.CalcWorkingMonth calcWorkingMonth() {
        return new DefaultCalcWorkingMonth();
    }
}
