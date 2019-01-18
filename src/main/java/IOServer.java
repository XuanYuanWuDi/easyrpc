import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Auther: hlj
 * @Date: 2019/1/11 11:40
 * @Description:
 */
public class IOServer {

    public static ServerSocket serverSocket;

    static {
        try {
            serverSocket = new ServerSocket(8000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        Thread thread = new Thread(new ServerThread());

        thread.start();

    }


     static class ServerThread implements Runnable{

        @Override
        public void run() {

            while(true){

                try {
                    Socket socket = serverSocket.accept();

                    Thread thread = new Thread(new ServerThread2(socket));

                    thread.start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    static class ServerThread2 implements Runnable{

        Socket socket;

        ServerThread2 (Socket socket){
            this.socket = socket;
        }

        @Override
        public void run() {

            while(true){

                try {
                    InputStream input = socket.getInputStream();

                    byte[] data = new byte[1024];

                    int len = 0;

                    while((len = input.read(data)) != -1){
                        System.out.println(new String(data,0,len));
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
