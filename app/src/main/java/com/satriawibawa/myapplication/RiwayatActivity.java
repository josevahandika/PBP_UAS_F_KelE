package com.satriawibawa.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.satriawibawa.myapplication.databinding.ActivityRiwayatBinding;
//import com.satriawibawa.myapplication.databinding.ActivityTampilanBinding;

import java.util.ArrayList;

public class RiwayatActivity extends AppCompatActivity {
    private ArrayList<Riwayat> ListRiwayat;
    private RecyclerView recyclerView;
    private AdapterRiwayat adapterRiwayat;
    private RecyclerView.LayoutManager mLayoutManager;
    ActivityRiwayatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_riwayat);

        //get data laundry
        ListRiwayat = new DaftarRiwayat().Riwayat;

        //recycler view
        //recyclerView = findViewById(R.id.recycler_view_mahasiswa);
        adapterRiwayat = new AdapterRiwayat(this, ListRiwayat);
        //mLayoutManager = new LinearLayoutManager(getApplicationContext());
        // recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.setSetAdapterRiwayat(adapterRiwayat);
    }
}