package com.satriawibawa.myapplication.UnitTest;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.satriawibawa.myapplication.RegisterActivity;

public class RegisterService {
    int cek=0;
    private static final String TAG = "EmailPassword";
    public void register(final RegisterView view, String username, String email, String password) {
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
//        final FirebaseUser[] user = new FirebaseUser[1];
        FirebaseUser fb = fAuth.getCurrentUser();

//        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    user[0] = FirebaseAuth.getInstance().getCurrentUser();
//                    System.out.println("yes");
//                    view.startMainActivity();
//                    cek=1;
//
//                } else {
////                    Toast.makeText(LoginActivity.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
//                    cek=0;
//                }
//            }
//        });
        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser fuser = fAuth.getCurrentUser();
                    fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                             cek = 1;
                             view.startMainActivity();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: Email not sent " + e.getMessage());
                        }
                    });
                } else {
                    cek = 0;

                }
            }
        });
    }
    public Boolean getValid(final RegisterView view, String username, String nim, String password){
        final Boolean[] bool = new Boolean[1];
        register(view, username, nim, password); ;
        if (cek == 1 ){
            bool[0] = true;
        } else {
            bool[0] = false;
        }
        return bool[0];
    }
}
