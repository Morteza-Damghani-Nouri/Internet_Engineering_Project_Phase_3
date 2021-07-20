package org.mongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.mongodb.rows.ProductRow;

import java.util.*;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
import static com.mongodb.client.model.Sorts.*;
public class UserTasks {
    private String username;
    private FindIterable<ProductRow> productRowIterable;
    private int low,high;
    public UserTasks(String username) {
        this.username = username;
        setLow(0);
        setHigh(1000000);
        filterProductsBasedOnPrice();
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
        UserTasks user = new UserTasks("Ahmad");
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
