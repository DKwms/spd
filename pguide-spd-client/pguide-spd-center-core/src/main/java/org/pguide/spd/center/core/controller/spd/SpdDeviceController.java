package org.pguide.spd.center.core.controller.spd;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.pguide.spd.center.core.entity.SpdClientDevice;
import org.pguide.spd.center.core.service.SpdClientDeviceService;
import org.pguide.spd.common.web.result.core.result.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author DKwms
 * @Date 2023/12/18 19:15
 * @description
 */

@RequestMapping("api/pguide/spd/center/device")
@RestController
public class SpdDeviceController {

    @Autowired
    SpdClientDeviceService spdClientDeviceService;

    /**
     * 集群信息
     * @return
     */
    @GetMapping
    public JsonResult<List<SpdClientDevice>> getAllDevice(){
        //TODO 分页 限制 条件
        List<SpdClientDevice> list = spdClientDeviceService.list();
        return JsonResult.success(list);
    }

    /**
     * 在线集群信息
     */
    @GetMapping("/healthy")
    public JsonResult<List<SpdClientDevice>> getAllHealthyDevice(){

        List<SpdClientDevice> list = spdClientDeviceService.list(Wrappers.<SpdClientDevice>lambdaQuery()
                .eq(SpdClientDevice::getDeviceStatus,"healthy"));

        return JsonResult.success();
    }

    /**
     * 删除
     */
    @GetMapping("/delete")
    public JsonResult<Void> deleteDevice(){

        // 1、 断开连接

        // 2、 删除数据库记录

        return JsonResult.success();
    }

    /**
     * update
     */
    @GetMapping("/update")
    public JsonResult<Void> updateDevice(){
        // 1、 断开连接

        // 2、 改表

        // 3、 重新连接
        return JsonResult.success();
    }

    /**
     * add
     */
    @PostMapping("/add")
    public JsonResult<Void> addDevice(){

        // 1、查重

        // 2、 该表

        // 3、 尝试连接

        return JsonResult.success();
    }



}
