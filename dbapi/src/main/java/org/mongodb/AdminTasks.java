package org.mongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.mongodb.rows.BillRow;
import org.mongodb.rows.CategoryRow;
import org.mongodb.rows.ProductRow;
import org.mongodb.rows.UserRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.*;

public class AdminTasks {
    public static boolean checkAuthentication(String username,String password)
    {
        if(!username.equals("admin@am.sd"))
            return false;
        UserRow admin = MongoClientInterface.getInstance().getUserCollection().find(eq("username", "admin@am.sd")).first();
        if (admin == null)
        {
            MongoClientInterface.getInstance().checkAndAddAdmin();
            return false;
        }

        return admin.getPassword().equals(password);
    }

    public static boolean AddCategory(String categoryName)
    {
        MongoCollection<CategoryRow> collection = MongoClientInterface.getInstance().getCategoryCollection();
        FindIterable<CategoryRow> categoryRows = collection.find(eq("name",categoryName));

        if(categoryRows.first() == null)
        {
            CategoryRow category = new CategoryRow(categoryName);
            collection.insertOne(category);
            return true;
        }
        return false;
    }

    public static boolean deleteCategory(String categoryName)
    {
        if(categoryName.equals("unspecific"))
            return false;
        MongoCollection<CategoryRow> collection = MongoClientInterface.getInstance().getCategoryCollection();
        FindIterable<CategoryRow> categoryRows = collection.find(eq("name",categoryName));

        if(categoryRows.first() == null)
            return false;
        collection.deleteMany(eq("name",categoryName));
        MongoCollection<ProductRow> productCollection = MongoClientInterface.getInstance().getProductCollection();
        productCollection.updateMany(eq("category_name", categoryName),set("category_name", "unspecific"));
        return true;
    }

    public static boolean changeCategoryName(String categoryName,String newName)
    {
        if(categoryName.equals("unspecific"))
            return false;
        MongoCollection<CategoryRow> collection = MongoClientInterface.getInstance().getCategoryCollection();
        FindIterable<CategoryRow> categoryRows = collection.find(eq("name",categoryName));

        if(categoryRows.first() == null)
            return false;
        categoryRows = collection.find(eq("name",newName));
        if(categoryRows.first() != null)
            return false;
        collection.updateOne(eq("name",categoryName),set("name",newName));
        MongoCollection<ProductRow> productCollection = MongoClientInterface.getInstance().getProductCollection();
        productCollection.updateMany(eq("category_name", categoryName),set("category_name",newName));
        return true;
    }

    public static List<BillRow> getBills()
    {
        return new ArrayList<BillRow>();
    }

    public static BillRow getBills(String trackingCode)
    {
        return new BillRow();
    }

    public static void changeBillStatus(String trackingCode, String status)
    {

    }

    public static boolean removeProduct(String name)
    {
        MongoCollection<ProductRow> collection = MongoClientInterface.getInstance().getProductCollection();
        FindIterable<ProductRow> findIterable = collection.find(eq("name", name));
        if(findIterable.first() == null)
            return false;
        collection.deleteMany(eq("name", name));
        return true;
    }

    public static boolean changeProduct(String name, HashMap<String, String> productAsMap)
    {
        MongoCollection<ProductRow> collection = MongoClientInterface.getInstance().getProductCollection();
        if(!productAsMap.containsKey("name"))
            productAsMap.put("name", name);
        else
        {
            FindIterable<ProductRow> findIterable = collection.find(eq("name",productAsMap.get("name")));
            if(findIterable.first() != null)
                return false;
        }

        ProductRow product = ProductRow.getProductRowByHashMap(productAsMap);
        ProductRow oldProduct = MongoClientInterface.checkExistence(collection, "name",name);
        if(product == null || oldProduct == null)
            return false;


        for(String key: productAsMap.keySet() )
        {
            if (key.equals("name") || name.equals("category_name"))
                collection.updateOne(eq("name",name),set(key, productAsMap.get(key)));
            else
                collection.updateOne(eq("name",name),set(key, Integer.parseInt(productAsMap.get(key))));
            if(key.equals("name"))
                name = productAsMap.get(key);
        }

        return true;
    }

    public static boolean addProduct(HashMap<String, String> productAsMap)
    {
        ProductRow product = ProductRow.getProductRowByHashMap(productAsMap);
        if(product == null)
            return false;
        product.setSoldNumber(0);
        if(product.getCategory_name().equals(""))
            product.setCategory_name("unspecific");
        if(MongoClientInterface.checkExistence(
                MongoClientInterface.getInstance().getCategoryCollection(),
                "name", product.getCategory_name()) == null)
            return false;
        MongoCollection<ProductRow> collection = MongoClientInterface.getInstance().getProductCollection();
        FindIterable<ProductRow> findIterable = collection.find(eq("name",product.getName()));

        if(findIterable.first() == null)
        {
            collection.insertOne(product);
            return true;
        }
        return false;
    }

    public static void test1()
    {
        MongoCollection<ProductRow> collection = MongoClientInterface.getInstance().getProductCollection();
        System.out.println(collection.find().into(new ArrayList<>()));
        HashMap<String, String> productAsMap = new HashMap<>();
        productAsMap.put("name", "Ahmad");
        productAsMap.put("category_name","newC");


    }
}
