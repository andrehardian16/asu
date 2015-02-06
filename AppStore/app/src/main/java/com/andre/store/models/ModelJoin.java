package com.andre.store.models;

/**
 * Created by Andre on 1/29/2015.
 */
public class ModelJoin {
    private int idImage;
    private int idStore;
    private int idUser;
    private int idEmployee;
    private String imagesPath;
    private String storeName;
    private String categoryStore;
    private String address;
    private String Email;
    private String lastVisit;
    private String telephoneStore;
    private String telephoneEmployee;
    private String nameEmployee;
    private String position;
    private double latitude;
    private double longitude;

    public void setTelephoneEmployee(String telephoneEmployee) {
        this.telephoneEmployee = telephoneEmployee;
    }

    public void setTelephoneStore(String telephoneStore) {
        this.telephoneStore = telephoneStore;
    }

    public String getTelephoneEmployee() {
        return telephoneEmployee;
    }

    public String getTelephoneStore() {
        return telephoneStore;
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



    public String getStoreName() {
        return storeName;
    }

    public void setIdImage(int id) {
        this.idImage = id;
    }

    public void setImagesPath(String imagesPath) {
        this.imagesPath = imagesPath;
    }

    public int getIdImage() {
        return idImage;
    }

    public String getImagesPath() {
        return imagesPath;
    }



    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public void setIdStore(int idStore) {
        this.idStore = idStore;
    }

    public void setNameEmployee(String nameEmployee) {
        this.nameEmployee = nameEmployee;
    }


    public void setPosition(String position) {
        this.position = position;
    }

    public int getIdEmployee() {
        return idEmployee;
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

