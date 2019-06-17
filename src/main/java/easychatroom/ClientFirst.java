package easychatroom;

import easychatroom.chatClient.NettyClient;
import easychatroom.model.RpcRequest;
import io.netty.channel.Channel;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

/**
 * @Auther: hlj
 * @Date: 2019/1/11 17:35
 * @Description:
 */
public class ClientFirst {

    public static void main(String[] args) throws IOException, InterruptedException {

        NettyClient client = new NettyClient("127.0.0.1", 8080);
        //启动client服务
        client.start("1");

        Channel channel = client.getChannel();

        while(true){
            Scanner input=new Scanner(System.in);

            String s = input.next();
            //消息体
            RpcRequest request = new RpcRequest();
            request.setId(UUID.randomUUID().toString());
            request.setData(s);
            request.setUserId("1");
            request.setToUserId("2");
            request.setMerNumber("0x256");
            request.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format(new Date()));
            channel.writeAndFlush(request);
        }


    }
}
