package org.mongodb;

import com.sun.net.httpserver.*;
import org.bson.Document;
import org.mongodb.rows.ProductRow;
import org.mongodb.rows.UserRow;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.System.out;

public class ServerForLogin {

    public static HashMap<Integer, UserTasks> tokens = new HashMap<>();

    public static void runServer() {
        ExecutorService pool = Executors.newCachedThreadPool();
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(9950), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.createContext("/main", new MyHandler());

        server.setExecutor(null); // creates a default executor
        server.start();
    }

}

class MyHandler implements HttpHandler {

    public static String loginCase(String username, String password)
    {
        if (username == null || password == null) {
            return new Document().append("authToken", "0").toJson();
        }
        out.println(username);
        out.println(password);
        Random random = new Random();
        int token = random.nextInt(10000000);
        token += 3;

        UserTasks user = new UserTasks(username);
        if(!user.checkAuthentication(password))
            return new Document().append("authToken", "0").toJson();

        ServerForLogin.tokens.put(token, user);
        Document a = new Document();
        a.put("authToken", token);

        return a.toJson();
    }

    private static String registerCase(String username, HashMap<String, String> hashMap) {
        boolean registerSuccess = UserTasks.register(hashMap);
        if(!registerSuccess)
            return new Document().append("authToken", "0").toJson();
        UserTasks user = new UserTasks(username);
        if(!user.existenceOfUser())
            return new Document().append("authToken", "0").toJson();
        Random random = new Random();
        int token = random.nextInt(10000000);
        token += 3;
        ServerForLogin.tokens.put(token, user);
        Document a = new Document();
        a.put("authToken", token);
        return a.toJson();
    }
    public static String getAllProductsCase(String base,String order)
    {
        out.println(base);
        out.println(order);
        UserTasks userTask = new UserTasks("unimportant");
        userTask.setHigh((int) 1e9);
        userTask.setLow(0);
        Document a = new Document();
        ArrayList<ProductRow> productRowArrayList= userTask.sortedBasedOnField(base, order.equals("0"));
        int counter = 0;
        for (ProductRow row:productRowArrayList)
        {
            a.append(String.valueOf(counter),row.toDocument());
            counter += 1;
        }
        out.println("adkklwqjdf");
        out.println(a.toJson());
        return a.toJson();
    }


    private static String changeInfoCase(int authToken, HashMap<String, String> hashMap)
    {
        out.println(ServerForLogin.tokens.keySet());
        if(!ServerForLogin.tokens.containsKey(authToken))
            return new Document().append("authToken", "0").toJson();
        UserTasks user = ServerForLogin.tokens.get(authToken);
        hashMap.put("username",user.getUserRow().getUsername());
        out.println(user.getUserRow().getUsername());
        Document a = new Document();
        a.put("authToken", authToken);
        if(user.changeAccount(hashMap))
            return a.toJson();
        return new Document().append("authToken", "0").toJson();
    }

    private static String getUserInfoCase(int authToken) {
        if(!ServerForLogin.tokens.containsKey(authToken))
            return new Document().append("authToken", "0").toJson();
        UserTasks user = ServerForLogin.tokens.get(authToken);
        Document a = new Document();
        a.put("authToken", authToken);
        a.put("firstname",user.getUserRow().getFirstname());
        a.put("lastname",user.getUserRow().getLastname());
        a.put("address",user.getUserRow().getAddress());
        a.put("charge",user.getUserRow().getCharge());
        return a.toJson();
    }

    private static String ChargeCase(int authToken) {
        if(!ServerForLogin.tokens.containsKey(authToken))
            return new Document().append("authToken", "0").toJson();
        UserTasks user = ServerForLogin.tokens.get(authToken);
        user.charge(1000000);
        return "";
    }

    public static String parseHeaders(HashMap<String,LinkedList> request)
    {
        String useCase = null;
        String username = null;
        String password = null;
        String categoryName = null;
        String baseOnNameSort = null;
        String sortType = null;
        int authToken = -1;
        HashMap <String,String> hashMap = new HashMap<>();
        out.println(request.keySet());
        for (String a: request.keySet()) {
            switch (a) {
                case "Usecase":
                    useCase = request.get(a).get(0).toString();
                    out.println(request.get(a));
                    out.println("useCase" + ": " + useCase);
                    break;
                case "Basename":
                    baseOnNameSort = request.get(a).get(0).toString();
                    break;
                case "Sorttype":
                    sortType = request.get(a).get(0).toString();
                    break;

                case "Password":
                    password = request.get(a).get(0).toString();
                    hashMap.put("password",password);
                    break;
                case "Username":
                    username = request.get(a).get(0).toString();
                    hashMap.put("username", username);
                    break;
                case "Firstname":
                    hashMap.put("firstname", request.get(a).get(0).toString());
                    break;
                case "Lastname":
                    hashMap.put("lastname", request.get(a).get(0).toString());
                    break;
                case "Address":
                    hashMap.put("address", request.get(a).get(0).toString());
                    break;
                case "Authtoken":
                    authToken = Integer.parseInt(request.get(a).get(0).toString());
                    out.println("authToken" + ": " + authToken);
                    break;
                case "Name":
                    hashMap.put("name", request.get(a).get(0).toString());
                    break;
                case "Pictureaddress":
                    hashMap.put("pictureAddress", request.get(a).get(0).toString());
                    break;
                case "Dateadded":
                    hashMap.put("dateAdded", request.get(a).get(0).toString());
                    break;
                case "Category_name":
                    categoryName = request.get(a).get(0).toString();
                    hashMap.put("category_name", request.get(a).get(0).toString());
                    break;
                case "Price":
                    hashMap.put("price", request.get(a).get(0).toString());
                    break;
                case "Remainingnumber":
                    hashMap.put("remainingNumber", request.get(a).get(0).toString());
                    break;
                case "Soldnumber":
                    hashMap.put("soldNumber", request.get(a).get(0).toString());
                    break;
                default:
                    break;
            }
        }
        out.println(hashMap);
        if(useCase == null)
            return new Document().append("authToken", "0").toJson();
        if(useCase.equals("Login"))
            return loginCase(username,password);
        if(useCase.equals("Register"))
            return registerCase(username, hashMap);
        if(useCase.equals("GetProduct"))
            return getAllProductsCase(baseOnNameSort, sortType);
        if(useCase.equals("changeinfo"))
            return changeInfoCase(authToken, hashMap);
        if(useCase.equals("Getuserinfo"))
            return getUserInfoCase(authToken);
        if(useCase.equals("Chargemoney"))
            return ChargeCase(authToken);

        return new Document().append("authToken", "0").toJson();
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
//        UserTasks.test2();
        String response;
        response = MyHandler.parseHeaders(hashMap);
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        out.println(response);
        os.write(response.getBytes());
        os.close();
    }

}

