package easyrpc.rpcHttpServer;

import easyrpc.rpcServer.ServerLocalCache;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * @Auther: hlj
 * @Date: 2019/1/16 13:52
 * @Description:
 */
public class HttpRpcServer {

    public static void main(String[] args) throws InterruptedException {

        ServerBootstrap server = new ServerBootstrap();

        NioEventLoopGroup boss = new NioEventLoopGroup();

        NioEventLoopGroup work = new NioEventLoopGroup();

        server.group(boss,work)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new HttpRequestDecoder());
                        nioSocketChannel.pipeline().addLast(new HttpObjectAggregator(65535));
                        nioSocketChannel.pipeline().addLast(new HttpResponseEncoder());
                        nioSocketChannel.pipeline().addLast(new HttpServerHandler());
                    }
                });

        server.bind(8080).sync().addListener(new ChannelFutureListener(){

            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    System.out.println("服务器开启成功");
                    //注册类和接口的关系
                    ServerLocalCache.add("easyrpc.test.Test","easyrpc.test.TestImpl");
                } else {
                    System.out.println("服务器开启失败");
                    channelFuture.cause().printStackTrace();
                }
            }
        });
    }
}
