package com.andre.store.interfaceData;

import android.content.ContentValues;
import android.database.Cursor;
import com.andre.store.models.ModelCategory;
import com.andre.store.models.ModelUser;

/**
 * Created by Andre on 1/22/2015.
 */
public interface CursorData<Model> {
//    ContentValues valuesData = new ContentValues();
//    public Model cursorData(Cursor cursor);
    public ContentValues setValuesData(Model model);

}
