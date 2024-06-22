package com.ruoyi.devices.netty.server;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.devices.domain.device.Device1;
import com.ruoyi.devices.mapper.Device1Mapper;
import com.ruoyi.devices.service.DeviceAuthService;
import com.ruoyi.devices.utils.DeviceAuthUtil;
import com.ruoyi.devices.utils.PortAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lfx
 * @program ruoyi
 * @description 自定义设备认证处理逻辑并考虑线程安全和资源管理
 * @date 2024/06/12
 */
@Slf4j
public class AuthHandler extends ChannelInboundHandlerAdapter {
    DeviceAuthService deviceAuthService = SpringUtil.getBean(DeviceAuthService.class);
    PortAllocator portAllocator = new PortAllocator();
    NettyServer nettyServer = SpringUtil.getBean(NettyServer.class);
    private ExecutorService executorService = Executors.newSingleThreadExecutor(); // 假设你有一个线程池注入进来
    Device1Mapper device1Mapper = SpringUtil.getBean(Device1Mapper.class);
    Device1 device1 = new Device1();
    RedisCache redisCache = SpringUtil.getBean(RedisCache.class);

    /**
     * 功能描述：有设备连接时执行该函数
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // 监听tcp连接是否握手成功
        // SslHandler sslHandler = (SslHandler) ctx.channel().pipeline().get("ssl");
        // sslHandler.handshakeFuture().addListener(future -> {
        //     if (future.isSuccess()) {
        //         log.info("握手成功");
        //     } else {
        //         log.info("握手失败");
        //         log.info("失败原因：{}", future.cause().getMessage());
        //         ctx.close();
        //     }
        // });

        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = insocket.getAddress().getHostAddress();
        int clientPort = insocket.getPort();
        // 获取连接通道唯一标识
        String channelId = String.valueOf(ctx.channel().id());
        // 如果map中不包含此连接，就保存连接
        if (!ChannelMap.getChannelMap().containsKey(channelId)) {
            ChannelMap.getChannelMap().put(channelId, ctx.channel());
            log.info("客户端:{},连接netty服务器的设备为[IP:{}-->PORT:{}]", channelId, clientIp, clientPort);
            log.info("连接通道数量：{}", ChannelMap.getChannelMap().size());
        }
    }

    /**
     * 功能描述: 有客户端终止连接服务器会触发此函数
     *
     * @param ctx 通道处理程序上下文
     * @Author keLe
     * @Date 2022/8/26
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        InetSocketAddress inSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = inSocket.getAddress().getHostAddress();
        String channelId = String.valueOf(ctx.channel().id());
        log.info("通道：{},客户端:{}", channelId, clientIp);
        log.info("通道包含：{}", ChannelMap.getChannelMap());
        // 包含此客户端才去删除
        if (ChannelMap.getChannelMap().containsKey(channelId)) {
            // 删除连接
            ChannelMap.getChannelMap().remove(channelId);
            log.info("客户端:{},连接netty服务器的设备为[IP:{}-->PORT:{}],已经断开连接", channelId, clientIp, inSocket.getPort());
            log.info("此时还在保持连接的通道数量为:{} ", ChannelMap.getChannelMap().size());
        }
    }

    /**
     * 功能描述: 有客户端发消息会触发此函数
     *
     * @param ctx 通道处理程序上下文
     * @param msg 客户端发送的消息
     * @return void
     * @Author keLe
     * @Date 2022/8/26
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 先确保消息不为空
        if (msg == null) {
            ctx.close();
            return;
        }
        ChannelId channelId = ctx.channel().id();
        JSONObject jsonMessage = new JSONObject(msg);
        log.info("客户端:{},发送的消息为:{}", channelId, msg);
        // 获取设备发送的消息
        Set<String> allKeys = jsonMessage.keySet();
        for (String key : allKeys) {
            if (jsonMessage.getStr(key) == null) {
                // 数据清洗：如果值为空，则设置为一个自定义的字符串来表明其没有值，保证没有空值出现
                jsonMessage.put(key, "该建值为空");
            }
        }

        // 根据设定注册规则获取相应字段
        String action = jsonMessage.getStr("Action");
        String deviceCode = jsonMessage.getStr("DeviceCode");
        String deviceName = jsonMessage.getStr("DeviceName");
        String deviceProductName = jsonMessage.getStr("DeviceProductName");
        String deviceType = jsonMessage.getStr("DeviceType");
        String firmwareVersion = jsonMessage.getStr("FirmwareVersion");
        String deviceModel = jsonMessage.getStr("DeviceModel");
        String clientIp = jsonMessage.getStr("IP");
        String sign = jsonMessage.getStr("Signature");
        String TimesTamp = jsonMessage.getStr("TimesTamp");


        Device1 device1 = device1Mapper.selectDevice1ByDeviceCode(deviceCode);
        if (device1 == null) {
            ctx.close();
            return;
        }

        // 设备已经在平台注册过，获取注册设备的信息用来认证
        String status = device1.getDeviceStatus();
        String DeviceId = device1.getDeviceId();
        String DeviceProductKey = device1.getProductKey();
        String DeviceSecret = device1.getDeviceSecret();
        Long timesTamp = System.currentTimeMillis();

        // 验证是否为注册请求，若是则进行验证
        if (action.equals("Register") && status.equals("7")) {
            // 调用设备认证服务并返回监听的端口号
           int port = deviceAuthService.deviceAuth(deviceCode, deviceName, deviceProductName, deviceType, deviceModel);

           // 认证成功后，将设备信息保存到数据库中
           if (port != 0) {
               String accessToken = DeviceAuthUtil.generateAccessToken(DeviceId, deviceType, deviceModel, DeviceProductKey, DeviceSecret);
               device1.setAccessToken(accessToken);
               device1.setDeviceIP(clientIp);
               device1.setDeviceStatus("1");
               device1.setFirmwareVersion(firmwareVersion);
               device1.setDevicePort(String.valueOf(port));
               device1Mapper.updateDevice1(device1);

               // 构造认证成功信息
               JSONObject jsonObject = new JSONObject();
               jsonObject.put("Action", "RegisterSuccess");
               jsonObject.put("SuccessCode", "200");
               jsonObject.put("AccessToken", accessToken);
               jsonObject.put("TimesTamp", timesTamp.toString());
               jsonObject.put("DeviceId", DeviceId);
               jsonObject.put("DeviceProductKey", DeviceProductKey);
               jsonObject.put("DeviceSecret", DeviceSecret);
               jsonObject.put("Port", "21035");
               jsonObject.put("SuccessMessage", "DeviceAuthSuccess");
               sendMessageByChannelId(channelId, jsonObject);
               log.info("认证成功，设备信息为：{}", jsonMessage);
               log.info("发送的消息：{}", jsonObject);
               // 认证成功后，将设备信息保存到redis中
               redisCache.setCacheObject(deviceCode, jsonMessage);
           }

        } else if (!status.equals("7") && action.equals("Register")) {
            sendMessageByChannelId(channelId, "设备已经注册认证过，请勿再次注册认证");
            ctx.close();
        }
    }

    /**
     * 功能描述: 服务端给客户端发送消息
     *
     * @param channelId 连接通道唯一id
     * @param msg       需要发送的消息内容
     * @return void
     */
    public void sendMessageByChannelId(ChannelId channelId, Object msg) {
        // 检查消息是否为空
        if (msg == null) {
            log.error("消息对象为null，无法发送消息。ChannelId: {}", channelId);
            return;
        }
        // 发送消息
        Channel channel = ChannelMap.getChannelMap().get(String.valueOf(channelId));
        if (channel == null) {
            log.error("Channel为null，无法发送消息。ChannelId: {}", channelId);
            return;
        }
        if (!channel.isActive()) {
            log.error("Channel不活跃，无法发送消息。ChannelId: {}", channelId);
            return;
        }
        // 使用try-catch捕获并处理可能的异常
        try {
            // 显式指定UTF-8编码，避免平台默认编码的问题
            byte[] messageBytes = JSON.toJSONString(msg).getBytes(StandardCharsets.UTF_8);
            // 使用copiedBuffer，考虑到可能的性能和资源管理问题
            channel.writeAndFlush(Unpooled.copiedBuffer(messageBytes));
        } catch (Exception e) {
            // 对于writeAndFlush操作中出现的异常进行处理
            // 根据实际情况，这里可以选择记录日志、重试发送或者其他错误处理机制
            log.error("发送消息时发生异常。ChannelId: {}, 异常信息: {}", channelId, e.getMessage(), e);
            e.printStackTrace();
            // 关闭连接
            channel.close();
        }
    }


