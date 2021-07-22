package org.mongodb;

import com.mongodb.client.*;
import org.mongodb.rows.*;

import java.util.*;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

public class AdminTasks {

    /**
     * Admin : 0
     * @param username
     * @param password
     * @return success
     */
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

    /**
     * Admin : 3
     * @param categoryName
     * @return success
     */
    public static boolean addCategory(String categoryName)
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

    /**
     * Admin : 3
     * @param categoryName
     * @return success
     */
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

    /**
     * Admin : 3
     * @param categoryName
     * @param newName
     * @return success
     */
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

    /**
     * Admin : 2
     * @return bills list
     */
    public static List<BillRow> getBills()
    {
        return  MongoClientInterface.getInstance().getBillCollection().find().into(new ArrayList<>());
    }

    /**
     * Admin : 2
     * @param trackingCode
     * @return the bill
     */
    public static BillRow getBill(int trackingCode)
    {
        FindIterable<BillRow> myDoc = MongoClientInterface.getInstance().getBillCollection().find(eq("trackingCode", trackingCode));
        return myDoc.first();
    }

    /**
     * Admin : 2
     * @param trackingCode
     * @param statusInText
     * @return success
     */
    public static boolean changeBillStatus(int trackingCode, String statusInText)
    {
        BillRow billRow = getBill(trackingCode);
        if(billRow == null)
            return false;
        BillRow.BillStatus status;
        switch (statusInText)
        {
            case "IN_PROCESS":
                status = BillRow.BillStatus.IN_PROCESS;
                break;
            case "DONE":
                status = BillRow.BillStatus.DONE;
                break;
            case "CANCELED":
                status = BillRow.BillStatus.CANCELED;
                break;
            default:
                return false;
        }
        MongoCollection<BillRow> collection = MongoClientInterface.getInstance().getBillCollection();
        collection.updateOne(eq("trackingCode", trackingCode),set("status",status.toString()));
        return true;
    }

    /**
     * Admin : 1
     * @param name
     * @return success
     */
    public static boolean removeProduct(String name)
    {
        MongoCollection<ProductRow> collection = MongoClientInterface.getInstance().getProductCollection();
        FindIterable<ProductRow> findIterable = collection.find(eq("name", name));
        if(findIterable.first() == null)
            return false;
        collection.deleteMany(eq("name", name));
        return true;
    }

    /**
     * Admin : 1
     * @param name
     * @param productAsMap
     * @return success
     */
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
            if (key.equals("name") || name.equals("category_name") || name.equals("pictureAddress") || name.equals("dateAdded"))
                collection.updateOne(eq("name",name),set(key, productAsMap.get(key)));
            else
                collection.updateOne(eq("name",name),set(key, Integer.parseInt(productAsMap.get(key))));
            if(key.equals("name"))
                name = productAsMap.get(key);
        }

        return true;
    }

    /**
     * Admin : 1
     * @param productAsMap
     * @return success
     */
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
//        MongoCollection<ProductRow> collection = MongoClientInterface.getInstance().getProductCollection();
//        System.out.println(collection.find().into(new ArrayList<>()));
//        HashMap<String, String> productAsMap = new HashMap<>();
//        addCategory("newC");
//        productAsMap.put("name", "Ahmad");
//        productAsMap.put("category_name","newC");
//        addProduct(productAsMap);
//        removeProduct("Ahmad");
        boolean x1 = addCategory("first");
        x1 = addCategory("second");
        x1 = addCategory("third");

        for(int i = 0; i < 45; ++i) {
            HashMap<String, String> product = new HashMap<>();
            if(i < 15) {
                product.put("name", "mountain_climbing_bag " + String.valueOf(i));
                product.put("price","150000");
                product.put("pictureAddress", "Images/bag0.png");
                product.put("category_name", "first");

            }
            else {
                if(i < 30) {
                    product.put("name", "mountain_climbing_bag " + String.valueOf(i));
                    product.put("price","170000");
                    product.put("pictureAddress", "Images/bag1.png");
                    product.put("category_name", "second");
                }
                else {

                    product.put("name", "mountain_climbing_bag " + String.valueOf(i));
                    product.put("price","200000");
                    product.put("pictureAddress", "Images/bag2.png");
                    product.put("category_name", "third");

                }

            }





        boolean x2 = addProduct(product);
        System.out.println(x1);
        System.out.println(x2);
    }
}




}


