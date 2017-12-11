package com.quartz.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by ZhangQingrong on 2017/2/24.
 */
public class QuartzTest {

    @Test
    public void test(){
        System.out.println("spring context 开始加载 .");
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
        System.out.println("spring context 加载完成 ." + applicationContext);
        try {
            Thread.sleep(100000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
