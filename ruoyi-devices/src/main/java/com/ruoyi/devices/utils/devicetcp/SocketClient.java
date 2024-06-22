package com.ruoyi.devices.utils.devicetcp;

import org.bouncycastle.util.Arrays;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * @author ZMYQ
 */
public class SocketClient {
    private static final Logger LOGGER = Logger.getLogger(SocketClient.class.getName());
    // private static final String SERVER_ADDRESS = "8.135.10.183";
    // private static final int SERVER_PORT = 31355;
    private static final String SERVER_ADDRESS = "8.135.10.183";
    private static final int SERVER_PORT = 35640;
    private static final String EXIT_COMMAND = "exit";
    private static final int TIMEOUT = 5000;
    private static final int MAX_CONNECTIONS = 5;

    public static void main(String[] args) {
        int connectionCount = 0;
        while (true) {
            try (Socket socket = createSocketWithTimeout(SERVER_ADDRESS, SERVER_PORT, TIMEOUT) ){
                processSocketConnection(socket);
                connectionCount = MAX_CONNECTIONS;
            } catch (UnknownHostException e) {
                LOGGER.log(Level.SEVERE, "设备连接失败：未知的主机", e);
                connectionCount++;
            }catch (SocketTimeoutException e){
                LOGGER.log(Level.SEVERE, "设备连接失败：连接超时", e);
                connectionCount++;
            }
            catch (IOException e) {
                LOGGER.log(Level.SEVERE, "设备连接失败：IO异常", e);
                connectionCount++;
            }
        }
    }

    private static Socket createSocketWithTimeout(String serverAddress, int serverPort, int timeout) throws IOException {

        Socket socket = new Socket();
        socket.connect(new java.net.InetSocketAddress(serverAddress, serverPort), timeout);
        return socket;
    }

    private static void processSocketConnection(Socket socket) throws IOException {
        try (InputStream is = socket.getInputStream();
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
            // 假设变量已定义
            String abc = "111";
            String deviceName = "ZMYQAA";
            String deviceId = "ID001";
            String command = "Led2Set";

            // 使用JSON对象构建JSON字符串，避免直接字符串拼接
            JSONObject jsonObject = new JSONObject();
            String key = "*" + abc + "*";
            String value = deviceName + "+" + deviceId + "+" + command;
            // 注意：在最终的生产代码中，可能需要对value进行额外的转义处理，确保没有特殊字符

            jsonObject.put(key, value);

            String jsonString = jsonObject.toString();

            writer.println(jsonString);
            writer.flush();

            // 读取并处理二进制数据
            handleBinaryData(is);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "处理Socket连接时发生错误", e);
        }
    }

    private static void handleBinaryData(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            // 处理从服务端接收到的二进制数据
            byte[] receivedData = Arrays.copyOfRange(buffer, 0, bytesRead);
            String utf8String = new String(receivedData, StandardCharsets.UTF_8);
            // 使用InputStreamReader和JSONTokener解析字符串为JSONObject
            //JSONObject jsonObject = new JSONObject(new JSONTokener(new ByteArrayInputStream(utf8String.getBytes())));

            // 根据实际需求对receivedData进行进一步处理，如保存到文件、解码为特定格式等
            System.out.println("接收到的数据： " + utf8String);
        }
    }
}
