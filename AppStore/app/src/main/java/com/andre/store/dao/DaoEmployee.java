package com.andre.store.dao;

import android.content.ContentValues;
import android.content.Context;
import com.andre.store.interfaceData.CursorData;
import com.andre.store.models.ModelEmployee;

import java.util.ArrayList;

/**
 * Created by Andre on 1/23/2015.
 */
public class DaoEmployee extends BaseDao<ModelEmployee> implements CursorData<ModelEmployee> {

    public String tableName = EMPLOYEE_TABLE;
    public String[] allColumn = {ID_EMPLOYEE,ROW_TELEPHONE_EMPLOYEE,ID_STORE,ROW_NAME_EMLOYEE,ROW_POSITION};

    public DaoEmployee(Context context) {
        super(context);
    }

    @Override
    public ModelEmployee cursorData() {
        ModelEmployee modelEmployee = new ModelEmployee();
        modelEmployee.setId(resourceCursor.cursorInt(ID_EMPLOYEE));
        modelEmployee.setPhone(resourceCursor.cursorString(ROW_TELEPHONE_EMPLOYEE));
        modelEmployee.setIdStore(resourceCursor.cursorInt(ID_STORE));
        modelEmployee.setNameEmployee(resourceCursor.cursorString(ROW_NAME_EMLOYEE));
        modelEmployee.setPosition(resourceCursor.cursorString(ROW_POSITION));

        return modelEmployee;
    }

    @Override
    public ContentValues setValuesData(ModelEmployee modelEmployee) {
        ContentValues valuesData = new ContentValues();
        valuesData.put(ID_STORE, modelEmployee.getIdStore());
        valuesData.put(ROW_TELEPHONE_EMPLOYEE, modelEmployee.getPhone());
        valuesData.put(ROW_POSITION, modelEmployee.getPosition());
        valuesData.put(ROW_NAME_EMLOYEE, modelEmployee.getNameEmployee());
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
    public void updateData(String tableName, ContentValues contentValues, String nameId, int id) {
        super.updateData(tableName, contentValues, nameId, id);
    }

    @Override
    public ArrayList<ModelEmployee> getAllData(String tableName, String[] column) {
        return super.getAllData(tableName, column);
    }


    @Override
    public Long createData(String tableName, ContentValues contentValues) {
        return super.createData(tableName, contentValues);
    }

    @Override
    public void close() {
        super.close();
    }

    @Override
    public void deleteData(String tableName, String where, String args) {
        super.deleteData(tableName, where, args);
    }

}
