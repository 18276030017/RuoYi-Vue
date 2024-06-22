package com.ruoyi.devices.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.logging.Logger;

/**
 * 自定义发送消息的格式
 */
public class MyEncoder extends MessageToByteEncoder<String> {

    private static final Logger logger = Logger.getLogger(MyEncoder.class.getName());
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(MyEncoder.class);

    /**
     * 16进制字符串转字节数组
     *
     * @param src 16进制字符串
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String src) {
        if (src == null || src.length() % 2 != 0) {
            throw new IllegalArgumentException("输入的字符串为空或不是偶数长度");
        }
        int len = src.length() / 2;
        byte[] ret = new byte[len];
        for (int i = 0; i < len; i++) {
            String subStr = src.substring(i * 2, i * 2 + 2);
            int value = Integer.parseInt(subStr, 16);
            if (value < Byte.MIN_VALUE || value > Byte.MAX_VALUE) {
                throw new IllegalArgumentException("转换的数值超出byte范围");
            }
            ret[i] = (byte) value;
        }
        return ret;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, String s, ByteBuf byteBuf) throws Exception {
        if (!isJsonFormat(s)) {
            throw new IllegalArgumentException("输入的字符串并不是JSON格式");
        }
        try {
            // 将JSON字符串转换为Base64编码
            // String base64Encoded = Base64.getEncoder().encodeToString(s.getBytes(StandardCharsets.UTF_8));
            // logger.info("原来数据：" + s + "，Base64编码: " + base64Encoded);
            // 将Base64编码转换为字节数组
            byte[] jsonBytes = s.getBytes(StandardCharsets.UTF_8);
            byteBuf.writeBytes(jsonBytes);
        } catch (Exception e) {
            logger.severe("消息编码异常: " + e.getMessage());
            // 根据具体情况决定是否需要关闭通道或者抛出异常
            channelHandlerContext.close();
            throw e;
        }
    }

    private boolean isJsonFormat(String s) {
        if (s == null || s.trim().isEmpty()) {
            return false;
        }
        try {
            // 使用JSON库验证字符串是否是有效的JSON格式
            new JSONObject(s);
            return true;
        } catch (Exception e) {
            logger.severe("JSON解析异常: " + e.getMessage());
            return false;
        }
    }
}
