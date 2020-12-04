package com.satriawibawa.myapplication.daopackage;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.satriawibawa.myapplication.BR;

@Entity
public class User extends BaseObservable {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    @ColumnInfo(name="username")
    private String username;
    @ColumnInfo(name="password")
    private String password;
    @ColumnInfo(name="email")
    private String email;
//    @ColumnInfo(name="dataPicture")
//    private String dataPicture;

    @Bindable
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        notifyPropertyChanged(BR.username);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }
    @Bindable
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }
//    @Bindable
//
//    public String getDataPicture() {
//        return dataPicture;
//    }
//
//    public void setDataPicture(String dataPicture) {
//        this.dataPicture = dataPicture;
//    }
}
