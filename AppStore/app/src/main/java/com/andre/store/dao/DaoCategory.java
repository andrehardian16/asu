package com.andre.store.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.andre.store.dbhelper.DBHelper;
import com.andre.store.interfaceData.CursorData;
import com.andre.store.interfaceData.InterfaceSetModel;
import com.andre.store.models.ModelCategory;
import com.andre.store.models.ModelValuesCursor;
import com.andre.store.resource.ResourceCursor;

import java.util.ArrayList;

/**
 * Created by Andre on 1/22/2015.
 */
public class DaoCategory extends BaseDao<ModelCategory> implements CursorData<ModelCategory> {
    public String tableName = CATEGORY_TABLE;
    public String[] allColumns = {ID_CATEGORY, ROW_CATEGORY};

    public DaoCategory(Context context) {
        super(context);
    }


    @Override
    public void writeData() {
        super.writeData();
    }

    @Override
    public void readData() {
        super.readData();
    }

    @Override
    public void close() {
        super.close();
    }


    @Override
    public ModelCategory cursorData() {
        ModelCategory modelCategory = new ModelCategory();
        modelCategory.setCategory(resourceCursor.cursorString(ROW_CATEGORY));

        return modelCategory;
    }

    @Override
    public ContentValues setValuesData(ModelCategory modelCategory) {
        ContentValues valuesData = new ContentValues();
        valuesData.put(ROW_CATEGORY, modelCategory.getCategory());
        return valuesData;
    }


    @Override
    public ArrayList<ModelCategory> getAllData(String tableName, String[] column) {
        return super.getAllData(tableName, column);
    }

    @Override
    public Long createData(String tableName, ContentValues contentValues) {
        return super.createData(tableName, contentValues);
    }
}
