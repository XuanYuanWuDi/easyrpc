package easyrpc.test;

import easyrpc.proxy.RpcProxy;

/**
 * @Auther: hlj
 * @Date: 2019/1/16 14:26
 * @Description:
 */
public class TestClient {

    public static void main(String[] args) {
        Test test = (Test) RpcProxy.create(Test.class,"easyrpc.test.Test","easy");
        System.out.println("客户端收到执行结果为----" + test.hello());
    }
}
