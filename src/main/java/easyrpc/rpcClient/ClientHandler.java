package easyrpc.rpcClient;

import easyrpc.model.EasyRpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Auther: hlj
 * @Date: 2019/1/16 11:51
 * @Description:
 */
public class ClientHandler extends SimpleChannelInboundHandler<EasyRpcResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, EasyRpcResponse msg) throws Exception {
        System.out.println("服务端返回结果为" + msg);
        EasyRpcResponse easyRpcResponse = new EasyRpcResponse();
        easyRpcResponse.setMethodName(msg.getMethodName());
        easyRpcResponse.setMethodParam(msg.getMethodParam());
        easyRpcResponse.setMethodParamClass(msg.getMethodParamClass());
        easyRpcResponse.setRequestId(msg.getRequestId());
        easyRpcResponse.setResult(msg.getResult());
        ClientLocalCache.add(msg.getRequestId(),easyRpcResponse);
    }
}
