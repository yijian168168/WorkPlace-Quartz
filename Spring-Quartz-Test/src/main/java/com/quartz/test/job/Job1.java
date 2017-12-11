package com.quartz.test.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ZhangQingrong on 2017/2/24.
 */
public class Job1 implements StatefulJob {

    int times = 0;

    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println(new Date() + "  job 1 start ......");
        SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            if (new Date().before(yyyyMMddHHmmss.parse("20170224142200"))) {
                Thread.sleep(65000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
