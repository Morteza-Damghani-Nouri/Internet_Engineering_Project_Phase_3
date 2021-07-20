package org.mongodb.rows;

import org.bson.types.ObjectId;

import java.util.HashMap;
import java.util.regex.Pattern;

public class UserRow {
    private ObjectId id;
    private String username, Firstname, Lastname, password, address;
    private int charge;

    public UserRow()
    {
        setPassword("");
        setAddress("");
        setLastname("");
        setFirstname("");
        setUsername("");
    }

    public UserRow(String username, String firstname, String lastname, int charge, String password, String address) {
        this.username = username;
        Firstname = firstname;
        Lastname = lastname;
        this.charge = charge;
        this.password = password;
        this.address = address;
    }

    public static UserRow getUserRowByHashMap(HashMap<String, String> productAsMap)
    {
        UserRow user = new UserRow();
        for (String i : productAsMap.keySet()) {
            switch (i) {
                case "username":
                    user.setUsername(productAsMap.get(i));
                    if(!user.isUsernameValid())
                        return null;
                    break;
                case "firstname":
                    user.setFirstname(productAsMap.get(i));
                    if(!user.isFirstnameValid())
                        return null;
                    break;
                case "lastname":
                    user.setLastname(productAsMap.get(i));
                    if(!user.isLastnameValid())
                        return null;
                    break;
                case "password":
                    user.setPassword(productAsMap.get(i));
                    if(!user.isPasswordValid())
                        return null;
                    break;
                case "address":
                    user.setAddress(productAsMap.get(i));
                    if(!user.isAddressValid())
                        return null;
                    break;
                case "charge":
                    user.setCharge(Integer.parseInt(productAsMap.get(i)));
                    break;
                default:
                    return null;
            }
        }
        if(user.username.equals(""))
            return null;

        return user;
    }

    public boolean isUserValid()
    {
        return isUsernameValid() && isFirstnameValid() && isLastnameValid() && isPasswordValid() && isAddressValid();
    }

    public boolean isUsernameValid()
    {
        return Pattern.matches("[a-z0-9A-Z_.\\-]+@[a-z0-9_\\-.]+\\.[a-z]+",getUsername());
    }

    public boolean isFirstnameValid()
    {
        return getFirstname().length() >= 1 && getFirstname().length() <= 255;
    }

    public boolean isLastnameValid()
    {
        return getLastname().length() >= 1 && getLastname().length() <= 255;
    }

    public boolean isPasswordValid()
    {
        if (Pattern.matches("[a-zA-Z]*",getPassword()) ||
                Pattern.matches("[0-9]*",getPassword()) )
            return false;

        return Pattern.matches("[a-zA-Z0-9]{8,}",getPassword());
    }

    public boolean isAddressValid()
    {
        return getAddress().length() >= 1 && getAddress().length() <= 1000;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserRow{" +
                "username='" + username + '\'' +
                ", Firstname='" + Firstname + '\'' +
                ", Lastname='" + Lastname + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
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
