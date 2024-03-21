package org.pguide.adapter.wifi.controller;

import org.pguide.adapter.wifi.common.core.result.JsonResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author DKwms
 * @Date 2023/12/16 11:36
 * @description
 */
@RestController
@RequestMapping
public class CenterController {

    @Value("${pguide.spd.centerurl}")
    private String CENTER_URL;

    @RequestMapping("/getter")
    public JsonResult<String> getCenter(){
        return JsonResult.success();
    }


}
