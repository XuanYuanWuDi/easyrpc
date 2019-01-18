import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @Auther: hlj
 * @Date: 2019/1/11 17:31
 * @Description:
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    private String userId;

    public ClientHandler(String userId){
          this.userId = userId;
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        RpcRequest request = JSONObject.parseObject(JSONObject.toJSONString(msg),RpcRequest.class);
        System.out.println("时间:" + request.getTime() +"\r\n"+ "发送人:"+request.getUserId() +
                "\r\n" + "消息:"+request.getData());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("成功连接服务器");

        RpcRequest request = new RpcRequest();
        request.setId(UUID.randomUUID().toString());
        request.setData("LOGIN");
        request.setUserId(userId);
        request.setToUserId("2");
        request.setMerNumber("0x257");
        request.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date()));
        ctx.writeAndFlush(request);
    }
}
