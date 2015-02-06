package com.andre.store.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import com.andre.store.interfaceData.CursorData;
import com.andre.store.models.ModelEmployee;
import com.andre.store.models.ModelImages;
import com.andre.store.models.ModelStore;

import java.util.ArrayList;

/**
 * Created by Andre on 1/23/2015.
 */
public class DaoStore extends BaseDao<ModelStore> implements CursorData<ModelStore> {
    private ContentValues values;
    public String tableName = STORE_TABLE;
    public String[] allColumn = {ID_STORE, ID_USER, ROW_ADDRESS, ROW_CATEGORY, ROW_EMAIL, ROW_LAST_VISIT
            , ROW_TELEPHONE_STORE, ROW_NAME_STORE, ROW_LONGITUDE, ROW_LATITUDE};

    public DaoStore(Context context) {
        super(context);
    }

    @Override
    public ModelStore cursorData() {
        ModelStore modelStore = new ModelStore();
        modelStore.setIdStore(resourceCursor.cursorInt(ID_STORE));
        modelStore.setAddress(resourceCursor.cursorString(ROW_ADDRESS));
        modelStore.setCategoryStore(resourceCursor.cursorString(ROW_CATEGORY));
        modelStore.setEmail(resourceCursor.cursorString(ROW_EMAIL));
        modelStore.setIdUser(resourceCursor.cursorInt(ID_USER));
        modelStore.setLastVisit(resourceCursor.cursorString(ROW_LAST_VISIT));
        modelStore.setPhone(resourceCursor.cursorString(ROW_TELEPHONE_STORE));
        modelStore.setStoreName(resourceCursor.cursorString(ROW_NAME_STORE));
        modelStore.setLatitude(resourceCursor.cursorDouble(ROW_LATITUDE));
        modelStore.setLongitude(resourceCursor.cursorDouble(ROW_LONGITUDE));

        return modelStore;
    }

    @Override
    public ArrayList<ModelStore> getAllData(String tableName, String[] column) {
        return super.getAllData(tableName, column);
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
    public void close() {
        super.close();
    }

    @Override
    public ModelStore getData(String tableName, String[] column, String selection, String args) {
        return super.getData(tableName, column, selection, args);
    }

    @Override
    public void updateData(String tableName, ContentValues contentValues, String nameId, int id) {
        super.updateData(tableName, contentValues, nameId, id);
    }

    //set values for insert data
    @Override
    public ContentValues setValuesData(ModelStore modelStore) {
        ContentValues valuesData = new ContentValues();
        valuesData.put(ID_USER, modelStore.getIdUser());
        valuesData.put(ROW_NAME_STORE, modelStore.getStoreName());
        valuesData.put(ROW_CATEGORY, modelStore.getCategoryStore());
        valuesData.put(ROW_ADDRESS, modelStore.getAddress());
        valuesData.put(ROW_EMAIL, modelStore.getEmail());
        valuesData.put(ROW_LAST_VISIT, modelStore.getLastVisit());
        valuesData.put(ROW_TELEPHONE_STORE, modelStore.getPhone());
        valuesData.put(ROW_LONGITUDE, modelStore.getLongitude());
        valuesData.put(ROW_LATITUDE, modelStore.getLatitude());

        return valuesData;


    }

    public ContentValues valuesUpdate (ModelStore modelStore){
        ContentValues valuesData = new ContentValues();
        valuesData.put(ROW_NAME_STORE, modelStore.getStoreName());
        valuesData.put(ROW_CATEGORY, modelStore.getCategoryStore());
        valuesData.put(ROW_ADDRESS, modelStore.getAddress());
        valuesData.put(ROW_EMAIL, modelStore.getEmail());
        valuesData.put(ROW_TELEPHONE_STORE, modelStore.getPhone());

        return valuesData;

    }


    public ModelImages cursorDataImage() {
        ModelImages modelImages = new ModelImages();
        modelImages.setId(resourceCursor.cursorInt(ID_IMAGE));
        modelImages.setIdStore(resourceCursor.cursorInt(ID_STORE));
        modelImages.setImagesPath(resourceCursor.cursorString(ROW_IMAGE_PATH));

        return modelImages;
    }

    public ModelImages getDataImage(String tableName, String[] column, String selection, String arg) {
        Cursor cursor = database.query(tableName, column, selection + " = " + arg, null, null, null, null);
        cursor.moveToFirst();
        resourceCursor.setCursor(cursor);
        ModelImages model = cursorDataImage();
        cursor.close();
        return model;
    }

    public ModelEmployee cursorDataEmployee() {
        ModelEmployee modelEmployee = new ModelEmployee();
        modelEmployee.setId(resourceCursor.cursorInt(ID_EMPLOYEE));
        modelEmployee.setPhone(resourceCursor.cursorString(ROW_TELEPHONE_EMPLOYEE));
        modelEmployee.setIdStore(resourceCursor.cursorInt(ID_STORE));
        modelEmployee.setNameEmployee(resourceCursor.cursorString(ROW_NAME_EMLOYEE));
        modelEmployee.setPosition(resourceCursor.cursorString(ROW_POSITION));

        return modelEmployee;
    }
    public ModelEmployee getDataEmployee(String tableName, String[] column, String selection, String arg) {
        Cursor cursor = database.query(tableName, column, selection + " = " + arg, null, null, null, null);
        cursor.moveToFirst();
        resourceCursor.setCursor(cursor);
        ModelEmployee model = cursorDataEmployee();
        cursor.close();
        return model;
    }

    public ArrayList<ModelEmployee> getAllDataEmployee(String tableName, String[] column,String selection, String arg) {
        ArrayList<ModelEmployee> allData = new ArrayList<ModelEmployee>();
        Cursor cursor;
        try {
            cursor = database.query(tableName, column,selection + " = " + arg, null, null, null, null);
            cursor.moveToFirst();
            resourceCursor.setCursor(cursor);

            try {
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        ModelEmployee model = cursorDataEmployee();
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


}
