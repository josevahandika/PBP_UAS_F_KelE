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

import com.satriawibawa.myapplication.databinding.ActivityTampilanAdapterBinding;
public class AdapterTampilan extends RecyclerView.Adapter<AdapterTampilan.MyViewHolder> {
    private Context context;
    private List<Laundry> result;

    public AdapterTampilan(){}

    public AdapterTampilan(Context context, List<Laundry> result){
        this.context = context;
        this.result = result;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ActivityTampilanAdapterBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.activity_tampilan_adapter,parent,false);
        //View v = LayoutInflater.from(context).inflate(R.layout.adapter_recycler_view, parent, false);
        //final MyViewHolder holder = new MyViewHolder(v);

        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final Laundry ldry = result.get(position);
        holder.bind(ldry);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(view.getContext(), MapActivity.class);
                        i.putExtra(MapActivity.KEY_LONGITUDE,ldry.getLongitude());
                        i.putExtra(MapActivity.KEY_LATITUDE,ldry.getLatitude());
                        i.putExtra(MapActivity.KEY_LAUNDRY,ldry.getCabang());
                        view.getContext().startActivity(i);
            }
        });
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
        public ActivityTampilanAdapterBinding itemRowBinding;
//        public MyViewHolder(@NonNull View itemView){
//            super(itemView);
//            this.itemRowBinding = itemRowBinding;
//        }//kons basic

        public MyViewHolder(ActivityTampilanAdapterBinding binding) {
            super(binding.getRoot());
            this.itemRowBinding=binding;
        }//kons untuk binding

        public void bind(Object obj)
        {
            itemRowBinding.setVariable(BR.laundry,obj);
            itemRowBinding.executePendingBindings();
            parent=itemView.findViewById(R.id.ParentAdapter);
        }
        public void onClick(View view) {
            Toast.makeText(context, "You touch me?", Toast.LENGTH_SHORT).show();
        }
    }
}