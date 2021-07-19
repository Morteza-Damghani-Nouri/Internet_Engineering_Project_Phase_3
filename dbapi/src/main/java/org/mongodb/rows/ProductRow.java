package org.mongodb.rows;

import org.bson.types.ObjectId;

public class ProductRow {
    private ObjectId id;
    private String name, category_name;
    private int price, remainingNumber, soldNumber;

    public ProductRow(String name, String category_name, int price, int remainingNumber, int soldNumber) {
        this.name = name;
        this.category_name = category_name;
        this.price = price;
        this.remainingNumber = remainingNumber;
        this.soldNumber = soldNumber;
    }

    @Override
    public String toString() {
        return "ProductRow{" +
                "name='" + name + '\'' +
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
