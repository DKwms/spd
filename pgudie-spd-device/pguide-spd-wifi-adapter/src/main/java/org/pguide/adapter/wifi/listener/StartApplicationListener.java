package org.pguide.adapter.wifi.listener;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author DKwms
 * @Date 2023/12/18 14:04
 * @description
 */

@Component
@Slf4j
public class StartApplicationListener implements ApplicationListener<ContextRefreshedEvent> {


    @Value("${pguide.spd.centerurl}")
    private String CENTER_URL;

    @Value("${pguide.spd.device.ip}")
    private String PGUIDE_SPD_DEVICE_IP;

    @Value("${pguide.spd.device.name}")
    private String PGUIDE_SPD_DEVICE_NAME;

    @Value("${pguide.spd.device.type}")
    private String PGUIDE_SPD_DEVICE_TYPE;

    @Value("${pguide.spd.device.group}")
    private String PGUIDE_SPD_DEVICE_GROUP;

    @Value("${pguide.spd.device.status}")
    private String PGUIDE_SPD_DEVICE_STATUS;

    @Value("${pguide.spd.device.retry}")
    private String PGUIDE_SPD_DEVICE_RETRY;

    @Value("${pguide.spd.device.place}")
    private String PGUIDE_SPD_DEVICE_PLACE;

    @Value("${pguide.spd.device.n}")
    private String PGUIDE_SPD_DEVICE_N;

    @Value("${pguide.spd.device.e}")
    private String PGUIDE_SPD_DEVICE_E;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        // TODO 连接失败重试次数
        int retry = Integer.parseInt(PGUIDE_SPD_DEVICE_RETRY+"");

        RestTemplate restTemplate = new RestTemplate();
        JSONObject paramEntity = new JSONObject();
        paramEntity.put("ip",PGUIDE_SPD_DEVICE_IP);
        paramEntity.put("name",PGUIDE_SPD_DEVICE_NAME);
        paramEntity.put("type",PGUIDE_SPD_DEVICE_TYPE);
        paramEntity.put("group",PGUIDE_SPD_DEVICE_GROUP);
        paramEntity.put("place",PGUIDE_SPD_DEVICE_PLACE);
        paramEntity.put("status",PGUIDE_SPD_DEVICE_STATUS);
        paramEntity.put("n", PGUIDE_SPD_DEVICE_N);
        paramEntity.put("e", PGUIDE_SPD_DEVICE_E);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.valueOf("application/json;UTF-8"));
        HttpEntity<String> stringHttpEntity = new HttpEntity<>(paramEntity.toString(),httpHeaders );

        try{
            ResponseEntity<String> exchange = restTemplate.exchange(CENTER_URL, HttpMethod.POST, stringHttpEntity, String.class);
        }catch (Exception e){
            log.error("连接中心服务器失败");
        }
        // TODO 异常处理

    }
}
