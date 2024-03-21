package org.pguide.spd.center.core.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 * @TableName spd_client_device
 */
@TableName(value ="spd_client_device")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpdClientDevice implements Serializable {
    /**
     * 设别id
     */
    @TableId(type= IdType.AUTO)
    private Integer deviceId;

    /**
     * 设备名
     */
    private String deviceName;

    /**
     * 设别类型
     */
    private String deviceType;

    /**
     * 设备分组
     */
    private String deviceGroup;

    /**
     * 设备ip
     */
    private String deviceIp;

    /**
     * 设别状态
     */
    private String deviceStatus;

    /**
     * 设备地理位置
     */
    private String devicePlace;

    /**
     * 设备详细经纬度
     */
    private String devicePlaceDetail;

    /**
     * 
     */
    private Integer deleted;

    /**
     * 
     */
    private LocalDateTime created;

    /**
     * 
     */
    private LocalDateTime updated;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}