package com.quartz.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * Created by ZhangQingrong on 2017/2/21.
 */
@DisallowConcurrentExecution
public class Job1 implements Job {

    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println(new Date() + "job 1 start......");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(new Date() + "job 1 end......");
    }
}
