package com.ruoyi.devices.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.devices.domain.device.Device1;
import com.ruoyi.devices.mapper.Device1Mapper;
import com.ruoyi.devices.netty.server.NettyServer;
import com.ruoyi.devices.service.DeviceAuthService;
import com.ruoyi.devices.utils.PortAllocator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author lfx
 * @program ruoyi
 * @description 设备认证具体实现类
 * @date 2024/06/11
 */
@Service
@Slf4j
public class DeviceAuthServiceImpl implements DeviceAuthService {
    @Autowired
    private Device1Mapper device1Mapper;
    @Autowired
    private NettyServer nettyServer;

    @Override
    public int deviceAuth(String deviceCode, String deviceName, String deviceProductName, String deviceType, String deviceModel) {
        // 边界条件处理：检查输入参数的有效性
        if (deviceCode.isEmpty() || deviceName.isEmpty() || deviceProductName.isEmpty() || deviceType.isEmpty() || deviceModel.isEmpty()) {
            log.error("无法进行认证，输入参数无效");
            return 0;
        }
        Device1 device1 = device1Mapper.selectDevice1ByDeviceCode(deviceCode);
        if (device1 == null) {
            log.error("无法进行认证，设备未注册");
            return 0;
        }

        // 设备已经在平台注册过，获取注册设备的信息用来认证
        String name = device1.getDeviceName();
        String productName = device1.getDeviceProductName();
        String type = device1.getDeviceType();
        String model = device1.getDeviceModel();
        log.debug("设备信息：{} {} {} {}", name, productName, type, model);
        log.debug("认证信息：{} {} {} {}", deviceName, deviceProductName, deviceType, deviceModel);
        if (!name.equals(deviceName) || !productName.equals(deviceProductName) || !type.equals(deviceType) || !model.equals(deviceModel)) {
            log.error("无法进行认证，设备信息不匹配");
            return 0;
        }

        // PortAllocator portAllocator = new PortAllocator();
        // int port = portAllocator.allocatePort();
        // if (isPortAvailable(port)) {
        //     log.info("设备认证成功，开始监听端口：{}", port);
        //     nettyServer.start(port);
        // }
        // int port = 8099;
        // nettyServer.start(port);

        return 8099;
    }

    @Override
    public List<Integer> findAllAllocatedPorts() {
        List<Device1> deviceList = device1Mapper.selectDevice1List(new Device1());
        if (deviceList == null) {
            log.error("无法获取已分配端口列表");
            return Collections.emptyList();
        }

        List<String> StringPortList = deviceList.stream()
                .map(Device1::getDevicePort)
                .collect(Collectors.toList());

        if (StringPortList.isEmpty()) {
            log.error("无法获取已分配端口列表");
            return Collections.emptyList();
        }

        List<Integer> portList = StringPortList.stream()
                .filter(Objects::nonNull) // 过滤null值
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        log.debug("已分配端口列表：{}", portList);

        return portList;
    }

    public void updateAllDeviceStatusToOffline() {
        List deviceList = device1Mapper.selectDevice1List(new Device1());
        if (deviceList != null) {
            for (Object device : deviceList) {
               String status = ((Device1) device).getDeviceStatus();
               String code = ((Device1) device).getDeviceCode();
               Device1 device1 = new Device1();
               if ("0".equals(status)) {
                   device1.setDeviceCode(code);
                   device1.setDeviceStatus("1");
                   device1Mapper.updateDevice1(device1);
               }
            }
        }
    }

    /**
     * 安全地比较两个字符串。
     * 避免时间攻击：比较两个字符串时，不希望让比较过程中的时间泄露它们是否匹配。
     *
     * @param str1 第一个字符串
     * @param str2 第二个字符串
     * @return 是否匹配
     */
    private boolean secureCompare(String str1, String str2) {
        if (str1 == null || str2 == null) {
            return false;
        }
        int length = str1.length();
        if (length != str2.length()) {
            return false;
        }
        int result = 0;
        for (int i = 0; i < length; i++) {
            result |= str1.charAt(i) ^ str2.charAt(i);
        }
        return result == 0;
    }

    /**
     * 检查指定端口是否已被监听。
     *
     * @param port 要检查的端口号。
     * @return 如果端口已被监听，则返回false；否则返回true。
     */
    private boolean isPortAvailable(int port) {
        try (ServerSocket serverSocket = new ServerSocket()) {
            serverSocket.setReuseAddress(true); // 尝试复用地址，减少TIME_WAIT状态的影响
            serverSocket.bind(new InetSocketAddress(port)); // 绑定端口
            return true;  // 绑定成功，端口未被占用
        } catch (IOException e) {
            // 端口不可用
            return false;
        }
    }
}
