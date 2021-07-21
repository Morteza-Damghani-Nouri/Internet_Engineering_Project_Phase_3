package org.mongodb;

import com.mongodb.client.*;
import org.bson.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
//        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
//        List<Document> databases = MongoClientInterface.getInstance().getDatabases();
//        databases.forEach(db -> System.out.println(db.toString()));
        AdminTasks.test1();
//        UserTasks.test2();
        ServerForLogin.runServer();
    }

}
