package com.andre.store.resource;

import android.database.Cursor;
import com.andre.store.interfaceData.InterfaceSetModel;

/**
 * Created by Andre on 1/23/2015.
 */
public class ResourceCursor implements InterfaceSetModel {
    private Cursor cursor;

    @Override
    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    @Override
    public int cursorInt(String field) {
        int cur = cursor.getInt(cursor.getColumnIndex(field));
        return cur;
    }

    @Override
    public String cursorString(String field) {
        String cur = cursor.getString(cursor.getColumnIndex(field));

        return cur;
    }

    @Override
    public double cursorDouble(String field) {
        double cur = cursor.getDouble(cursor.getColumnIndex(field));
        return cur;
    }

}
