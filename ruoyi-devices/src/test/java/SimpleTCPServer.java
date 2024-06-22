import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class SimpleTCPServer {
    public static void main(String[] args) throws Exception {
        int port = 8099; // 选择一个端口号
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("服务器启动，监听端口：" + port);

        while (true) {
            // 监听并接受客户端的连接
            Socket clientSocket = serverSocket.accept();
            System.out.println("客户端连接成功：" + clientSocket.getRemoteSocketAddress());

            // 处理请求
            //BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            InputStream inputStream = clientSocket.getInputStream();
            byte[] buffer = new byte[1024]; // 或根据预期最大数据大小调整缓冲区大小
            int bytesRead = inputStream.read(buffer);
            String asciiData = new String(buffer, 0, bytesRead, StandardCharsets.US_ASCII);

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            // String message;
            // while ((message = in.readLine()) != null) {
            //     inputStringBuilder.append(message);
            //     System.out.println("客户端消息：" + message);
            //     out.println("服务器回应：" + message); // 可以根据需要修改回应内容
            // }
            System.out.println("客户端消息：" + asciiData);

            String command = "{\"*111*\":\"ZMYQAA+ID001+Led2Set\"}";

            out.println(command);

            clientSocket.close();
        }
    }
}