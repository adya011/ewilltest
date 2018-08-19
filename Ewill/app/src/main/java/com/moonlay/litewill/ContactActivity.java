package com.moonlay.litewill;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.moonlay.litewill.fragments.ContactEmailFragment;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        showContactEmail();
    }

    private void showContactEmail(){
        ContactEmailFragment frag = new ContactEmailFragment();
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.content_contact, frag);
        trans.commit();
    }
}
