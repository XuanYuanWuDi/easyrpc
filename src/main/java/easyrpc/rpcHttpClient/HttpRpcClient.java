package easyrpc.rpcHttpClient;

import easyrpc.model.EasyRpcRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;


/**
 * @Auther: hlj
 * @Date: 2019/1/18 14:10
 * @Description:
 */
public class HttpRpcClient {

    /**
     * 请求的服务端地址
     */
    private String host;

    /**
     * 请求的服务端端口
     */
    private Integer port;

    /**
     * 请求参数
     */
    private EasyRpcRequest easyRpcRequest;

    public HttpRpcClient(String host, Integer port,EasyRpcRequest easyRpcRequest) {
        this.host = host;
        this.port = port;
        this.easyRpcRequest = easyRpcRequest;
    }

    public FullHttpResponse send(FullHttpRequest request) throws InterruptedException {
        FullHttpResponse response;
        Bootstrap bootstrap = new Bootstrap();

        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
        try {

            bootstrap.group(nioEventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {

                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(new HttpRequestEncoder());
                            ch.pipeline().addLast(new HttpResponseDecoder());
                            ch.pipeline().addLast(new HttpObjectAggregator(65536));
                            ch.pipeline().addLast(new HttpClientHandler());
                        }
                    });
            Channel channel = bootstrap.connect(host, port).sync().channel();

            channel.writeAndFlush(request);

            /**
             * 因为netty默认为异步,所以要确认客户端正确收到了回复才进行返回
             * 但是也不能无限制的进行空转,所以限制空转时间为1分钟
             */
            long beginTime = System.currentTimeMillis();

            while ((System.currentTimeMillis() - beginTime) <= (60 * 1000)) {
                if (HttpClientLocalCache.isExsit(easyRpcRequest.getRequestId())) {
                    response = HttpClientLocalCache.get(easyRpcRequest.getRequestId());
                    channel.closeFuture().sync();
                    return response;
                }
            }
            return new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.FAILED_DEPENDENCY);
        } finally {
            //释放资源
            nioEventLoopGroup.shutdownGracefully();
        }
    }
}
