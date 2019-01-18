package easyrpc.model;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @Auther: hlj
 * @Date: 2019/1/16 11:15
 * @Description:
 */
public class EasyRpcResponse implements Serializable {

    /**
     * 返回结果
     */
    private Object result;

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

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

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

    @Override
    public String toString() {
        return "EasyRpcResponse{" +
                "result=" + result +
                ", methodName='" + methodName + '\'' +
                ", methodParamClass=" + Arrays.toString(methodParamClass) +
                ", methodParam=" + Arrays.toString(methodParam) +
                ", requestId='" + requestId + '\'' +
                '}';
    }
}
