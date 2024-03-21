package org.pguide.adapter.wifi.listener;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * @author DKwms
 * @Date 2023/12/16 21:44
 * @description
 */

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DisposableListener {

    @Autowired
    @Qualifier("wifiScheduler")
    Scheduler scheduler;


    @PreDestroy
    public void destroy(){
        try {
            scheduler.clear();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
}
