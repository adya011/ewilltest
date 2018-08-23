package com.moonlay.litewill;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.moonlay.litewill.fragments.home.Tab2MyAccountFragment;
import com.moonlay.litewill.fragments.home.Tab3ContactUsFragment;
import com.moonlay.litewill.fragments.home.Tab1HomeFragment;
import com.moonlay.litewill.fragments.notification.NotificationFragment;
import com.moonlay.litewill.model.Shared;
import com.moonlay.litewill.utility.SharedPrefManager;

import java.io.File;
import java.util.ArrayList;

public class DashboardActivity extends BaseActivity {
    private static final String TAG = "mydebug_dashboard";
    SharedPrefManager sharedPrefManager;
    public ArrayList<Shared> shareds = new ArrayList<>();
    TextView tvNotificationCount;
    int notificationCount;
    Menu menu;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.d("mydebug", "++ Dashboard Activity ++");
        init();
    }

    private void init() {
        sharedPrefManager = new SharedPrefManager(this);
        sharedPreferences = this.getSharedPreferences("ewillSp", MODE_PRIVATE);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bot_nav);

        sharedPreferences.registerOnSharedPreferenceChangeListener(sharedPreferencesListener);

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

    SharedPreferences.OnSharedPreferenceChangeListener sharedPreferencesListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            Log.d(TAG, "preferences changed: " + sharedPrefManager.getNotificationCount());
            tvNotificationCount.setText(String.valueOf(sharedPrefManager.getNotificationCount()));
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "on create options menu");

        /*SharedPrefManager sharedPrefManager2 = new SharedPrefManager(this);
        sharedPrefManager2.addNotificationCount(1);*/

        getMenuInflater().inflate(R.menu.menu_toolbar_dashboard, menu);

        final MenuItem menuItem = menu.findItem(R.id.action_notification);

        View actionView = MenuItemCompat.getActionView(menuItem);
        tvNotificationCount = actionView.findViewById(R.id.notification_badge);

        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_notification:
                Log.d("DashboardAct", "Notificaitons Icon Selected");

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_layout, new NotificationFragment());
                ft.addToBackStack(null);
                ft.commit();

                tvNotificationCount.setVisibility(View.GONE);
                sharedPrefManager.clearNotificationCount();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupBadge() {
        notificationCount = sharedPrefManager.getNotificationCount();
        Log.d(TAG, "notification count:" + notificationCount);

        if (tvNotificationCount != null) {
            if (notificationCount == 0) {
                if (tvNotificationCount.getVisibility() != View.GONE) {
                    tvNotificationCount.setVisibility(View.GONE);
                }
            } else {
                tvNotificationCount.setText(String.valueOf(Math.min(notificationCount, 99)));
                if (tvNotificationCount.getVisibility() != View.VISIBLE) {
                    tvNotificationCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    /*public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }*/


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


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "dashboard resume");
        invalidateOptionsMenu();
    }
}
