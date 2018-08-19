package com.moonlay.litewill.fragments.reg_member_up_maker;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.moonlay.litewill.R;
import com.moonlay.litewill.fragments.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 *
 * Activity: RegUpMemberActivity
 * Frame: frame_register2
 */
public class Reg1PassportImageFragment extends BaseFragment {
    Button btnScanPassport;
    public static final String TAG = "mydebug_passport";

    public Reg1PassportImageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reg11_passport, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        //((RegUpMemberActivity) getActivity()).setActionBarTitle("Register MyWill");
    }

    private void initView() {
        btnScanPassport = mView.findViewById(R.id.btn_next);
        btnScanPassport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame_register2, new Reg2ScanFragment());
                ft.addToBackStack("reg11");
                ft.commit();

                Log.d(TAG, "+++ To Reg12 Fragment +++");
            }
        });
    }
}
