package com.andre.store.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.andre.store.interfaceData.CursorData;
import com.andre.store.models.ModelHistory;
import com.andre.store.models.ModelValuesCursor;
import com.andre.store.resource.ResourceCursor;

/**
 * Created by Andre on 1/23/2015.
 */
public class DaoHistory extends BaseDao<ModelHistory> implements CursorData<ModelHistory> {

    private ContentValues values;
    public String tableName = HISTORY_TABLE;
    public String[] allColumns = {ID_STORE, ID_HISTORY, ROW_QUANTITY, ROW_TOTAL, ROW_LAST_DATE};

    public DaoHistory(Context context) {
        super(context);
    }

    @Override
    public ModelHistory cursorData() {
        ModelHistory modelHistory = new ModelHistory();
        modelHistory.setIdStore(resourceCursor.cursorInt(ID_STORE));
        modelHistory.setId(resourceCursor.cursorInt(ID_HISTORY));
        modelHistory.setLastDate(resourceCursor.cursorString(ROW_LAST_DATE));
        modelHistory.setQuantity(resourceCursor.cursorInt(ROW_QUANTITY));
        modelHistory.setTotal(resourceCursor.cursorInt(ROW_TOTAL));

        return modelHistory;
    }

    @Override
    public ContentValues setValuesData(ModelHistory modelHistory) {
        ContentValues valuesData = new ContentValues();
        valuesData.put(ID_STORE, modelHistory.getIdStore());
        valuesData.put(ROW_QUANTITY, modelHistory.getQuantity());
        valuesData.put(ROW_TOTAL, modelHistory.getTotal());
        return valuesData;

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
    public Long createData(String tableName, ContentValues contentValues) {
        return super.createData(tableName, contentValues);
    }
}
