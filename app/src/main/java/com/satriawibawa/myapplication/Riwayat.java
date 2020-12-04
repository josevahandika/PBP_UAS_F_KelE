package com.satriawibawa.myapplication;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class Riwayat {
    public String jumlah;
    public String paket;

    public Riwayat(String  jumlah, String paket) {
        this.jumlah = jumlah;
        this.paket = paket;
    }


    public String  getJumlah() {
        return jumlah;
    }

    public void setJumlah(String  jumlah) {
        this.jumlah = jumlah;
    }

    public String getPaket() {
        return paket;
    }

    public void setPaket(String paket) {
        this.paket = paket;
    }
//    @BindingAdapter("profileImage")
//    public static void loadImage(ImageView view, String imageUrl) {
//        Glide.with(view.getContext())
//                .load(imageUrl)
//                .into(view);
//    }
}