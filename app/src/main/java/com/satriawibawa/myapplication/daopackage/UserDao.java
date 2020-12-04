package com.satriawibawa.myapplication.daopackage;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void Register(User user);

    @Query("SELECT * FROM USER WHERE USERNAME = :username AND PASSWORD = :password")
    User Login(String username, String password);

    @Query("SELECT * FROM USER WHERE USERNAME = :username")
    User Login(String username);


    @Query("SELECT * FROM USER WHERE USERNAME = :username")
    List<User> UserException(String username);

    @Update
    void Update(User user);


}
