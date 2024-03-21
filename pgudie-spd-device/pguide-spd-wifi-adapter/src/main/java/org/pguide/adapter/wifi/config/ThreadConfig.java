package org.pguide.adapter.wifi.config;

import org.pguide.adapter.wifi.common.thread.SpdTheadPools;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author DKwms
 * @Date 2023/12/17 17:58
 * @description
 */
@Configuration
public class ThreadConfig {
    @Bean
    public SpdTheadPools initThreadPools(){
        return new SpdTheadPools();
    }

}
