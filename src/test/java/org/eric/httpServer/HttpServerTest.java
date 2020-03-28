package org.eric.httpServer;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HttpServerTest {

    private static HttpServer server;

    @BeforeClass
    public static void runServer() {
        server = HttpServer.create();

        server.get("/hello", (request, response) -> {
            response.ok()
                    .SetContentType("application/json; charset=utf-8")
                    .withBody("{\"abc\": 123}")
                    .flush();
        });

        server.post("/game", (request, response) -> {
            response.ok()
                    .SetContentType("application/json; charset=utf-8")
                    .withBody("{\"status\": \"success\"}")
                    .flush();
        });
        server.start();
    }

    @AfterClass
    public static void stopServer() {
        server.stop();
    }

    @Test
    public void testStartServer() {
        try {
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofMillis(5000))
                    .followRedirects(HttpClient.Redirect.NORMAL)
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/hello"))
                    .timeout(Duration.ofMillis(5000))
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            Assert.assertEquals("{\"abc\": 123}", response.body());

        } catch (Exception e) {
            Assert.fail();
        }
    }
}
