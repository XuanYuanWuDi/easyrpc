package easyrpc.rpcHttpClient;

import io.netty.handler.codec.http.FullHttpResponse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: hlj
 * @Date: 2019/1/18 15:24
 * @Description:
 */
public class HttpClientLocalCache {

    /**
     * 用于确认消息正确返回
     */
    private static Map<String, FullHttpResponse> map = new ConcurrentHashMap<String,FullHttpResponse>();


    public static void add(String key,FullHttpResponse value){
        map.put(key,value);
    }

    public static void remove(String key){
        map.remove(key);
    }

    public static FullHttpResponse get(String key){
        return  map.get(key);
    }
    public static boolean isExsit(String key){
        return  map.containsKey(key);
    }

}
