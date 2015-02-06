package com.andre.store.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Andre on 1/22/2015.
 */
public class ModelStore implements Serializable {
    private int idStore;
    private int idUser;
    private String storeName;
    private String categoryStore;
    private String address;
    private String Email;
    private String lastVisit;
    private String phone;
    private ArrayList<ModelImages> listImage;
    private ArrayList<ModelEmployee> listEmployee;
    private double latitude;
    private double longitude;


    public void setListEmployee(ArrayList<ModelEmployee> listEmployee) {
        this.listEmployee = listEmployee;
    }

    public ArrayList<ModelEmployee> getListEmployee() {
        return listEmployee;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setListImage(ArrayList<ModelImages> listImage) {
        this.listImage = listImage;
    }

    public ArrayList<ModelImages> getListImage() {
        return listImage;
    }

    public int getIdStore() {
        return idStore;
    }

    public void setIdStore(int idStore) {
        this.idStore = idStore;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCategoryStore(String categoryStore) {
        this.categoryStore = categoryStore;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setLastVisit(String lastVisit) {
        this.lastVisit = lastVisit;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getAddress() {
        return address;
    }

    public String getCategoryStore() {
        return categoryStore;
    }

    public String getEmail() {
        return Email;
    }

    public String getLastVisit() {
        return lastVisit;
    }

    public String getPhone() {
        return phone;
    }

    public String getStoreName() {
        return storeName;
    }
}
