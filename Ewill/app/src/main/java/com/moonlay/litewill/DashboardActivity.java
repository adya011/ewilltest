package com.moonlay.litewill;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.moonlay.litewill.fragments.home.Tab2MyAccountFragment;
import com.moonlay.litewill.fragments.home.Tab3ContactUsFragment;
import com.moonlay.litewill.fragments.home.Tab1HomeFragment;
import com.moonlay.litewill.model.Shared;
import com.moonlay.litewill.utility.SharedPrefManager;

import java.util.ArrayList;

public class DashboardActivity extends BaseActivity {
    SharedPrefManager sharedPrefManager;
    public ArrayList<Shared> shareds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.d("mydebug", "++ Dashboard Activity ++");

        init();
    }

    private void init() {
        sharedPrefManager = new SharedPrefManager(this);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bot_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = Tab1HomeFragment.newInstance();

                        switch (item.getItemId()) {
                            case R.id.action_item1:
                                selectedFragment = Tab1HomeFragment.newInstance();
                                break;
                            case R.id.action_item2:
                                selectedFragment = Tab2MyAccountFragment.newInstance();
                                break;
                            case R.id.action_item3:
                                selectedFragment = Tab3ContactUsFragment.newInstance();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();

                        return true;
                    }
                });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, Tab1HomeFragment.newInstance());
        transaction.commit();

        String currentLoggedInUsername = sharedPrefManager.getUserLoggedIn();
        sharedPrefManager.setUserLastLoggedIn(currentLoggedInUsername);
    }


    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        /*FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            finish();
        }*/
        onBackPressed();
        return true;
    }
}
