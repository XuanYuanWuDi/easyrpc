package easyrpc.rpcClient;

import easyrpc.model.EasyRpcRequest;
import easyrpc.model.EasyRpcResponse;
import easyrpc.rpccode.RpcDecoder;
import easyrpc.rpccode.RpcEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @Auther: hlj
 * @Date: 2019/1/16 11:42
 * @Description:
 */
public class RpcClient {

    /**
     * 请求的服务端地址
     */
    private String host;

    /**
     * 请求的服务端端口
     */
    private Integer port;


    public RpcClient(String host,Integer port){
           this.host = host;
           this.port = port;
    }

    public EasyRpcResponse send(EasyRpcRequest request) throws InterruptedException {
        EasyRpcResponse response = new EasyRpcResponse();
        Bootstrap bootstrap = new Bootstrap();

        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
        try {
            bootstrap.group(nioEventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {

                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(new RpcEncoder(EasyRpcRequest.class));
                            ch.pipeline().addLast(new RpcDecoder(EasyRpcResponse.class));
                            ch.pipeline().addLast(new ClientHandler());
                        }
                    });
            Channel channel = bootstrap.connect(host, port).sync().channel();

            channel.writeAndFlush(request);

            /**
             * 因为netty默认为异步,所以要确认客户端正确收到了回复才进行返回
             * 但是也不能无限制的进行空转,所以限制空转时间为1分钟
             */
            long beginTime = System.currentTimeMillis();

            while((System.currentTimeMillis() - beginTime) <= (60 * 1000)) {
                if(ClientLocalCache.get(request.getRequestId()) != null ) {
                    response = ClientLocalCache.get(request.getRequestId());
                    channel.closeFuture().sync();
                    return response;
                }
            }
            return response;
        }finally {
            //释放资源
            nioEventLoopGroup.shutdownGracefully();
        }
    }
}
