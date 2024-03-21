package org.pguide.adapter.wifi.controller;

import lombok.extern.slf4j.Slf4j;
import org.pguide.adapter.wifi.common.core.result.JsonResult;
import org.pguide.adapter.wifi.controller.vo.QuartzJobsVo;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author DKwms
 * @Date 2023/12/19 9:28
 * @description
 */

@RestController
@Slf4j
@RequestMapping("/api/spd/adapter/wifi/scheduler")
public class SchedulerController {

    @Autowired
    @Qualifier("wifiScheduler")
    Scheduler scheduler;

    /**
     * 停止调度
     *
     *  name | group
     */
    @GetMapping("/stop")
    public JsonResult<Void> stopJob(String name,String group){
        try {
            scheduler.pauseJob(JobKey.jobKey(name,group));
        } catch (SchedulerException e) {
            log.error("调度停止失败，name:{},group:{}",name,group);
            return JsonResult.error("调度停止失败");
        }
        // 请求调度
        log.info("调度停止成功，name:{},group:{}",name,group);
        return JsonResult.success("调度停止成功");
    }

    /**
     * 开始调度
     */
    @GetMapping("/start")
    public JsonResult startJob(String name,String group){
        try {
            scheduler.resumeJob(JobKey.jobKey(name,group));
        } catch (SchedulerException e) {
            log.error("调度停止失败，name:{},group:{}",name,group);
            return JsonResult.error("调度停止失败");
        }
        // 请求调度
        log.info("调度停止成功，name:{},group:{}",name,group);
        return JsonResult.success();

    }

    /**
     * 当前调度池
     */
    @GetMapping("/showAll")
    public JsonResult<ArrayList<JobKey>> allJob(){
        List<JobExecutionContext> jobContextList = null;
        try {
            jobContextList = scheduler.getCurrentlyExecutingJobs();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }

        ArrayList<JobKey> result = new ArrayList<>();
        for (JobExecutionContext jc : jobContextList) {
            JobKey jobKey = jc.getTrigger().getJobKey();
            result.add(jobKey);
        }

        return JsonResult.success(result);
    }

    /**
     * 查看执行调度
     *
     * [{
     *     name:
     *     group:
     *     cronExpression:
     *     status:
     * }]
     */
    @GetMapping("/showWork")
    public JsonResult<List<QuartzJobsVo>>  workStatus(){
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        Set<JobKey> jobKeys = null;
        List<QuartzJobsVo> result = new ArrayList();
        try {
            jobKeys = scheduler.getJobKeys(matcher);
            for (JobKey jobKey : jobKeys) {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {

                    QuartzJobsVo tempVo = QuartzJobsVo.builder()
                            .name(jobKey.getName())
                            .group(jobKey.getGroup())
                            .status(scheduler.getTriggerState(trigger.getKey()).name())
                            .cronExpression(trigger.getKey().toString()).build();
                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        String cronExpression = cronTrigger.getCronExpression();
                        tempVo.setCronExpression(cronExpression);
                    }
                    result.add(tempVo);
                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        return JsonResult.success(result);
    }

}
