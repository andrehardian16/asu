package com.andre.store.interfaceData;

import android.content.ContentValues;

/**
 * Created by Andre on 1/22/2015.
 */
public interface CursorData<Model> {
    //    ContentValues valuesData = new ContentValues();
//    public Model cursorData(Cursor cursor);
    public ContentValues setValuesData(Model model);

}
