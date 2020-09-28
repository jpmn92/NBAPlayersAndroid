package com.nbastatsquiz.tools;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_user";
    String SESSION_USERNAME = "session_username";
    String SESSION_EMAIL = "session_email";
    String SESSION_SOUND = "session_sound";
    String SESSION_IMAGE = "session_image";
    String SESSION_CRONO = "session_CRONO";
    String SESSION_CONCURSO = "session_CONCURSO";


    public SessionManagement(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    //TODO: si se habilitan dos jugadores habrá que generar id´s random
    public void saveSession(String userName) {
        editor.putInt(SESSION_KEY, 1);
        editor.putString(SESSION_USERNAME, userName).commit();
    }

    public void saveSession(boolean cronoOrSound, String valor) {
        editor.putInt(SESSION_KEY, 1);
        if(valor.equals("sound")) {
            editor.putBoolean(SESSION_SOUND, cronoOrSound).commit();
        }
        else{
            editor.putBoolean(SESSION_CRONO, cronoOrSound).commit();
        }
    }

    public void saveSessionConcurso(boolean concurso) {
        editor.putInt(SESSION_KEY, 1);
        editor.putBoolean(SESSION_CONCURSO, concurso).commit();

    }

    public void saveSession(String userName, boolean sound) {
        editor.putInt(SESSION_KEY, 1);
        editor.putString(SESSION_USERNAME, userName).commit();
        editor.putBoolean(SESSION_SOUND, sound).commit();
    }

    public void saveSession(String userName, boolean sound, String url_image) {
        editor.putInt(SESSION_KEY, 1);
        editor.putString(SESSION_USERNAME, userName).commit();
        editor.putBoolean(SESSION_SOUND, sound).commit();
        editor.putString(SESSION_IMAGE, url_image).commit();

    }

    public void saveSession(String userName, String email, String url_image) {
        editor.putInt(SESSION_KEY, 1);
        editor.putString(SESSION_USERNAME, userName).commit();
        editor.putString(SESSION_IMAGE, url_image).commit();
        editor.putString(SESSION_EMAIL, email).commit();

    }

    public void saveSession(String userName, String email) {
        editor.putInt(SESSION_KEY, 1);
        editor.putString(SESSION_USERNAME, userName).commit();
        editor.putString(SESSION_EMAIL, email).commit();

    }



    public int getSession() {

        return sharedPreferences.getInt(SESSION_KEY, -1);

    }

    public String getSessionUserName() {
        return sharedPreferences.getString(SESSION_USERNAME, "sin nombre");
    }

    public String getSesionImage(){
        return sharedPreferences.getString(SESSION_IMAGE, "https://regionaldevelopment.org.au/wp-content/uploads/2019/01/person.png");
    }


    public String getSessionEmail(){
        return sharedPreferences.getString(SESSION_EMAIL, "sin email");
    }

    public boolean getSound(){
        return sharedPreferences.getBoolean(SESSION_SOUND, false);
    }

    public boolean getCrono(){
        return sharedPreferences.getBoolean(SESSION_CRONO, true);
    }

    public boolean getConcurso(){
        return sharedPreferences.getBoolean(SESSION_CONCURSO, false);
    }


    public void removeSession() {
        editor.putInt(SESSION_KEY, -1).commit();
    }

}
