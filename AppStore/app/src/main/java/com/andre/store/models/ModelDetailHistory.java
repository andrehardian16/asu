package com.andre.store.models;

/**
 * Created by Andre on 1/23/2015.
 */
public class ModelDetailHistory {
    private int id;
    private int idStore;
    private int price;
    private int quantity;
    private String code;
    private String nameDetailHistory;
    private String lastDate;

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdStore(int idUser) {
        this.idStore = idUser;
    }

    public void setNameDetailHistory(String nameDetailHistory) {
        this.nameDetailHistory = nameDetailHistory;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public int getIdStore() {
        return idStore;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getNameDetailHistory() {
        return nameDetailHistory;
    }

    public String getCode() {
        return code;
    }
}
