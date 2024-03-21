package org.pguide.spd.center.core.controller.spd;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.pguide.spd.common.web.result.core.result.JsonResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author DKwms
 * @Date 2023/12/17 22:33
 * @description
 */

//TODO 抽取service

@RestController
@RequestMapping("api/pguide/spd/center/wifi")
@Slf4j
public class SpdWifiAdapterController {

    @Value("${pguide.spd.wifi.apipath.scheduler}")
    String WIFI_ADAPTER_BASE_API_PATH;

    /**
     * 停止调度
     */
    @GetMapping("/stop")
    public JsonResult<Void> stopJob(String name, String group, String ip){
        RestTemplate restTemplate = new RestTemplate();

        JSONObject paramEntity = new JSONObject();
        // 参数填充
        paramEntity.put("name",name);
        paramEntity.put("group",group);


        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.valueOf("application/json;UTF-8"));
        HttpEntity<String> stringHttpEntity = new HttpEntity<>(paramEntity.toString(),httpHeaders );

        try{
            ResponseEntity<String> exchange = restTemplate.exchange(ip+WIFI_ADAPTER_BASE_API_PATH+"/stop?name="+name+"&group="+group, HttpMethod.POST, stringHttpEntity, String.class);
        }catch (Exception e){
            log.error("连接adapter设备失败");
            return JsonResult.error("调度失败");
        }
        return JsonResult.success();
    }

    /**
     * 开始调度
     */
    @GetMapping("/start")
    public JsonResult<Void> startJob(String name,String group,String ip){

        RestTemplate restTemplate = new RestTemplate();

        JSONObject paramEntity = new JSONObject();
        // 参数填充
        paramEntity.put("name",name);
        paramEntity.put("group",group);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.valueOf("application/json;UTF-8"));
        HttpEntity<String> stringHttpEntity = new HttpEntity<>(paramEntity.toString(),httpHeaders );

        try{
            ResponseEntity<String> exchange = restTemplate.exchange(ip+WIFI_ADAPTER_BASE_API_PATH+"/start?name="+name+"&group="+group, HttpMethod.POST, stringHttpEntity, String.class);
        }catch (Exception e){
            log.error("连接adapter设备失败");
            return JsonResult.error("调度失败");
        }

        return JsonResult.success();
    }

    /**
     * 当前调度池
     */
    @GetMapping("/showAll")
    public JsonResult<Void> allJob(String ip){

        RestTemplate restTemplate = new RestTemplate();

        JSONObject paramEntity = new JSONObject();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.valueOf("application/json;UTF-8"));
        HttpEntity<String> stringHttpEntity = new HttpEntity<>(paramEntity.toString(),httpHeaders );

        try{
            ResponseEntity<String> exchange = restTemplate.exchange(ip+WIFI_ADAPTER_BASE_API_PATH+"/showAll", HttpMethod.GET, stringHttpEntity, String.class);

            // to JSON
            String body = exchange.getBody();

        }catch (Exception e){
            log.error("连接adapter设备失败");
            return JsonResult.error("调度失败");
        }
        return JsonResult.success();
    }

    /**
     * 查看执行调度
     */
    @GetMapping("/showWork")
    public JsonResult<Void>  workStatus(String ip){


        RestTemplate restTemplate = new RestTemplate();

        JSONObject paramEntity = new JSONObject();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.valueOf("application/json;UTF-8"));
        HttpEntity<String> stringHttpEntity = new HttpEntity<>(paramEntity.toString(),httpHeaders );

        try{
            ResponseEntity<String> exchange = restTemplate.exchange(ip+WIFI_ADAPTER_BASE_API_PATH+"/showAll", HttpMethod.GET, stringHttpEntity, String.class);

            //TODO to JSON fastJosn 2 去掉层数
            return JsonResult.success(exchange.getBody());

        }catch (Exception e){
            log.error("连接adapter设备失败");
            return JsonResult.error("调度失败");
        }
    }

}
