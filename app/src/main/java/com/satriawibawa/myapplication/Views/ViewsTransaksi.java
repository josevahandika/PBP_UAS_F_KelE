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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    private FloatingActionButton flyingAdd;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_views_transaksi, container, false);
        flyingAdd = view.findViewById(R.id.addBtn);

        flyingAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new TambahEditLaundry());
            }
        });
        loadDaftarLaundry();
        return view;
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//    }


    public void loadDaftarLaundry(){
        setAdapter();
        getLaundry();
    }

//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.menu_bar_transaksi, menu);
//
//        MenuItem searchItem = menu.findItem(R.id.btnSearch);
//
//        SearchView searchView = (SearchView) searchItem.getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//            @Override
//            public boolean onQueryTextChange(String s) {
//                adapter.getFilter().filter(s);
//                return false;
//            }
//        });
//    }
//
//    @Override
//    public void onPrepareOptionsMenu(Menu menu) {
//        super.onPrepareOptionsMenu(menu);
//        menu.findItem(R.id.btnSearch).setVisible(true);
//        menu.findItem(R.id.btnAdd).setVisible(true);
//    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.btnAdd) {
//            Bundle data = new Bundle();
//            data.putString("status", "tambah");
//            TambahEditLaundry tambahEditLaundry = new TambahEditLaundry();
//            tambahEditLaundry.setArguments(data);
//
//            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//            fragmentManager .beginTransaction()
//                    .replace(R.id.frame_view_transaksi, tambahEditLaundry)
//                    .commit();
//        }
//        return super.onOptionsItemSelected(item);
//    }


    public void setAdapter(){
        getActivity().setTitle("Daftar Laundry");
        /*Buat tampilan untuk adapter jika potrait menampilkan 2 data dalam 1 baris,
        sedangakan untuk landscape 4 data dalam 1 baris*/
        listLaundry = new ArrayList<LaundryModel>();
        recyclerView = view.findViewById(R.id.recycler_view_transaksi);
        adapter = new AdapterLaundry(listLaundry, getContext(), new AdapterLaundry.deleteItemListener() {
            @Override
            public void deleteItem(Boolean delete) {
                if(delete){
                    getLaundry();
                }
            }
        });
//        if(getContext().getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT){
//            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
//        }else{
//            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
//        }
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }


    public void getLaundry() {
        //Tambahkan tampil buku disini
        RequestQueue queue = Volley.newRequestQueue(view.getContext());

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Menampilkan data Laundry");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        final JsonObjectRequest stringRequest = new JsonObjectRequest(GET, LaundryAPI.URL_SELECT,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                //testing
                Toast.makeText(view.getContext(), "tes", Toast.LENGTH_SHORT).show();
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    if(!listLaundry.isEmpty())
                        listLaundry.clear();

                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                        int id   = jsonObject.optInt("id");
                        String paket   = jsonObject.optString("paket");
                        double harga   = jsonObject.optDouble("harga");
                        int berat   = jsonObject.optInt("berat");
                        double total_harga = jsonObject.optDouble("total_harga");
                        int lama_pengerjaan = jsonObject.optInt("lama_pengerjaan");
                        String status = jsonObject.optString("status");
                        String email = jsonObject.optString("email");
                        LaundryModel laundry = new LaundryModel(paket, status,id,lama_pengerjaan,harga,total_harga, berat,email);

                        listLaundry.add(laundry);
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
                progressDialog.dismiss();
                Toast.makeText(view.getContext(), "error", Toast.LENGTH_SHORT).show();
                Toast.makeText(view.getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }
    public void loadFragment(Fragment fragment) {
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction
                .replace(R.id.frame_view_transaksi,fragment)
                //.addToBackStack(null)
                .commit();
    }


}
