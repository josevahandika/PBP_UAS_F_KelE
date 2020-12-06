package com.satriawibawa.myapplication.Model;

import java.io.Serializable;

public class Pesan implements Serializable {
    private int id;
    private String isi_pesan, judul, email, pengirim;

    public Pesan(int id, String isi_pesan, String judul, String pengirim) {
        this.id = id;
        this.isi_pesan = isi_pesan;
        this.judul = judul;
        this.pengirim = pengirim;
    }

    public Pesan(int id, String isi_pesan, String judul, String email, String pengirim) {
        this.id = id;
        this.isi_pesan = isi_pesan;
        this.judul = judul;
        this.email = email;
        this.pengirim = pengirim;
    }

    public Pesan(String isi_pesan, String judul, String pengirim) {
        this.isi_pesan = isi_pesan;
        this.judul = judul;
        this.pengirim = pengirim;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsi_pesan() {
        return isi_pesan;
    }

    public void setIsi_pesan(String isi_pesan) {
        this.isi_pesan = isi_pesan;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPengirim() {
        return pengirim;
    }

    public void setPengirim(String pengirim) {
        this.pengirim = pengirim;
    }

}
