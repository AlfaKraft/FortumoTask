import javax.management.StringValueExp;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class HttpConnectionWorker extends Thread {
    Socket socket;
    String sentValue;
    int calculatedValue = 0;
    private BlockingQueue<String> queue = null;

    public HttpConnectionWorker(Socket socket, BlockingQueue<String> blockingQueue) {
        this.queue = blockingQueue;
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("Current Thread " +  Thread.currentThread().getId() + " is running");
        InputStream inputStream = null;
        OutputStream outputStream = null;
        RequestHttp request;



        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();



            HttpParser httpParser = new HttpParser();
            request = httpParser.parseHttpRequest(inputStream);
            sentValue = request.getHttpMessage().getMessage();

            if (!sentValue.equals("end")) {
                join();
            }
            else {

            }

            for (String value : queue){
                calculatedValue = Integer.parseInt(value);
            }




            final String CRLF = "\n\r";

            String html = "<html><head><title>Test</title></head><body><h1>" + calculatedValue + "</h1></body></html>";
            String response = "HTTP/1.1 200 OK" + CRLF + "Content-Length: " + html.getBytes().length + CRLF + CRLF + html + CRLF + CRLF;
            outputStream.write(response.getBytes());




        } catch (IOException | InterruptedException e){
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
