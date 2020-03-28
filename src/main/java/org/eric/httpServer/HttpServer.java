package org.eric.httpServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private int port = 8080;

    private boolean isStop = false;

    private Thread serverThread;

    private ServerSocket socket;

    private Map<String, Route> routes = new HashMap<>();

    private HttpServer() {

    }

    public static HttpServer create() {
        return new HttpServer();
    }

    public void start() {
        this.start(this.port);
    }

    public void start(int port) {
        this.port = port;
        if (serverThread == null) {
            serverThread = new Thread(() -> init());
            serverThread.start();
        }
    }

    public void stop() {
        try {
            this.isStop = true;
            if (socket != null) {
                this.socket.close();
            }
            serverThread.stop();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void init() {
        try {
            if (socket == null) {
                socket = new ServerSocket(this.port);
            }
            ExecutorService fixedThreadPool = Executors.newFixedThreadPool(30);
            while (!isStop) {
                Socket clientSocket = socket.accept();
                HttpRequestHandler request = new HttpRequestHandler(clientSocket);
                fixedThreadPool.execute(request);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public HttpServer get(String path, Route route) {
        routes.put("GET:" + path, route);
        return this;
    }

    public HttpServer post(String path, Route route) {
        routes.put("POST:" + path, route);
        return this;
    }

    public HttpServer put(String path, Route route) {
        routes.put("PUT:" + path, route);
        return this;
    }

    public HttpServer delete(String path, Route route) {
        routes.put("DELETE:" + path, route);
        return this;
    }

    public HttpServer patch(String path, Route route) {
        routes.put("PATCH:" + path, route);
        return this;
    }

    class HttpRequestHandler implements Runnable {

        private Socket socket;

        public HttpRequestHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                HttpRequest request = new HttpRequest(socket);
                HttpResponse response = new HttpResponse(socket);

                String routeKey = String.format("%s:%s", request.getMethod(), request.getUri().getPath());
                Route route = routes.getOrDefault(routeKey, getDefaultRoute());
                route.handle(request, response);
                socket.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        private Route getDefaultRoute() {
            return (req, res) -> res.notFound()
                    .SetContentType("text/plain; charset=utf-8")
                    .withBody(String.format("Can not found the path: %s", req.getUri().getPath())).flush();
        }
    }
}
