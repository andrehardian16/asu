package com.andre.store.models;

import java.io.Serializable;

/**
 * Created by Andre on 1/22/2015.
 */
public class ModelImages implements Serializable{
    private int id;
    private int idStore;
    private String imagesPath;

    public void setIdStore(int idStore) {
        this.idStore = idStore;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImagesPath(String imagesPath) {
        this.imagesPath = imagesPath;
    }

    public int getId() {
        return id;
    }

    public int getIdStore() {
        return idStore;
    }

    public String getImagesPath() {
        return imagesPath;
    }
}
