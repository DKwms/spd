package org.pguide.adapter.wifi.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author DKwms
 * @Date 2023/12/19 9:49
 * @description
 */

@Data
@AllArgsConstructor
@NoArgsConstructor@Builder
public class QuartzJobsVo {

    String name;

    String group;

    String cronExpression;

    String status;
}
