package com.ruoyi.devices.utils;

import com.ruoyi.devices.service.DeviceAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * @author lfx
 * @program ruoyi
 * @description 在执行程序关闭之前，将平台与设备之间的连接断开，并且设置设备的状态为离线状态
 * @date 2024/06/15
 */
@Component
public class SetDeviceStatusOFF {
    private static final Logger log = LoggerFactory.getLogger(SetDeviceStatusOFF.class);
    @Autowired
    private DeviceAuthService deviceAuthService;

    @PreDestroy
    public void setDeviceStatusOFF() {
        log.info("程序关闭之前，将平台与设备之间的连接断开，并且设置设备的状态为离线状态");
        deviceAuthService.updateAllDeviceStatusToOffline();
    }
}
