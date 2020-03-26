package org.eric.httpServer;

@FunctionalInterface
public interface Route {
    void handle(HttpRequest request, HttpResponse response);
}
