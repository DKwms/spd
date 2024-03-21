package org.pguide.adapter.wifi.config.quartz.job;


import lombok.extern.slf4j.Slf4j;
import org.pguide.adapter.wifi.service.WIFISenderService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author DKwms
 * @Date 2023/12/16 11:41
 * @description
 */
//持久化
@PersistJobDataAfterExecution
//禁止并发执行
@DisallowConcurrentExecution
@Component
@Slf4j
public class FIndCardTaskJob implements Job {


    @Autowired
    WIFISenderService wifiSenderService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();

        // 执行次数增加
        int count = jobDataMap.getInt("count");
        jobDataMap.put("count",++count);
        log.info("---------第{}次寻卡",count);

        ArrayList<String> cards = wifiSenderService.findCard();

        // card
        System.out.println("cards = " + cards);

        if (cards == null) {
            cards = new ArrayList<>();
        }

        // 获取上一次的卡号集合
        String lastResult = jobDataMap.getString("result");
        String[] split = lastResult.split(">");

        ArrayList<ArrayList<String>> res = get(cards, split);


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


        // 与之前的有区别
        String result = "";
        for (String card : cards) {
            result  = result + card + ">";
        }

        System.out.println("result = " + result);
        jobDataMap.put("result",result.substring(0,result.length() - 1));

    }

    private ArrayList<String> getTheChangeCards(ArrayList<String> cards, String[] split) {
        ArrayList<String> res = new ArrayList<>();
        boolean key = true;
         if(cards.size() <= split.length){
             for (String s : split) {
                 for (String card : cards) {
                     // 找到card
                     if(s.equals(card)){
                         key = false;
                         break;
                     }
                 }
                 // 没有找到相同的
                 if (key){
                     res.add(s);
                     key = true;
                 }
             }
         }else{
             for (String card : cards) {
                 for (String s : split) {
                     // 找到card
                     if(s.equals(card)){
                         key = false;
                         break;
                     }
                 }
                 // 没有找到相同的
                 if (key){
                     res.add(card);
                     key = true;
                 }
             }
         }

        return res;
    }

    /**
     * @param cards
     * @param split
     * @return
     * 第一个是新的，<br>
     * 第二个是旧的
     */
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
