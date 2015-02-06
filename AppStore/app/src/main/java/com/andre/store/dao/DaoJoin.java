package com.andre.store.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import com.andre.store.dbhelper.DBHelper;
import com.andre.store.interfaceData.CursorData;
import com.andre.store.models.*;
import com.andre.store.resource.ResourceCursor;

import java.util.ArrayList;

/**
 * Created by Andre on 1/29/2015.
 */
public class DaoJoin extends DBHelper implements CursorData<ModelJoin> {
    private ResourceCursor resourceCursor = new ResourceCursor();
    private SQLiteDatabase database;
    private SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
    public String tableImage = IMAGE_TABLE;
    public String tableEmployee = EMPLOYEE_TABLE;
    public String tableStore = STORE_TABLE;
    public String[] allColumn = {STORE_TABLE + "." + ID_STORE,
            STORE_TABLE + "." + ID_USER,
            STORE_TABLE + "." + ID_IMAGE,
            EMPLOYEE_TABLE + "." + ID_EMPLOYEE,
            STORE_TABLE + "." + ROW_ADDRESS,
            STORE_TABLE + "." + ROW_CATEGORY,
            STORE_TABLE + "." + ROW_EMAIL,
            STORE_TABLE + "." + ROW_LAST_VISIT,
            STORE_TABLE + "." + ROW_TELEPHONE_STORE,
            STORE_TABLE + "." + ROW_NAME_STORE,
            IMAGE_TABLE + "." + ROW_IMAGE_PATH,
            EMPLOYEE_TABLE + "." + ROW_TELEPHONE_EMPLOYEE,
            EMPLOYEE_TABLE + "." + ROW_NAME_EMLOYEE,
            EMPLOYEE_TABLE + "." + ROW_POSITION};

    public String[] columnStoreAndImage = {STORE_TABLE + "." + ID_STORE,
            STORE_TABLE + "." + ID_USER,
            STORE_TABLE + "." + ID_IMAGE,
            STORE_TABLE + "." + ROW_ADDRESS,
            STORE_TABLE + "." + ROW_CATEGORY,
            STORE_TABLE + "." + ROW_EMAIL,
            STORE_TABLE + "." + ROW_LAST_VISIT,
            STORE_TABLE + "." + ROW_TELEPHONE_STORE,
            STORE_TABLE + "." + ROW_NAME_STORE,
            IMAGE_TABLE + "." + ROW_IMAGE_PATH};

    public String[] columnStoreAndEmployee = {STORE_TABLE + "." + ID_STORE,
            STORE_TABLE + "." + ID_USER,
            STORE_TABLE + "." + ID_IMAGE,
            EMPLOYEE_TABLE + "." + ID_EMPLOYEE,
            STORE_TABLE + "." + ROW_ADDRESS,
            STORE_TABLE + "." + ROW_CATEGORY,
            STORE_TABLE + "." + ROW_EMAIL,
            STORE_TABLE + "." + ROW_LAST_VISIT,
            STORE_TABLE + "." + ROW_TELEPHONE_STORE,
            STORE_TABLE + "." + ROW_NAME_STORE,
            EMPLOYEE_TABLE + "." + ID_STORE,
            EMPLOYEE_TABLE + "." + ROW_TELEPHONE_EMPLOYEE,
            EMPLOYEE_TABLE + "." + ROW_NAME_EMLOYEE,
            EMPLOYEE_TABLE + "." + ROW_POSITION};


    public DaoJoin(Context context) {
        super(context);
    }

    public void read() {
        database = getReadableDatabase();
    }

    public void close() {
        database.close();
    }

    @Override
    public ContentValues setValuesData(ModelJoin modelJoin) {
        return null;
    }

    public ArrayList<ModelImages> getAllDataJoin(String tableName1, String tableName2,
                                                 String tableParent, String foreignKey1,
                                                 String foreignKey2, String primaryKeyJoin) {
        ArrayList<ModelImages> allDataJoin;
        if (tableName2 != null && foreignKey2 != null) {
            allDataJoin = allTableJoin(tableName1, tableName2, tableParent, foreignKey1, foreignKey2
                    , primaryKeyJoin);
        } else {
            allDataJoin = tableJoin(tableName1, tableParent, foreignKey1, primaryKeyJoin);
        }
        return allDataJoin;
    }

