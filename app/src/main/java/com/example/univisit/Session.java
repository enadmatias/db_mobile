package com.example.univisit;

import android.content.Context;
import android.content.Intent;
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
    private static final String FNAME = "FNAME";
    private static final String LNAME = "LNAME";
    private static final String ADDRESS = "ADDRESS";
    private static final String CONTACT = "CONTACT";
    private static final String EMAIL = "EMAIL";
    private static final String USERNAME = "USERNAME";
    private static final String PHOTO_PATH = "PHOTO_PATH";



    public Session(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String id , String fname, String lname, String address, String contact, String email, String username, String path){
        editor.putBoolean(LOGIN, true);
        editor.putString("ID", id);
        editor.putString("FNAME", fname);
        editor.putString("LNAME", lname);
        editor.putString("ADDRESS", address);
        editor.putString("CONTACT", contact);
        editor.putString("EMAIL", email);
        editor.putString("USERNAME", username);
        editor.putString("PHOTO_PATH", path);
        editor.apply();


    }
    public boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public HashMap<String, String> getUserDetail(){

        HashMap<String, String> user = new HashMap<>();
        user.put(ID, sharedPreferences.getString(ID, null));
        user.put(FNAME, sharedPreferences.getString(FNAME, null));
        user.put(LNAME, sharedPreferences.getString(LNAME, null));
        user.put(ADDRESS, sharedPreferences.getString(ADDRESS, null));
        user.put(CONTACT, sharedPreferences.getString(CONTACT, null));
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        user.put(USERNAME, sharedPreferences.getString(USERNAME, null));
        user.put(PHOTO_PATH, sharedPreferences.getString(PHOTO_PATH, null));
        return user;
    }
    public void setfname(String fname){
        editor.putString(FNAME, fname);
        editor.commit();
    }
    public void setlname(String lname){
        editor.putString(LNAME, lname);
        editor.commit();
    }
    public void setcontact(String contact){
        editor.putString(CONTACT, contact);
        editor.commit();
    }
    public void setaddress(String address){
        editor.putString(ADDRESS, address);
        editor.commit();
    }
    public void setemail(String email){
        editor.putString(EMAIL, email);
        editor.commit();
    }
    public void setusername(String uname){
        editor.putString(USERNAME, uname);
        editor.commit();
    }
    public void setuserPhoto(String photo){
        editor.putString(PHOTO_PATH, photo);
        editor.commit();
    }



    @Override
    public String toString() {
        return  getUserDetail().get(ID) ;
    }

    public String getfname(){
        return getUserDetail().get(FNAME);
    }
    public String getlname(){
        return  getUserDetail().get(LNAME);
    }

    public String getaddress(){
        return  getUserDetail().get(ADDRESS);
    }
    public String getcontact(){
        return getUserDetail().get(CONTACT);
    }

    public String getemail(){
        return getUserDetail().get(EMAIL);
    }
    public String getuname(){
        return getUserDetail().get(USERNAME);
    }
    public String getPath(){ return getUserDetail().get(PHOTO_PATH);}

    public void checkLogin(){

        if (!this.isLoggin()){
            Intent i = new Intent(context, MainActivity.class);
            context.startActivity(i);
            ((UserNavigationActivity) context).finish();
        }
    }

    public void logout(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, MainActivity.class);
         context.startActivity(i);
        ((UserNavigationActivity) context).finish();

    }
}
