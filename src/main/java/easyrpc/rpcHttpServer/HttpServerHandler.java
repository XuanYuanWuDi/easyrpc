package easyrpc.rpcHttpServer;


import com.alibaba.fastjson.JSONObject;
import easyrpc.model.EasyRpcRequest;
import easyrpc.model.EasyRpcResponse;
import easyrpc.rpcServer.ServerLocalCache;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.lang.reflect.Method;

/**
 * @Auther: hlj
 * @Date: 2019/1/16 13:54
 * @Description:
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端链接成功");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        System.out.println("服务端接收到客服端消息为" + request);

        byte[] reqContent = new byte[request.content().readableBytes()];

        request.content().readBytes(reqContent);

        EasyRpcRequest easyRpcRequest = JSONObject.parseObject(new String(reqContent),EasyRpcRequest.class);

        String className = ServerLocalCache.get(easyRpcRequest.getInterfaceClass());

        Object obj = Class.forName(className).newInstance();
        String methodName = easyRpcRequest.getMethodName();
        Class<?>[] type = easyRpcRequest.getMethodParamClass();
        Object[] param = easyRpcRequest.getMethodParam();
        Method method = obj.getClass().getMethod(methodName, type);
        Object result = method.invoke(obj, param);


        EasyRpcResponse response =  new EasyRpcResponse();
        response.setResult(result);
        response.setRequestId(easyRpcRequest.getRequestId());
        response.setMethodParamClass(type);
        response.setMethodParam(param);
        response.setMethodName(methodName);

        DefaultFullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK, Unpooled.wrappedBuffer(JSONObject.toJSONString(response).getBytes()));

        httpResponse.headers().set(HttpHeaders.Names.CONTENT_TYPE, "application/json");
        httpResponse.headers().set("requestId",easyRpcRequest.getRequestId());
        httpResponse.headers().set(HttpHeaders.Names.CONTENT_LENGTH, httpResponse.content().readableBytes());

        System.out.println("服务端处理完毕,返回结果为" + httpResponse);
        ctx.writeAndFlush(httpResponse).addListener(ChannelFutureListener.CLOSE);
    }

}
