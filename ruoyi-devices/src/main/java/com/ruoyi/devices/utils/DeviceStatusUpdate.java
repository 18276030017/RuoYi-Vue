package com.ruoyi.devices.utils;

/**
 * @author lfx
 * @program ruoyi
 * @description 记录设备状态改变
 * @date 2024/04/08
 */
import com.ruoyi.devices.mapper.Device1Mapper;

import java.time.Duration;
import java.time.LocalDateTime;

public class DeviceStatusUpdate {
    /**
     * 当设备离线时
     */

    /**
     * 获取设备离线时长
     */
    public Duration
    getOfflineDuration(LocalDateTime newOnlineTimestamp,LocalDateTime lastOfflineTimestamp) {
        return Duration.between(lastOfflineTimestamp, newOnlineTimestamp);
    }

    /**
     * 获取格式化后的设备离线时长
     * @return
     */
    public String getFormattedOfflineDuration(LocalDateTime newOnlineTimestamp,LocalDateTime lastOfflineTimestamp) {
        Duration offlineDuration = getOfflineDuration(newOnlineTimestamp, lastOfflineTimestamp);
        long days = offlineDuration.toDays();
        long hours = offlineDuration.toHours() % 24;
        long minutes = offlineDuration.toMinutes() % 60;
        String formatted = String.format("%d天%d小时%d分钟", days, hours, minutes);
        // 输出结果
        System.out.println(formatted);

        return formatted;
    }
}
