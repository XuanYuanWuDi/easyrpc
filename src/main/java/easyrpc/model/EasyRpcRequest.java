package easyrpc.model;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @Auther: hlj
 * @Date: 2019/1/16 11:14
 * @Description:
 */
public class EasyRpcRequest implements Serializable {

    /**
     * 请求的接口
     */
    private String interfaceClass;

    /**
     * 请求的方法名
     */
    private String methodName;

    /**
     * 请求的参数类型
     */
    private Class<?> [] methodParamClass;

    /**
     * 请求的参数
     */
    private Object [] methodParam;


    /**
     * 请求的编号
     */
    private String requestId;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getMethodParamClass() {
        return methodParamClass;
    }

    public void setMethodParamClass(Class<?>[] methodParamClass) {
        this.methodParamClass = methodParamClass;
    }

    public Object[] getMethodParam() {
        return methodParam;
    }

    public void setMethodParam(Object[] methodParam) {
        this.methodParam = methodParam;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(String interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    @Override
    public String toString() {
        return "EasyRpcRequest{" +
                "interfaceClass='" + interfaceClass + '\'' +
                ", methodName='" + methodName + '\'' +
                ", methodParamClass=" + Arrays.toString(methodParamClass) +
                ", methodParam=" + Arrays.toString(methodParam) +
                ", requestId='" + requestId + '\'' +
                '}';
    }
}
