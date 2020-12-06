package com.satriawibawa.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.satriawibawa.myapplication.daopackage.AppDatabase;
import com.satriawibawa.myapplication.daopackage.User;
import com.satriawibawa.myapplication.databinding.ActivityRegisterBinding;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
//    private AppDatabase db;
    private FirebaseAuth fb;
    private String CHANNEL_ID = "Channel 1";
    private static final int USERNAME_EXIST = 4;
    private static final int REGISTER_SUKSES = 3;
    private static final int REGISTER_GAGAL = 2;
    private static final int KOSONG = 1;
    int success = 0;
    private static final String stateUsername = "stateUsername";
    private static final String statePassword = "statePassword";
    private static final String stateEmail = "stateEmail";
    private String email,password,username;
    private static final String TAG = "EmailPassword";

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        User user = binding.getUser();
        super.onSaveInstanceState(outState);
//        System.out.println("USERNAME: "+user.getUsername());
        outState.putString(stateUsername,user.getUsername());
        outState.putString(statePassword,user.getPassword());
        outState.putString(stateEmail,user.getEmail());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fb = FirebaseAuth.getInstance();
        binding = DataBindingUtil.setContentView( this, R.layout.activity_register);
        //db = AppDatabase.getInstance(this);//ini room kah?
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doReg();
            }
        });
        binding.btnLogin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
        binding.setUser(new User());
        if (savedInstanceState != null) {
            User restored = new User();
            String result = savedInstanceState.getString(stateUsername);
//            System.out.println("restored name : " + result);
            binding.inputLgusername.setText(result);
            String resultPass = savedInstanceState.getString(statePassword);
            binding.inputLgpassword.setText(resultPass);
            String resultEmail = savedInstanceState.getString(stateEmail);
            binding.inputLgemail.setText(resultEmail);
            restored.setUsername(result);
            restored.setPassword(resultPass);
            restored.setEmail(resultEmail);
            binding.setUser(restored);
        }
    }
    private void doReg()
    {
        //new usernameException().execute();
        signUpWithFirebase();

    }
    private boolean isValid(User user) {
        if(user.getUsername() == null || user.getUsername().equals("")) return false;
        if(user.getPassword() == null || user.getPassword().equals("")) return false;
        if(user.getEmail() == null || user.getEmail().equals("")) return false;
        return true;
    }
    private void setFirebaseUsername (){
        username = binding.getUser().getUsername();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(username)
                //.setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                        }
                    }
                });
    }
//    private class RegisterTask extends AsyncTask<URL, Integer, Integer>
//    {
//        User user = binding.getUser();
////        String emailSt = user.getUsername();
////        String passwordSt = user.getPassword();
//        @Override
//        protected Integer doInBackground(URL... urls) {
//            int flagMessage = -1;
//            if(isValid(user)) {
//                if (success == 0) {
//                    flagMessage = USERNAME_EXIST;
//                } else if (user.getPassword().length() < 6) {
//                    flagMessage = REGISTER_GAGAL;
//                } else {
//                    try {
//                        user.setDataPicture(""); //buat set foto kosongan pas awal
//                        db.getUserDao().Register(user);
//                        flagMessage = REGISTER_SUKSES;
//                        //                    fb.createUserWithEmailAndPassword(emailSt,passwordSt)
//                        //                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
//                        //                                @Override
//                        //                                public void onComplete(@NonNull Task<AuthResult> task)
//                        //                                {
//                        //                                    if(task.isSuccessful())
//                        //                                    {
//                        //                                        makeToast("Berhasil register!");
//                        //                                    }
//                        //                                }
//                        //                            });
//                    } catch (Exception e) {
//                        flagMessage = REGISTER_GAGAL;
////                        System.out.println("\n\nGagal karena dia: " + e.getMessage() + "\n\n");
//                    }
//                }
//            }
//            else
//            {
//                flagMessage = KOSONG;
//            }
//            return flagMessage;
//        }
//        @Override
//        protected void onPostExecute(Integer a) {
//            super.onPostExecute(a);
//            if(a == REGISTER_SUKSES) {
//                makeToast(getResources().getString(R.string.text_register_sukses));
//                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
//                finish();
//            }
//            else if(a == REGISTER_GAGAL) {
//                makeToast(getResources().getString(R.string.text_register_error));
//            }
//            else if(a == KOSONG) {
//                makeToast(getResources().getString(R.string.text_field_empty));
//            }
//            else if(a == USERNAME_EXIST){
//                makeToast(getResources().getString(R.string.text_username_exist));
//            }
//        }
//    }
    private void signUpWithFirebase(){
        email = binding.getUser().getEmail();
        password = binding.getUser().getPassword();

        fb.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(RegisterActivity.this, "Authentication Success",
                                    Toast.LENGTH_SHORT).show();
//                            FirebaseUser userfb = fb.getCurrentUser();
                            System.out.println("email yang resigtr 222: "+fb.getCurrentUser().getEmail());
                            sendEmailVerification();
                            setFirebaseUsername();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
//        System.out.println("email yang resigtr: "+fb.getCurrentUser().getEmail());
//
//
    }
    private void sendEmailVerification(){
        FirebaseUser userfb = fb.getCurrentUser();
        userfb.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this,
                                    "Verification email sent to " + userfb.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(RegisterActivity.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

//    private class usernameException extends AsyncTask<Void, Void, Void>
//    {
//        User user = binding.getUser();
//        List<User> ListA = new ArrayList<>();
//        @Override
//        protected Void doInBackground(Void... voids) {
//            ListA = db.getUserDao().UserException(user.getUsername());
//            if(ListA.size() > 0) {
//                success = 0;
//            }
//            else{
//                success = 1;
//            }
//            return null;
//        }
//    }

    private void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

