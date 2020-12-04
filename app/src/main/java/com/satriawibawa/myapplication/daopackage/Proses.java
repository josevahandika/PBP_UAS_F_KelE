package com.satriawibawa.myapplication.daopackage;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.satriawibawa.myapplication.BR;

@Entity
public class Proses extends BaseObservable{
    @ColumnInfo(name="inputBaju")
    private String inputBaju;
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @Bindable
    public String getInputBaju() {
        return inputBaju;
    }

    public void setInputBaju(String inputBaju) {
        this.inputBaju = inputBaju;
        notifyPropertyChanged(BR.inputBaju);
    }
    @Bindable
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }
}
