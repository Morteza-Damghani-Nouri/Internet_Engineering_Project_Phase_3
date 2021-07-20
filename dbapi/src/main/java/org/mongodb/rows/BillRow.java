package org.mongodb.rows;

import org.bson.types.ObjectId;

public class BillRow {
    public enum BillStatus{
        IN_PROCESS,
        DONE,
        CANCELED
    }

    private ObjectId id;
    private String name, username, firstname, lastname, address, date;
    private int trackingCode, price, soldNumber;
    BillStatus status;

    public BillRow()
    {
        status = BillStatus.IN_PROCESS;
    }

    public BillRow(String name, String username, String firstname, String lastname, String address, String date,
                   int trackingCode, int price, int soldNumber, BillStatus status) {
        this.name = name;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public int getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(int trackingCode) {
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

    public BillStatus getStatus() {
        return status;
    }

    public void setStatus(BillStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BillRow{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", address='" + address + '\'' +
                ", date='" + date + '\'' +
                ", trackingCode=" + trackingCode +
                ", price=" + price +
                ", soldNumber=" + soldNumber +
                ", status=" + status +
                '}';
    }

}
