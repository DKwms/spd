package org.pguide.adapter.wifi.config.quartz;

import org.pguide.adapter.wifi.config.CustomJobFactory;
import org.pguide.adapter.wifi.config.quartz.job.CheckPathAndFindCardsJob;
import org.pguide.adapter.wifi.config.quartz.job.FIndCardTaskJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author DKwms
 * @Date 2023/12/11 22:29
 * @description 任务调度器配置
 */
@Component
public class SchedulerConfig {

    @Autowired
    private CustomJobFactory customJobFactory;


    /**
     * 调度器
     * @return
     */
    @Bean(name = "wifiScheduler")
    public Scheduler schedulerBean() {
        JobDetail findcardJobDetail = JobBuilder.newJob(FIndCardTaskJob.class)
                .withIdentity("findcardjob", "group1")
                .usingJobData("result", "")
                .usingJobData("count",0)
                .build();

        JobDetail checkPathAndFindCardsJobDetail = JobBuilder.newJob(CheckPathAndFindCardsJob.class)
                .withIdentity("checkPathAndFindCardsJob", "group1")
                .usingJobData("result", "")
                .usingJobData("count",0)
                .usingJobData("path",1)
                .build();

        CronTrigger findcardTrigger = TriggerBuilder.newTrigger()
                .withIdentity("findCardTrigger", "triggerGroup1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ?"))
                .build();

        CronTrigger checkPathAndFindCardsTrigger = TriggerBuilder.newTrigger()
                .withIdentity("checkPathAndFindCardsTrigger", "triggerGroup1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ?"))
                .build();

        Scheduler schedule = null;
        try {
            schedule = StdSchedulerFactory.getDefaultScheduler();
            schedule.setJobFactory(customJobFactory);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }

        try {
            schedule.scheduleJob(findcardJobDetail,findcardTrigger);
            schedule.scheduleJob(checkPathAndFindCardsJobDetail,checkPathAndFindCardsTrigger);
            schedule.start();
            schedule.pauseJob(JobKey.jobKey("findcardjob","group1"));
            schedule.pauseJob(JobKey.jobKey("checkPathAndFindCardsJob","group1"));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }

        return schedule;
    }


}
