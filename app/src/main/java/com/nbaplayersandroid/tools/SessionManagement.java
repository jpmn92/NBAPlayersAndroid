package com.nbaplayersandroid.tools;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_user";
    String SESSION_USERNAME = "session_username";


    public SessionManagement(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    //TODO: si se habilitan dos jugadores habrá que generar id´s random
    public void saveSession(String userName) {
        editor.putInt(SESSION_KEY, 1);
        editor.putString(SESSION_USERNAME, userName).commit();
    }

    public int getSession() {

        return sharedPreferences.getInt(SESSION_KEY, -1);

    }

    public String getSessionUserName() {
        return sharedPreferences.getString(SESSION_USERNAME, "sin_nombre");
    }

    public void removeSession() {
        editor.putInt(SESSION_KEY, -1).commit();
    }

}