    public ModelImages cursorData() {

        ModelJoin modelJoin = new ModelJoin();

/*
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

*/
        ModelImages modelImages = new ModelImages();
        modelImages.setId(resourceCursor.cursorInt(ID_IMAGE));
        modelImages.setIdStore(resourceCursor.cursorInt(ID_STORE));
        modelImages.setImagesPath(resourceCursor.cursorString(ROW_IMAGE_PATH));

/*
            modelJoin.setLongitude(resourceCursor.cursorDouble(ROW_LONGITUDE));
            modelJoin.setIdStore(resourceCursor.cursorInt(ID_STORE));
            modelJoin.setAddress(resourceCursor.cursorString(ROW_ADDRESS));
            modelJoin.setCategoryStore(resourceCursor.cursorString(ROW_CATEGORY));
            modelJoin.setEmail(resourceCursor.cursorString(ROW_EMAIL));
            modelJoin.setIdUser(resourceCursor.cursorInt(ID_USER));
            modelJoin.setLastVisit(resourceCursor.cursorString(ROW_LAST_VISIT));
            modelJoin.setTelephoneStore(resourceCursor.cursorString(ROW_TELEPHONE_STORE));
            modelJoin.setStoreName(resourceCursor.cursorString(ROW_NAME_STORE));
            modelJoin.setLatitude(resourceCursor.cursorDouble(ROW_LATITUDE));

            modelJoin.setIdImage(resourceCursor.cursorInt(ID_IMAGE));
            modelJoin.setIdStore(resourceCursor.cursorInt(ID_STORE));
            modelJoin.setImagesPath(resourceCursor.cursorString(ROW_IMAGE_PATH));

            modelJoin.setIdEmployee(resourceCursor.cursorInt(ID_EMPLOYEE));
            modelJoin.setTelephoneEmployee(resourceCursor.cursorString(ROW_TELEPHONE_EMPLOYEE));
            modelJoin.setIdStore(resourceCursor.cursorInt(ID_STORE));
            modelJoin.setNameEmployee(resourceCursor.cursorString(ROW_NAME_EMLOYEE));
            modelJoin.setPosition(resourceCursor.cursorString(ROW_POSITION));

*/

        return modelImages;

    }

    private ArrayList<ModelImages> allTableJoin(String tableName1, String tableName2,
                                                String tableParent, String foreignKey1,
                                                String foreignKey2, String primaryKeyJoin) {
        ArrayList<ModelImages> allDataJoin = new ArrayList<ModelImages>();
        Cursor cursor;
        try {

            sqLiteQueryBuilder.setTables(tableName1 + "," + tableName2
                    + " LEFT OUTER JOIN " + tableParent + " ON "
                    + tableName1 + "." + foreignKey1 + " = "
                    + tableParent + "." + primaryKeyJoin
                    + " && " + tableName2 + "."
                    + foreignKey2 + " = " + tableParent
                    + "." + primaryKeyJoin);

            cursor = sqLiteQueryBuilder.query(getReadableDatabase(), null, null, null, null, null, null);
            cursor.moveToFirst();
            resourceCursor.setCursor(cursor);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        ModelImages model = cursorData();
                        allDataJoin.add(model);

                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allDataJoin;
    }

    private ArrayList<ModelImages> tableJoin(String tableName1, String tableParent,
                                             String foreignKey1, String primaryKeyJoin) {
        ArrayList<ModelImages> allDataJoin = new ArrayList<ModelImages>();
        Cursor cursor;
        try {
            sqLiteQueryBuilder.setTables(tableName1
                    + " LEFT OUTER JOIN "
                    + tableParent + " ON "
                    + tableName1 + "." + foreignKey1
                    + " = " + tableParent + "." + primaryKeyJoin);
            cursor = sqLiteQueryBuilder.query(getReadableDatabase(), null, null, null, null, null, null);
//            cursor = database.rawQuery(queryData,new String[]{});
            cursor.moveToFirst();
            resourceCursor.setCursor(cursor);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        ModelImages model = cursorData();
                        allDataJoin.add(model);
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
                close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allDataJoin;
    }

}
