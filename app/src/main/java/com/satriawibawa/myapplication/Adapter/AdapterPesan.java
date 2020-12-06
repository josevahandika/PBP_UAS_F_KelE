package com.satriawibawa.myapplication.Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.satriawibawa.myapplication.API.PesanAPI;
import com.satriawibawa.myapplication.Model.Pesan;
import com.satriawibawa.myapplication.R;
import com.satriawibawa.myapplication.Views.TambahEditPesan;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.Request.Method.DELETE;

public class AdapterPesan extends RecyclerView.Adapter<AdapterPesan.adapterPesanViewHolder> {

    private List<Pesan> pesanList;
    private List<Pesan> pesanFilterList;
    private Context context;
    View view;
    private  AdapterPesan.deleteItemListener deleteItemListener;

//    public AdapterPesan(List<Pesan> pesanList, List<Pesan> pesanFilterList, Context context, AdapterPesan.deleteItemListener deleteItemListener) {
//        this.pesanList = pesanList;
//        this.pesanFilterList = pesanFilterList;
//        this.context = context;
//        this.deleteItemListener = deleteItemListener;
//    }

    public AdapterPesan(List<Pesan> pesanList, Context context, AdapterPesan.deleteItemListener deleteItemListener) {
        this.pesanList = pesanList;
        this.context = context;
        this.deleteItemListener = deleteItemListener;
    }

    public void setPesanList(List<Pesan> pesanList) {
        this.pesanList = pesanList;
    }

    public interface deleteItemListener {
        void deleteItem( Boolean delete);
    }

    @NonNull
    @Override
    public AdapterPesan.adapterPesanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.activity_adapter_pesan, parent, false);
        return new adapterPesanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterPesanViewHolder holder, int position) {
        final Pesan pesan = pesanList.get(position);

        holder.txtPengirim.setText(pesan.getPengirim());
        holder.txtJudul.setText(pesan.getJudul());
        holder.txtIsiPesan.setText(pesan.getIsi_pesan());

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Bundle data = new Bundle();
                data.putSerializable("pesan", pesan);
                data.putString("status","edit");
                TambahEditPesan tambahEditPesan = new TambahEditPesan();
                tambahEditPesan.setArguments(data);
                loadFragment(tambahEditPesan);
            }
        });

        holder.ivHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Anda yakin ingin menghapus pesan ?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deletePesan(pesan.getId());
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return pesanList.size();
    }

    public class adapterPesanViewHolder extends RecyclerView.ViewHolder{
        private TextView txtIsiPesan, txtJudul, txtPengirim, ivEdit, ivHapus;
        private CardView cardPesan;

        public adapterPesanViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIsiPesan = itemView.findViewById(R.id.txtIsiPesanAdapter);
            txtJudul = itemView.findViewById(R.id.txtJudul);
            txtPengirim = itemView.findViewById(R.id.txtPengirim);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            ivHapus = itemView.findViewById(R.id.ivHapus);
            cardPesan = itemView.findViewById(R.id.cardPesan);
        }
    }

    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String userInput = charSequence.toString();
                if(userInput.isEmpty()){
                    pesanFilterList = pesanList;
                }else{
                    List<Pesan> filteredList = new ArrayList<>();
                    for (Pesan pesan : pesanList){
                        if(String.valueOf(pesan.getJudul()).toLowerCase().contains(userInput) ||
                                pesan.getPengirim().toLowerCase().contains(userInput)){
                            filteredList.add(pesan);
                        }
                    }
                    pesanFilterList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = pesanFilterList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                pesanFilterList = (ArrayList<Pesan>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void loadFragment(Fragment fragment){
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_view_buku,fragment)
                .commit();
    }

    public void deletePesan(int id){
        RequestQueue queue = Volley.newRequestQueue(context);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Menghapus data buku");
        progressDialog.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        //Memulai membuat permintaan request menghapus data ke jaringan
        StringRequest stringRequest = new StringRequest(DELETE, PesanAPI.URL_DELETE + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Disini bagian jika response jaringan berhasil tidak terdapat ganguan/error
                progressDialog.dismiss();
                try {
                    //Mengubah response string menjadi object
                    JSONObject obj = new JSONObject(response);
                    //obj.getString("message") digunakan untuk mengambil pesan message dari response
                    Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                    deleteItemListener.deleteItem(true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Disini bagian jika response jaringan terdapat ganguan/error
                progressDialog.dismiss();
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //Disini proses penambahan request yang sudah kita buat ke reuest queue yang sudah dideklarasi
        queue.add(stringRequest);
    }
}