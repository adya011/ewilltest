package com.moonlay.litewill.fragments.change;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.moonlay.litewill.DashboardActivity;
import com.moonlay.litewill.MainActivity;
import com.moonlay.litewill.R;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.fragments.home.MyAccountFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeEmail3Fragment extends BaseFragment {
    Button btnNext;

    public ChangeEmail3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_email3, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //((DashboardActivity) getActivity()).setActionBarTitle("Change Email Address");
        initView();
    }

    private void initView() {
        btnNext = mView.findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*FragmentManager fm = getFragmentManager();
                fm.popBackStack(null, fm.POP_BACK_STACK_INCLUSIVE);*/
                MainActivity.isDeepLinkChangeEmail = false;
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame_tab1_layout, new MyAccountFragment());
                ft.commit();
            }
        });
    }
}
