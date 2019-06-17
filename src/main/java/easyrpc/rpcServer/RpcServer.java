package easyrpc.rpcServer;

import easyrpc.model.EasyRpcRequest;
import easyrpc.model.EasyRpcResponse;
import easyrpc.rpcCode.RpcDecoder;
import easyrpc.rpcCode.RpcEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Auther: hlj
 * @Date: 2019/1/16 13:52
 * @Description:
 */
public class RpcServer {

    public static void main(String[] args) throws InterruptedException {

        ServerBootstrap server = new ServerBootstrap();

        NioEventLoopGroup boss = new NioEventLoopGroup();

        NioEventLoopGroup work = new NioEventLoopGroup();

        server.group(boss,work)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new RpcDecoder(EasyRpcRequest.class));
                        nioSocketChannel.pipeline().addLast(new RpcEncoder(EasyRpcResponse.class));
                        nioSocketChannel.pipeline().addLast(new ServerHandler());
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
