package com.satriawibawa.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.satriawibawa.myapplication.daopackage.AppDatabase;
import com.satriawibawa.myapplication.daopackage.User;
import com.satriawibawa.myapplication.databinding.ActivityEditProfileBinding;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.Base64;

public class EditProfileActivity extends AppCompatActivity {
    public static final int CAMERA_FROM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    private ActivityEditProfileBinding binding;
    private SharedPref sharedPref;
    private String foto_profil, email, password, username;
    private static AppDatabase db;
    private Bitmap image;
    private FirebaseAuth fb = FirebaseAuth.getInstance();
    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = new SharedPref(this);
        db = AppDatabase.getInstance(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile);
        email = fb.getCurrentUser().getEmail();
        username = fb.getCurrentUser().getDisplayName();
        binding.showEmail.setText(email);
        binding.showUsername.setText(username);
       // binding.showUsername.setText(sharedPref.getUsernameS());
//        binding.btnUpdatePhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                askCameraPermissions();
//            }
//        });
        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    //foto_profil = convertBitmapToString(image);
                    //new EditProfileTask().execute();
                    //sharedPref.setDataPicture(foto_profil);
                    ChangePasswordFirebase();
//                    System.out.println("sampe password");
//
//                    System.out.println("sampe username");
                    Toast.makeText(EditProfileActivity.this, "Profil Berhasil Diubah!", Toast.LENGTH_SHORT).show();
                    setFirebaseUsername();
                }
                catch(Exception e){

                }
            }
        });
        binding.btnCancelEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //ngeload foto dari sharedpref
//        if(sharedPref.getDataPictureS().equalsIgnoreCase("")==false)
//        {
//            foto_profil = sharedPref.getDataPictureS();
//          //  Bitmap foto_bitmap = convertStringToBitmap(foto_profil);
//         //   binding.avatar.setImageBitmap(foto_bitmap);
//        }
    }
//    private void askCameraPermissions ()
//    {
//        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, CAMERA_FROM_CODE);;
//        }else{
//            openCamera();
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if(requestCode == CAMERA_FROM_CODE) {
//            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                openCamera();
//            }else{
//                Toast.makeText(this, "Camera Permission is Required to Use camera", Toast.LENGTH_SHORT).show();
//            }
//        }
    }

//    private void openCamera() {
//        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(camera, CAMERA_REQUEST_CODE);
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == CAMERA_REQUEST_CODE) {
//            image = (Bitmap) data.getExtras().get("data");
//            //binding.avatar.setImageBitmap(image);
//        }
    }

//    public String convertBitmapToString(Bitmap image) {
//        //converting bitmap to bytearray source: https://stackoverflow.com/questions/7620401/how-to-convert-byte-array-to-bitmap
//        ByteArrayOutputStream blob = new ByteArrayOutputStream();
//        image.compress(Bitmap.CompressFormat.PNG, 0 /* Ignored for PNGs */, blob);
//        byte[] bitmapdata = blob.toByteArray();
//
//        String imageString = Base64.getEncoder().encodeToString(bitmapdata);
//        return imageString;
//    }

//    public Bitmap convertStringToBitmap(String imageString) {
//        //converting bitmap to bytearray source: https://stackoverflow.com/questions/7620401/how-to-convert-byte-array-to-bitmap
//        byte[] bit = Base64.getDecoder().decode(imageString);
//        Bitmap bitmap = BitmapFactory.decodeByteArray(bit, 0, bit.length);
//        return bitmap;
//    }
//    private class EditProfileTask extends AsyncTask<URL, Integer, Integer>
//    {
//
//        @Override
//        protected Integer doInBackground(URL... urls) {
////            User longin;
////            int flagMessage = -1;
//            User user = db.getUserDao().Login(sharedPref.getUsernameS());
////            System.out.println("Update data"+user.getUsername());
////            System.out.println("Foto sebelum"+user.getDataPicture());
////            user.setDataPicture(foto_profil);
////            System.out.println("Foto Sesudah"+user.getDataPicture());
//            user.setEmail(email);
//            user.setPassword(password);
//            db.getUserDao().Update(user);
//            return 0;
//        }
//    }
    private void ChangePasswordFirebase(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String newPassword = binding.showPassword.getText().toString();

        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(newPassword, "User password updated.");
                        }
                    }
                });
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

}