package com.satriawibawa.myapplication.UnitTest;

public interface RegisterView {
    String getEmail();
    void showEmailError(String message);
    String getPassword();
    void showPasswordError(String message);
    String getUsername();
    void showUsernameError(String message);
    void startMainActivity();
    void startUserProfileActivity();
    void showLoginError(String message);
    void showErrorResponse(String message);
}
