package com.moonlay.litewill.fragments.reg_member_up_maker;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.moonlay.litewill.R;
import com.moonlay.litewill.RegUpMemberActivity;
import com.moonlay.litewill.ValidDocumentTypes;
import com.moonlay.litewill.fragments.BaseFragment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

import xavier.blacksharktech.com.xavierlib.XavierActivity;
import xmlwise.Plist;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.moonlay.litewill.R.raw.xavier;

/**
 * A simple {@link Fragment} subclass.
 */
public class Reg3PassportFragment extends BaseFragment {
    public static final String TAG = "mydebug_passport";
    public static final String MRZ_KEY_VALUE_MAP = "MrzKeyValueMap";
    public static final String PASSPORT_DOC_TYPE = "P";
    public static final int MRZ_REQUEST = 1;
    public static final int USER_DOCTYPE_SELECTION_REQUEST = 2;

    EditText etIdNumber;
    Button btnNext;

    public Reg3PassportFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reg12_passport, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnNext = mView.findViewById(R.id.btn_next);
        etIdNumber = mView.findViewById(R.id.et_idnumber);

        init();
    }

    private void init() {
        etIdNumber.setText(((RegUpMemberActivity) getActivity()).passportNo);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((RegUpMemberActivity) getActivity()).passportNo = etIdNumber.getText().toString();

                goToNextFrag();

                Log.d(TAG, "+++ To Reg13 Fragment +++");
                Log.d(TAG, "passport id:" + ((RegUpMemberActivity) getActivity()).passportNo);
                Log.d(TAG, "full name:" + ((RegUpMemberActivity) getActivity()).fullName);
                Log.d(TAG, "dob:" + ((RegUpMemberActivity) getActivity()).dob);
                Log.d(TAG, "pob:" + ((RegUpMemberActivity) getActivity()).pob);
                Log.d(TAG, "nationality:" + ((RegUpMemberActivity) getActivity()).nationality);
                Log.d(TAG, "gender:" + ((RegUpMemberActivity) getActivity()).gender);
            }
        });
    }

    private void goToNextFrag(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frame_register2, new Reg4PassportFragment());
        ft.addToBackStack("reg12");
        ft.commit();
    }
}
