package com.andre.store.models;

import java.io.Serializable;

/**
 * Created by Andre on 1/22/2015.
 */
public class ModelCategory implements Serializable {
    private int id;
    private String category;

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
