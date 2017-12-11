package com.quartz.model;

import lombok.Data;

import java.util.Date;

/**
 * 定时任务
 *
 * Created by ZhangQingrong on 2017/2/23.
 */
@Data
public class QuartzDto {

    /**
     * id
     * */
    private String id;
    /**
     * 定时任务名
     * */
    private String jobName;
    /**
     * 定时任务组
     * */
    private String jobGroup;
    /**
     * 定时任务状态 0禁用 1启用 2删除
     * */
    private String jobStatus;
    /**
     * 任务运行时间表达式
     * */
    private String jobCorn;
    /**
     * job执行类,全类名
     * */
    private String jobClass;
    /**
     * 任务描述
     */
    private String desc;
    /**
     * 创建人
     * */
    private String createdBy;
    /**
     * 创建日期
     * */
    private Date createdAt;
    /**
     * 修改人
     * */
    private String updateBy;
    /**
     * 更新日期
     * */
    private Date updateAt;
    /**
     * 状态
     * */
    private String stat;
}
