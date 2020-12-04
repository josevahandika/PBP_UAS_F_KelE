package com.satriawibawa.myapplication.daopackage;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface ProsesDAO {
    @Insert
    void Register(Proses proses);



}
