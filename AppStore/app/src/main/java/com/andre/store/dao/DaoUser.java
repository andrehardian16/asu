package com.andre.store.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.andre.store.interfaceData.CursorData;
import com.andre.store.models.ModelUser;
import com.andre.store.resource.ResourceCursor;

import java.util.ArrayList;

/**
 * Created by Andre on 1/20/2015.
 */
public class DaoUser extends BaseDao<ModelUser> implements CursorData<ModelUser> {

    public String tableName = USER_TABLE;
    public String[] allColumn = {ID_USER,
            ROW_USER_NAME,
            ROW_PASSWORD};

    public DaoUser(Context context) {
        super(context);
    }

    @Override
    public void close() {
        super.close();
    }

    @Override
    public void readData() {
        super.readData();
    }

    @Override
    public void writeData() {
        super.writeData();
    }

    @Override
    public Long createData(String tableName, ContentValues contentValues) {
        return super.createData(tableName, contentValues);
    }

    public ModelUser cursorData() {
        ModelUser modelUser = new ModelUser();
        modelUser.setUserName(resourceCursor.cursorString(ROW_USER_NAME));
        modelUser.setPassword(resourceCursor.cursorString(ROW_PASSWORD));
        modelUser.setId(resourceCursor.cursorInt(ID_USER));

        return modelUser;
    }

    @Override
    public ContentValues setValuesData(ModelUser modelUser) {
        ContentValues valuesData = new ContentValues();
        valuesData.put(ROW_USER_NAME, modelUser.getUserName());
        valuesData.put(ROW_PASSWORD, modelUser.getPassword());
        return valuesData;
    }

    public ModelUser isUser(String user, String pass) {
        String selection = ROW_USER_NAME + " =  ? AND " + ROW_PASSWORD + " = ?";
        String[] args = {user, pass};
        Cursor cursor = database.query(USER_TABLE, allColumn, selection, args, null, null, null);
        cursor.moveToFirst();
        resourceCursor.setCursor(cursor);
        if (cursor != null && cursor.moveToFirst()) {
            if (cursor.getCount() > 0) {
                ModelUser modelUser = cursorData();
                return modelUser;
            }
        }
        cursor.close();
        return null;
    }

    @Override
    public ModelUser getData(String tableName, String[] column, String selection, String args) {
        return super.getData(tableName, column, selection, args);
    }

    @Override
    public ArrayList<ModelUser> getAllData(String tableName, String[] column) {
        ArrayList<ModelUser> allData = new ArrayList<ModelUser>();
        Cursor cursor;
        try {
            cursor = database.query(USER_TABLE, allColumn, null, null, null, null, null);
            cursor.moveToFirst();
            resourceCursor.setCursor(cursor);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        ModelUser model = cursorData();
                        allData.add(model);
                        return allData;
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

}

