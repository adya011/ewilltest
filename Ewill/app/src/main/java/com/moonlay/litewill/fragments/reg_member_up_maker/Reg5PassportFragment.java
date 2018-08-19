package com.moonlay.litewill.fragments.reg_member_up_maker;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.moonlay.litewill.R;
import com.moonlay.litewill.RegUpMemberActivity;
import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.fragments.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class Reg5PassportFragment extends BaseFragment {
    public static final String TAG = "mydebug_passport";
    Button btnNext;
    EditText etNationality, etAddress;
    AppCompatSpinner spGender, spReside;

    public Reg5PassportFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reg14_passport, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etNationality = mView.findViewById(R.id.et_nationality);
        btnNext = mView.findViewById(R.id.btn_next);
        spGender = mView.findViewById(R.id.sp_gender);
        spReside = mView.findViewById(R.id.sp_reside);
        etAddress = mView.findViewById(R.id.et_address);

        etNationality.setText(((RegUpMemberActivity) getActivity()).nationality);

        init();
    }

    private void init() {
        genderAdapter();
        resideAdapter();

        Bundle args = new Bundle();
        if (args != null) {

            etNationality.setText(((RegUpMemberActivity) getActivity()).nationality);

            if(((RegUpMemberActivity) getActivity()).gender == null) {
                spGender.setSelection(0);
            }



                /*if (((RegUpMemberActivity) getActivity()).gender.equals("F")) {
                    spGender.setSelection(1);
                }else{
                    spGender.setSelection(0);
                }
            }
            else {
                spGender.setSelection(0);
            }*/
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((RegUpMemberActivity) getActivity()).nationality = etNationality.getText().toString();
                ((RegUpMemberActivity) getActivity()).gender = "male"; //etGender.getText().toString();
                ((RegUpMemberActivity) getActivity()).isSingaporean = true;
                ((RegUpMemberActivity) getActivity()).address = etAddress.getText().toString();

                Log.d(TAG, "sp gender: " + spGender.getSelectedItem().toString());

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame_register2, new Reg6PackageFragment());
                ft.addToBackStack("reg14");
                ft.commit();
            }
        });
    }

    private void genderAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, Constants.gender);
        spGender.setAdapter(adapter);
        spGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ((RegUpMemberActivity) getActivity()).gender = "male";
                        break;
                    case 1:
                        ((RegUpMemberActivity) getActivity()).gender = "female";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void resideAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, Constants.yesNo);
        spReside.setAdapter(adapter);
        spReside.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ((RegUpMemberActivity) getActivity()).isSingaporean = true;
                        break;
                    case 1:
                        ((RegUpMemberActivity) getActivity()).isSingaporean = false;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
