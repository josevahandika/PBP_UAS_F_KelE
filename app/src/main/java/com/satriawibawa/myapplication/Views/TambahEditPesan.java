package com.satriawibawa.myapplication.Views;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.satriawibawa.myapplication.API.PesanAPI;
import com.satriawibawa.myapplication.Model.Pesan;
import com.satriawibawa.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;
import static com.android.volley.Request.Method.PUT;

public class TambahEditPesan extends Fragment {
    private TextInputEditText txtIsiPesan, txtJudul, txtPengirim;
    private ImageView ivGambar;
    private int id;
    private Button btnSimpan, btnBatal;
    private Pesan pesan;
    private View view;
    private Bitmap bitmap;
    private String status;
    private Uri selectedImage = null;
    private static final int PERMISSION_CODE =1000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tambah_edit_pesan, container, false);
        init();
        setAtribut();
        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if(menu.findItem(R.id.btnAdd)!=null)
            menu.findItem(R.id.btnAdd).setVisible(false);
        if(menu.findItem(R.id.btnSearch) != null)
            menu.findItem(R.id.btnSearch).setVisible(false);
    }

    public void init(){
        try {
            pesan = (Pesan) getArguments().getSerializable("pesan");
            status = getArguments().getString("status");
        } catch (Exception e){
            status = "tambah";
        }
        txtIsiPesan = view.findViewById(R.id.txtIsiPesan);
        txtJudul = view.findViewById(R.id.txtJudul);
        txtPengirim = view.findViewById(R.id.txtPengirim);
        btnSimpan = view.findViewById(R.id.btnSimpan);
        btnBatal = view.findViewById(R.id.btnBatal);
        //ivGambar = view.findViewById(R.id.ivGambar);

        //status = getArguments().getString("status");
        if(status.equals("edit")){
            id = pesan.getId();
            txtJudul.setText(pesan.getJudul());
            txtPengirim.setText(pesan.getPengirim());
            txtIsiPesan.setText(pesan.getIsi_pesan());
        }
    }

    private void setAtribut(){
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String isiPesan = txtIsiPesan.getText().toString();
                String pengirim = txtPengirim.getText().toString();
                String judul = txtJudul.getText().toString();

                if(isiPesan.isEmpty() || pengirim.isEmpty() || judul.isEmpty()){
                    Toast.makeText(getContext(), "Data Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }else{
                    pesan = new Pesan(isiPesan, judul, pengirim);

                    if(status.equals("tambah")){
                        tambahPesan();
                    }else{
                        editPesan();
                    }
                }
            }
        });

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new ViewsPesan());
            }
        });
    }

    private void loadFragment(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(Build.VERSION.SDK_INT >=26){
            fragmentTransaction.setReorderingAllowed(false);
        }
        fragmentTransaction.replace(R.id.frame_tambah_edit_pesan, fragment)
                .detach(this)
                .attach(this)
                .commit();
    }


    private void closeFragment(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.hide(TambahEditPesan.this).detach(this)
                .attach(this).commit();
    }

    private void tambahPesan(){
        RequestQueue queue = Volley.newRequestQueue(getContext());

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("loading...");
        progressDialog.setTitle("Menambahkan data Pesan");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(POST, PesanAPI.URL_ADD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject obj = new JSONObject(response);
//                    if (obj.getString("status").equals("Success")) {
                    loadFragment(new ViewsPesan());
//                    }
                    Toast.makeText(getContext(), "message", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("judul", pesan.getJudul());
                params.put("isi_pesan", pesan.getIsi_pesan());
                params.put("pengirim", pesan.getPengirim());
                return params;
            }
        };
        queue.add(stringRequest);

    }

    private void editPesan(){
        RequestQueue queue = Volley.newRequestQueue(getContext());

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("loading...");
        progressDialog.setTitle("Menambahkan data Pesan");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(PUT, PesanAPI.URL_UPDATE+id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    //Mengubah response string menjadi object
                    JSONObject obj = new JSONObject(response);

                    //obj.getString("message") digunakan untuk mengambil pesan message dari response
                    Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                    loadFragment(new ViewsPesan());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("judul", pesan.getJudul());
                params.put("isi_pesan", pesan.getIsi_pesan());
                params.put("pengirim", pesan.getPengirim());
                return params;
            }
        };
        queue.add(stringRequest);
    }
}
