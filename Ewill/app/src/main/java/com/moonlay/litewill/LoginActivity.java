package com.moonlay.litewill;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.moonlay.litewill.api.ApiInterface;
import com.moonlay.litewill.fragments.login.LoginFragment;
import com.moonlay.litewill.utility.SharedPrefManager;

public class LoginActivity extends AppCompatActivity {
    public static String userName;
    public static String password;
    ApiInterface apiInterface;
    SharedPrefManager sharedPrefManager;

    public static String TAG = "mydebug_login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_register);
        if (fragment == null) {
            showLoginFragment();
        }
    }

    private void showLoginFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_login, new LoginFragment());
        transaction.commit();
    }
}
