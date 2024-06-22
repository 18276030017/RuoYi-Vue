package com.ruoyi.devices.service;

import com.ruoyi.devices.domain.device.DevicesData;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lfx
 * @program ruoyi
 * @description 创建Repository接口
 * @date 2024/05/29
 */
public interface DeviceDataRepository extends JpaRepository<DevicesData, Long> {
}
