package com.satriawibawa.myapplication;

import java.util.ArrayList;

public class DaftarRiwayat {
    public ArrayList<Riwayat> Riwayat;

    public DaftarRiwayat(){
        Riwayat = new ArrayList();
        Riwayat.add(R1);
        Riwayat.add(R2);
        Riwayat.add(R3);
        Riwayat.add(R4);
    }

    public static final Riwayat R1 = new Riwayat("2", "Reguler");

    public static final Riwayat R2 = new Riwayat("3", "Quick");

    public static final Riwayat R3 = new Riwayat("1", "Reguler");

    public static final Riwayat R4 = new Riwayat("6", "Quick");

}
