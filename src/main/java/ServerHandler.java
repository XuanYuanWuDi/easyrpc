import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


/**
 * @Auther: hlj
 * @Date: 2019/1/11 17:22
 * @Description:
 */
public class ServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest request) throws Exception {
        System.out.println("接收到客户端信息:" + request.toString());

        String toUserId = request.getToUserId();
        ChannelHandlerContext context = NettyServer.userMap.get(toUserId);
        if(context != null) {
            context.channel().writeAndFlush(request);
        }
    }
}
