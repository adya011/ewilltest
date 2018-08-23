package com.moonlay.litewill;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.fragments.reg_member.Reg0Intro;
import com.moonlay.litewill.fragments.reg_member.Reg10PhoneVerifyFragment;
import com.moonlay.litewill.fragments.reg_member.Reg9EmailVerifyFragment;
import com.moonlay.litewill.utility.SharedPrefManager;

public class RegMemberActivity extends AppCompatActivity {
    public static String TAG = "mydebug_register";

    String regUsername;
    String regStatus;

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_register);

        if (fragment == null) {
            init();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void init() {
        sharedPrefManager = new SharedPrefManager(this);
        regUsername = sharedPrefManager.getRegUsername();
        regStatus = sharedPrefManager.getRegStatus();       //Reg Status: Wait verify phone, email, or registration from beginning
        Log.d(TAG, "reg_username: " + String.valueOf(regUsername));
        Log.d(TAG, "reg_status: " + String.valueOf(regStatus));
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (regStatus != null) {
            switch (sharedPrefManager.getRegStatus()) {
                case Constants.REG_WAIT_VER_EMAIL:
                    ft.replace(R.id.content_register, Reg9EmailVerifyFragment.newInstance(regUsername), "reg9");
                    Log.d(TAG, "Reg status: Wait for verify email. Username: " + regUsername + " Status:" + regStatus);
                    break;

                case Constants.REG_WAIT_VER_PHONE:
                    ft.replace(R.id.content_register, Reg10PhoneVerifyFragment.newInstance(regUsername), "reg10");
                    Log.d(TAG, "Reg status: Wait for verify phone. Username: " + regUsername + " Status:" + regStatus);
                    break;
            }
        } else {
            ft.replace(R.id.content_register, new Reg0Intro());
            Log.d(TAG, "Reg status: Normal. Username: " + regUsername);
        }

        ft.commit();
    }


    @Override
    public boolean onSupportNavigateUp() {
        FragmentManager fm = getSupportFragmentManager();
        if (regStatus != null && fm.getBackStackEntryCount() <= 1) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();

        } else {
            super.onBackPressed();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (regStatus != null && fm.getBackStackEntryCount() <= 1) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();

        } else {
            super.onBackPressed();
        }
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}