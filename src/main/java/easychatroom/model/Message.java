package easychatroom.model;

import java.io.Serializable;

/**
 * @Auther: hlj
 * @Date: 2019/1/11 16:53
 * @Description:
 */
public class Message implements Serializable {

    private String userid;

    private String msg;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
