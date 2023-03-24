package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


/**
 * 融合到spring 中的netty 启动类
 * 交给spring管理
 */
@Slf4j
//@Component
public class SpringNettyTcpServer {

    @Value("${netty.port}")
    private Integer port;

    //请求处理线程池,包含了一组nio线程,用于网络事件的处理
    private EventLoopGroup workerGroup = null;
    private EventLoopGroup bossGroup = null;
    //Netty引导对象,用于引导服务启动
    private ServerBootstrap serverBootstrap = null;

    @Autowired
    private SpringNettyTcpByteHandler springNettyTcpByteHandler;

    public SpringNettyTcpServer(){
        log.info("=初始化SpringNettyTcpServer=");
    }

    @PostConstruct
    public void start() {
        log.info("=启动netty-tcp-server.进行中=");
        //将本实例传递到TCP处理器类，用于回调本实例的receivedData方法
//        NettyTcpStringHandler.nettyTcpServer = this;
        //用于78点取数

        //初始化netty对象
        workerGroup = new NioEventLoopGroup();
        bossGroup = new NioEventLoopGroup();
        serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(workerGroup,bossGroup);
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024); //连接数
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true); //长连接
        serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) {
                ChannelPipeline pipeline = socketChannel.pipeline();

//                pipeline.addLast(new LineBasedFrameDecoder(1024));
//                pipeline.addLast(new StringDecoder());
//                pipeline.addLast(new NettyTcpStringHandler());

                //用于78点取数
                pipeline.addLast(new ByteArrayDecoder());
                pipeline.addLast(new ByteArrayEncoder());
//                pipeline.addLast(new NettyTcpByteHandler());
                pipeline.addLast(springNettyTcpByteHandler);

            }
        });
        // 获取服务启动后的IO状态对象，阻塞直至停止监听
        try {
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            if (channelFuture.isSuccess()){
                log.info("=启动netty-tcp-server 成功=");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    private void destory() throws Exception{
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
        log.info("关闭netty-server");
    }

}
