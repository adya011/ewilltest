package com.moonlay.litewill;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.moonlay.litewill.fragments.home.HomeFragment;
import com.moonlay.litewill.fragments.reg_will.Regw1WillNameFragment;
import com.moonlay.litewill.model.Address;
import com.moonlay.litewill.model.Document;
import com.moonlay.litewill.model.Shared;

import java.util.ArrayList;
import java.util.List;

public class RegWillActivity extends AppCompatActivity {
    public static final String TAG = "mydebug_will";
    public String getIntent;

    public String willName;
    public boolean isLFStatus;
    public boolean isLFShared;
    public boolean isLFLocationShared;
    public boolean isLFDocumentShared;
    public boolean isPAStatus;
    public boolean isPAShared;
    public boolean isPALocationShared;
    public boolean isPADocumentShared;
    public boolean isUserStatus;
    public boolean isUserShared;
    public boolean isUserLocationShared;
    public boolean isUserDocumentShared;
    public List<Document> documents = new ArrayList<>();
    public ArrayList<Shared> shareds = new ArrayList<>();
    public List<Address> addresses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_will);
        getIntent = getIntent().getStringExtra(HomeFragment.ACTION_ID);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_register);
        if (fragment == null) {
            init();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void init() {
        Fragment actionFragment = new Regw1WillNameFragment();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_will, actionFragment);
        ft.commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
