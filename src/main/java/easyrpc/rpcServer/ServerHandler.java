package easyrpc.rpcServer;


import easyrpc.model.EasyRpcRequest;
import easyrpc.model.EasyRpcResponse;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Method;
import java.net.InetAddress;

/**
 * @Auther: hlj
 * @Date: 2019/1/16 13:54
 * @Description:
 */
public class ServerHandler extends SimpleChannelInboundHandler<EasyRpcRequest> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("11111110000000011111111111");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, EasyRpcRequest msg) throws Exception {
        System.out.println("服务端接收到客服端消息为" + msg);

        String className = ServerLocalCache.get(msg.getInterfaceClass());

        Object obj = Class.forName(className).newInstance();
        String methodName = msg.getMethodName();
        Class<?>[] type = msg.getMethodParamClass();
        Object[] param = msg.getMethodParam();
        Method method = obj.getClass().getMethod(methodName, type);
        Object result = method.invoke(obj, param);


        EasyRpcResponse response =  new EasyRpcResponse();
        response.setResult(result);
        response.setRequestId(msg.getRequestId());
        response.setMethodParamClass(msg.getMethodParamClass());
        response.setMethodParam(msg.getMethodParam());
        response.setMethodName(msg.getMethodName());
        System.out.println("服务端处理完毕,返回结果为" + response);
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE); ;
    }
}
