package com.satriawibawa.myapplication.UnitTest;

import android.content.Context;
import android.content.Intent;

import com.satriawibawa.myapplication.LoginActivity;
import com.satriawibawa.myapplication.MainActivity;

public class ActivityUtil {
    private Context context;
    public ActivityUtil(Context context) {
        this.context = context;
    }
    public void startMainActivity() {
        context.startActivity(new Intent(context, LoginActivity.class));
    }
    public void startUserProfile() {
        Intent i = new Intent(context, MainActivity.class);
        context.startActivity(i);
    }
}
