package org.pguide.adapter.wifi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author DKwms
 * @Date 2023/12/16 10:49
 * @description
 */

@SpringBootApplication
@EnableScheduling
public class WIFIAdapterApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext run = SpringApplication.run(WIFIAdapterApplication.class, args);

    }
}
