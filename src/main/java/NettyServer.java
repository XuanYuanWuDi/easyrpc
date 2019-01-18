import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: hlj
 * @Date: 2019/1/11 16:19
 * @Description:
 */
public class NettyServer {

    public static final Map<String,ChannelHandlerContext> userMap = new ConcurrentHashMap<String,ChannelHandlerContext>();

    public static void main(String[] args) throws InterruptedException {

        ServerBootstrap server = new ServerBootstrap();

        NioEventLoopGroup boss = new NioEventLoopGroup();

        NioEventLoopGroup work = new NioEventLoopGroup();

        server.group(boss,work)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new RpcDecoder(RpcRequest.class));
                        nioSocketChannel.pipeline().addLast(new RpcEncoder(RpcRequest.class));
                        nioSocketChannel.pipeline().addLast(new LoginHandler());
                        nioSocketChannel.pipeline().addLast(new ServerHandler());
                    }
                });


        server.bind(8080).sync().addListener(new ChannelFutureListener(){

            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    System.out.println("服务器开启成功");
                } else {
                    System.out.println("服务器开启失败");
                    channelFuture.cause().printStackTrace();
                }
            }
        });
    }
}
