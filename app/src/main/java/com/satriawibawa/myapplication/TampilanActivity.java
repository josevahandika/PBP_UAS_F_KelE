package com.satriawibawa.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.satriawibawa.myapplication.databinding.ActivityTampilanBinding;

import java.util.ArrayList;

public class TampilanActivity extends AppCompatActivity {
    private ArrayList<Laundry> ListLaundry;
    private RecyclerView recyclerView;
    private AdapterTampilan adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ActivityTampilanBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tampilan);

        //get data laundry
        ListLaundry = new DaftarLaundry().Laundry;

        //recycler view
        //recyclerView = findViewById(R.id.recycler_view_mahasiswa);
        adapter = new AdapterTampilan(this, ListLaundry);
        //mLayoutManager = new LinearLayoutManager(getApplicationContext());
        // recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.setSetAdapter(adapter);
    }
}