package com.satriawibawa.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.satriawibawa.myapplication.daopackage.User;

import java.io.StreamTokenizer;

public class SharedPref {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private static final String PREFERENCE_NAME = "JSMPref";
    private static final String LOGIN = "Login";
    private static final String USERNAME = "Username";
    private static final String DATA_PICTURE = "Data Picture";

    public SharedPref(Context context){
        preferences = context.getSharedPreferences(PREFERENCE_NAME, Activity.MODE_PRIVATE);
        editor = preferences.edit();
    }
    public void createSession(User user){
        editor.putBoolean(LOGIN, true);
        editor.putString(USERNAME, user.getUsername());
//        editor.putString(DATA_PICTURE, user.getDataPicture());
        editor.commit();
    }

    public void setDataPicture(String dataPicture){
        editor.putString(DATA_PICTURE, dataPicture); //ngeset foto user pas diganti (update)
        editor.commit();
    }
    public void closeSession(){
        if(this.loginS()){
        editor.clear();
        editor.commit();}
    }
    public String getUsernameS(){
        return preferences.getString(USERNAME,"");
    }
    public String getDataPictureS(){
        return preferences.getString(DATA_PICTURE,"");
    }
    public boolean loginS(){
        return preferences.getBoolean(LOGIN, false);
    }
}
