package com.satriawibawa.myapplication.UnitTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegisterPresenterTest {
    @Mock
    private RegisterView view;
    @Mock
    private RegisterService service;
    private RegisterPresenter presenter;
    @Before
    public void setUp() throws Exception {
        presenter = new RegisterPresenter(view, service);
    }
    @Test
    public void usernameKosong() throws Exception {
        when(view.getUsername()).thenReturn("");
        System.out.println("username : " + view.getUsername());
        presenter.onLoginClicked();
        verify(view).showUsernameError("Field tidak boleh kosong");
    }
    @Test
    public void emailKosong() throws Exception {
        when(view.getUsername()).thenReturn("test");
        System.out.println("Username : " + view.getUsername());
        when(view.getEmail()).thenReturn("");
        System.out.println("Email : " + view.getEmail());
        presenter.onLoginClicked();
        verify(view).showEmailError("Field tidak boleh kosong");
    }
    @Test
    public void passwordKosong() throws Exception {
        when(view.getUsername()).thenReturn("test");
        System.out.println("Username : " + view.getUsername());
        when(view.getEmail()).thenReturn("josevahandika@gmail.com");
        System.out.println("Email : " + view.getEmail());
        when(view.getPassword()).thenReturn("");
        System.out.println("Password : " + view.getPassword());
        presenter.onLoginClicked();
        verify(view).showPasswordError("Field tidak boleh kosong");
    }
    @Test
    public void registerValid() throws
            Exception {
        when(view.getUsername()).thenReturn("test");
        System.out.println("Username : " + view.getUsername());
        when(view.getEmail()).thenReturn("josevahandika@gmail.com");
        System.out.println("Email : " + view.getEmail());
        when(view.getPassword()).thenReturn("123456");
        System.out.println("Password : " + view.getPassword());
        when(service.getValid(view, view.getUsername(), view.getEmail(),
                view.getPassword())).thenReturn(true);
        System.out.println("Hasil : " + service.getValid(view, view.getUsername(), view.getEmail(),
                view.getPassword()));
        presenter.onLoginClicked();
        //verify(view).startMainActivity();
    }
}