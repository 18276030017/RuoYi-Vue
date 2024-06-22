package com.ruoyi.devices.netty.server;

import cn.hutool.core.date.DateTime;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.devices.domain.device.Device1;
import com.ruoyi.devices.domain.device.DeviceRegistrationInfo;
import com.ruoyi.devices.mapper.Device1Mapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.InetSocketAddress;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Set;

/**
 * @author lfx
 * @program device-socket
 * 功能描述: 有客户端连接服务器会触发此函数
 * @description 自定义报文管理,
 * @date 2024/04/11
 */
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    RedisCache redisCache = SpringUtil.getBean(RedisCache.class);
    Device1 device1 = new Device1();
    Device1Mapper device1Mapper = SpringUtil.getBean(Device1Mapper.class);

    // 设置允许的最大连接次数
    private static final int MAX_CONNECTIONS = 5;
    private static final int currentConnections = 0;

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
        if (ChannelMap.getChannelMap().containsKey(channelId)) {
            log.info("客户端:{},是连接状态，连接通道数量:{} ", channelId, ChannelMap.getChannelMap().size());
        } else {
            // 保存连接
            ChannelMap.addChannel(channelId, ctx.channel());
            log.info("客户端:{},连接netty服务器[IP:{}-->PORT:{}]", channelId, clientIp, clientPort);
            log.info("连接通道数量: {}", ChannelMap.getChannelMap().size());
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
            log.info("此时还在保持连接的通道数量为: " + ChannelMap.getChannelMap().size());
        }

        // 设备离线
        device1.setChannelId(channelId);
        device1.setDeviceStatus("1");
        device1.setLastOnlineTime(DateUtils.getNowDate());
        device1Mapper.updateDevice1ByChannelId(device1);
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
        log.info("加载客户端报文,客户端id:{},客户端返回消息:{}", ctx.channel().id(), msg);
        if (msg == null) {
            log.info("客户端发送空的消息");
            ctx.close();
            return;
        }

        String channelId = String.valueOf(ctx.channel().id());
        // 使用 cn. hutool，解析判断返回的数据是否为JSON格式
        JSONObject dataJson = new JSONObject(msg);

        // 获取设备信息
        String deviceCode = dataJson.getStr("DeviceCode");
        String deviceId = dataJson.getStr("DeviceID");
        String accessToken = dataJson.getStr("AccessToken");
        String sign = dataJson.getStr("Sign");
        log.info("设备信息:{},设备ID:{},AccessToken:{},Sign:{}", deviceCode, deviceId, accessToken, sign);

        // 根据设备的唯一标识符和设备ID进行验证
        Device1 deviceResult = device1Mapper.selectDevice1ByDeviceCode(deviceCode);
        if (deviceResult == null) {
            log.info("设备没有在平台上注册,请先完成注册");
            ctx.close();
        }

        // 首次正式通信
        if (dataJson.getStr("Action").equals("Connect") || dataJson.getStr("Action").equals("ResConnect") && deviceResult.getDeviceStatus().equals("1")) {
            // 保证上传的数据不为空
            if (deviceCode.isEmpty() || deviceId.isEmpty() || accessToken.isEmpty() || sign.isEmpty()) {
                log.info("设备上报的数据存在空值，没办法进行验证");
                ctx.close();
            }
            log.info("设备开始正式通信,设备信息{}，连接信息{}", deviceResult, dataJson);
            try {
                if (deviceResult.getAccessToken().equals(accessToken) && deviceResult.getDeviceId().equals(deviceId)) {
                    log.info("设备正式通信验证成功");
                    device1.setDeviceCode(deviceCode);
                    device1.setChannelId(channelId);
                    device1.setDeviceStatus("0");
                    if (dataJson.getStr("Action").equals("ResConnect")) {
                        device1.setNewOnlineTime(DateUtils.getNowDate());
                        String offlineTime = DateUtils.timeDistance(DateUtils.getNowDate(),deviceResult.getLastOnlineTime());
                        device1.setOffTime(offlineTime);
                    }
                    device1Mapper.updateDevice1(device1);
                    redisCache.saveDeviceMessage(deviceCode,dataJson.getStr("Action"),dataJson.toString());
                }
            } catch (Exception e) {
                log.info("设备正式通信验证失败,失败信息：{}", e.getMessage());
                ctx.close();
            }
        }

        // 设备主动上报
        if (dataJson.getStr("Action").equals("Report")) {
            log.info("设备主动上报,设备code:{},设备id:{},设备上报数据:{}", deviceCode, deviceId, dataJson);
            redisCache.saveDeviceMessage(deviceCode,dataJson.getStr("Action"),dataJson.toString());
        }

        // 设备响应平台
        if (dataJson.getStr("Action").equals("Response")) {
            log.info("设备响应平台,设备code:{},设备id:{},设备响应数据:{}", deviceCode, deviceId, dataJson);
            redisCache.saveDeviceMessage(deviceCode,dataJson.getStr("Action"),dataJson.toString());
        }
    }


   /* @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        String bytes = "01 03 00 02 00 01 25 CA";
        ctx.writeAndFlush(bytes);
    }*/

    /**
     * 功能描述: 服务端给客户端发送消息
     *
     * @param channelId 连接通道唯一id
     * @param msg       需要发送的消息内容
     * @return void
     * @Author keLe
     * @Date 2022/8/26
     */
    public void channelWrite(ChannelId channelId, Object msg) throws Exception {
        Channel channel = ChannelMap.getChannelMap().get(channelId);
        if (channel == null) {
            log.info("通道:{},不存在", channelId);
            return;
        }
        log.info("服务端响应客户端:{},消息:{}", channelId, msg);
        // 将客户端的信息直接返回写入ctx
        channel.write(msg);
        // 刷新缓存区
        channel.flush();
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
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            // 根据不同的空闲状态执行相应的操作
            switch (event.state()) {
                case READER_IDLE:
                    log.info("客户端{}，读取超时", socketString);
                    handleDeviceDisconnect(ctx); // 处理设备断开连接
                    break;
                case WRITER_IDLE:
                    log.info("客户端{}，写入超时", socketString);
                    handleDeviceDisconnect(ctx); // 处理设备断开连接
                    break;
                case ALL_IDLE:
                    log.info("客户端{}，总超时", socketString);
                    handleDeviceDisconnect(ctx); // 处理设备断开连接
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

        String channelId = String.valueOf(ctx.channel().id());
        // 设备离线
        device1.setChannelId(channelId);
        device1.setDeviceStatus("1");
        device1.setLastOnlineTime(DateUtils.getNowDate());
        device1Mapper.updateDevice1ByChannelId(device1);
    }

    // 空闲时间过长拒绝连接，即长时间没有收到从设备端收到数据
    private void handleDeviceDisconnect(ChannelHandlerContext ctx) {
        String channelId = String.valueOf(ctx.channel().id());
        device1.setChannelId(channelId);
        device1.setDeviceStatus("1");
        device1.setLastOnlineTime(DateUtils.getNowDate());
        device1Mapper.updateDevice1ByChannelId(device1);
        ctx.disconnect();
        ChannelMap.removeChannelByName(channelId);
    }
}
