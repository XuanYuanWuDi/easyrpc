import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


/**
 * @Auther: hlj
 * @Date: 2019/1/14 11:43
 * @Description:
 */
public class LoginHandler extends SimpleChannelInboundHandler<RpcRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest request) throws Exception {
        if(NettyServer.userMap.get(request.getUserId()) == null) {
            NettyServer.userMap.put(request.getUserId(), ctx);
        }
        if("0x256".equals(request.getMerNumber())) {
            ctx.fireChannelRead(request);
        }
    }
}
