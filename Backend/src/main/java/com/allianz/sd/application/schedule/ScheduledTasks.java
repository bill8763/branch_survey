package com.allianz.sd.application.schedule;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 上午 10:55
 * To change this template use File | Settings | File Templates.
 */
import com.allianz.sd.core.schedule.job.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

@Configuration
@ComponentScan("com.allianz.sd.core.schedule.job")
public class ScheduledTasks {

    @Bean(name="CustomerAutoDeleteJob")
    public JobDetailFactoryBean getCustomerAutoDeleteJob(){
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(CustomerAutoDelete.class);
        factory.setGroup("customerAutoDeleteGroup");
        factory.setName("customerAutoDeleteJob");
        return factory;
    }

    //Job is scheduled after every 1 day
    @Bean(name="CustomerAutoDeleteCron")
    public CronTriggerFactoryBean customerAutoDeleteTriggerFactoryBean(){
        CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
        stFactory.setJobDetail(getCustomerAutoDeleteJob().getObject());
        stFactory.setStartDelay(1000);
        stFactory.setName("customerAutoDeletetrigger");
        stFactory.setGroup("autoDeleteGroup");
        stFactory.setCronExpression("0 0 10 * * ?");
//        stFactory.setCronExpression("0 0/1 * 1/1 * ? *");
//        stFactory.setCronExpression("0 0/1 * * * ?");
        return stFactory;
    }

    @Bean(name="CustomerCheckMaintainTimeJob")
    public JobDetailFactoryBean getCustomerCheckMaintainTimeJob(){
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(CustomerCheckMaintainTime.class);
        factory.setGroup("customerCheckMaintainTimeGroup");
        factory.setName("customerCheckMaintainTimeJob");
        return factory;
    }

    //Job is scheduled after every 1 day
    @Bean(name="CustomerCheckMaintainTimeCron")
    public CronTriggerFactoryBean customerCheckMaintainTimeTriggerFactoryBean(){
        CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
        stFactory.setJobDetail(getCustomerCheckMaintainTimeJob().getObject());
        stFactory.setStartDelay(1000);
        stFactory.setName("customerCheckMaintainTimetrigger");
        stFactory.setGroup("checkMaintainTimeGroup");
        stFactory.setCronExpression("0 0 12 * * ?");
//        stFactory.setCronExpression("0 0/1 * 1/1 * ? *");
        return stFactory;
    }

    @Bean(name="CustomerCountAgeJob")
    public JobDetailFactoryBean getCustomerCountAgeJob(){
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(CustomerCountAge.class);
        factory.setGroup("customerCountAgeGroup");
        factory.setName("customerCountAgeJob");
        return factory;
    }

    //Job is scheduled after every 1 day
    @Bean(name="CustomerCountAgeCron")
    public CronTriggerFactoryBean customerCountAgeTriggerFactoryBean(){
        CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
        stFactory.setJobDetail(getCustomerCountAgeJob().getObject());
        stFactory.setStartDelay(1000);
        stFactory.setName("customerCountAgetrigger");
        stFactory.setGroup("countAgeGroup");
        stFactory.setCronExpression("0 0 0 * * ?");
//        stFactory.setCronExpression("0 0/1 * 1/1 * ? *");
        return stFactory;
    }

    @Bean(name="CalendarReminderByDayJob")
    public JobDetailFactoryBean getCalendarReminderByDayBean(){
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(CalendarReminderByDay.class);
        factory.setGroup("calendarReminderDayGroup");
        factory.setName("calendarReminderDayJob");
        return factory;
    }

    //Job is scheduled after every 1 day
    @Bean(name="CalendarReminderByDayCron")
    public CronTriggerFactoryBean calendarReminderByDayTriggerFactoryBean(){
        CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
        stFactory.setJobDetail(getCalendarReminderByDayBean().getObject());
        stFactory.setStartDelay(1000);
        stFactory.setName("reminderDaytrigger");
        stFactory.setGroup("reminderDayGroup");
        stFactory.setCronExpression("0 0 8 * * ?");
//        stFactory.setCronExpression("0 1/1 * * * ?");
        return stFactory;
    }

    @Bean(name="CalendarReminderJob")
    public JobDetailFactoryBean getCalendarReminderBean(){
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(CalendarReminderByMin.class);
        factory.setGroup("calendarReminderGroup");
        factory.setName("calendarReminderJob");
        return factory;
    }

    //Job is scheduled after every 5 minute
    @Bean(name="CalendarReminderCron")
    public CronTriggerFactoryBean calendarReminderTriggerFactoryBean(){
        CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
        stFactory.setJobDetail(getCalendarReminderBean().getObject());
        stFactory.setStartDelay(1000);
        stFactory.setName("reminderMinTrigger");
        stFactory.setGroup("reminderMingroup");
        stFactory.setCronExpression("0 5/5 * * * ?");
//        stFactory.setCronExpression("0 1/1 * * * ?");
        return stFactory;
    }

    @Bean(name="Job1")
    public JobDetailFactoryBean jobDetailFactoryBean(){
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(CustomerJob.class);
        factory.setGroup("mygroup");
        factory.setName("myjob");
        return factory;
    }

