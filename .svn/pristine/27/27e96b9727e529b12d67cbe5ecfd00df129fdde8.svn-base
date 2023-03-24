package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;

import java.net.InetSocketAddress;


/**
 * netty tcp客户端
 */
public class NettyTcpClient {



    //请求处理线程池,包含了一组nio线程,用于网络事件的处理
    private EventLoopGroup group = null;
    //Netty引导对象,用于引导服务启动
    private Bootstrap clientBootstrap = null;


    /**
     * 初始化TCP监听
     */
    public void init(){
        try{
            group = new NioEventLoopGroup();
            clientBootstrap = new Bootstrap();

            clientBootstrap.group(group);
            clientBootstrap.channel(NioSocketChannel.class);
//            默认一次接收1024长度，短了要改一下
            clientBootstrap.option(ChannelOption.RCVBUF_ALLOCATOR,new FixedRecvByteBufAllocator(1206));
            clientBootstrap.remoteAddress(new InetSocketAddress("192.168.0.11", 2000));
//            clientBootstrap.remoteAddress(new InetSocketAddress("127.0.0.1", 50000));
            clientBootstrap.handler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new ByteArrayDecoder());
                    socketChannel.pipeline().addLast(new ByteArrayEncoder());
                    socketChannel.pipeline().addLast(new NettyTcpByteHandler());
                }
            });
            ChannelFuture channelFuture = clientBootstrap.connect().sync();
//            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
//            try {
//                group.shutdownGracefully().sync();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }

}
