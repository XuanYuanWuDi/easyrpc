import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.IOException;

/**
 * @Auther: hlj
 * @Date: 2019/1/11 16:19
 * @Description:
 */
public class NettyClient {


    private final String host;
    private final int port;
    private Channel channel;

    //连接服务端的端口号地址和端口号
    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }



    public void start(final String userId) throws IOException, InterruptedException {

        Bootstrap bootstrap = new Bootstrap();

        final NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {

                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        channel.pipeline().addLast(new RpcEncoder(RpcRequest.class));
                        channel.pipeline().addLast(new RpcDecoder(RpcRequest.class));
                        channel.pipeline().addLast(new ClientHandler(userId));
                    }
                });


        this.channel = bootstrap.connect(host, port).sync().channel();

    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
