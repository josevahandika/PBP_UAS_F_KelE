package com.satriawibawa.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.satriawibawa.myapplication.databinding.ActivitySplashAfterLoginBinding;
public class SplashActivityAfterLogin extends AppCompatActivity {

    private ActivitySplashAfterLoginBinding binding;
    private Handler handler = new Handler();
    private SharedPref sharedPref;
    private TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_splash_after_login);
        sharedPref = new SharedPref(this);
        username = findViewById(R.id.txtNama);
        username.setText(sharedPref.getUsernameS());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                  startActivity(new Intent(SplashActivityAfterLogin.this, MainActivity.class));
                  finish();
            }
        },3000);
    }
}