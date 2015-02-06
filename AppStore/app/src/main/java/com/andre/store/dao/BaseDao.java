package com.andre.store.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.andre.store.dbhelper.DBHelper;
import com.andre.store.interfaceData.CursorData;
import com.andre.store.models.ModelUser;
import com.andre.store.models.ModelValuesCursor;
import com.andre.store.resource.ResourceCursor;

import java.util.ArrayList;

/**
 * Created by Andre on 1/22/2015.
 */
public class BaseDao<Model> extends DBHelper {
    protected SQLiteDatabase database;
    protected ResourceCursor resourceCursor = new ResourceCursor();
    private CursorData<Model> cursorData;

    public BaseDao(Context context) {
        super(context);
/*
        this.ModelData = ((Class) ((java.lang.reflect.ParameterizedType)
                getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
*/

    }

    public void writeData() {
        database = getWritableDatabase();
    }

    public void readData() {
        database = getReadableDatabase();
    }

    public void close() {
        database.close();
    }

    public Long createData(String tableName, ContentValues contentValues) {
        Long result = null;
        try {
            result = database.insert(tableName, null, contentValues);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Model getData(String tableName, String[] column, String selection, String arg) {
        Cursor cursor = database.query(tableName, column, selection + " = " + arg, null, null, null, null);
        cursor.moveToFirst();
        resourceCursor.setCursor(cursor);
        Model model = cursorData();
        cursor.close();
        return model;
    }



    public ArrayList<Model> getAllData(String tableName, String[] column) {
        ArrayList<Model> allData = new ArrayList<Model>();
        Cursor cursor;
        try {
            cursor = database.query(tableName, column,null, null, null, null, null);
            cursor.moveToFirst();
            resourceCursor.setCursor(cursor);

            try {
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        Model model = cursorData();
                        allData.add(model);
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
            }
        } catch (Exception e) {
            Log.e("DB error", e.toString());
            e.printStackTrace();
        }
        return allData;
    }

    public void deleteData(String tableName, String where, String args) {
        try {
            database.delete(tableName, where + " = " + args, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateData(String tableName, ContentValues contentValues,String nameId, int id) {
        database.update(tableName, contentValues, nameId + " = " + id, null);

    }


    public Model cursorData() {
        return null;
    }

}
