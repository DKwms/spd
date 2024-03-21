package org.pguide.adapter.wifi.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author DKwms
 * @Date 2023/12/16 11:01
 * @description
 */
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "pguide")
public class SystemProperties {
    spd spd = new spd();

}

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * spd 基础配置属性
 */
class spd{
    String usedb = null;
    String useuid = null;
    String centerurl = null;
}
