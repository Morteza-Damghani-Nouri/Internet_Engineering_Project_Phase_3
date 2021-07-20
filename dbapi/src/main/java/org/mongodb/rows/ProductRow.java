package org.mongodb.rows;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductRow {
    private ObjectId id;
    private String name, category_name;
    private int price, remainingNumber, soldNumber;

    public ProductRow()
    {
        name = "";
        category_name = "";
    }

    public ProductRow(String name, String category_name, int price, int remainingNumber, int soldNumber) {
        this.name = name;
        this.category_name = category_name;
        this.price = price;
        this.remainingNumber = remainingNumber;
        this.soldNumber = soldNumber;
    }

    public static ProductRow getProductRowByHashMap(HashMap<String, String> productAsMap)
    {
        ProductRow product = new ProductRow();
        for (String i : productAsMap.keySet()) {
            switch (i) {
                case "name":
                    product.setName(productAsMap.get(i));
                    break;
                case "category_name":
                    product.setCategory_name(productAsMap.get(i));
                    break;
                case "price":
                    product.setPrice(Integer.parseInt(productAsMap.get(i)));
                    break;
                case "remainingNumber":
                    product.setRemainingNumber(Integer.parseInt(productAsMap.get(i)));
                    break;
                case "soldNumber":
                    product.setSoldNumber(Integer.parseInt(productAsMap.get(i)));
                    break;
                default:
                    return null;
            }
        }
        if(product.name.equals(""))
            return null;

        return product;
    }

    @Override
    public String toString() {
        return "ProductRow{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category_name='" + category_name + '\'' +
                ", price=" + price +
                ", remainingNumber=" + remainingNumber +
                ", soldNumber=" + soldNumber +
                '}';
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRemainingNumber() {
        return remainingNumber;
    }

    public void setRemainingNumber(int remainingNumber) {
        this.remainingNumber = remainingNumber;
    }

    public int getSoldNumber() {
        return soldNumber;
    }

    public void setSoldNumber(int soldNumber) {
        this.soldNumber = soldNumber;
    }
}
