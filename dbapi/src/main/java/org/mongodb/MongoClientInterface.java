package org.mongodb;

import com.mongodb.client.*;

public class MongoClientInterface {
    private static MongoClientInterface instance = new MongoClientInterface();

    private MongoClient mongoClient;
    public static MongoClientInterface getInstance() {
        return instance;
    }
    private MongoClientInterface()
    {
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        checkAndAddAdmin();
        checkAndCreateTables();
        checkAndAddUnspecificCategory();
    }

    private void checkAndCreateTables()
    {

    }

    private void checkAndAddAdmin()
    {

    }

    private void checkAndAddUnspecificCategory()
    {

    }
}
