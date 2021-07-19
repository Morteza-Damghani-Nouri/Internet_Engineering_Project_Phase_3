package org.mongodb.rows;

import org.bson.types.ObjectId;

public class UserRow {
    private ObjectId id;
    private String username, Firstname, Lastname;
    private int charge;

    public UserRow()
    {

    }

    public UserRow(String username, String firstname, String lastname, int charge) {
        this.username = username;
        Firstname = firstname;
        Lastname = lastname;
        this.charge = charge;
    }

    @Override
    public String toString() {
        return "UserRow{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", Firstname='" + Firstname + '\'' +
                ", Lastname='" + Lastname + '\'' +
                ", charge=" + charge +
                '}';
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }
}
