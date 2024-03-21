package org.pguide.spd.center.core.controller.spd;


import org.pguide.spd.common.web.result.core.result.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

/**
 * @author DKwms
 * @Date 2023/12/17 18:29
 * @description server
 */

@Controller
@RequestMapping("api/pguide/spd/center/usb")
public class SpdUsbAdapterController {

    // TODO 持久化ip信息，存储数据库中防止内存清空

    /**
     *  ip : healthy
     */
    HashMap<String, String> clientInfoMap = new HashMap<>();

    /**
     * 处理客户端信息
     */
    @RequestMapping("/deal")
    public JsonResult<Void> dealClientInfo(){
        return JsonResult.success();
    }

    /**
     * 轮询客户端状态
     * 返回动态维护的列表
     */
    @GetMapping("/showall")
    public JsonResult<HashMap<String,String>> showAll(){
        return JsonResult.success(clientInfoMap);
    }

    /**
     * 删除客户端
     */
    @RequestMapping("/delete")
    public JsonResult<Void> delete(){
        return JsonResult.success();
    }

    /**
     * 更改客户端状态
     * 增量更新
     */
    @PostMapping("/update")
    public JsonResult<Void> update(String ip,String type){
        clientInfoMap.put(ip,type);
        return JsonResult.success();
    }




}
