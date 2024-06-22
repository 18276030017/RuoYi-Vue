package com.ruoyi.devices.netty.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.*;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.net.ssl.SSLEngine;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 功能描述: 服务端初始化，客户端与服务器端连接一旦创建，这个类中方法就会被回调，设置出站编码器和入站解码器
 *
 * @Author keLe
 * @Date 2022/8/26
 */
@Slf4j
public class NettyServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // SSLEngine engine = null;
        // try {
        //     // 从resources目录下读取证书
        //     Resource serverCertResource = new ClassPathResource("cert/certificate.crt");
        //     Resource serverKeyResource = new ClassPathResource("cert/private_key_pkcs8.pem");
        //     // 确保文件存在
        //     if (!serverCertResource.exists() || !serverKeyResource.exists()) {
        //         throw new IllegalStateException("resource文件中没有找到相关证书");
        //     }
        //     String serverCert = serverCertResource.getFile().getAbsolutePath();
        //     String serverKey = serverKeyResource.getFile().getAbsolutePath();
        //     /**
        //      * 创建并初始化一个包含特定加密套件的列表。
        //      * <p>该列表包含了两种加密套件：</p>
        //      * <ul>
        //      *     <li>TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256: 使用ECDHE RSA密钥交换算法，AES 128位 GCM 加密算法，以及SHA256散列算法。</li>
        //      *     <li>TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384: 使用ECDHE RSA密钥交换算法，AES 256位 GCM 加密算法，以及SHA384散列算法。</li>
        //      * </ul>
        //      *
        //      * @return 返回一个包含指定加密套件的列表。
        //      */
        //     List<String> ciphers = Arrays.asList("TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256", "TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384");
        //     SslContext sslContext = SslContextBuilder.forServer(new File(serverCert), new File(serverKey))
        //             .sslProvider(SslProvider.OPENSSL)
        //             .ciphers(ciphers)
        //             .clientAuth(ClientAuth.NONE) // 指定客户端是否需要进行认证，require表示需要认证，none表示不需要认证
        //             .protocols("TLSv1.2")  // 指定支持的协议版本
        //             .build();
        //     engine = sslContext.newEngine(socketChannel.alloc());
        //     engine.setUseClientMode(false);  // 指定是否是客户端模式，true表示客户端模式，false表示服务端模式
        //     engine.setNeedClientAuth(false);  // 指定客户端是否需要进行认证，true表示需要认证，false表示不需要认证
        // } catch (Exception e) {
        //     log.error("初始化SSL Engine上下文失败", e);
        //     throw e; // 重新抛出异常，以便Netty可以处理连接错误
        // }

        // SocketChannel是NIO中的一种通道类型，可以用于进行网络通信。
        // ChannelPipeline是Netty中的一个重要的组件，它负责处理通道中的数据。它可以对数据进行编解码、压缩、加密等操作，并且可以动态地添加或移除处理程序。
        // 通过调用pipeline()方法，可以获取到与该SocketChannel关联的ChannelPipeline实例，从而可以对数据进行相应的处理
        // 设置服务端模式
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 使用Netty提供的SslHandler, 用于处理Ssl握手
        // pipeline.addFirst("ssl", new SslHandler(engine));
        // 接收消息格式,使用自定义解析数据格式
        pipeline.addLast("decoder", new MyDecoder());
        // 发送消息格式，使用自定义解析数据格式
        pipeline.addLast("encoder", new MyEncoder());

        // 针对客户端，如果在5分钟时没有想服务端发送写心跳(ALL)，则主动断开
        // 如果是读空闲或者写空闲，不处理,这里根据自己业务考虑使用
        // 这个处理器用于检测网络连接的空闲状态，即读、写活动是否超时
        pipeline.addLast(new IdleStateHandler(300,0,0, TimeUnit.SECONDS));
        // 自定义的空闲检测
        pipeline.addLast(new NettyServerHandler());
    }
}
