package easyrpc.proxy;

import easyrpc.model.EasyRpcRequest;
import easyrpc.model.EasyRpcResponse;
import easyrpc.rpcClient.RpcClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * @Auther: hlj
 * @Date: 2019/1/16 11:33
 * @Description:
 */
public class RpcProxy {


    public static <T> Object create(Class<?> interfaceClass, final String interfaceRefence) {

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

                        RpcClient client = new RpcClient("127.0.0.1",8080);

                        EasyRpcResponse response = client.send(request);

                        if(response != null){
                            return response.getResult();
                        }
                        return null;
                    }
                }
        );
      }

}
