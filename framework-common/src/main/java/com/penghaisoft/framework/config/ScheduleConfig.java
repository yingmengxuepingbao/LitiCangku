package com.penghaisoft.framework.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;

/**
 *
 * @Description ScheduleConfig
 * @Auther zhangxu
 * @Date 2019/12/6 11:35
 **/
@Slf4j
@Configuration
public class ScheduleConfig implements SchedulingConfigurer {

    @Value("${jobs.pool}")
    private Integer poolSize;
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        log.info("设置定时任务多线程 poolSize="+poolSize);
        scheduledTaskRegistrar.setScheduler(Executors.newScheduledThreadPool(poolSize));
    }
}