    /**
     * 当用户事件被触发时，处理空闲状态事件
     *
     * @param ctx       ChannelHandlerContext，上下文对象，用于发送和接收数据
     * @param evt        Object，触发的事件，这里期望是IdleStateEvent
     * @throws Exception 可能抛出的异常
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        String socketString = ctx.channel().remoteAddress().toString();
        String channelId = String.valueOf(ctx.channel().id());
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            // 根据不同的空闲状态执行相应的操作
            switch (event.state()) {
                case READER_IDLE:
                    log.info("客户端{}，读取超时", socketString);
                    ctx.disconnect();
                    ChannelMap.removeChannelByName(channelId);
                    break;
                case WRITER_IDLE:
                    log.info("客户端{}，写入超时", socketString);
                    ctx.disconnect();
                    ChannelMap.removeChannelByName(channelId);
                    break;
                case ALL_IDLE:
                    log.info("客户端{}，总超时", socketString);
                    ctx.disconnect();
                    ChannelMap.removeChannelByName(channelId);
                    break;
            }
        }
    }

    // 注意：读写超时时间由上文的IdleStateHandler设置，具体时长取决于其构造函数参数


    /**
     * 功能描述: 发生异常会触发此函数
     *
     * @param ctx   通道处理程序上下文
     * @param cause 异常
     * @return void
     * @Author keLe
     * @Date 2022/8/26
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("连接通道{}，发生了异常，异常详情:{}", ctx.channel().id(), cause.getMessage());
        cause.printStackTrace();
        ctx.close();
        log.info("{}:发生了错误,此连接被关闭。此时连通数量:{}", ctx.channel().id(), ChannelMap.getChannelMap().size());
    }

}
