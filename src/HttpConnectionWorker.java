import javax.management.StringValueExp;
import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class HttpConnectionWorker extends Thread {
    Socket socket;
    String sentValue;
    AtomicInteger calculatedValue;
    Object object;
    AtomicReference<String> id;

    public HttpConnectionWorker(Socket socket, AtomicInteger atomicInteger, Object object, AtomicReference<String> id) {
        this.calculatedValue = atomicInteger;
        this.socket = socket;
        this.object = object;
        this.id = id;
    }

    @Override
    public synchronized void  run() {
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

            if (!sentValue.contains("end")){
                calculatedValue.addAndGet(Integer.parseInt(sentValue));
                synchronized (object) {
                    object.wait();
                }
            }
            else {
                id.set(sentValue.split(" ")[1]);
                synchronized (object) {
                    object.notifyAll();
                }
            }
            final String CRLF = "\n\r";

            String result =  calculatedValue.toString() + " " + id.toString();
            String response = "HTTP/1.1 200 OK" + CRLF + "Content-Length: " + result.getBytes().length + CRLF + CRLF +
                    result+ CRLF + CRLF;
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
