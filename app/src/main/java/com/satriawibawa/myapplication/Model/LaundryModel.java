package com.satriawibawa.myapplication.Model;

import java.io.Serializable;

public class LaundryModel implements Serializable {
    private String paket, status,email;
    private int idLaundry,lama_pengerjaan;
    private Double harga,  total_harga;
    private int berat;

    public LaundryModel (int idLaundry, String email, String paket, Double harga, int berat, Double total_harga, int lama_pengerjaan, String status ){
        this.idLaundry = idLaundry;
        this.email = email;
        this.paket = paket;
        this.harga = harga;
        this.berat = berat;
        this.total_harga = total_harga;
        this.lama_pengerjaan = lama_pengerjaan;
        this.status = status;
    }

    public LaundryModel(String paket, String status, int lama_pengerjaan, Double harga, Double total_harga, int berat) {
        this.paket = paket;
        this.status = status;
        this.lama_pengerjaan = lama_pengerjaan;
        this.harga = harga;
        this.total_harga = total_harga;
        this.berat = berat;
    }

    public LaundryModel(String paket, String status, int idLaundry, int lama_pengerjaan, Double harga, Double total_harga, int berat, String email) {
        this.paket = paket;
        this.status = status;
        this.idLaundry = idLaundry;
        this.lama_pengerjaan = lama_pengerjaan;
        this.harga = harga;
        this.total_harga = total_harga;
        this.berat = berat;
        this.email = email;
    }

    public LaundryModel(String paket, Double total_harga, int berat){
        this.paket = paket;
        this.total_harga = total_harga;
        this.berat = berat;
    }

    public LaundryModel(int idLaundry, String paket, Double total_harga, int berat){
        this.idLaundry = idLaundry;
        this.paket = paket;
        this.total_harga = total_harga;
        this.berat = berat;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPaket() {
        return paket;
    }

    public void setPaket(String paket) {
        this.paket = paket;
    }

    public int getLama_pengerjaan() {
        return lama_pengerjaan;
    }

    public void setLama_pengerjaan(int lama_pengerjaan) {
        this.lama_pengerjaan = lama_pengerjaan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIdLaundry() {
        return idLaundry;
    }

    public void setIdLaundry(int idLaundry) {
        this.idLaundry = idLaundry;
    }

    public Double getHarga() {
        return harga;
    }

    public void setHarga(Double harga) {
        this.harga = harga;
    }

    public int getBerat() {
        return berat;
    }

    public void setBerat(int berat) {
        this.berat = berat;
    }

    public Double getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(Double total_harga) {
        this.total_harga = total_harga;
    }
}
