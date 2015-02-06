package com.andre.store.models;

import java.io.Serializable;

/**
 * Created by Andre on 1/22/2015.
 */
public class ModelEmployee implements Serializable{
    private int id;
    private int idStore;
    private String phone;
    private String nameEmployee;
    private String position;

    public String getPhone() {
        return phone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdStore(int idStore) {
        this.idStore = idStore;
    }

    public void setNameEmployee(String nameEmployee) {
        this.nameEmployee = nameEmployee;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public int getIdStore() {
        return idStore;
    }

    public String getNameEmployee() {
        return nameEmployee;
    }

    public String getPosition() {
        return position;
    }
}
