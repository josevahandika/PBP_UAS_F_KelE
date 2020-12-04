package com.satriawibawa.myapplication.Model;

import java.io.Serializable;

public class PesanModel implements Serializable {
    private String judul, isi_pesan, pengirim;

    public PesanModel(String judul, String isi_pesan, String pengirim){
        this.judul = judul;
        this.isi_pesan = isi_pesan;
        this.pengirim = pengirim;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getIsi_pesan() {
        return isi_pesan;
    }

    public void setIsi_pesan(String isi_pesan) {
        this.isi_pesan = isi_pesan;
    }

    public String getPengirim() {
        return pengirim;
    }

    public void setPengirim(String pengirim) {
        this.pengirim = pengirim;
    }
}
