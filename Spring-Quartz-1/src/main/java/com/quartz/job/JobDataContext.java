package com.quartz.job;

import com.quartz.model.QuartzDto;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZhangQingrong on 2017/2/27.
 */
public class JobDataContext {

    private static Map<String, QuartzDto> quartzDtoMap = new HashMap<String, QuartzDto>();

    static {
        QuartzDto quartzDto = new QuartzDto();
        quartzDto.setJobName("quartz-1");
        quartzDto.setJobGroup("quartz-test");
        quartzDto.setJobCorn("0/30 * * * * ? *");
        quartzDto.setJobClass("com.quartz.job.Job1");
        quartzDto.setDesc("定时任务：job1");
        addQuartzDto(quartzDto);
    }

    public static void addQuartzDto(QuartzDto quartzDto) {
        String key = quartzDto.getJobGroup() + "_" + quartzDto.getJobName();
        quartzDtoMap.put(key, quartzDto);
    }

    public static QuartzDto getQuartzDto(String group, String name) {
        return quartzDtoMap.get(group + "_" + name);
    }

    public static Map<String, QuartzDto> getAllQuartzDto() {
        return quartzDtoMap;
    }


}
