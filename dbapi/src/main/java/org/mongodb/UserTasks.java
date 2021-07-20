package org.mongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.mongodb.rows.BillRow;
import org.mongodb.rows.ProductRow;
import org.mongodb.rows.UserRow;

import java.util.*;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
import static com.mongodb.client.model.Sorts.*;

public class UserTasks {
    private final String username;
    private FindIterable<ProductRow> productRowIterable;
    private int low,high;
    private Random random;
    public UserTasks(String username) {
        this.username = username;
        setLow(0);
        setHigh(1000000);
        filterProductsBasedOnPrice();
        random = new Random();
    }

    public List<BillRow> getBills()
    {
        return  MongoClientInterface.getInstance().getBillCollection().find(eq("username",username)).into(new ArrayList<>());
    }

    public UserRow getUserRow()
    {
        MongoCollection<UserRow> collection = MongoClientInterface.getInstance().getUserCollection();
        return MongoClientInterface.checkExistence(collection, "username",username);
    }

    public boolean existenceOfUser()
    {
        MongoCollection<UserRow> collection = MongoClientInterface.getInstance().getUserCollection();
        UserRow user = MongoClientInterface.checkExistence(collection, "username",username);
        return user != null;
    }

    public static boolean register(HashMap<String, String> userAsMap)
    {
        UserRow user = UserRow.getUserRowByHashMap(userAsMap);
        if(user == null || !user.isUserValid())
            return false;
        MongoCollection<UserRow> collection = MongoClientInterface.getInstance().getUserCollection();
        if(MongoClientInterface.checkExistence(collection, "username",user.getUsername()) != null)
            return false;
        collection.insertOne(user);
        return true;
    }

    public boolean checkAuthentication(String password)
    {
        MongoCollection<UserRow> collection = MongoClientInterface.getInstance().getUserCollection();
        UserRow user = MongoClientInterface.checkExistence(collection, "username",username);
        return user != null && password.equals(user.getPassword());
    }

    public boolean changeAccount(HashMap<String, String> userAsMap)
    {
        UserRow user = UserRow.getUserRowByHashMap(userAsMap);
        if(user == null || !(user.getUsername().equals(username) || user.getUsername().equals("")))
            return false;
        MongoCollection<UserRow> collection = MongoClientInterface.getInstance().getUserCollection();
        for(String key: userAsMap.keySet() )
        {
            if (!key.equals("charge"))
                collection.updateOne(eq("username",username),set(key, userAsMap.get(key)));
            else
                collection.updateOne(eq("username",username),set(key, Integer.parseInt(userAsMap.get(key))));
        }
        return true;
    }
    public boolean charge(int amount)
    {
        if(amount <= 0 || !existenceOfUser())
            return false;
        MongoCollection<UserRow> collection = MongoClientInterface.getInstance().getUserCollection();
        collection.updateOne(eq("username",username),inc("charge",amount));
        return true;
    }

    public int buyProduct(String name, int number, String date)
    {
        MongoCollection<ProductRow> productCollection = MongoClientInterface.getInstance().getProductCollection();
        MongoCollection<UserRow> userCollection = MongoClientInterface.getInstance().getUserCollection();
        MongoCollection<BillRow> billCollection = MongoClientInterface.getInstance().getBillCollection();

        ProductRow product = MongoClientInterface.checkExistence(productCollection, "name", name);
        UserRow userRow = MongoClientInterface.checkExistence(userCollection, "username", username);
        if(userRow == null || product == null || number < 1)
            return -1;
        if(number > product.getRemainingNumber() || number * product.getPrice() > userRow.getCharge())
            return -1;

        int trackingCode;
        FindIterable<BillRow> myDoc;
        do {
            trackingCode = random.nextInt(1000000);
            myDoc = billCollection.find(eq("trackingCode", trackingCode));
        }while (myDoc.first() != null);

        BillRow bill = new BillRow(product.getName(),userRow.getUsername(), userRow.getFirstname(), userRow.getLastname()
                ,userRow.getAddress(), date ,trackingCode,product.getPrice(),number, BillRow.BillStatus.IN_PROCESS );

        userCollection.updateOne(eq("username",username),inc("charge", -number * product.getPrice()));
        productCollection.updateOne(eq("name",name), inc("soldNumber", number));
        productCollection.updateOne(eq("name",name), inc("remainingNumber", -number));
        billCollection.insertOne(bill);
        return trackingCode;
    }

    public void filterProductsBasedOnPrice()
    {
        MongoCollection<ProductRow> collection = MongoClientInterface.getInstance().getProductCollection();
        productRowIterable = collection.find(and(gte("price", getLow()), lte("price", getHigh())));
    }

    public ArrayList<ProductRow> sortedBasedOnField(String field, boolean sortType)
    {
        HashMap<String,String> checker = new HashMap<>();
        checker.put(field,"0");
        checker.put("name","X");
        if(ProductRow.getProductRowByHashMap(checker) == null)
            return null;
        filterProductsBasedOnPrice();
        if(sortType)
            return productRowIterable.sort(descending(field)).into(new ArrayList<>());
        return productRowIterable.sort(ascending(field)).into(new ArrayList<>());
    }

    public static void test2()
    {
        HashMap<String, String> userAsMap = new HashMap<>();
        userAsMap.put("username","x@aut.ac.ir");
        userAsMap.put("firstname","Amirreza");
        userAsMap.put("lastname","Shirmast");

//        userRow.setUsername("amir@aut.ac.ir");

        UserTasks user = new UserTasks("amir@aut.ac.ir");
        user.charge(15000);
        System.out.println(user.getBills());
//        int x8 = user.buyProduct("Sim", 1, new Date().toString());
//        System.out.println(x8);
//        int x9 = user.buyProduct("Card", 11, new Date().toString());
//        System.out.println(x9);
//        int x10 = user.buyProduct("Ice", 1, new Date().toString());
//        System.out.println(x10);
//        System.out.println(AdminTasks.getBills());
//        System.out.println(AdminTasks.getBill(511288));
//        boolean x11 = AdminTasks.changeBillStatus(511288,"DONE");
//        System.out.println(x11);
//        System.out.println(AdminTasks.getBills());
//        boolean x2 = user.changeAccount(userAsMap);
//        System.out.println(x2);
//        userAsMap.replace("username","amir@aut.ac.ir");
//        boolean x3 = user.changeAccount(userAsMap);
//        System.out.println(x3);
//        boolean x4 = user.checkAuthentication("123421aa");
//        System.out.println(x4);
//        boolean x5 = user.checkAuthentication("123421aaa");
//        System.out.println(x5);
//        user = new UserTasks("x@aut.ac.ir");
//        boolean x6 = user.checkAuthentication("123421aaa");
//        System.out.println(x6);
//        AdminTasks.addCategory("All");
//        HashMap<String, String> product = new HashMap<>();
//        product.put("name","Ice");
//        product.put("price","150");
//        AdminTasks.addProduct(product);
//        product.replace("name", "Sim");
//        product.replace("price","10000");
//        AdminTasks.addProduct(product);
//        product.replace("name", "Card");
//        product.replace("price","130");
//        product.put("category_name","All");
//        AdminTasks.addProduct(product);
        System.out.println(user.sortedBasedOnField("price",false));
        user.setHigh(1000);
        System.out.println(user.sortedBasedOnField("name",true));
        System.out.println(user.sortedBasedOnField("category_name",false));

    }

    public int getLow() {
        return low;
    }

    public void setLow(int low) {
        this.low = low;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

}
