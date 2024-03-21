package org.pguide.spd.center.core.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author DKwms
 * @Date 2023/12/28 16:34
 * @description
 */
@Configuration
@MapperScan("org.pguide.spd.center.core.mapper")
public class MybatisConfig {
}
