import java.io.Serializable;

/**
 * @Auther: hlj
 * @Date: 2019/1/11 17:09
 * @Description:
 */
public class RpcResponse implements Serializable {

    private String id;

    private Object data;

    private String merNumber;

    private String time;

    private String userId;

    private String toUserId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMerNumber() {
        return merNumber;
    }

    public void setMerNumber(String merNumber) {
        this.merNumber = merNumber;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    @Override
    public String toString() {
        return "RpcResponse{" +
                "id='" + id + '\'' +
                ", data=" + data +
                ", merNumber='" + merNumber + '\'' +
                ", time='" + time + '\'' +
                ", userId='" + userId + '\'' +
                ", toUserId='" + toUserId + '\'' +
                '}';
    }
}
