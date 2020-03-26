package org.eric.httpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private int port = 8080;
    private ServerSocket socket;

    private HttpServer() {}

    public static HttpServer create() {
        return new HttpServer();
    }

    public HttpServer start() {
        return this.start(this.port);
    }

    public HttpServer start(int port) {
        this.port = port;
        new Thread(() -> init()).start();
        return this;
    }

    private void init() {
        try {
            if (socket == null) {
                socket = new ServerSocket(this.port);
            }
            ExecutorService fixedThreadPool = Executors.newFixedThreadPool(30);
            while (true) {
                Socket clientSocket = socket.accept();
                HttpRequestHandler request = new HttpRequestHandler(clientSocket);
                fixedThreadPool.execute(request);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    class HttpRequestHandler implements Runnable {

        private Socket socket;

        public HttpRequestHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                String line = in.readLine();
                while (!line.isEmpty()) {
                    System.out.println(line);
                    line = in.readLine();
                }

                out.println("HTTP/1.1 200 OK");
                out.println("Server: JAVA");
                out.println("Content-Type: text/html");
                out.println("");
                out.println("<h1>Hello World</h1>");

                out.close();
                in.close();
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        HttpServer server = HttpServer.create();
        server.start();
    }
}
