package com.satriawibawa.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.satriawibawa.myapplication.databinding.ActivitySplashBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SplashActivity#} factory method to
 * create an instance of this fragment.
 */
public class SplashActivity extends AppCompatActivity {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private ActivitySplashBinding binding;
    private Handler handler = new Handler();
    private SharedPref sharedPref;
//    private FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        super.onCreate(savedInstanceState);
//        fauth = FirebaseAuth.getInstance();
        FirebaseMessaging.getInstance().subscribeToTopic("news");//<-------------------------------------
        binding = DataBindingUtil.setContentView(this,R.layout.activity_splash);
        sharedPref = new SharedPref(this);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sharedPref.loginS()){

                    System.out.println("\n\n\nLOGIN SHARED PREF\n\n\n");
                    startActivity(new Intent(SplashActivity.this,SplashActivityAfterLogin.class));
                }
                else{
                    System.out.println("\n\n\nLOGIN gagal  PREF\n\n\n");
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }

                finish();
            }
        },3000);
    }
}