package com.moonlay.litewill;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.moonlay.litewill.api.ApiInterface;
import com.moonlay.litewill.api.GetToken;
import com.moonlay.litewill.fragments.change.ChangeEmail1Fragment;
import com.moonlay.litewill.fragments.change.ChangeMobileNum1Fragment;
import com.moonlay.litewill.fragments.change.ChangePIN1Fragment;
import com.moonlay.litewill.fragments.change.ChangePass1Fragment;
import com.moonlay.litewill.dialogfragment.PinFragment;
import com.moonlay.litewill.utility.SharedPrefManager;

public class ChangeActivity extends AppCompatActivity {
    public static Bundle bundles = new Bundle();
    ApiInterface apiInterface;

    String token = null;

    SharedPrefManager sharedPrefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        String change = getIntent().getStringExtra("change_action");

        /*switch (change) {
            case "change_email":
                showPinDialog("Change Email");
                showChangeEmail();
            case "change_password":
                showPinDialog("Change Password");
                showChangePassword();
            case "change_pin":
                showPinDialog("Change PIN");
                showChangePIN();
            case "change_mobilenum":
                showPinDialog("Change Mobile Number");
                showChangeMobileNum();
        }*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    private void showChangeEmail() {

        ChangeEmail1Fragment frag = new ChangeEmail1Fragment();

        frag.setArguments(bundles);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_change, frag);
        transaction.commit();
        //action = "change_email_1";
    }

    private void showChangePassword() {
        ChangePass1Fragment frag = new ChangePass1Fragment();

        /*frag.setArguments(bundles);*/
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_change, frag);
        transaction.commit();
        //action = "change_email_1";
    }

    private void showChangePIN() {
        ChangePIN1Fragment frag = new ChangePIN1Fragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_change, frag);
        transaction.commit();
    }

    private void showChangeMobileNum() {
        ChangeMobileNum1Fragment frag = new ChangeMobileNum1Fragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_change, frag);
        transaction.commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            finish();
        }
        return super.onNavigateUp();
    }

    private void showPinDialog(String title) {
        FragmentManager fm = getSupportFragmentManager();
        PinFragment pinFrag = PinFragment.newInstance(title);
        pinFrag.show(fm, "fragment_edit_name");
    }


    public void requestChangePin(Context context) throws InterruptedException {
        final boolean[] retVal = {false};
        final GetToken getRest = new GetToken();

        Thread threadToken = new Thread(new Runnable() {
            @Override
            public void run() {
                token = getRest.requestToken();
            }
        });
        threadToken.start();
        threadToken.join();


    }
}
