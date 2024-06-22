package com.ruoyi.devices.nettyTcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

/**
 * @author lfx
 * @program ruoyi
 * @description 自定义的消息处理类
 * @date 2024/04/23
 */
public class TcpServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端已连接: " + ctx.channel().remoteAddress());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf receivedMessage = (ByteBuf) msg;
        String message = null;
        try {
            // 判断ByteBuf是否为UTF-8编码
            if (StandardCharsets.UTF_8.newEncoder().canEncode(receivedMessage.toString(StandardCharsets.UTF_8))) {
                message = receivedMessage.toString(StandardCharsets.UTF_8);
                System.out.println("从客户端发过来的数据: " + message);
            } else {
                // 处理非UTF-8编码数据
                // TODO: 可根据实际需求进行处理
            }
        } finally {
            // 释放ByteBuf资源
            receivedMessage.release();
        }


        // 在这里处理接收到的消息，例如回复客户端、存储数据等

        // 示例：向客户端发送一条消息
        //ctx.writeAndFlush("服务端已收到您的消息: " + receivedMessage);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}
