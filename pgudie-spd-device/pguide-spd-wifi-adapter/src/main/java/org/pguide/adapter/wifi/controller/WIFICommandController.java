package org.pguide.adapter.wifi.controller;

import lombok.extern.slf4j.Slf4j;
import org.pguide.adapter.wifi.common.core.result.JsonResult;
import org.pguide.adapter.wifi.service.WIFISenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * @author DKwms
 * @Date 2023/12/16 10:48
 * @description
 */


@RestController
@RequestMapping("/api/spd/adapter/wifi/command")
@Slf4j
public class WIFICommandController {

    @Autowired
    WIFISenderService wifiSenderService;

    /**
     * 路径切换
     * @param num
     * @return
     */
    @RequestMapping("/checkpath")
    public JsonResult<Void> checkPath(@RequestParam String num){
        System.out.println(num);
        wifiSenderService.checkPath(Integer.parseInt(num));
        return JsonResult.success();
    }

    @RequestMapping("/findcard")
    public JsonResult<ArrayList<String>> findCard(){
        ArrayList<String> card = wifiSenderService.findCard();
        return JsonResult.success(card);
    }

}
