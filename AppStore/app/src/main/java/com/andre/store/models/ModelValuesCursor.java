package com.andre.store.models;

import android.content.ContentValues;

/**
 * Created by Andre on 1/23/2015.
 */
public class ModelValuesCursor {
    private ContentValues contentValues = new ContentValues();


    public void setContentValues(ContentValues contentValues) {
        this.contentValues = contentValues;
    }

    public ContentValues getContentValues() {
        return contentValues;
    }
}
