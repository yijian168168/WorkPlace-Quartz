package com.quartz.service;

import com.google.common.base.Throwables;
import com.quartz.job.JobDataContext;
import com.quartz.model.QuartzDto;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 定时任务操作类
 * <p>
 * Created by ZhangQingrong on 2017/2/27.
 */
@Slf4j
@Component
public class JobService {

    @Resource
    private SchedulerFactoryBean fasSchedulerFactoryBean;


    /**
     * 加载所有的定时任务
     */
    public void loadAllJob(Map<String,QuartzDto> quartzDtoMap) {
        //读数据库中的任务
        for (QuartzDto quartzDto : quartzDtoMap.values()){
            addJob(quartzDto);
        }
    }

    /**
     * 加载一个定时任务
     */
    public void addJob(QuartzDto quartzDto) {
        try {
            JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) Class.forName(quartzDto.getJobClass()))
                    .withIdentity(quartzDto.getJobName(), quartzDto.getJobGroup()).build();
            jobDetail.getJobDataMap().put("scheduleJob", quartzDto);

            //创建CronTrigger
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzDto.getJobCorn());
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(quartzDto.getJobName(), quartzDto.getJobGroup())
                    .withSchedule(scheduleBuilder).build();

            fasSchedulerFactoryBean.getScheduler().scheduleJob(jobDetail, trigger);
        } catch (ClassNotFoundException e) {
            log.error("ClassNotFound , 请确认[{}]是否存在，Exception=[{}] .", quartzDto.getJobClass(), Throwables.getStackTraceAsString(e));
        } catch (SchedulerException e) {
            log.error("初始化定时任务[{}]失败，Exception=[{}] .", quartzDto.getJobClass(), Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * 删除一个定时任务
     */
    public void deleteJob(QuartzDto quartzDto) {
        try {
            JobKey jobKey = JobKey.jobKey(quartzDto.getJobName(), quartzDto.getJobGroup());
            fasSchedulerFactoryBean.getScheduler().deleteJob(jobKey);
        } catch (SchedulerException e) {
            log.error("delete job [{},{}] fail , exception= [{}]", quartzDto.getJobName(), quartzDto.getJobGroup(), Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * 更新一个定时任务
     */
    public void updateJob(QuartzDto quartzDto) {
        deleteJob(quartzDto);
        addJob(quartzDto);
    }

    /**
     * 立即执行一次定时任务
     */
    public void executeJobOnce(QuartzDto quartzDto) {
        try {
            JobKey jobKey = JobKey.jobKey(quartzDto.getJobName(), quartzDto.getJobGroup());
            fasSchedulerFactoryBean.getScheduler().triggerJob(jobKey);
        } catch (SchedulerException e) {
            log.error("execute job [{},{}] fail , exception= [{}]", quartzDto.getJobName(), quartzDto.getJobGroup(), Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * 查询所有正在执行的定时任务
     */
    public List<QuartzDto> queryAllExecutingJob() {
        List<QuartzDto> quartzDtos = new ArrayList<QuartzDto>();
        try {
            Scheduler scheduler = fasSchedulerFactoryBean.getScheduler();
            List<JobExecutionContext> currentlyExecutingJobs = scheduler.getCurrentlyExecutingJobs();
            for (JobExecutionContext jobExecutionContext : currentlyExecutingJobs) {
                JobDetail jobDetail = jobExecutionContext.getJobDetail();
                QuartzDto scheduleJob = (QuartzDto) jobDetail.getJobDataMap().get("scheduleJob");
                Trigger trigger = jobExecutionContext.getTrigger();
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                scheduleJob.setStat(triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    scheduleJob.setJobCorn(cronTrigger.getCronExpression());
                }
                quartzDtos.add(scheduleJob);
            }
        } catch (SchedulerException e) {
            log.error("query all jobs fail , exception= [{}]", Throwables.getStackTraceAsString(e));
        }
        return quartzDtos;
    }

    /**
     * 查询所有定时任务
     * */
    public List<QuartzDto> queryAllJob(){
        List<QuartzDto> quartzDtos = new ArrayList<QuartzDto>();
        for (QuartzDto quartzDto : JobDataContext.getAllQuartzDto().values()){
            quartzDtos.add(quartzDto);
        }
        return quartzDtos;
    }


}
