package com.andre.store.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.andre.store.interfaceData.CursorData;
import com.andre.store.models.ModelDetailHistory;
import com.andre.store.models.ModelHistory;

import java.util.ArrayList;

/**
 * Created by Andre on 1/23/2015.
 */
public class DaoDetailHistory extends BaseDao<ModelDetailHistory> implements CursorData<ModelDetailHistory> {
    private ContentValues values;
    public String tableName = DETAIL_HISTORY_TABLE;
    public String[] allColumns = {ID_STORE, ID_DETAIL_HISTORY, ROW_PRICE, ROW_QUANTITY, ROW_CODE,
            ROW_NAME_DETAIL_HISTORY, ROW_LAST_DATE};

    public DaoDetailHistory(Context context) {
        super(context);
    }

    @Override
    public ModelDetailHistory cursorData() {
        ModelDetailHistory modelDetailHistory = new ModelDetailHistory();
        modelDetailHistory.setId(resourceCursor.cursorInt(ID_DETAIL_HISTORY));
        modelDetailHistory.setCode(resourceCursor.cursorString(ROW_CODE));
        modelDetailHistory.setIdStore(resourceCursor.cursorInt(ID_STORE));
        modelDetailHistory.setNameDetailHistory(resourceCursor.cursorString(ROW_NAME_DETAIL_HISTORY));
        modelDetailHistory.setPrice(resourceCursor.cursorInt(ROW_PRICE));
        modelDetailHistory.setQuantity(resourceCursor.cursorInt(ROW_QUANTITY));
        modelDetailHistory.setLastDate(resourceCursor.cursorString(ROW_LAST_DATE));

        return modelDetailHistory;
    }

    @Override
    public ContentValues setValuesData(ModelDetailHistory modelDetailHistory) {
        ContentValues valuesData = new ContentValues();
        valuesData.put(ID_STORE, modelDetailHistory.getIdStore());
        valuesData.put(ROW_CODE, modelDetailHistory.getCode());
        valuesData.put(ROW_NAME_DETAIL_HISTORY, modelDetailHistory.getNameDetailHistory());
        valuesData.put(ROW_PRICE, modelDetailHistory.getPrice());
        valuesData.put(ROW_QUANTITY, modelDetailHistory.getQuantity());
        return valuesData;
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
    public ArrayList<ModelDetailHistory> getAllData(String tableName, String[] column) {
        return super.getAllData(tableName, column);
    }

    @Override
    public Long createData(String tableName, ContentValues contentValues) {
        return super.createData(tableName, contentValues);
    }

    public ModelDetailHistory getData(String selection1, String selection2, String[] arg) {
        String selection = selection1 + " = ? AND " + selection2 + " = ?";
        Cursor cursor = database.query(true, tableName, allColumns, selection, arg, null, null, null, null);
        resourceCursor.setCursor(cursor);
        cursor.moveToFirst();
        if (cursor.moveToFirst() && cursor != null) {
            if (cursor.getCount() > 0) {
                ModelDetailHistory modelDetailHistory = cursorData();
                return modelDetailHistory;
            }
        }
        cursor.close();
        return null;
    }
}
