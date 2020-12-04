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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.satriawibawa.myapplication.API.LaundryAPI;
import com.satriawibawa.myapplication.Laundry;
import com.satriawibawa.myapplication.Model.LaundryModel;
import com.satriawibawa.myapplication.R;
import com.satriawibawa.myapplication.Views.TambahEditLaundry;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import static com.android.volley.Request.Method.DELETE;

public class AdapterLaundry extends RecyclerView.Adapter<AdapterLaundry.adapterLaundryViewHolder>{
    private List<LaundryModel> laundryList;
    private List<LaundryModel> laundryListFiltered;
    private Context context;
    private View view;
    private int tempId;
    private AdapterLaundry.deleteItemListener mListener;

    public AdapterLaundry(Context context, List<LaundryModel> laundryList,
                       AdapterLaundry.deleteItemListener mListener) {
        this.context            = context;
        this.laundryList        = laundryList;
        this.laundryListFiltered = laundryList;
        this.mListener          = mListener;
    }

    public interface deleteItemListener {
        void deleteItem( Boolean delete);
    }

    @NonNull
    @Override
    public AdapterLaundry.adapterLaundryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.activity_adapter_laundry, parent, false);
        return new AdapterLaundry.adapterLaundryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterLaundry.adapterLaundryViewHolder holder, int position) {
        final LaundryModel laundry = laundryListFiltered.get(position);

        NumberFormat formatter = new DecimalFormat("#,###");
        holder.txtNama.setText(laundry.getPaket());
        holder.txtHarga.setText(String.valueOf(laundry.getTotal_harga()));
        holder.txtBerat.setText(String.valueOf(laundry.getBerat()));
//        holder.txtPengarang.setText(laundry.getTotal_harga());
//        holder.txtPengarang.setText(laundry.getLama_pengerjaan());
//        holder.txtPengarang.setText(laundry.getStatus());
//        Glide.with(context)
//                .load(LaundryAPI.URL_IMAGE+buku.getGambar())
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .skipMemoryCache(true)
//                .into(holder.ivGambar);

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Bundle data = new Bundle();
                data.putSerializable("laundry", laundry);
                data.putString("status", "edit");
                TambahEditLaundry tambahEditLaundry = new TambahEditLaundry();
                tambahEditLaundry.setArguments(data);
                loadFragment(tambahEditLaundry);
            }
        });

        holder.ivHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Anda yakin ingin menghapus transaksi ?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tempId = laundry.getIdLaundry();
                        deleteLaundry();
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
        return (laundryListFiltered != null) ? laundryListFiltered.size() : 0;
    }

    public class adapterLaundryViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNama, txtHarga, txtBerat, ivEdit, ivHapus;;
        private ImageView ivGambar;
        private CardView cardLaundry;

        public adapterLaundryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNama         = itemView.findViewById(R.id.tvNamaPaket);
            txtHarga        = itemView.findViewById(R.id.tvHarga);
            txtBerat    = itemView.findViewById(R.id.tvBerat);
            //ivGambar        = itemView.findViewById(R.id.ivGambar);
            ivEdit          = (TextView) itemView.findViewById(R.id.ivEdit);
            ivHapus         = (TextView) itemView.findViewById(R.id.ivHapus);
            cardLaundry        = itemView.findViewById(R.id.cardTransaksi);
        }
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String userInput = charSequence.toString();
                if (userInput.isEmpty()) {
                    laundryListFiltered = laundryList;
                }
                else {
                    List<LaundryModel> filteredList = new ArrayList<>();
//                    for(LaundryModel laundry : laundryList) {
//                        if(String.valueOf(laundry.getPaket()).toLowerCase().contains(userInput) ||
//                                laundry.getTotal_harga(), laundry.getBerat().) {
//                            filteredList.add(laundry);
//                        }
//                    }
                    laundryListFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = laundryListFiltered;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                laundryListFiltered = (ArrayList<LaundryModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void loadFragment(Fragment fragment) {
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.recycler_view_transaksi,fragment)
                .commit();
    }

    public void deleteLaundry(){
        //Tambahkan hapus buku disini
        //Pendeklarasian queue
        RequestQueue queue = Volley.newRequestQueue(context);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Menghapus data laundry");
        progressDialog.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        //Memulai membuat permintaan request menghapus data ke jaringan
        StringRequest stringRequest = new StringRequest(DELETE, LaundryAPI.URL_DELETE + tempId, new Response.Listener<String>() {
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
                    mListener.deleteItem(true);
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
