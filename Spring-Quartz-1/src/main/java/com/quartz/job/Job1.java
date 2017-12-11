package com.quartz.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 查询订单状态 定时任务
 *
 * Created by ZhangQingrong on 2017/2/23.
 */
@DisallowConcurrentExecution
public class Job1 implements Job{
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("job 1 .....");
    }
}
