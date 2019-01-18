package easyrpc.rpcClient;

import easyrpc.model.EasyRpcResponse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: hlj
 * @Date: 2019/1/16 14:38
 * @Description:
 */
public class ClientLocalCache {

    /**
     * 用于确认消息正确返回
     */
    private static Map<String, EasyRpcResponse> map = new ConcurrentHashMap<String,EasyRpcResponse>();


    public static void add(String key,EasyRpcResponse value){
        map.put(key,value);
    }

    public static void remove(String key){
        map.remove(key);
    }

    public static EasyRpcResponse get(String key){
        return  map.get(key);
    }
}
