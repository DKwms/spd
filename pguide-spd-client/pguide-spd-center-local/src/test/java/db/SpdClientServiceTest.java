package db;

import org.junit.jupiter.api.Test;
import org.pguide.spd.center.core.PGuideSPDCenterApplication;
import org.pguide.spd.center.core.service.SpdClientDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author DKwms
 * @Date 2023/12/18 19:19
 * @description
 */

@SpringBootTest(classes = {PGuideSPDCenterApplication.class})
public class SpdClientServiceTest {

    @Autowired
    SpdClientDeviceService spdClientDeviceService;

    @Test
    void testDb1(){
        spdClientDeviceService.list().forEach(System.out::println);
    }




}
