    package com.satriawibawa.myapplication.Views;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.satriawibawa.myapplication.API.LaundryAPI;
import com.satriawibawa.myapplication.Model.LaundryModel;
import com.satriawibawa.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static com.android.volley.Request.Method.POST;
import static com.android.volley.Request.Method.PUT;

public class TambahEditLaundry extends Fragment {
    private TextInputEditText txtNamaPaket, txtBerat, txtHarga,txtLamaPengerjaan, txtStatus;
    private ImageView ivGambar;
    private Button btnSimpan, btnBatal, btnUnggah;
    private String status, selectedPaket, selectedSP, email;
    private int idLaundry;
    private LaundryModel laundryModel;
    private View view;
    private Bitmap bitmap;
    private Uri selectedImage = null;
    private static final int PERMISSION_CODE = 1000;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tambah_edit_laundry, container, false);
        setAttribut(view);


        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtHarga.getText().length()<1 || txtLamaPengerjaan.getText().length()<1||
                        txtBerat.getText().length()<1)
                {
                    if(txtLamaPengerjaan.getText().length()<1)
                        txtLamaPengerjaan.setError("Data Tidak Boleh Kosong");
                    if(txtHarga.getText().length() <1)
                        txtHarga.setError("Data Tidak Boleh Kosong");
                    if(txtBerat.getText().length()<1)
                        txtBerat.setError("Data Tidak Boleh Kosong");
                }
                else
                {
                    String harga      = txtHarga.getText().toString();
                    String berat     = txtBerat.getText().toString();
                    String lamaPengerjaan = txtLamaPengerjaan.getText().toString();

                    if(status.equals("tambah"))
                        tambahLaundry(harga, berat, lamaPengerjaan, selectedPaket, selectedSP);
                    else
                        editLaundry(idLaundry, harga, berat, lamaPengerjaan, selectedPaket, selectedSP);
                }
            }
        });

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new ViewsTransaksi());
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if(menu.findItem(R.id.btnSearch) != null)
            menu.findItem(R.id.btnSearch).setVisible(false);
        if(menu.findItem(R.id.btnAdd) != null)
            menu.findItem(R.id.btnAdd).setVisible(false);
    }


    private void setAttribut(View view) {
        try {
            laundryModel = (LaundryModel) getArguments().getSerializable("laundry");
            status = getArguments().getString("status");
        } catch (Exception e){
            status = "tambah";
        }
        txtHarga = view.findViewById(R.id.txtHarga);
        txtBerat = view.findViewById(R.id.txtBerat);
        txtLamaPengerjaan = view.findViewById(R.id.txtLamaPengerjaan);
        btnSimpan = view.findViewById(R.id.btnSimpan);
        btnBatal = view.findViewById(R.id.btnBatal);
        final String[] PaketArray = getResources().getStringArray(R.array.paket);
        final String[] SPArray = getResources().getStringArray(R.array.statusPembayaran);

        if(status.equals("tambah"))
        {
            selectedPaket = PaketArray[0];
            selectedSP = SPArray[0];
        }
        else
        {
            email = laundryModel.getEmail();
            System.out.println("email" + email);
            idLaundry = laundryModel.getIdLaundry();
            txtBerat.setText(String.valueOf(laundryModel.getBerat()));
            txtLamaPengerjaan.setText(String.valueOf(laundryModel.getLama_pengerjaan()));
            txtHarga.setText(String.format("%.0f",laundryModel.getHarga()));
            for(String pk : PaketArray)
            {
                if(pk.equals(laundryModel.getPaket()))
                    selectedPaket = pk;
            }
            for(String hutang : SPArray)
            {
                if(hutang.equals(laundryModel.getStatus()))
                    selectedSP = hutang;
            }
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.paket, android.R.layout.simple_spinner_item);
        final AutoCompleteTextView paketDropdown = view.findViewById(R.id.edPaket);
        paketDropdown.setText(selectedPaket);
        paketDropdown.setAdapter(adapter);
        paketDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedPaket = paketDropdown.getEditableText().toString();
            }
        });

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(),
                R.array.statusPembayaran, android.R.layout.simple_spinner_item);
        final AutoCompleteTextView SPDropdown = view.findViewById(R.id.edStatus);
        SPDropdown.setText(selectedSP);
        SPDropdown.setAdapter(adapter2);
        SPDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedSP = SPDropdown.getEditableText().toString();
            }
        });
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            fragmentTransaction.setReorderingAllowed(false);
        }
        fragmentTransaction.replace(R.id.frame_tambah_edit_buku, fragment)
                .detach(this)
                .attach(this)
                .commit();
    }

    public void tambahLaundry(final String berat, final String harga, final String lamaPengerjaan,  final String selectedPaket, final String selectedSP){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        final double totalHarga = Double.parseDouble(berat)*Double.parseDouble(harga);
//        RequestQueue queue = Volley.newRequestQueue(getContext());

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("loading...");
        progressDialog.setTitle("Menambahkan data laundry");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(POST, LaundryAPI.URL_ADD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "masukrespn", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject obj = new JSONObject(response);
//                    if (obj.getString("status").equals("Success")) {
                    loadFragment(new ViewsTransaksi());
//                    }
                    Toast.makeText(getContext(), "message", Toast.LENGTH_SHORT).show();
                    //getActivity().finish();
//                    getFragmentManager().popBackStack();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "errrespn", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "errOnErro", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                // Disiniproses mengirimkan parameter key dengan data value dan nama key
                //harus sesuai dengan paramater key yang diminta oleh jaringan key
                Map<String, String> params = new HashMap<String, String>();
                params.put("paket",selectedPaket);
                params.put("harga",harga);
                params.put("berat",berat);
                params.put("total_harga",String.valueOf(totalHarga));
                params.put("lama_pengerjaan",lamaPengerjaan);
                params.put("status",selectedSP);
                email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                params.put("email",email);
                return params;
            }
        };
        // disini proses penambahan request yang sudah kita buat request queue yang sudah dideklarasi
        queue.add(stringRequest);
    }

    public void editLaundry(final int idLaundry, final String berat, final String harga, final String lamaPengerjaan,  final String selectedPaket, final String selectedSP){
        RequestQueue queue = Volley.newRequestQueue(getContext());

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Mengubah data laundry");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        final double totalHarga = Double.parseDouble(berat)*Double.parseDouble(harga);


        //memulai membuat permintaan request menghapus data ke jaringan
        StringRequest stringRequest = new StringRequest(PUT, LaundryAPI.URL_UPDATE + String.valueOf(idLaundry),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Disini bagian jika response jaringan berhasil tidak error
                        progressDialog.dismiss();
                        try {
                            //Mengubah response String menjadi object
                            JSONObject obj = new JSONObject(response);

                            //obj get message digunakan untuk mengambil pesan dari response
                            Toast.makeText(getContext(),obj.getString("message"), Toast.LENGTH_SHORT).show();
                            loadFragment(new ViewsTransaksi());
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //disini bagian jika response jaringan terdapat gangguan/error
                progressDialog.dismiss();
                Toast.makeText(getContext(), "salah", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                // Disiniproses mengirimkan parameter key dengan data value dan nama key
                //harus sesuai dengan paramater key yang diminta oleh jaringan key
                Map<String, String> params = new HashMap<String, String>();
                params.put("email",email);
               // params.put("id",String.valueOf(idLaundry));
                params.put("paket",selectedPaket);
                params.put("harga",harga);
                params.put("berat",berat);
                params.put("total_harga",String.valueOf(totalHarga));
                params.put("lama_pengerjaan",lamaPengerjaan);
                params.put("status",selectedSP);
                return params;
            }
        };
        queue.add(stringRequest);
    }
}