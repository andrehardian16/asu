package com.andre.store.models;

import android.content.ContentValues;

import java.io.Serializable;

/**
 * Created by Andre on 1/20/2015.
 */
public class ModelUser implements Serializable {
    private int id;
    private String UserName;
    private String Password;

    public void setId(int id) {
        this.id = id;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return Password;
    }

    public String getUserName() {
        return UserName;
    }
}
