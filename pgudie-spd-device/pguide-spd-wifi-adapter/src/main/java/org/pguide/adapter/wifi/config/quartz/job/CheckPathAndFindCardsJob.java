package org.pguide.adapter.wifi.config.quartz.job;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.pguide.adapter.wifi.service.WIFISenderService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author DKwms
 * @Date 2023/12/16 21:12
 * @description
 */

//持久化
@PersistJobDataAfterExecution
//禁止并发执行
@DisallowConcurrentExecution
@Component
@Slf4j
public class CheckPathAndFindCardsJob implements Job {

    // 最高切换路径上限
    private Integer PATH_MAX_VALUE = 8;

    // 切换寻找的等待时间，0.5s
    private Integer CHECK_FIND_WAIT_TIME = 1500;

    @Value("${pguide.spd.centerurl}")
    private String PGUIDE_SPD_CENTER_URL = "";

    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    WIFISenderService wifiSenderService;

    // 存放上一次数据值
    // path : card
    // path 从0 开始，对应第一层
    private static HashMap<Integer,String[]> lastMap = new HashMap<>();


    // 200 + 300 + 1500
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();

        // 执行次数增加
        int count = jobDataMap.getInt("count");
        jobDataMap.put("count",++count);
        log.info("---------第{}次寻卡");

        //路径切换
        int path = jobDataMap.getInt("path");
        int patnNow = path;
        if(path > PATH_MAX_VALUE) path = 1;
        wifiSenderService.checkPath(path);
        jobDataMap.put("path",++path);
        try {
            Thread.sleep(CHECK_FIND_WAIT_TIME);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String[] changeCards = lastMap.get(path);

        //寻卡
        ArrayList<String> cards = wifiSenderService.findCard();

        // 更新卡号
        lastMap.put(path,cards.toArray(new String[0]));

        log.info("获取卡数量：{}",cards.size());

        ArrayList<ArrayList<String>> res = get(cards, changeCards);

        ArrayList<String> newList = res.get(0);
        System.out.println("newList");
        newList.forEach(System.out::println);

        ArrayList<String> oldList = res.get(1);
        System.out.println("oldList");
        oldList.forEach(System.out::println);

        //TODO 业务逻辑的重点
        if (newList.isEmpty() && oldList.isEmpty()){
            // 不变
        }else if(newList.isEmpty()){
            // 新的变
        }else if(oldList.isEmpty()){
            // 旧的变
        }else {
            // 都变
        }
        //String res = "";
        //for (String card : cards) {
        //    res += card + ">";
        //}
        //
        //
        //JSONObject paramEntity = new JSONObject();
        //paramEntity.put(patnNow+"",res);
        ////TODO 发送给中心服务器信息
        //HttpEntity<String> stringHttpEntity = new HttpEntity<>(paramEntity.toString(), new HttpHeaders());
        //ResponseEntity<String> exchange = restTemplate.exchange(PGUIDE_SPD_CENTER_URL, HttpMethod.GET, stringHttpEntity, String.class);


    }

    private  ArrayList<ArrayList<String>> get(ArrayList<String> cards, String[] split){
        /**
         *  0 ： 两个都有
         *  1 ： split 独有   | 减少
         *  -1 : card 独有    | 新增
         */
        Map<String, Integer> hashMap = new HashMap<>();
        for (String s : split) {
            if ("".equals(s)) {
                continue;
            }
            Integer oldValue = hashMap.get(s);
            int newValue =1;
            hashMap.put(s, newValue);
        }
        for(String s: cards){
            Integer oldValue = hashMap.get(s);
            int newValue = (oldValue == null) ? -1 : oldValue - 1;
            hashMap.put(s, newValue);
        }

        ArrayList<String> newList = new ArrayList<>();
        ArrayList<String> oldList = new ArrayList<>();

        ArrayList<ArrayList<String>> resultList = new ArrayList<>();


        hashMap.entrySet().stream()
                .filter(e->e.getValue()==-1)
                .forEach(
                        e->{
                            newList.add(e.getKey());
                        }
                );

        hashMap.entrySet().stream()
                .filter(e->e.getValue()==1)
                .forEach(
                        e->{
                            oldList.add(e.getKey());
                        }
                );

        resultList.add(newList);
        resultList.add(oldList);

        return resultList;
    }


}
