package org.mongodb.rows;

import org.bson.types.ObjectId;

public class CategoryRow {
    private ObjectId id;
    private String name;

    public CategoryRow()
    {

    }

    public CategoryRow(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "CategoryRow{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
