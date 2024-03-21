package org.pguide.spd.center.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author DKwms
 * @Date 2023/12/27 23:01
 * @description
 */

@SpringBootApplication
public class PGuideSPDCloudClientApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(PGuideSPDCloudClientApplication.class, args);
    }

}
