package com.ruoyi.devices.netty.server;

import com.ruoyi.devices.service.DeviceAuthService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Netty服务端实现类，用于启动Netty服务器
 * @author lfx
 * @date 2024/06/12 14:05
 */
@Slf4j
@Component
public class NettyServer implements CommandLineRunner {
    /**
     * 已分配端口的列表，用于确保端口不被重复使用。
     */
    private volatile List<Integer> assignedPorts = new CopyOnWriteArrayList<>();
    /**
     * 用于接受进来的连接请求的事件循环组。
     */
    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    /**
     * 用于处理已接受的连接请求的事件循环组。
     */
    private EventLoopGroup workerGroup = new NioEventLoopGroup();
    /**
     * 最大重试次数，用于端口绑定失败时的重试。
     */
    private static final int MAX_RETRY_ATTEMPTS = 5;
    /**
     * 重试延迟时间，用于端口绑定失败时的重试间隔。
     */
    private static final long RETRY_DELAY_MS = 1000L;

    @Autowired
    private DeviceAuthService deviceAuthService;

    @Override
    public void run(String... args) {
        // 启动服务器监听认证端口，后续端口可以根据需求进行修改
        start(8090);

        // 从数据库中获取已分配的端口并且启动服务器监听
        // List<Integer> ports = deviceAuthService.findAllAllocatedPorts();
        // if (ports != null && !ports.isEmpty()) {
        //     for (Integer port : ports) {
        //         start(port);
        //     }
        // }
        start(8099);
    }

    public void start(int initialPort) {
        int retryAttempts = 0;
        while (retryAttempts <= MAX_RETRY_ATTEMPTS) {
            try {
                if (!assignedPorts.contains(initialPort)) { // 避免端口重复使用
                    bindPort(initialPort);
                    log.info("Netty服务器启动成功，开始监听端口：{}", initialPort);
                    return;
                } else {
                    throw new IllegalStateException("端口已被占用：" + initialPort);
                }
            } catch (Exception e) {
                log.error("尝试启动Netty服务器失败，第{}次重试，原因：{}", retryAttempts + 1, e.getMessage());
                if (retryAttempts < MAX_RETRY_ATTEMPTS) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(RETRY_DELAY_MS);
                    } catch (InterruptedException interruptedException) {
                        Thread.currentThread().interrupt(); // 保留中断状态
                        log.error("线程中断，无法继续重试", interruptedException);
                        shutdownGracefully(bossGroup, workerGroup); // 确保资源释放
                        return;
                    }
                } else {
                    log.error("重试次数已达上限，未能成功启动Netty服务器");
                    shutdownGracefully(bossGroup, workerGroup); // 确保资源释放
                    return;
                }
            }
            retryAttempts++;
        }
    }

    /**
     * 创建服务器启动对象，用于配置和启动Netty服务器。
     *
     * @return ServerBootstrap 对象，配置完成后的服务器启动器。
     */
    private ServerBootstrap createServerBootstrap(boolean useAuthChannelInitializer) throws Exception {
        return new ServerBootstrap()
                // 配置线程组，用于处理连接接受和数据读写。
                .group(bossGroup, workerGroup)
                // 指定使用的通道类型为NIO服务器套接字通道。
                .channel(NioServerSocketChannel.class)
                // 根据是否启用认证，选择不同的通道初始化器。
                .childHandler(useAuthChannelInitializer ? new AuthChannelInitializer() : new NettyServerChannelInitializer())
                // 配置服务器的连接队列大小。
                .option(ChannelOption.SO_BACKLOG, 128)
                // 配置客户端连接的保持活动状态。
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                // 配置TCP的延迟发送属性，以减少延迟。
                .childOption(ChannelOption.TCP_NODELAY, true);

    }

    private void shutdownGracefully(EventLoopGroup... groups) {
        for (EventLoopGroup group : groups) {
            if (group != null) {
                group.shutdownGracefully();
                log.info("优雅地关闭了EventLoopGroup：{}", group.getClass().getSimpleName());
            }
        }
    }

    private void bindPort(int port) {
        try {
            ServerBootstrap bootstrap = createServerBootstrap(port == 8090); // 通过参数决定是否使用AuthChannelInitializer
            ChannelFuture future = bootstrap.bind(new InetSocketAddress(port)).sync();
            future.channel().closeFuture().addListener(f -> {
                if (!f.isSuccess()) {
                    log.error("端口 {} 绑定失败", port, f.cause());
                    shutdownGracefully(bossGroup, workerGroup);
                }
            });

            assignedPorts.add(port);
        } catch (Exception e) {
            log.error("端口 {} 绑定失败，原因：{}", port, e.getMessage());
        }
    }
}
