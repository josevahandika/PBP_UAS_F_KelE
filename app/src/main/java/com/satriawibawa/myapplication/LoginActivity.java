package com.satriawibawa.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.satriawibawa.myapplication.daopackage.AppDatabase;
import com.satriawibawa.myapplication.daopackage.User;
import com.satriawibawa.myapplication.databinding.ActivityLoginBinding;

import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private AppDatabase db;
    private SharedPref sharedPref;
    private FirebaseAuth fb;
    private String CHANNEL_ID = "Channel 1";

    private static final int LOGIN_SUKSES = 3;
    private static final int LOGIN_GAGAL = 2;
    private static final int KOSONG = 1;
    private static final String stateEmail = "stateEmail";
    private static final String stateUsername = "stateUsername";
    private static final String statePassword = "statePassword";
    private String email,password;
    private static final String TAG = "EmailPassword";

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        User user = binding.getUser();
        super.onSaveInstanceState(outState);
//        System.out.println("USERNAME: "+user.getUsername());
        outState.putString(stateUsername,user.getUsername());
        outState.putString(stateEmail,user.getEmail());
        outState.putString(statePassword,user.getPassword());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        //db = AppDatabase.getInstance(this);
        fb =FirebaseAuth.getInstance();
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doLogin();
            }
        });
        binding.btnRegister1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });
        binding.setUser(new User());
        if (savedInstanceState != null) {
            User restored = new User();
            String result = savedInstanceState.getString(stateUsername);
//            System.out.println("restored name : " + result);
            binding.inputLgusername.setText(result);
            String resultEmail = savedInstanceState.getString(stateEmail);
            binding.inputLgemail.setText(resultEmail);
            String resultPass = savedInstanceState.getString(statePassword);
            binding.inputLgpassword.setText(resultPass);
            restored.setUsername(result);
            restored.setPassword(resultPass);
            binding.setUser(restored);
        }
    }
    private void doLogin(){
        //new LoginTask().execute();
        signInWithFirebase();
    }
    private boolean isValid(User user) {
        if(user.getUsername() == null || user.getUsername().equals("")) return false;
        if(user.getEmail() == null || user.getEmail().equals("")) return false;
        if(user.getPassword() == null || user.getPassword().equals("")) return false;
        return true;
    }
//    private class LoginTask extends AsyncTask<URL, Integer, Integer>
//    {
//        User user = binding.getUser();
//        User longin;
//        @Override
//        protected Integer doInBackground(URL... urls) {
//            int flagMessage = -1;
//            if(isValid(user)) {
//                    longin = db.getUserDao().Login(user.getUsername(),user.getPassword());
//                   if(longin==null)
//                   {
//                       flagMessage = LOGIN_GAGAL;
//                   }
//                   else{
//                       flagMessage = LOGIN_SUKSES;
//                   }
//                }
//            else flagMessage = KOSONG;
//            return flagMessage;
//        }
//        @Override
//        protected void onPostExecute(Integer a) {
//            super.onPostExecute(a);
//            if(a == LOGIN_SUKSES) {
//                makeToast(getResources().getString(R.string.text_login_sukses));
//                startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                sharedPref = new SharedPref(getApplicationContext());
//                sharedPref.createSession(longin);
//                createNotificationChannel();
//                addNotification();
//                finish();
//            }
//            else if(a == LOGIN_GAGAL) {
//                makeToast(getResources().getString(R.string.text_login_error));
//            }
//            else if(a == KOSONG) {
//                makeToast(getResources().getString(R.string.text_field_empty));
//            }
//        }
//    }

    private void signInWithFirebase(){
        email = binding.getUser().getEmail();
        password = binding.getUser().getPassword();
        fb.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(LoginActivity.this, "Authentication Success", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = fb.getCurrentUser();
                            if (user.isEmailVerified())
                            {
                                //berhasil login
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                finish();
                            }
                            else{
                                //gagal login
                                Toast.makeText(LoginActivity.this, "Silahkan verifikasi email dulu",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            // ...
                        }

                        // ...
                    }
                });
    }

    private void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Channel 1";
            String description = "This is Channel 1";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    private void addNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.logo_laundry)
                .setContentTitle("Hello" )
                .setContentText("Welcome back! Please enjoy your stay..")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent notificationIntent = new Intent(this, LoginActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}

