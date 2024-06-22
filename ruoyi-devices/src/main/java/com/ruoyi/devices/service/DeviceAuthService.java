package com.ruoyi.devices.service;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lfx
 * @program ruoyi
 * @description 设备认证服务
 * @date 2024/06/11
 */
@Service
public interface DeviceAuthService {
    /**
     * 设备认证
     *
     * @param deviceCode
     * @param deviceName
     * @param deviceProductName
     * @param deviceType
     * @param deviceModel
     * @return
     */
    public int deviceAuth(String deviceCode, String deviceName, String deviceProductName, String deviceType, String deviceModel);

    /**
     * 获取所有已分配端口
     * @return
     */
    public List<Integer> findAllAllocatedPorts();

    public void updateAllDeviceStatusToOffline();
}
