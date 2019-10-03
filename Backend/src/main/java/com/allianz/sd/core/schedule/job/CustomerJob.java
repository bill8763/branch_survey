package com.allianz.sd.core.schedule.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
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
public class CustomerJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println("CustomerJob Start:"+Thread.currentThread().getId());


//        try {
//            Thread.sleep(1000 * 10);
//        } catch (InterruptedException e) {

//
//            e.printStackTrace();
//        }
        System.out.println("Job1 End");
    }
}
