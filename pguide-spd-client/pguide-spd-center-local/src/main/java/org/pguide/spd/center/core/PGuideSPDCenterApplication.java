package org.pguide.spd.center.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author DKwms
 * @Date 2023/12/18 14:53
 * @description
 */
@SpringBootApplication
@Slf4j
public class PGuideSPDCenterApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(PGuideSPDCenterApplication.class, args);
        log.info("启动成功");
    }
}
