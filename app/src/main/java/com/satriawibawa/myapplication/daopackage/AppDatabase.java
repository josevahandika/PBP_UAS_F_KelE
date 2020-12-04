package com.satriawibawa.myapplication.daopackage;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class,Proses.class},version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao getUserDao();
    public abstract ProsesDAO getProsesDAO();
    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context){
        if(instance==null)
        {
            instance = Room.databaseBuilder(context,AppDatabase.class,"test.db").build();
        }
        return instance;
    }
}
