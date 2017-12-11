package com.quartz.test.thread;

/**
 * Created by ZhangQingrong on 2017/2/24.
 */
public class TaskThread implements Runnable{

    private String jobName;

    public void run() {
        System.out.println("new thread start , jobname: " + jobName);
        try {
            Thread.sleep(100000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
}
