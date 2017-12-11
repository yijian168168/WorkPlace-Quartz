package com.springtask;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 定时任务
 * Created by ZhangQingrong on 2017/2/27.
 */
@Slf4j
@Service
public class MyJob {

    @Scheduled(fixedRateString = "5000")
//    @Scheduled(fixedDelayString = "5000")
//    @Scheduled(cron = "0/10 * * * * ?")
    public void mytask(){
        log.info("￥￥￥￥￥￥￥   myjob ￥￥￥￥￥￥￥￥  ：  " + DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss:SSS"));
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
