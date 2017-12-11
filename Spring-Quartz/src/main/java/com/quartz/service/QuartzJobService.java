package com.quartz.service;

import com.quartz.dao.DataWorkContext;
import com.quartz.job.Job3;
import com.quartz.model.ScheduleJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * Created by ZhangQingrong on 2017/2/21.
 */
@Component
public class QuartzJobService {

    @Autowired
    @Qualifier("schedulerFactoryBean")
    SchedulerFactoryBean schedulerFactoryBean;
    @Autowired
    private MethodInvokingJobDetailFactoryBean jobDetailFactoryBean;

//    @PostConstruct
    public void init() throws SchedulerException, ClassNotFoundException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        for (ScheduleJob scheduleJob : DataWorkContext.getScheduleJobs()) {
            //创建JobDetail
            JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) Class.forName(scheduleJob.getClassName()))
                    .withIdentity(scheduleJob.getJobName(), scheduleJob.getJobGroup()).build();
            jobDetail.getJobDataMap().put("scheduleJob", scheduleJob);

            //创建CronTrigger
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
//                    .withMisfireHandlingInstructionDoNothing();
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(scheduleJob.getJobName(), scheduleJob.getJobGroup())
                    .withSchedule(scheduleBuilder)
                    .startAt(new Date())
                    .build();

            Date startTime = trigger.getStartTime();
            System.out.println("misfire : " + trigger.getMisfireInstruction());

            scheduler.scheduleJob(jobDetail, trigger);
        }
        scheduler.start();
    }

    /**
     * 刷新定时任务
     */
    @PostConstruct
    public void refresh() throws SchedulerException {

        JobDetail object = jobDetailFactoryBean().getObject();
        CronTrigger cornTrigger = getTrigger().getObject();
        getTrigger().setStartTime(new Date());
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduler.scheduleJob(object,cornTrigger);
    }

    @Bean(name="jobDetailFactoryBean")
    @Scope("prototype")
    public MethodInvokingJobDetailFactoryBean jobDetailFactoryBean(){
        MethodInvokingJobDetailFactoryBean jobDetailFactoryBean = new MethodInvokingJobDetailFactoryBean();
        jobDetailFactoryBean.setTargetClass(Job3.class);
        jobDetailFactoryBean.setTargetMethod("execute");
        return jobDetailFactoryBean;
    }

    @Bean(name="getTrigger")
    @Scope("prototype")
    public CronTriggerFactoryBean getTrigger(){
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(jobDetailFactoryBean().getObject());
        cronTriggerFactoryBean.setCronExpression("0 0/5 * * * ?");
        return cronTriggerFactoryBean;
    }


}
