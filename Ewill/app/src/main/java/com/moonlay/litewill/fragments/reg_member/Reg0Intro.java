package com.moonlay.litewill.fragments.reg_member;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.moonlay.litewill.R;
import com.moonlay.litewill.RegMemberActivity;
import com.moonlay.litewill.fragments.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class Reg0Intro extends BaseFragment {
    Button btnNext;

    public Reg0Intro() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reg0_intro, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        ((RegMemberActivity) getActivity()).setActionBarTitle("Open Account");
    }

    private void initView() {
        btnNext = mView.findViewById(R.id.button_next0);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reg1EmailFragment nextFrag = new Reg1EmailFragment();
                fragmentTrans(R.id.content_register, nextFrag, null, "reg1");
            }
        });
    }
}
