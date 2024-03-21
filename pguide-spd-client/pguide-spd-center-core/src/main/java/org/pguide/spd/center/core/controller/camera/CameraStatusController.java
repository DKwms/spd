package org.pguide.spd.center.core.controller.camera;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author DKwms
 * @Date 2023/12/27 19:11
 * @description 状态控制
 */

@RequestMapping("/api/pguide/spd/center/camera/status")
@RestController
public class CameraStatusController {

    // waiter
    @PostMapping
    public void waitForMsg(@RequestBody String a){

    }


    // sender


}
