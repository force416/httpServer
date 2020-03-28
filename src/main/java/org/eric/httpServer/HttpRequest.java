package org.eric.httpServer;

import rawhttp.core.RawHttp;
import rawhttp.core.RawHttpRequest;
import rawhttp.core.body.BodyReader;
import rawhttp.core.errors.InvalidHttpRequest;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;
import java.util.Optional;

public class HttpRequest {
    private RawHttpRequest rawHttpRequest;

    public HttpRequest(Socket socket) throws IOException {
        try {
            this.rawHttpRequest = new RawHttp().parseRequest(socket.getInputStream());
        } catch (InvalidHttpRequest e) {
            // nothing
        }
    }

    public String getMethod() {
        return this.rawHttpRequest.getMethod();
    }

    public URI getUri() {
        return this.rawHttpRequest.getUri();
    }

    public Optional<InetAddress> getSenderAddress() {
        return this.rawHttpRequest.getSenderAddress();
    }

    public Optional<? extends BodyReader> getBody() {
        return this.rawHttpRequest.getBody();
    }
}
