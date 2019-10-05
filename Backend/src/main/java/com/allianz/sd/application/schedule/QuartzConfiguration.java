package com.allianz.sd.application.schedule;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 上午 10:57
 * To change this template use File | Settings | File Templates.
 */
import com.allianz.sd.core.schedule.AutoWiringSpringBeanJobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzConfiguration {

    @Autowired
    @Qualifier("Cron1")
    private CronTriggerFactoryBean cron1;

    @Autowired
    @Qualifier("CalendarReminderCron")
    private CronTriggerFactoryBean calendarReminderCron;

    @Autowired
    @Qualifier("CalendarReminderByDayCron")
    private CronTriggerFactoryBean calendarReminderByDayCron;

    @Autowired
    @Qualifier("CustomerCountAgeCron")
    private CronTriggerFactoryBean customerCountAgeCron;

    @Autowired
    @Qualifier("CustomerCheckMaintainTimeCron")
    private CronTriggerFactoryBean customerCheckMaintainTimeCron;

    @Autowired
    @Qualifier("CustomerAutoDeleteCron")
    private CronTriggerFactoryBean CustomerAutoDeleteCron;

    @Autowired
    @Qualifier("ProgressPointCron")
    private CronTriggerFactoryBean ProgressPointCron;
    
    @Autowired
    @Qualifier("NeedGoalSettingCron")
    private CronTriggerFactoryBean NeedGoalSettingCron;
    
    @Autowired
    @Qualifier("VerifyCron")
    private CronTriggerFactoryBean VerifyCron;

    @Autowired
    @Qualifier("SysDataSaleMonthPlanMonthCron")
    private CronTriggerFactoryBean SysDataSaleMonthPlanMonthCron;

    @Autowired
    @Qualifier("CalculationPersonalProgressCron")
    private CronTriggerFactoryBean calculationPersonalProgressCron;

    @Autowired
    @Qualifier("CalculationTeamDataCron")
    private CronTriggerFactoryBean calculationTeamDataCron;

    @Autowired
    @Qualifier("ReminderAgentDefaultGoalCron")
    private CronTriggerFactoryBean reminderAgentDefaultGoalCron;

    @Autowired
    @Qualifier("ReminderAgentLeaderNoApproveCron")
    private CronTriggerFactoryBean reminderAgentLeaderNoApproveCron;

    @Autowired
    @Qualifier("TestingInsertAgentDailyDataCron")
    private CronTriggerFactoryBean testingInsertAgentDailyDataCron;
    
    @Autowired
    @Qualifier("DataPrivacyProtectionCron")
    private CronTriggerFactoryBean dataPrivacyProtectionCron;
    
    @Bean
    public AutoWiringSpringBeanJobFactory autoWiringSpringBeanJobFactory(){
        return new AutoWiringSpringBeanJobFactory();
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        scheduler.setJobFactory(autoWiringSpringBeanJobFactory());
        scheduler.setTriggers(
                calendarReminderCron.getObject(),
                calendarReminderByDayCron.getObject(),
                customerCountAgeCron.getObject(),
                customerCheckMaintainTimeCron.getObject(),
                CustomerAutoDeleteCron.getObject(),
                ProgressPointCron.getObject(),
        		NeedGoalSettingCron.getObject(),
        		VerifyCron.getObject(),
//        		SysDataSaleMonthPlanMonthCron.getObject(),
//                testingInsertAgentDailyDataCron.getObject(),
                 calculationPersonalProgressCron.getObject()
                , calculationTeamDataCron.getObject()
                , reminderAgentDefaultGoalCron.getObject()
                , reminderAgentLeaderNoApproveCron.getObject()
                , dataPrivacyProtectionCron.getObject()
                );

        return scheduler;
    }
}