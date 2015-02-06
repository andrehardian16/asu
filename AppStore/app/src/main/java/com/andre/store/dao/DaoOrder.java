package com.andre.store.dao;

import android.content.ContentValues;
import android.content.Context;
import com.andre.store.interfaceData.CursorData;
import com.andre.store.models.ModelOrder;

import java.util.ArrayList;

/**
 * Created by Andre on 1/23/2015.
 */
public class DaoOrder extends BaseDao<ModelOrder> implements CursorData<ModelOrder>{
    private ContentValues values;
    public String tableName = ORDER_TABLE;
    public String[] allColumns = {ID_ORDER,ROW_PRICE,ROW_NAME_ORDER,ROW_CODE,ROW_STOCK,ROW_PRICE,ROW_BUY};
    public DaoOrder(Context context) {
        super(context);
    }

    @Override
    public ModelOrder cursorData() {
        ModelOrder modelOrder = new ModelOrder();
        modelOrder.setId(resourceCursor.cursorInt(ID_ORDER));
        modelOrder.setPrice(resourceCursor.cursorInt(ROW_PRICE));
        modelOrder.setCode(resourceCursor.cursorString(ROW_CODE));
        modelOrder.setBuy(resourceCursor.cursorInt(ROW_BUY));
        modelOrder.setNameOrder(resourceCursor.cursorString(ROW_NAME_ORDER));
        modelOrder.setStock(resourceCursor.cursorInt(ROW_STOCK));

        return modelOrder;
    }

    @Override
    public ContentValues setValuesData(ModelOrder modelOrder) {
    ContentValues valuesData = new ContentValues();
        valuesData.put(ROW_PRICE,modelOrder.getPrice());
        valuesData.put(ROW_CODE,modelOrder.getCode());
        valuesData.put(ROW_BUY,modelOrder.getBuy());
        valuesData.put(ROW_NAME_ORDER,modelOrder.getNameOrder());
        valuesData.put(ROW_STOCK,modelOrder.getStock());

        return valuesData;
    }

    @Override
    public Long createData(String tableName, ContentValues contentValues) {
        return super.createData(tableName, contentValues);
    }

    @Override
    public ArrayList<ModelOrder> getAllData(String tableName, String[] column) {
        return super.getAllData(tableName, column);
    }

    @Override
    public void deleteData(String tableName, String where, String args) {
        super.deleteData(tableName, where, args);
    }

    @Override
    public void updateData(String tableName, ContentValues contentValues, String nameId, int id) {
        super.updateData(tableName, contentValues, nameId, id);
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
    public void writeData() {
        super.writeData();
    }
}
