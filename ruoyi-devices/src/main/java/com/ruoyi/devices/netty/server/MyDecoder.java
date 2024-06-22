package com.ruoyi.devices.netty.server;

import cn.hutool.json.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Set;

/**
 * @author lfx
 * @program device-socket
 * @description 自定义解码器
 * @date 2024/04/11
 */
public class MyDecoder extends ByteToMessageDecoder {

    /**
     * 定义返回的数据的最小长度
     */
    private static final int MINIMUM_DATA_LENGTH = 9;

    /**
     * 临时字节数组，用于存储读取的数据
     */
    private static final byte[] TEMP_BYTE_ARRAY = new byte[MINIMUM_DATA_LENGTH];
    private static final Logger log = LoggerFactory.getLogger(MyDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        // 确保有足够字节进行Base64解码和JSON解析
        // Base64最小解码长度为4（对应3字节数据），考虑到JSON至少包含{}，故设定最小长度为9
        if (byteBuf.readableBytes() < MINIMUM_DATA_LENGTH) {
            return;
        }

        /**
         * 标记当前的读取索引位置，以便之后可以通过调用resetReaderIndex()方法恢复到此标记的位置。
         * 这个方法经常在需要临时保存当前读取位置，之后又需要返回到这个位置的情况下使用。
         * 例如，在处理协议消息头时，可能需要先标记读取位置，然后读取消息头，确定消息体的长度，
         * 最后重置读取索引到标记位置以读取消息体。
         */
        byteBuf.markReaderIndex();

        try {
            // 读取所有可以读取的字节到字节数组中
            byte[] data = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(data);

            // 进行base64解码
            // data = Base64.getDecoder().decode(data);
            // log.info("解码后的数据:{}", data);

            InputStream inputStream = new ByteArrayInputStream(data);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            String jsonLine;
            while ((jsonLine = in.readLine()) != null) {
                log.info("解码后的数据:{}", jsonLine);
                // 找到第一个双引号（忽略前导部分）
                // int start = jsonLine.indexOf("\"", jsonLine.indexOf("=") + 1);
                // // 找到最后一个双引号
                // int end = jsonLine.lastIndexOf("\"");
                // // 提取第一个双引号到最后一个双引号之间的子串,并且去除首尾空格
                // String jsonString = jsonLine.substring(start + 1, end).trim();
                // 解析判断单片机发送是否为JSON数据，校验传输的数据是否为json格式
                JSONObject receivedJson = new JSONObject(jsonLine);
                list.add(receivedJson);

                // 统一使用bytesToHexString方法转换数据
                // String msg = bytesToHexString(receivedJson);
            }

        } catch (Exception e) {
            // 异常处理，例如记录日志、重置读取索引等
            byteBuf.resetReaderIndex();
            // 适当的异常处理逻辑
            e.printStackTrace();
        }
    }

    // 优化后的bytesToHexString方法
    public String bytesToHexString(byte[] bArray) {
        if (bArray == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(bArray.length);
        String sTemp;
        for (byte b : bArray) {
            sTemp = Integer.toHexString(0xFF & b);
            if (sTemp.length() < 2) {
                sb.append(0);
            }
            sb.append(sTemp.toUpperCase());
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

    // 删除了未使用的toHexString1方法簇，以简化代码

}
