package com.satriawibawa.myapplication;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class Laundry {
    public String cabang;
    public String alamat;
    public double latitude;
    public double longitude;

    public Laundry(String cabang, String alamat, double latitude, double longitude) {
        this.cabang = cabang;
        this.alamat = alamat;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCabang() {
        return cabang;
    }

    public void setCabang(String cabang) {
        this.cabang = cabang;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
//    @BindingAdapter("profileImage")
//    public static void loadImage(ImageView view, String imageUrl) {
//        Glide.with(view.getContext())
//                .load(imageUrl)
//                .into(view);
//    }
}

