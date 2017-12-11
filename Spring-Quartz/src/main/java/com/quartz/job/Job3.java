package com.quartz.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by ZhangQingrong on 2017/2/21.
 */
@Component
@DisallowConcurrentExecution
public class Job3 implements Job {

    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println(new Date() + "job 3 start......");
        try {
            Thread.sleep(1800000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(new Date() + "job 3 end......");
    }
}
