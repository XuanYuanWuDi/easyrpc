package easyrpc.proxy;

import com.alibaba.fastjson.JSONObject;
import easyrpc.model.EasyRpcRequest;
import easyrpc.model.EasyRpcResponse;
import easyrpc.rpcClient.RpcClient;
import easyrpc.rpcHttpClient.HttpRpcClient;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

/**
 * @Auther: hlj
 * @Date: 2019/1/16 11:33
 * @Description:
 */
public class RpcProxy {


    public static <T> Object create(Class<?> interfaceClass, final String interfaceRefence
            ,final String protocol) {

        return Proxy.newProxyInstance(interfaceClass.getClassLoader()
                , new Class<?>[]{interfaceClass}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        EasyRpcRequest request = new EasyRpcRequest();
                        request.setMethodName(method.getName());
                        request.setMethodParam(args);
                        request.setMethodParamClass(method.getParameterTypes());
                        request.setRequestId(UUID.randomUUID().toString());
                        request.setInterfaceClass(interfaceRefence);

                        /**
                         * 根据请求协议创建不同的客户端
                         */
                        if("easy".equalsIgnoreCase(protocol)) {
                            return easy(request);
                        }else if("http".equalsIgnoreCase(protocol)){
                            return http(request);
                        }
                        throw new RuntimeException("Exception---");
                    }
                }
        );
      }


      private static final  <T> Object easy(EasyRpcRequest request) throws InterruptedException {
          RpcClient client = new RpcClient("127.0.0.1", 8080);

          EasyRpcResponse response = client.send(request);

          if (response != null) {
              return response.getResult();
          }
          return null;
      }


    private static final  <T> Object http(EasyRpcRequest request) throws InterruptedException, URISyntaxException, UnsupportedEncodingException {
        URI uri = new URI("http://127.0.0.1:8080");


        FullHttpRequest httpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1,
                HttpMethod.POST,uri.toASCIIString(), Unpooled.wrappedBuffer(JSONObject.toJSONString(request)
                .getBytes("UTF-8")));

        httpRequest.headers().set(HttpHeaders.Names.HOST, "127.0.0.1");
        httpRequest.headers().set(HttpHeaders.Names.CONTENT_TYPE,"application/json");
        httpRequest.headers().set(HttpHeaders.Names.CONTENT_LENGTH, httpRequest.content().readableBytes());//可以在httpRequest.headers中设置各种需要的信息。

        HttpRpcClient client = new HttpRpcClient("127.0.0.1",8080,request);

        FullHttpResponse response = client.send(httpRequest);

        if(response.status().code() == HttpResponseStatus.OK.code()){
            byte [] b = new byte [response.content().readableBytes()];
            response.content().readBytes(b);
            return JSONObject.parseObject(new String(b),EasyRpcResponse.class).getResult();
        }
        return null;
    }
}
