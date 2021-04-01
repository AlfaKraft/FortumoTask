package com.FortumoTask.server;

import com.FortumoTask.server.HttpConnectionWorker;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class AcceptServerConnections extends Thread {

    private int port;
    private String webroot;
    private ServerSocket serverSocket;
    private ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
    private AtomicInteger calculatedValue = new AtomicInteger();
    private AtomicReference<String> id = new AtomicReference<>();
    private static Object syncObject = new Object();


    public AcceptServerConnections(int port, String webroot) throws IOException {
        this.port = port;
        this.webroot = webroot;
        this.serverSocket = new ServerSocket(this.port);
    }
    @Override
    public void run() {
        try {
            while (serverSocket.isBound() && !serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("Connection accepted: " + socket.getInetAddress());
                HttpConnectionWorker httpConnectionWorker = new HttpConnectionWorker(socket, calculatedValue, syncObject, id);
                httpConnectionWorker.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                }
            }
        }

    }
}
