package com.quartz.test.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

/**
 * Created by ZhangQingrong on 2017/2/24.
 */
public class Job2 implements StatefulJob {

    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("job 2 start ......");
        try {
            Thread.sleep(1000000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
