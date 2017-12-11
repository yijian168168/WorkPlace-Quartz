package com.quartz.zookeeper;

import com.quartz.listener.JobLoaderListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by ZhangQingrong on 2017/2/23.
 */
@Slf4j
@Component
public class ZookeeperLeaderLatch {

    /**
     * leader选举节点
     * */
    private String quartzZookeeperLockPath = "/rootPath/billPayChildPathFas_6";
    /**
     * zookeeper地址
     * */
    private String zookeeperUrl = "172.26.7.23:2181";
    @Autowired
    private JobLoaderListener jobLoaderListener;

    /**
     * 定时任务 leader选举
     * */
    @PostConstruct
    public void quartzLeaderLatch() throws Exception {
        log.info("start to latch leader ....");
        String id = RandomStringUtils.random(14, false, true);
        ExponentialBackoffRetry retry =  new ExponentialBackoffRetry(1000, 3);
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(zookeeperUrl, retry);
        curatorFramework.start();
        LeaderLatch leaderLatch = new LeaderLatch(curatorFramework, quartzZookeeperLockPath, id);
        leaderLatch.addListener(jobLoaderListener);
        leaderLatch.start();
    }
}
