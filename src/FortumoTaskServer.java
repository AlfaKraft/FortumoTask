import java.io.IOException;


public class FortumoTaskServer {


    private final static int port = 8082;
    static final String WEB_ROOT = "C:\\Users\\Geitrud\\ExampleServer\\src";


    public static void main(String[] args) {
        System.out.println("Server is starting...");
        System.out.println("Using port: " + port);

        try {
            AcceptServerConnections serverListenerThread = new AcceptServerConnections(port, WEB_ROOT);
            serverListenerThread.start();
        } catch (IOException e){
            e.printStackTrace();
        }





    }

}
