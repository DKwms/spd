package org.pguide.spd.center.core.controller.spd;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.pguide.spd.center.core.controller.vo.AdapterVO;
import org.pguide.spd.center.core.entity.SpdClientDevice;
import org.pguide.spd.center.core.service.SpdClientDeviceService;
import org.pguide.spd.common.web.result.core.result.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author DKwms
 * @Date 2023/12/17 16:52
 * @description
 */

@RestController
@RequestMapping("api/pguide/spd/center")
@Slf4j
public class SpdWaiterController {

    @Autowired
    SpdClientDeviceService spdClientDeviceService;
    /**
     * 接受发送过来的系统响应
     * 暂时通过名字为id来进行唯一性匹配，后序通过其他方式完善
     */
    @PostMapping
    public JsonResult<Void> waiter(@RequestBody AdapterVO request){
        /**
         * db & redis
         */
        System.out.println(request);

        SpdClientDevice newAdapter = SpdClientDevice.builder()
                .deviceId(null)
                .deviceType(request.getType())
                .deviceIp(request.getIp())
                .devicePlace(request.getPlace())
                .deviceName(request.getName())
                .deviceGroup(request.getGroup())
                .deviceStatus(request.getStatus())
                .devicePlace(request.getPlace())
                .devicePlaceDetail(request.getN() + "N" + request.getE() + "E").build();


        //TODO 增量更新待优化
        SpdClientDevice ifHasOne = spdClientDeviceService.getOne(Wrappers.<SpdClientDevice>lambdaQuery().eq(SpdClientDevice::getDeviceName, request.getName()));
        if (ifHasOne!=null && ifHasOne.getDeviceId()!=null){
            newAdapter.setDeviceId(ifHasOne.getDeviceId());
            spdClientDeviceService.updateById(newAdapter);
        }else{
            spdClientDeviceService.save(newAdapter);
        }

        log.info("新adapter上线，检测成功：{}",newAdapter);

        return JsonResult.success();
    }
}
