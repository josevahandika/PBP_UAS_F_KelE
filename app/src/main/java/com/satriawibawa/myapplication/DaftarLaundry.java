package com.satriawibawa.myapplication;

import java.util.ArrayList;

public class DaftarLaundry {
    public ArrayList<Laundry> Laundry;

    public DaftarLaundry(){
        Laundry = new ArrayList();
        Laundry.add(SOLOBARU);
        Laundry.add(BABARSARI);
        Laundry.add(KLEDOKAN);
        Laundry.add(UPN);
        Laundry.add(UGM);
    }

    public static final Laundry SOLOBARU = new Laundry("Solo Baru", "Jl. Ir. Soekarno No. 1", -7.592042,110.816772);

    public static final Laundry BABARSARI = new Laundry("Babarsari", "Jl. Babarsari No. 44", -7.779186,110.416097);

    public static final Laundry KLEDOKAN = new Laundry("Kledokan", "Jl. Kledokan II No.C25", -7.777653, 110.409588);

    public static final Laundry UPN = new Laundry("UPN", "Jl. Ring Road Utara No.104", -7.762190, 110.409247);

    public static final Laundry UGM = new Laundry("UGM", "Bulaksumur, Caturtunggal", -7.770913, 110.377496);

}
