package com.ruoyi.devices.netty.server;

import cn.hutool.extra.spring.SpringUtil;
import com.ruoyi.devices.domain.device.Device1;
import com.ruoyi.devices.mapper.Device1Mapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Base64;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import static com.ruoyi.common.core.domain.AjaxResult.error;
import static com.ruoyi.common.core.domain.AjaxResult.success;

@Slf4j
@Component
public class DtuManage {

    private Device1Mapper getDevice1Mapper() {
        return SpringUtil.getBean(Device1Mapper.class);
    }

    /**
     * 定时发送Dtu报文
     *
     * @param device1 设备信息
     */
    public CompletableFuture<String> sendMsg(Device1 device1) {
        ConcurrentHashMap<String, Channel> channelMap = ChannelMap.getChannelMap();
        if (CollectionUtils.isEmpty(channelMap)) {
            log.error("没有可用的连接");
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
        if (deviceChannelId == null) {
            log.error("设备编码：{}没有进行连接过", deviceCode);
            //return error("设备信息查询失败，设备编码为{}的设备不存在", deviceCode);
            return CompletableFuture.completedFuture("设备编码为" + deviceCode + "的设备没有进行过连接");
        }

        String deviceCommand = device1.getDeviceCommand();
        // 进行 Base64 编码
        // String encodedCommand = Base64.getEncoder().encodeToString(deviceCommand.getBytes());
        // log.info("编码前的指令：{}，编码后的指令：{}", deviceCommand, encodedCommand);
        // 将编码后的字符串转换为字节数组
        byte[] msgBytes = deviceCommand.getBytes();
        Channel targetChannel = channelMap.get(deviceChannelId);
        if (targetChannel == null || !targetChannel.isActive()) {
            log.error("目标设备通道不活跃，设备编码：{}", deviceCode);
            //return error("目标设备通道不活跃，设备编码为{}的设备无法发送指令", deviceCode);
            return CompletableFuture.completedFuture("目标设备当前没有接入平台，无法发送指令");
        }

        try {
            ByteBuf buffer = Unpooled.buffer();
            log.info("开始发送报文:{}", new String(msgBytes));
            buffer.writeBytes(msgBytes);

            CompletableFuture<String> resultFuture = new CompletableFuture<>();

            //发送前端下发的指令消息给设备
            ChannelFuture future = targetChannel.writeAndFlush(buffer);
            //监听设备是否成功发送给设备
            future.addListener((ChannelFutureListener) future1 -> {
                if (future1.isSuccess()) {
                    log.info("客户端:{},回写成功:{}", deviceChannelId, new String(msgBytes));
                    resultFuture.complete("客户端成功接收下发的指令");
                } else {
                    log.error("客户端:{},回写失败:{}", deviceChannelId, new String(msgBytes));
                    resultFuture.completeExceptionally(new Exception("客户端回写失败"));
                }
            });
            return resultFuture;
        } catch (Exception e) {
            log.error("发送消息失败, channelId: {}, 错误: {}", deviceChannelId, e.getMessage());
            //return error("发送消息失败, 具体发送错误的通道和信息是", deviceChannelId + e.getMessage());
            return CompletableFuture.completedFuture("发送消息失败, 具体发送错误的通道和信息是" + deviceChannelId + e.getMessage());
        }

    }


    /**
     * 定时删除不活跃的连接
     */
    public void deleteInactiveConnections() {
        ConcurrentHashMap<String, Channel> channelMap = ChannelMap.getChannelMap();
        if (!CollectionUtils.isEmpty(channelMap)) {
            for (Map.Entry<String, Channel> entry : channelMap.entrySet()) {
                String channelId = entry.getKey();
                Channel channel = entry.getValue();
                if (!channel.isActive()) {
                    channelMap.remove(channelId);
                    log.info("客户端:{},连接已经中断", channelId);
                }
            }
        }
    }
}
