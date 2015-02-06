package com.andre.store.interfaceData;

import android.database.Cursor;

/**
 * Created by Andre on 1/23/2015.
 */
public interface InterfaceSetModel {
    public void setCursor(Cursor cursor);
    public int cursorInt(String field);
    public String cursorString(String field);
    public double cursorDouble(String field);
}
