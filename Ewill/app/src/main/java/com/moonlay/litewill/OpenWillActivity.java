package com.moonlay.litewill;


import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.moonlay.litewill.fragments.will.OpenWillDetailFragment;
import com.moonlay.litewill.fragments.will.OpenWillsFragment;

public class OpenWillActivity extends AppCompatActivity {
    private static final String TAG = "mydebug_openwillAct";
    int willId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_will);
        init();
    }

    private void init() {
        Bundle extras = getIntent().getExtras();
        willId = extras.getInt("will_id");

        if(extras != null && willId != 0){
            goOpenWillDetail(willId);

        }else {
            //membuka will dari notification
            goOpenWill();
        }
    }

    private void goOpenWill() {
        Log.d(TAG, "go open will");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_will, new OpenWillsFragment());
        ft.commit();
    }

    private void goOpenWillDetail(int willId){
        Log.d(TAG, "go open will detail");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_will, new OpenWillDetailFragment().newInstance(willId));
        ft.commit();
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
