package com.satriawibawa.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import com.satriawibawa.myapplication.databinding.ActivityAdapterRiwayatBinding;
public class AdapterRiwayat extends RecyclerView.Adapter<AdapterRiwayat.MyViewHolder> {
    private Context context;
    private List<Riwayat> result;

    public AdapterRiwayat(){}

    public AdapterRiwayat(Context context, List<Riwayat> result){
        this.context = context;
        this.result = result;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ActivityAdapterRiwayatBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.activity_adapter_riwayat,parent,false);
        //View v = LayoutInflater.from(context).inflate(R.layout.adapter_recycler_view, parent, false);
        //final MyViewHolder holder = new MyViewHolder(v);

        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final Riwayat rwy = result.get(position);
        holder.bind(rwy);
        //holder.itemRowBinding.setItemClickListener(this);
//        Glide.with(context).load(mhs.imgURL).into(holder.foto_profil);
//        if (!mhs.getImgURL().equals("")){
//            Glide.with(context)
//                    .load(mhs.getImgURL())
//                    .into(holder.foto_profil);
//        }else{
//            holder.foto_profil.setImageResource(R.drawable.ic_broken_image);
//        }
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView cabang, alamat;
        private CardView parent;
        public ActivityAdapterRiwayatBinding itemRowBinding;
//        public MyViewHolder(@NonNull View itemView){
//            super(itemView);
//            this.itemRowBinding = itemRowBinding;
//        }//kons basic

        public MyViewHolder(ActivityAdapterRiwayatBinding binding) {
            super(binding.getRoot());
            this.itemRowBinding=binding;
        }//kons untuk binding

        public void bind(Object obj)
        {
            itemRowBinding.setVariable(BR.riwayat,obj);
            itemRowBinding.executePendingBindings();
            parent=itemView.findViewById(R.id.ParentAdapter);
        }
        public void onClick(View view) {
            Toast.makeText(context, "You touch me?", Toast.LENGTH_SHORT).show();
        }
    }
}