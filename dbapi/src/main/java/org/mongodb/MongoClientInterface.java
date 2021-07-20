package org.mongodb;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.mongodb.rows.BillRow;
import org.mongodb.rows.CategoryRow;
import org.mongodb.rows.ProductRow;
import org.mongodb.rows.UserRow;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static org.bson.codecs.configuration.CodecRegistries.*;

public class MongoClientInterface {
    private static MongoClientInterface instance = new MongoClientInterface();

    private MongoClient mongoClient;
    private MongoDatabase database;

    public static MongoClientInterface getInstance() {
        return instance;
    }

    public MongoCollection<CategoryRow> getCategoryCollection()
    {
        return database.getCollection("Categories", CategoryRow.class);
    }

    public MongoCollection<UserRow> getUserCollection()
    {
        return database.getCollection("Users",UserRow.class);
    }

    public MongoCollection<BillRow> getBillCollection()
    {
        return database.getCollection("Bills", BillRow.class);
    }

    public MongoCollection<ProductRow> getProductCollection()
    {
        return database.getCollection("Products", ProductRow.class);
    }

    public List<Document> getDatabases()
    {
//        MongoDatabase database = mongoClient.getDatabase("School");
//        MongoCollection<Document> collection = database.getCollection("School");
//        List collection2 = database.listCollectionNames().into(new ArrayList<String>());
//        System.out.println(collection2.toString());
//        System.out.println(collection.find().into(new ArrayList<>()));
        return mongoClient.listDatabases().into(new ArrayList<>());
    }

    private MongoClientInterface()
    {
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        checkAndCreateTables();
        checkAndAddAdmin();
        checkAndAddUnspecificCategory();
    }

    private void checkAndCreateTables()
    {
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        database = mongoClient.getDatabase("WebDB").withCodecRegistry(pojoCodecRegistry);
        List collection2 = database.listCollectionNames().into(new ArrayList<String>());
        System.out.println(collection2.toString());
        System.out.println(database.getName());
    }

    public static < E > E checkExistence(MongoCollection<E> collection, String fieldName, String value)
    {
        FindIterable<E> myDoc = collection.find(eq(fieldName, value));
        return myDoc.first();
    }

    public void checkAndAddAdmin()
    {
        MongoCollection<UserRow> collection = getUserCollection();
        FindIterable<UserRow> myDoc = collection.find(eq("username", "admin@am.sd"));

        if(myDoc.first() == null)
        {
            UserRow admin = new UserRow("admin@am.sd"," Amirreza","Damghani", 0, "WebIr123", "Tehran");
            collection.insertOne(admin);
        }
    }

    private void checkAndAddUnspecificCategory()
    {
        MongoCollection<CategoryRow> collection = getCategoryCollection();
        FindIterable<CategoryRow> categoryRows = collection.find(eq("name","unspecific"));

        if(categoryRows.first() == null)
        {
            CategoryRow unspecific = new CategoryRow("unspecific");
            collection.insertOne(unspecific);
        }
    }
}
