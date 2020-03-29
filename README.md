# httpServer
Java socket http server 

## code

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

## Maven ~/.m2/settings.xml

```
</repositories>
  <repository>
    <id>github</id>
    <name>GitHub OWNER Apache Maven Packages</name>
    <url>https://maven.pkg.github.com/force416/mvn-repo</url>
  </repository>
</repositories>
```

## Maven pom.xml

```
<dependency>
  <groupId>org.eric</groupId>
  <artifactId>httpServer</artifactId>
  <version>1.0.7</version>
</dependency>
```
