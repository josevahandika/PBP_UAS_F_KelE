package com.satriawibawa.myapplication.Views;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.satriawibawa.myapplication.API.LaundryAPI;
import com.satriawibawa.myapplication.Adapter.AdapterLaundry;
import com.satriawibawa.myapplication.Model.LaundryModel;
import com.satriawibawa.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.Request.Method.GET;

public class ViewsTransaksi extends Fragment {
    private RecyclerView recyclerView;
    private AdapterLaundry adapter;
    private List<LaundryModel> listLaundry;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_views_transaksi, container, false);

        loadDaftarLaundry();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    public void loadDaftarLaundry(){
        setAdapter();
        getLaundry();
    }

    public void setAdapter(){
        getActivity().setTitle("Data Laund");
        /*Buat tampilan untuk adapter jika potrait menampilkan 2 data dalam 1 baris,
        sedangakan untuk landscape 4 data dalam 1 baris*/
        listLaundry = new ArrayList<LaundryModel>();
        recyclerView = view.findViewById(R.id.recycler_view_transaksi);
        adapter = new AdapterLaundry(view.getContext(), listLaundry, new AdapterLaundry.deleteItemListener() {
            @Override
            public void deleteItem(Boolean delete) {
                if(delete){
                    loadDaftarLaundry();
                }
            }
        });
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

//    public void loadFragment(Fragment fragment) {
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.frame_view_buku,fragment)
//                .addToBackStack(null)
//                .commit();
//    }

    public void getLaundry() {
        //Tambahkan tampil buku disini
        //Pendeklarasian queue
        RequestQueue queue = Volley.newRequestQueue(view.getContext());

        //Meminta tanggapan string dari URL yang telah disediakan menggunakan method GET
        //untuk request ini tidak memerlukan parameter
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Menampilkan data buku");
        progressDialog.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        final JsonObjectRequest stringRequest = new JsonObjectRequest(GET, LaundryAPI.URL_SELECT
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Disini bagian jika response jaringan berhasil tidak terdapat ganguan/error
                progressDialog.dismiss();
                try {
                    //Mengambil data response json object yang berupa data mahasiswa
                    JSONArray jsonArray = response.getJSONArray("data");

                    if(!listLaundry.isEmpty())
                        listLaundry.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        //Mengubah data jsonArray tertentu menjadi json Object
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                        String idLaundry        = jsonObject.optString("id");
                        String email            = jsonObject.optString("email");
                        String namaPaket         = jsonObject.optString("paket");
                        String harga       = jsonObject.optString("harga");
                        String berat            = jsonObject.optString("berat");
                        String total_harga       = jsonObject.optString("total_harga");
                        String lama_pengerjaan            = jsonObject.optString("lama_pengerjaan");
                        String status       = jsonObject.optString("status");

                        System.out.println("nama apaket " + namaPaket);
                        //Membuat objek user
                        LaundryModel laundryModel = new LaundryModel(Integer.parseInt(idLaundry), email, namaPaket, Double.parseDouble(harga), Double.parseDouble(berat), Double.parseDouble(total_harga), Integer.parseInt(lama_pengerjaan), status);

                        //Menambahkan objek user tadi ke list user
                        listLaundry.add(laundryModel);
                    }
                    adapter.notifyDataSetChanged();
                }catch (JSONException e){
                    e.printStackTrace();
                }
                Toast.makeText(view.getContext(), response.optString("message"),
                        Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Disini bagian jika response jaringan terdapat ganguan/error
                progressDialog.dismiss();
                Toast.makeText(view.getContext(), error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        //Disini proses penambahan request yang sudah kita buat ke reuest queue yang sudah dideklarasi
        queue.add(stringRequest);
    }
    public void onConfigurationChanged(Configuration _newConfig) {

        if (_newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));
        }

        else if (_newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        }

        super.onConfigurationChanged(_newConfig);
    }
}
