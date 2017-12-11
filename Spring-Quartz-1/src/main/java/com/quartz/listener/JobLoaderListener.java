package com.quartz.listener;

import com.google.common.base.Throwables;
import com.quartz.job.JobDataContext;
import com.quartz.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * 定时任务加载器
 * Created by ZhangQingrong on 2017/2/23.
 */
@Slf4j
@Component
public class JobLoaderListener implements LeaderLatchListener {

    @Autowired
    private JobService jobService;
    @Resource
    private SchedulerFactoryBean fasSchedulerFactoryBean;

    @Override
    public void isLeader() {
        log.info("this ip is leader now , start to load quartz ");
        try {
            jobService.loadAllJob(JobDataContext.getAllQuartzDto());
            fasSchedulerFactoryBean.start();
        } catch (Exception e) {
            log.error("start schedulerJob throws unknow exception :{}", Throwables.getStackTraceAsString(e));
        }
    }

    @Override
    public void notLeader() {
        log.info("this ip is not leader now , the quartz will shutdown .");
        try {
            fasSchedulerFactoryBean.destroy();
        } catch (SchedulerException e) {
            log.error("shutdown schedulerJob throws unknow exception :{}", Throwables.getStackTraceAsString(e));
        }
    }

}
