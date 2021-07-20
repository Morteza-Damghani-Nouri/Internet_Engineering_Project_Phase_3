package org.mongodb.rows;

import org.bson.types.ObjectId;

public class BillRow {
    public enum BillStatus{
        IN_PROCESS,
        DONE,
        CANCELED
    }

    private ObjectId id;
    private String name, userFirstname, userLastname, address, date, trackingCode;
    private int price, soldNumber;
    BillStatus status;

    @Override
    public String toString() {
        return "BillRow{" +
                "name='" + name + '\'' +
                ", userFirstname='" + userFirstname + '\'' +
                ", userLastname='" + userLastname + '\'' +
                ", address='" + address + '\'' +
                ", date='" + date + '\'' +
                ", trackingCode='" + trackingCode + '\'' +
                ", price=" + price +
                ", soldNumber=" + soldNumber +
                ", status=" + status +
                '}';
    }

    public BillRow()
    {

    }

    public BillRow(String name, String userFirstname, String userLastname, String address, String date, String trackingCode, int price, int soldNumber, BillStatus status) {
        this.name = name;
        this.userFirstname = userFirstname;
        this.userLastname = userLastname;
        this.address = address;
        this.date = date;
        this.trackingCode = trackingCode;
        this.price = price;
        this.soldNumber = soldNumber;
        this.status = status;
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

    public String getUserFirstname() {
        return userFirstname;
    }

    public void setUserFirstname(String userFirstname) {
        this.userFirstname = userFirstname;
    }

    public String getUserLastname() {
        return userLastname;
    }

    public void setUserLastname(String userLastname) {
        this.userLastname = userLastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSoldNumber() {
        return soldNumber;
    }

    public void setSoldNumber(int soldNumber) {
        this.soldNumber = soldNumber;
    }
}
