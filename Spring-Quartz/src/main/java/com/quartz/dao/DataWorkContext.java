package com.quartz.dao;

import com.quartz.model.ScheduleJob;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangQingrong on 2017/2/21.
 */
public class DataWorkContext {

    /**
     * 计划任务map
     */
    private static List<ScheduleJob> scheduleJobs = new ArrayList<ScheduleJob>();

    static {
        for (int i = 1; i < 2; i++) {
            ScheduleJob job = new ScheduleJob();
            job.setJobId("10001" + i);
            job.setJobName("data_import" + i);
            job.setJobGroup("dataWork");
            job.setJobStatus("1");
            job.setCronExpression("0 0/5 * * * ?");
//            job.setCronExpression("0/3 * * * * ?");
            job.setClassName("com.quartz.job.Job" + i);
            job.setDesc("数据导入任务");
            scheduleJobs.add(job);
        }
    }

    public static List<ScheduleJob> getScheduleJobs() {
        return scheduleJobs;
    }
}
