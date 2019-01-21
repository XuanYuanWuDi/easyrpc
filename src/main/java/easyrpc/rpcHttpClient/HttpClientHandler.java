package easyrpc.rpcHttpClient;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;


/**
 * @Auther: hlj
 * @Date: 2019/1/16 11:51
 * @Description:
 */
public class HttpClientHandler extends SimpleChannelInboundHandler<FullHttpResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpResponse response) throws Exception {
        System.out.println("服务端返回结果为" + response);

        if(response.status().code() == HttpResponseStatus.OK.code()) {
            //这个必须有,因为SimpleChannelInboundHandler的channelRead0会自动调用release,清除对象引用
            response.content().retain();
            HttpClientLocalCache.add(response.headers().get("requestId"), response);
        }
    }
}