    //Job is scheduled after every 1 minute
    @Bean(name="Cron1")
    public CronTriggerFactoryBean cronTriggerFactoryBean(){
        CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
        stFactory.setJobDetail(jobDetailFactoryBean().getObject());
        stFactory.setStartDelay(1000);
        stFactory.setName("mytrigger");
        stFactory.setGroup("mygroup");
        stFactory.setCronExpression("0 0/1 * 1/1 * ? *");
        return stFactory;
    }

    //MiniPoint by Jeff
    @Bean(name="ProgressPoint")
    public JobDetailFactoryBean getProgressPoint(){
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(ProgressPoint.class);
        factory.setGroup("progressPointGroup");
        factory.setName("progressPointJob");
        return factory;
    }

    //Job is scheduled after every 12 o'clock
    @Bean(name="ProgressPointCron")
    public CronTriggerFactoryBean progressPointTriggerFactoryBean(){
        CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
        stFactory.setJobDetail(getProgressPoint().getObject());
        stFactory.setStartDelay(1000);
        stFactory.setName("progressPointtrigger");
        stFactory.setGroup("PointGroup");
        stFactory.setCronExpression("0 0 19 * * ?");
//        stFactory.setCronExpression("0 0/1 * * * ?");
        return stFactory;
    }

    //NeedGoalSetting by Jeff
    @Bean(name="NeedGoalSetting")
    public JobDetailFactoryBean getNeedGoalSetting(){
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(NeedGoalSetting.class);
        factory.setGroup("needGoalSettingGroup");
        factory.setName("needGoalSettingJob");
        return factory;
    }

    //Job is scheduled after every 9 AM
    @Bean(name="NeedGoalSettingCron")
    public CronTriggerFactoryBean needGoalSettingTriggerFactoryBean(){
        CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
        stFactory.setJobDetail(getNeedGoalSetting().getObject());
        stFactory.setStartDelay(1000);
        stFactory.setName("needGoalSettingtrigger");
        stFactory.setGroup("NeedSettingGroup");
        stFactory.setCronExpression("0 0 9 * * ?");
//        stFactory.setCronExpression("0 0/2 * * * ?");
        return stFactory;
    }

    //ReminderAgentDefaultGoal
    @Bean(name="ReminderAgentDefaultGoal")
    public JobDetailFactoryBean getReminderAgentDefaultGoal(){
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(ReminderAgentDefaultGoal.class);
        factory.setGroup("ReminderAgentDefaultGoalGroup");
        factory.setName("ReminderAgentDefaultGoalJob");
        return factory;
    }

    //Job is scheduled after every 9 AM
    @Bean(name="ReminderAgentDefaultGoalCron")
    public CronTriggerFactoryBean ReminderAgentDefaultGoalTriggerFactoryBean(){
        CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
        stFactory.setJobDetail(getReminderAgentDefaultGoal().getObject());
        stFactory.setStartDelay(1000);
        stFactory.setName("ReminderAgentDefaultGoaltrigger");
        stFactory.setGroup("ReminderAgentDefaultGoalGroup");
        stFactory.setCronExpression("0 0 9 * * ?");
        return stFactory;
    }

    //ReminderAgentDefaultGoal
    @Bean(name="ReminderAgentLeaderNoApprove")
    public JobDetailFactoryBean getReminderAgentLeaderNoApprove(){
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(ReminderAgentLeaderNoApprove.class);
        factory.setGroup("ReminderAgentLeaderNoApproveGroup");
        factory.setName("ReminderAgentLeaderNoApproveJob");
        return factory;
    }

    //Job is scheduled after every 9 AM
    @Bean(name="ReminderAgentLeaderNoApproveCron")
    public CronTriggerFactoryBean ReminderAgentLeaderNoApproveTriggerFactoryBean(){
        CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
        stFactory.setJobDetail(getReminderAgentLeaderNoApprove().getObject());
        stFactory.setStartDelay(1000);
        stFactory.setName("ReminderAgentLeaderNoApprovetrigger");
        stFactory.setGroup("ReminderAgentLeaderNoApproveGroup");
        stFactory.setCronExpression("0 0 9 * * ?");
        return stFactory;
    }

    //Verify by Jeff
    @Bean(name="Verify")
    public JobDetailFactoryBean getVerify(){
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(Verify.class);
        factory.setGroup("verifyGroup");
        factory.setName("verifyJob");
        return factory;
    }

    //Job is scheduled after every 12 o'clock
    @Bean(name="VerifyCron")
    public CronTriggerFactoryBean verifyTriggerFactoryBean(){
        CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
        stFactory.setJobDetail(getVerify().getObject());
        stFactory.setStartDelay(1000);
        stFactory.setName("verifytrigger");
        stFactory.setGroup("verifyGroup");
        stFactory.setCronExpression("0 0 9 * * ?");
//    	stFactory.setCronExpression("0 0/1 * * * ?");
        return stFactory;
    }
    //SysDataSaleMontPlanMonth by Jeff
    @Bean(name="SysDataSaleMonthPlanMonth")
    public JobDetailFactoryBean getSysDataSaleMonthPlanMonth(){
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(SysDataSaleMonthPlanMonth.class);
        factory.setGroup("sysDataSaleMonthPlanMonthGroup");
        factory.setName("sysDataSaleMonthPlanMonthJob");
        return factory;
    }

