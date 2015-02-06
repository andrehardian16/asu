package com.andre.store.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.andre.store.interfaceData.CursorData;
import com.andre.store.models.ModelImages;
import com.andre.store.models.ModelValuesCursor;
import com.andre.store.resource.ResourceCursor;

import java.util.ArrayList;

/**
 * Created by Andre on 1/23/2015.
 */
public class DaoImage extends BaseDao<ModelImages> implements CursorData<ModelImages> {
    private ContentValues values;
    public String tableName = IMAGE_TABLE;
    public String[] allColumns = {ID_IMAGE, ID_STORE, ROW_IMAGE_PATH};

    public DaoImage(Context context) {
        super(context);
    }

    @Override
    public ModelImages cursorData() {
        ModelImages modelImages = new ModelImages();
        modelImages.setId(resourceCursor.cursorInt(ID_IMAGE));
        modelImages.setIdStore(resourceCursor.cursorInt(ID_STORE));
        modelImages.setImagesPath(resourceCursor.cursorString(ROW_IMAGE_PATH));


        return modelImages;
    }

    @Override
    public ContentValues setValuesData(ModelImages modelImages) {
        ContentValues valuesData = new ContentValues();
        valuesData.put(ID_STORE, modelImages.getIdStore());
        valuesData.put(ROW_IMAGE_PATH, modelImages.getImagesPath());
        return valuesData;
    }

    public ContentValues valuesUpdate(ModelImages modelImages){
        ContentValues valuesData = new ContentValues();
        valuesData.put(ROW_IMAGE_PATH, modelImages.getImagesPath());
        return valuesData;
    }


    @Override
    public void writeData() {
        super.writeData();
    }

    @Override
    public ArrayList<ModelImages> getAllData(String tableName, String[] column) {
        return super.getAllData(tableName, column);
    }


    @Override
    public Long createData(String tableName, ContentValues contentValues) {
        return super.createData(tableName, contentValues);
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
    public void updateData(String tableName, ContentValues contentValues, String nameId, int id) {
        super.updateData(tableName, contentValues, nameId, id);
    }

    @Override
    public ModelImages getData(String tableName, String[] column, String selection, String arg) {
        return super.getData(tableName, column, selection, arg);
    }

    @Override
    public void deleteData(String tableName, String where, String args) {
        super.deleteData(tableName, where, args);
    }


}
