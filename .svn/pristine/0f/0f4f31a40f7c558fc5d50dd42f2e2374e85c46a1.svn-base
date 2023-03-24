package com.penghaisoft.wcs.netty;

import com.penghaisoft.wcs.task.connect.PalletizerOtherTcpByteHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;


/**
 * netty tcp客户端
 * 负责连接线体主控PLC的
 */
@Slf4j
@Component
@ConditionalOnProperty(prefix = "wcs.config", name = "tcp-client", havingValue = "true")
public class PalletizerTcpClient {

    @Value("${wcs.config.tcp-server-ip}")
    private String IP;
    @Value("${wcs.config.tcp-server-port}")
    private Integer PORT;
    /**
     * NETTY接收数据长度，默认 1024
     */
    private static final int DATA_LEN = 1206;

    //请求处理线程池,包含了一组nio线程,用于网络事件的处理
    private EventLoopGroup group = null;
    //Netty引导对象,用于引导服务启动
    private Bootstrap clientBootstrap = null;

    @Autowired
    private PalletizerOtherTcpByteHandler palletizerOtherTcpByteHandler;


    @PostConstruct
    public void init() {
        log.info("=线体主控PLC tcp client启动连接=");
        try {
            group = new NioEventLoopGroup();
            clientBootstrap = new Bootstrap();
            clientBootstrap.group(group);
            clientBootstrap.channel(NioSocketChannel.class);
            clientBootstrap.option(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(DATA_LEN));
            clientBootstrap.remoteAddress(new InetSocketAddress(IP, PORT));
            clientBootstrap.handler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new ByteArrayDecoder());
                    socketChannel.pipeline().addLast(new ByteArrayEncoder());
                    socketChannel.pipeline().addLast(palletizerOtherTcpByteHandler);
                }
            });
            ChannelFuture channelFuture = clientBootstrap.connect().sync();
            if (channelFuture.isSuccess()) {
                log.info("=线体主控PLC tcp client连接 PLC成功，开始接收PLC数据=");

            }

//            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        finally {
//            关闭代码
//            group.shutdownGracefully().sync();
//        }
    }

    /**
     * 重启tcp client,关闭了重连
     */
    public void restart() {
        try {
            group.shutdownGracefully().sync();
            init();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        if(!group.isShutdown()){
//        }
    }

}
