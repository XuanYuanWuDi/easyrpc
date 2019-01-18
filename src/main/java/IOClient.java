import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Auther: hlj
 * @Date: 2019/1/11 11:40
 * @Description:
 */
public class IOClient {

    static ExecutorService service = Executors.newCachedThreadPool();


    public static void main(String[] args) {

        service.execute(new Client());
        service.execute(new Client());
        service.execute(new Client());
        service.execute(new Client());
        service.execute(new Client());
        service.execute(new Client());
        service.execute(new Client());
        service.execute(new Client());
        service.execute(new Client());

    }


    static class Client implements Runnable{

        @Override
        public void run() {

            try {
                Socket socket = new Socket("127.0.0.1",8000);

                while(true){

                    OutputStream outputStream = socket.getOutputStream();

                    outputStream.write(("hello" + Math.random()).getBytes());

                    outputStream.flush();

                    System.out.println(Thread.currentThread().getId());

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
