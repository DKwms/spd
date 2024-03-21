package org.pguide.adapter.wifi.controller;

import org.pguide.adapter.wifi.common.SystemProperties;
import org.pguide.adapter.wifi.common.core.result.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author DKwms
 * @Date 2023/12/16 10:56
 * @description 向中心反映当前实例状态
 */
@RestController
public class LocalStatusController {

    @Autowired
    SystemProperties systemProperties;

    @GetMapping("/test")
    public JsonResult<SystemProperties> statusTestI(){

        return JsonResult.success(systemProperties);
    }
}