    //Job is scheduled after every 12 o'clock
    @Bean(name="SysDataSaleMonthPlanMonthCron")
    public CronTriggerFactoryBean sysDataSaleMonthPlanMonthTriggerFactoryBean(){
        CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
        stFactory.setJobDetail(getSysDataSaleMonthPlanMonth().getObject());
        stFactory.setStartDelay(1000);
        stFactory.setName("sysDataSaleMonthPlanMonthtrigger");
        stFactory.setGroup("sysDataSaleMonthPlanMonthGroup");
        stFactory.setCronExpression("0 0 0 * * ?");
//    	stFactory.setCronExpression("0 0/1 * * * ?");
        return stFactory;
    }

    /**
     * for neux test insert sample data
     */

    //SysDataSaleMontPlanMonth by Jeff
    @Bean(name="TestingInsertAgentDailyData")
    public JobDetailFactoryBean getTestingInsertAgentDailyData(){
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(TestingInsertAgentDailyData.class);
        factory.setGroup("TestingInsertAgentDailyDataGroup");
        factory.setName("TestingInsertAgentDailyDataJob");
        return factory;
    }


    @Bean(name="TestingInsertAgentDailyDataCron")
    public CronTriggerFactoryBean TestingInsertAgentDailyDataTriggerFactoryBean(){
        CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
        stFactory.setJobDetail(getTestingInsertAgentDailyData().getObject());
        stFactory.setStartDelay(1000);
        stFactory.setName("TestingInsertAgentDailyDatatrigger");
        stFactory.setGroup("TestingInsertAgentDailyDataGroup");
        stFactory.setCronExpression("0 0 12 * * ?");
//        stFactory.setCronExpression("0 0/1 * 1/1 * ? *");

        return stFactory;
    }



    //CalculationAgencyPlan by Jeff
    @Bean(name="CalculationTeamData")
    public JobDetailFactoryBean getCalculationTeamData(){
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(CalculationTeamData.class);
        factory.setGroup("calculationTeamDataGroup");
        factory.setName("calculationTeamDataJob");
        return factory;
    }

    //Job is scheduled after every 3 o'clock
    @Bean(name="CalculationTeamDataCron")
    public CronTriggerFactoryBean calculationTeamDataCronTriggerFactoryBean(){
        CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
        stFactory.setJobDetail(getCalculationTeamData().getObject());
        stFactory.setStartDelay(1000);
        stFactory.setName("calculationTeamDatatrigger");
        stFactory.setGroup("calculationTeamDataGroup");
        stFactory.setCronExpression("0 0 3 * * ?");
//        stFactory.setCronExpression("0 0/1 * * * ?");
        return stFactory;
    }



    //CalculationProgress by Jeff
    @Bean(name="CalculationPersonalProgress")
    public JobDetailFactoryBean getCalculationPersonalProgress(){
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(CalculationPersonalProgress.class);
        factory.setGroup("calculationPersonalProgressGroup");
        factory.setName("calculationPersonalProgressJob");
        return factory;
    }

    //Job is scheduled after every 7 o'clock
    @Bean(name="CalculationPersonalProgressCron")
    public CronTriggerFactoryBean calculationPersonalProgressTriggerFactoryBean(){
        CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
        stFactory.setJobDetail(getCalculationPersonalProgress().getObject());
        stFactory.setStartDelay(1000);
        stFactory.setName("calculationPersonalProgresstrigger");
        stFactory.setGroup("calculationPersonalProgressGroup");
        stFactory.setCronExpression("0 0 4 * * ?");
//        stFactory.setCronExpression("0 0/1 * * * ?");
        return stFactory;
    }
    
    //DataPrivacyProtection 
    @Bean(name="DataPrivacyProtection")
    public JobDetailFactoryBean getDataPrivacyProtection(){
    	JobDetailFactoryBean factory = new JobDetailFactoryBean();
    	factory.setJobClass(DataPrivacyProtection.class);
    	factory.setGroup("DataPrivacyProtectionGroup");
    	factory.setName("DataPrivacyProtectionJob");
    	return factory;
    }
    
    //Job is scheduled Push time: 1/15, 4/15, 7/15, 10/15, AM 10:00 on the same day
    @Bean(name="DataPrivacyProtectionCron")
    public CronTriggerFactoryBean dataPrivacyProtectionTriggerFactoryBean(){
    	CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
    	stFactory.setJobDetail(getDataPrivacyProtection().getObject());
    	stFactory.setStartDelay(1000);
    	stFactory.setName("DataPrivacyProtectiontrigger");
    	stFactory.setGroup("DataPrivacyProtectionGroup");
    	stFactory.setCronExpression("0 0 10 15 1,4,7,10 ?");
    	return stFactory;
    }
}