package org.mongodb;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerForLogin {
    public static void runServer() {
        ExecutorService pool = Executors.newCachedThreadPool();
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(8000), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.createContext("/main", new MyHandler());

        server.setExecutor(null); // creates a default executor
        server.start();
    }

}

class MyHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange t) throws IOException {
        String response = "This is the response";
        t.getResponseHeaders().put("Access-Control-Allow-Origin", Collections.singletonList("*"));
        t.getResponseHeaders().put("Access-Control-Allow-Credentials", Collections.singletonList("true"));
        t.getResponseHeaders().put("Access-Control-Allow-Methods",
                Collections.singletonList("GET, POST, DELETE, PUT, OPTIONS, HEAD"));
        t.getResponseHeaders().put("Access-Control-Allow-Headers",
                Collections.singletonList("*"));
        t.sendResponseHeaders(200, response.length());
        System.out.println(t.getResponseHeaders().keySet());
        System.out.println(t.getRequestHeaders().keySet());
        System.out.println(t.getRequestHeaders().get("Usecase"));
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}

