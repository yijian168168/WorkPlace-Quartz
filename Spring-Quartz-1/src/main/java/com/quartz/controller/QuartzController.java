package com.quartz.controller;

import com.quartz.model.QuartzDto;
import com.quartz.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by ZhangQingrong on 2017/2/27.
 */
@Controller
public class QuartzController {

    @Autowired
    private JobService jobService;

    @ResponseBody
    @RequestMapping("/addJob")
    public String addJob(QuartzDto quartzDto) {
        jobService.addJob(quartzDto);
        return "list-schedule-job";
    }

    /**
     * 任务列表页
     */
    @RequestMapping(value = "list-schedule-job")
    public String queryAllJob(ModelMap modelMap) {
        List<QuartzDto> scheduleJobVoList = jobService.queryAllJob();
        modelMap.put("scheduleJobVoList", scheduleJobVoList);
        List<QuartzDto> executingJobList = jobService.queryAllExecutingJob();
        modelMap.put("executingJobList", executingJobList);
        return "list-schedule-job";
    }

    /**
     * 运行一次
     *
     * @return
     */
    @RequestMapping(value = "run-once-schedule-job")
    public String runOnceScheduleJob(String jobGroup, String jobName) {
        QuartzDto quartzDto = new QuartzDto();
        quartzDto.setJobGroup(jobGroup);
        quartzDto.setJobName(jobName);
        jobService.executeJobOnce(quartzDto);
        return "redirect:list-schedule-job.shtml";
    }
}
