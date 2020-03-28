package org.eric.httpServer;

import rawhttp.core.RawHttp;
import rawhttp.core.RawHttpRequest;
import rawhttp.core.body.BodyReader;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;
import java.util.Optional;

public class HttpRequest {
    private RawHttpRequest rawHttpRequest;

    public HttpRequest(Socket socket) throws IOException {
        this.rawHttpRequest = new RawHttp().parseRequest(socket.getInputStream());
    }

    public String getMethod() {
        return this.rawHttpRequest.getMethod();
    }

    public URI getUri() {
        return this.rawHttpRequest.getUri();
    }

    public String getQuery() {
        return this.getUri().getQuery();
    }

    public Optional<InetAddress> getSenderAddress() {
        return this.rawHttpRequest.getSenderAddress();
    }

    public Optional<? extends BodyReader> getBody() {
        return this.rawHttpRequest.getBody();
    }
}
