package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;


/**
 * 基于Netty的TCP通信组件
 * @Date 2018年10月23日
 * @Author xc
 */
public class NettyTcpServer{

    //请求处理线程池,包含了一组nio线程,用于网络事件的处理
    private EventLoopGroup workerGroup = null;
    private EventLoopGroup bossGroup = null;
    //Netty引导对象,用于引导服务启动
    private ServerBootstrap serverBootstrap = null;

    /**
     * 初始化TCP监听
     */
    public void init() {
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
                pipeline.addLast(new NettyTcpByteHandler());

            }
        });
        // 获取服务启动后的IO状态对象，阻塞直至停止监听
        int tcpPort = 50000;
        try {
            ChannelFuture channelFuture = serverBootstrap.bind(tcpPort).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
