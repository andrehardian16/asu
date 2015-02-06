package com.andre.store.sessions;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Andre on 1/21/2015.
 */
public class SessionUser {
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;
    private final String KEY_LOGIN = "TEST";

    public SessionUser(Context context) {
        sharedPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.commit();
    }

    public void setId(int id) {
        editor.putBoolean(KEY_LOGIN, true);
        editor.putInt("login", id);
        editor.commit();
    }

    public Integer getId() {
        return sharedPreferences.getInt("login", 0);
    }

    public boolean isSession() {
        return sharedPreferences.getBoolean(KEY_LOGIN, false);
    }

    public void clearId() {
        editor.clear();
        editor.commit();
    }
}
