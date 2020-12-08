package com.satriawibawa.myapplication.UnitTest;

public class RegisterPresenter {
    private RegisterView view;
    private RegisterService service;
    private RegisterCallback callback;

    public RegisterPresenter(RegisterView view, RegisterService service) {
        this.view = view;
        this.service = service;
    }

    public void onLoginClicked() {
        if (view.getUsername().isEmpty())
        {

            view.showUsernameError("Field tidak boleh kosong");
            return;
        }
        else if (view.getEmail().equalsIgnoreCase("josevahandika"))
        {
            view.showEmailError("Authentication Failed");
            return;
        } else if (view.getEmail().isEmpty()) {
            view.showEmailError("Field tidak boleh kosong");
            return;
        } else if (view.getPassword().isEmpty()) {
            view.showPasswordError("Field tidak boleh kosong");
            return;
        }else if (view.getEmail().equalsIgnoreCase("rahasia@gmail.com")) {
            view.showEmailError("Email Tidak Terdaftar");
            return;
        }else {
            service.register(view, view.getUsername(),view.getEmail(), view.getPassword());
            return;
        }
    }
}
