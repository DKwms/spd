package org.pguide.spd.center.core.controller.spd;


import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.pguide.spd.common.web.result.core.result.JsonResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author DKwms
 * @Date 2023/12/19 11:23
 * @description
 * 基础操作转发
 */


@RestController
@RequestMapping("api/pguide/spd/center/wifi/command")
@Slf4j
public class SpdWifiAdapterCommandController {

    @Value("${pguide.spd.wifi.apipath.command}")
    String WIFI_ADAPTER_BASE_API_PATH;

    /**
     * 转发消息到指定设备
     * @param ip
     * @param num
     * @return
     */
    @GetMapping("/checkpath")
    public JsonResult<Void> checkPath(@RequestParam String ip, @RequestParam String num){

        RestTemplate restTemplate = new RestTemplate();

        JSONObject paramEntity = new JSONObject();

        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> stringHttpEntity = new HttpEntity<>(paramEntity.toString(),httpHeaders );

        try{
            ResponseEntity<String> exchange = restTemplate.exchange(ip+WIFI_ADAPTER_BASE_API_PATH+"/checkpath?num="+num, HttpMethod.GET, stringHttpEntity, String.class);
        }catch (Exception e){
            log.error("连接adapter设备失败");
            return JsonResult.error("内部adapter访问异常");
        }

        return JsonResult.success();
    }

    @GetMapping("/findcard")
    public JsonResult<String> findCard(@RequestParam String ip){

        RestTemplate restTemplate = new RestTemplate();

        JSONObject paramEntity = new JSONObject();

        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> stringHttpEntity = new HttpEntity<>(paramEntity.toString(),httpHeaders );

        try{
            ResponseEntity<String> exchange = restTemplate.exchange(ip+WIFI_ADAPTER_BASE_API_PATH+"/findcard", HttpMethod.GET, stringHttpEntity, String.class);
            String body = exchange.getBody();
            return JsonResult.success(body);
        }catch (Exception e){
            log.error("连接adapter设备失败");
            return JsonResult.error("内部adapter访问异常");
        }
    }





}
