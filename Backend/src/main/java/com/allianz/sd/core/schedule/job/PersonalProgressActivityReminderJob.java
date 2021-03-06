package com.allianz.sd.core.schedule.job;

import com.allianz.sd.core.service.ActivityService;
import com.allianz.sd.core.service.AgentDataService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/1/22
 * Time: 下午 8:38
 * To change this template use File | Settings | File Templates.
 */
@Component
public class PersonalProgressActivityReminderJob extends QuartzJobBean {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private AgentDataService agentDataService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {



//        activityService.calcPushProgressActivityMessage();
    }
}
