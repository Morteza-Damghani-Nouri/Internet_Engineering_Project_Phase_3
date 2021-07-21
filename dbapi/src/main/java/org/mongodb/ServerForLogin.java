package org.mongodb;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.bson.Document;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.System.out;

public class ServerForLogin {

    public static HashMap<Integer, UserTasks> tokens = new HashMap<>();

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

    public static String parseHeaders(HashMap<String,LinkedList> request)
    {
        String useCase = null;
        String username = null;
        String password = null;
        out.println(request.keySet());
        for (String a: request.keySet()) {
            switch (a) {
                case "Usecase":
                    useCase = request.get(a).get(0).toString();
                    out.println("ok1");
                    out.println(request.get(a));
                    out.println("useCase" + ": " + useCase);
                    break;
                case "Password":
                    out.println("ok2");
                    password = request.get(a).get(0).toString();
                    out.println("password" + ": " + password);
                    break;
                case "Username":
                    out.println("ok3");

                    username = request.get(a).get(0).toString();
                    out.println("username" + ": " + username);
                    break;
                default:
                    break;
            }
        }
        if (useCase == null || username == null || password == null) {
            return new Document().append("token", "0").toJson();
        }
        Random random = new Random();
        int token = random.nextInt(10000000);
        token += 3;
        out.println("ok4");

        UserTasks user = new UserTasks(username);
        if(!user.checkAuthentication(password))
            return new Document().append("token", "0").toJson();
        out.println("ok5");

        ServerForLogin.tokens.put(token, user);
        out.println("ok5.5");
        Document a = new Document();
        a.put("authToken", token);
        out.println("ok6");

        return a.toJson();
    }

    @Override
    public void handle(HttpExchange t) throws IOException {
        t.getResponseHeaders().put("Access-Control-Allow-Origin", Collections.singletonList("*"));
        t.getResponseHeaders().put("Access-Control-Allow-Credentials", Collections.singletonList("true"));
        t.getResponseHeaders().put("Access-Control-Allow-Methods",
                Collections.singletonList("GET, POST, DELETE, PUT, OPTIONS, HEAD"));
        t.getResponseHeaders().put("Access-Control-Allow-Headers",
                Collections.singletonList("*"));
        HashMap <String, LinkedList> hashMap = new HashMap<>();

        for (Object o:t.getRequestHeaders().keySet())
            hashMap.put(o.toString(), (LinkedList) t.getRequestHeaders().get(o));
        UserTasks.test2();
        String response;
        response = MyHandler.parseHeaders(hashMap);
//        String response = new Document().append("token", "0").toJson();
        t.sendResponseHeaders(200, response.length());
        out.println("ok7");

//        System.out.println(t.getResponseHeaders().keySet());
//        System.out.println(t.getRequestHeaders().keySet());
//        System.out.println(t.getRequestHeaders().get("Usecase"));
        OutputStream os = t.getResponseBody();
        out.println("ok8");

        out.println(response);
        os.write(response.getBytes());
        os.close();
    }

}

