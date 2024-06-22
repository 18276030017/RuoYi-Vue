package com.ruoyi.devices.utils.devicetcp;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.ruoyi.devices.domain.device.Device1;
import com.ruoyi.devices.domain.device.Upgrade;
import com.ruoyi.devices.mapper.Device1Mapper;
import com.ruoyi.devices.netty.server.ChannelMap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.util.CollectionUtils;

import java.util.concurrent.*;

/**
 * @author lfx
 * @program ruoyi
 * @description 发送升级包信息给对应的设备
 * @date 2024/05/07
 */
@Slf4j
public class SendDeviceManage {
    private Device1Mapper getDevice1Mapper() {
        return SpringUtil.getBean(Device1Mapper.class);
    }
    public CompletableFuture<String> sendMsg(Upgrade device1) {
        ConcurrentHashMap<String, Channel> channelMap = ChannelMap.getChannelMap();
        if (CollectionUtils.isEmpty(channelMap)) {
            log.error("当前没有设备接入平台");
            //return error("没有可用的连接");
            return CompletableFuture.completedFuture("当前没有设备接入平台");
        }

        Device1Mapper device1Mapper = getDevice1Mapper();
        String deviceCode = device1.getDeviceCode();
        Device1 list = device1Mapper.selectDevice1ByDeviceCode(deviceCode);
        if (list == null) {
            log.error("设备信息查询失败，设备编码：{}", deviceCode);
            //return error("设备信息查询失败，设备编码为{}的设备不存在", deviceCode);
            return CompletableFuture.completedFuture("设备信息查询失败，设备编码为" + deviceCode + "的设备不存在");
        }
        String deviceChannelId = list.getChannelId();
        if (deviceChannelId == null && list.getDeviceStatus().equals("7")) {
            log.error("设备编码：{}的设备还没有进行接入激活", deviceCode);
            return CompletableFuture.completedFuture("设备编码为" + deviceCode + "的设备没有进行接入激活！");
        }
        // 升级指令
        JSONObject deviceCommand = new JSONObject();
        deviceCommand.append("*1*", "UpgradeStatusSet");
        // 升级信息构建
        JSONObject updateInfo = new JSONObject();
        updateInfo.append("DeviceCode", deviceCode);
        updateInfo.append("UpgradeUrl", device1.getUpgradeUrl());
        updateInfo.append("UpgradeDesc", device1.getUpgradeDesc());
        String command = deviceCommand.toString();
        String updated = updateInfo.toString();
        log.info("设备升级信息：{}" , updated);

        Channel targetChannel = channelMap.get(deviceChannelId);
        if (targetChannel == null || !targetChannel.isActive()) {
            log.error("目标设备通道不活跃，设备编码：{}", deviceCode);
            //return error("目标设备通道不活跃，设备编码为{}的设备无法发送指令", deviceCode);
            return CompletableFuture.completedFuture("目标设备通道不活跃，无法发送指令");
        }

        try {
            // 进行 Base64 编码
            // command = new String(Base64.encodeBase64(command.getBytes()));
            byte[] msgBytes = updated.getBytes("UTF-8");
            byte[] commandBytes = command.getBytes("UTF-8");
            log.info("开始发送报文:{}", new String(msgBytes));

            ByteBuf buffer = Unpooled.buffer();
            buffer.writeBytes(commandBytes);
            ChannelFuture future = targetChannel.writeAndFlush(buffer);

            ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

            CompletableFuture<String> resultFuture = new CompletableFuture<>();
            executor.schedule(() -> {
                ByteBuf buffer1 = Unpooled.buffer();
                buffer1.writeBytes(msgBytes);
                ChannelFuture future1 = targetChannel.writeAndFlush(buffer1);
                //监听设备是否成功发送给设备
                future1.addListener((ChannelFutureListener) futureResult -> {
                    if (future1.isSuccess()) {
                        log.info("客户端:{},回写成功:{}", deviceChannelId, new String(msgBytes));
                        resultFuture.complete("客户端成功接收下发的指令");
                    } else {
                        log.error("客户端:{},回写失败:{}", deviceChannelId, new String(msgBytes));
                        resultFuture.completeExceptionally(new Exception("客户端回写失败"));
                    }
                });
            },100, TimeUnit.MILLISECONDS);

            return resultFuture;

        } catch (Exception e) {
            log.error("发送消息失败, channelId: {}, 错误: {}", deviceChannelId, e.getMessage());
            //return error("发送消息失败, 具体发送错误的通道和信息是", deviceChannelId + e.getMessage());
            return CompletableFuture.completedFuture("发送消息失败, 具体发送错误的通道和信息是" + deviceChannelId + e.getMessage());
        }
    }
}
