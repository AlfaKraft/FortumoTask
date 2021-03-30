import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.*;

public class AcceptServerConnections extends Thread {

    private int port;
    private String webroot;
    private ServerSocket serverSocket;
    public ThreadPoolExecutor service = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
    ArrayList<Thread> threads = new ArrayList<>();


    public AcceptServerConnections(int port, String webroot) throws IOException {
        this.port = port;
        this.webroot = webroot;
        this.serverSocket = new ServerSocket(this.port);

    }

    @Override
    public void run() {
        try {
            while (serverSocket.isBound() && !serverSocket.isClosed()) {
                ArrayBlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(20);

                Socket socket = serverSocket.accept();
                System.out.println("Connection accepted: " + socket.getInetAddress());

                HttpConnectionWorker httpConnectionWorker = new HttpConnectionWorker(socket, blockingQueue);
                httpConnectionWorker.start();






            }



        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null){
                try {
                    System.out.println(service.toString());
                    serverSocket.close();
                } catch (IOException e) {
                }
            }
        }

    }
}
