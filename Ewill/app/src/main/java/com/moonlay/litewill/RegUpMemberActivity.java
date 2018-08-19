package com.moonlay.litewill;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.moonlay.litewill.fragments.reg_member_up_maker.Reg1PassportImageFragment;
import com.moonlay.litewill.fragments.reg_member_up_searcher.UpSearcher1Fragment;

public class RegUpMemberActivity extends AppCompatActivity {
    public static final String TAG = "mydebug_register2";

    public String passportNo;
    public String fullName;
    public String gender;
    public String dob;
    public String pob;
    public String nationality;
    public String address;
    public boolean isSingaporean;
    public int subscribePackage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        Log.d(TAG, "++ Register Upgrade Activity ++");

        Bundle extras = getIntent().getExtras();
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_register);
        if (fragment == null) {
            if (extras != null) {
                String getIntentAction = extras.getString("up_member_intent");
                if (getIntentAction.equals("up_searcher")) {
                    upSearcher();
                }else if(getIntentAction.equals("up_maker")){
                    upMaker();
                }
            }
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void upSearcher() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_register2, new UpSearcher1Fragment());
        ft.commit();
    }

    private void upMaker(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_register2, new Reg1PassportImageFragment(), "reg11");
        ft.commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return true;
    }
}
