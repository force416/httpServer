package org.eric.httpServer;

import java.io.IOException;

@FunctionalInterface
public interface Route {
    void handle(HttpRequest request, HttpResponse response);
}
