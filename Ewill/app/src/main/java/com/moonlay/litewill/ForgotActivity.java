package com.moonlay.litewill;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.moonlay.litewill.api.ApiInterface;
import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.fragments.forgot.ForgotName1Fragment;
import com.moonlay.litewill.fragments.forgot.ForgotPass1Fragment;
import com.moonlay.litewill.fragments.forgot.ForgotPass3Fragment;

public class ForgotActivity extends AppCompatActivity {
    public static String newPass;
    public static String newPassConf;
    ApiInterface apiInterface;

    public String token;
    public String respStat;

    String uriUserName = null;
    String uriToken = null;
    String command = null;
    String action;

    public static Handler h;

    public static ForgotActivity newInstance(){
        ForgotActivity activity = new ForgotActivity();
        return activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        action = String.valueOf(intent.getStringExtra("action"));
        command = intent.getStringExtra("command");
        uriUserName = intent.getStringExtra("username");
        uriToken = intent.getStringExtra("token");

        Log.d("mydebug", "action: " + action);
        Log.d("mydebug", "uri command: " + command);
        Log.d("mydebug", "uri username: " + uriUserName);
        Log.d("mydebug", "uri token: " + uriToken);

        if (action.equals(Constants.FORGOT_PASSWORD)) {
            showForgotPassword();

        } else if (action.equals(Constants.FORGOT_USERNAME)) {
            showForgotUsername();

        } else {
            if (uriUserName != null && uriToken != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_layout_forgot, ForgotPass3Fragment.newInstance(uriUserName, uriToken), null);
                ft.addToBackStack(null);
                ft.commit();

                Log.d("mydebug", "Go To ForgotPass 3 " + uriUserName + " " + uriToken);
            }
        }


    }

    private void showForgotPassword() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_forgot, new ForgotPass1Fragment());
        transaction.commit();
    }

    private void showForgotUsername() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_forgot, new ForgotName1Fragment());
        transaction.commit();
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onSupportNavigateUp() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 1) {
            fragmentManager.popBackStack();
        } else {
            finish();
        }
        return super.onNavigateUp();
    }
}
