package com.example.univisit;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class Session {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    private static final String ID = "ID";




    public Session(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String id ){
        editor.putBoolean(LOGIN, true);
        editor.putString("ID", id);
        editor.apply();


    }

    public HashMap<String, String> getUserDetail(){

        HashMap<String, String> user = new HashMap<>();
        user.put(ID, sharedPreferences.getString(ID, null));

        return user;
    }

    @Override
    public String toString() {
        return  getUserDetail().get(ID) ;
    }

}
