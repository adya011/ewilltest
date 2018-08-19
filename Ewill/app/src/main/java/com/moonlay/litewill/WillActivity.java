package com.moonlay.litewill;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.moonlay.litewill.fragments.will.MyWillDetailFragment;
import com.moonlay.litewill.fragments.will.MyWillsFragment;
import com.moonlay.litewill.fragments.will.OpenWillDetailFragment;

public class WillActivity extends AppCompatActivity {
    public String getIntentAction;
    public final static String WILL_INTENT = "will_intent";
    private final static String TAG = "mydebug_will";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_will);

        Bundle extras = getIntent().getExtras();
        getIntentAction = extras.getString("will_intent");

        if (extras != null && getIntentAction != null) {
            Log.d(TAG, "Intent action: " + getIntentAction);

            if (getIntentAction.equals("go_to_my_wills")) {
                showMyWills();

            } else if (getIntentAction.equals("go_to_will_detail_open")) {
                showMuWillDetailOpenFragment();
            }

        } else {
            Log.d(TAG, "Intent action null");
        }
    }

    private void showMyWills() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_will, new MyWillsFragment());
        ft.commit();
    }

    private void showMuWillDetailOpenFragment(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //ft.replace(R.id.frame_will, OpenWillDetailFragment.newInstance(getIntentWillDetId));
        //ft.commit();
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }*/
}
