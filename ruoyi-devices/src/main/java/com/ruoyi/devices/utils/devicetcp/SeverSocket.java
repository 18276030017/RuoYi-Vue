package com.ruoyi.devices.utils.devicetcp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author lfx
 * @program ruoyi
 * @description 处理公网ip获得的数据
 * @date 2024/04/11
 */
public class SeverSocket {
    public static void main(String[] args) {
        // 创建一个服务器端Socket，即ServerSocket，指定绑定的端口，并监听此端口
        try (ServerSocket server = new ServerSocket(8090)) {
            System.out.println("服务端就绪，等待客户端连接...");
            while (true) {
                // 调用accept()方法开始监听，等待客户端的连接
                Socket clientSocket = server.accept();
                String clientIp = clientSocket.getInetAddress().getHostAddress();
                String clientPort = String.valueOf(clientSocket.getPort());
                System.out.println("客户端IP：" + clientIp);
                System.out.println("客户端端口：" + clientPort);
                System.out.println("客户端连接成功！");

                // 获取输入流，并读取客户端信息
                // 创建线程来处理单片机的JSON数据和发送JSON数据
                Thread clientHandlerThread = new Thread(() -> handleClient(clientSocket));
                clientHandlerThread.start();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
                OutputStream out = clientSocket.getOutputStream()
        ) {
            String jsonLine;
            while ((jsonLine = in.readLine()) != null) {
                // 解析单片机发送的JSON数据
                JSONObject receivedJson = new JSONObject(jsonLine);
                System.out.println("Received JSON from single-board computer: " + receivedJson.toString(4));

                // 根据实际需求处理接收到的JSON数据，例如存储、转发、响应等

                // 示例中仅打印接收到的JSON数据

                // 构造要发送的JSON数据
                JSONObject responseJson = new JSONObject();
                responseJson.put("status", "success");
                responseJson.put("message", "Data received successfully");

                // 向单片机发送JSON数据
                out.write(responseJson.toString().getBytes(StandardCharsets.UTF_8));
                out.write("\n".getBytes(StandardCharsets.UTF_8)); // 添加换行符（假设单片机期望以换行符结束）
                out.flush(); // 确保数据立即发送
            }
        } catch (IOException | JSONException e) {
            System.err.println("Error handling client: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
