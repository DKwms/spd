package org.pguide.adapter.wifi.controller;


import org.pguide.adapter.wifi.common.core.result.JsonResult;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author DKwms
 * @Date 2023/12/12 9:44
 * @description 调度控制器
 */


@RestController
@RequestMapping("/api/spd/adapter/wifi/task")
public class TaskController {

    @Autowired
    @Qualifier("wifiScheduler")
    Scheduler scheduler;

    /**
     * 暂停当前所有的调度值
     * @return
     * @throws SchedulerException
     */
    @RequestMapping("/close")
    public JsonResult<Void> shutdown() throws SchedulerException {
        theWorld();
        return JsonResult.success();
    }

    /**
     * 开启调度，默认是寻卡模式
     * @return
     * @throws SchedulerException
     */
    @RequestMapping("/start")
    public JsonResult<Void> start() throws SchedulerException {
        theWorld();
        scheduler.resumeJob(JobKey.jobKey("findcardjob","group1"));
        //scheduler.start();
        return JsonResult.success();
    }

    /**
     * 开启切换轮询
     */
    @RequestMapping("/start/cfc")
    public JsonResult<Void> checkAndFindCards() throws SchedulerException {
        theWorld();
        // 进行轮询切换
        scheduler.resumeJob(JobKey.jobKey("checkPathAndFindCardsJob","group1"));
        return JsonResult.success();
    }

    /**
     * 关闭切换轮询
     */
    @RequestMapping("/close/cfc")
    public JsonResult<Void> checkAdnFindCardsClose() throws SchedulerException {
        scheduler.pauseJob(JobKey.jobKey("checkPathAndFindCardsJob","group1"));
        return JsonResult.success();
    }


    public void theWorld(){
        List<JobExecutionContext> jobContextList = null;
        try {
            jobContextList = scheduler.getCurrentlyExecutingJobs();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }

        for (JobExecutionContext jc : jobContextList) {
            JobKey jobKey = jc.getTrigger().getJobKey();
            try {
                scheduler.pauseJob(jobKey);
            } catch (SchedulerException e) {

                throw new RuntimeException(e);
            }
        }
    }
}
