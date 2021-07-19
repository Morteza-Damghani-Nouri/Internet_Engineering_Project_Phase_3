package org.mongodb;

import org.mongodb.rows.ProductRow;

import java.util.ArrayList;
import java.util.List;

public class AdminTasks {
    public boolean checkAuthentication(String username,String password)
    {
        return true;
    }

    public void AddCategory(String categoryName)
    {

    }

    public void DeleteCategory(String categoryName)
    {

    }

    public void ChangeCategoryName(String categoryName)
    {

    }

    public List getBills()
    {
        return new ArrayList();
    }

    public List getBills(String trackingCode)
    {
        return new ArrayList();
    }

    public void changeProduct(String name, ProductRow product)
    {

    }

    public void addProduct(ProductRow product)
    {

    }

}
