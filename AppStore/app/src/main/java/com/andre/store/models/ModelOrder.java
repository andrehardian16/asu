package com.andre.store.models;

import java.io.Serializable;

/**
 * Created by Andre on 1/22/2015.
 */
public class ModelOrder implements Serializable {
    private int id;
    private int buy;
    private int stock;
    private int price;
    private String code;
    private String nameOrder;
    private int quantity = 1;
    private int idStore;

    public void setIdStore(int idStore) {
        this.idStore = idStore;
    }

    public int getIdStore() {
        return idStore;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBuy(int buy) {
        this.buy = buy;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setNameOrder(String nameOrder) {
        this.nameOrder = nameOrder;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getBuy() {
        return buy;
    }

    public int getPrice() {
        return price;
    }

    public String getCode() {
        return code;
    }

    public String getNameOrder() {
        return nameOrder;
    }
}
