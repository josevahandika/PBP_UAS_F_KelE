package com.satriawibawa.myapplication.UnitTest;

import com.satriawibawa.myapplication.daopackage.User;

public interface RegisterCallback {
    void onSuccess(boolean value, User user);
    void onError();
}
