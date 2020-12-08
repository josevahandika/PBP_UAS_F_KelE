package com.satriawibawa.myapplication.UnitTest;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.satriawibawa.myapplication.R;

public class RegisterActivity extends AppCompatActivity implements RegisterView {
    private MaterialButton btnRegister;
    private TextInputEditText nim, password, username;
    private RegisterPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnRegister = findViewById(R.id.btnRegister);
        username = findViewById(R.id.input_lgusername);
        nim = findViewById(R.id.input_lgemail);
        password = findViewById(R.id.input_lgpassword);
        presenter = new RegisterPresenter(this, new RegisterService());
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onLoginClicked();
            }
        });
    }

    @Override
    public String getUsername() {
        return username.getText().toString();
    }
    @Override
    public void showUsernameError(String message) {
        username.setError(message);
    }
    @Override
    public String getEmail() {
        return nim.getText().toString();
    }
    @Override
    public void showEmailError(String message) {
        nim.setError(message);
    }
    @Override
    public String getPassword() {
        return password.getText().toString();
    }
    @Override
    public void showPasswordError(String message) {
        password.setError(message);
    }
    @Override
    public void startMainActivity() {
        new ActivityUtil(this).startMainActivity();
    }
    @Override
    public void startUserProfileActivity() {
        new ActivityUtil(this).startUserProfile();
    }
    @Override
    public void showLoginError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void showErrorResponse(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
