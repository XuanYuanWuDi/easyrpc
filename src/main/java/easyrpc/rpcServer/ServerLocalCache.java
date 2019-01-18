package easyrpc.rpcServer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: hlj
 * @Date: 2019/1/16 14:16
 * @Description:
 */
public class ServerLocalCache {

    /**
     * 用于模拟spring容器存放接口和实现类的关系
     */
    private static Map<String,String> map = new ConcurrentHashMap<String,String>();


    public static void add(String key,String value){
        map.put(key,value);
    }

    public static void remove(String key){
        map.remove(key);
    }

    public static String get(String key){
        return  map.get(key);
    }
}
