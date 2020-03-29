# httpServer
Java socket http server 

## how to use

```JAVA
    public static void main(String[] args) {
        HttpServer server = HttpServer.create();

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
```
