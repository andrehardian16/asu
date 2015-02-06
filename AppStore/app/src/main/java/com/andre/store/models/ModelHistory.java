package com.andre.store.models;

/**
 * Created by Andre on 1/22/2015.
 */
public class ModelHistory {
    private int id;
    private int idStore;
    private int quantity;
    private int total;
    private String lastDate;

    public int getId() {
        return id;
    }

    public int getIdStore() {
        return idStore;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdStore(int idStore) {
        this.idStore = idStore;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getLastDate() {
        return lastDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTotal() {
        return total;
    }
}
