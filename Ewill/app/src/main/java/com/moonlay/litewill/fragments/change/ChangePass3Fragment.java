package com.moonlay.litewill.fragments.change;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.moonlay.litewill.DashboardActivity;
import com.moonlay.litewill.R;
import com.moonlay.litewill.fragments.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePass3Fragment extends BaseFragment {
    Button btnNext;

    public ChangePass3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_pass3, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //((DashboardActivity) getActivity()).setActionBarTitle("Change Password");
        initView();
    }

    private void initView() {
        btnNext = mView.findViewById(R.id.btn_next);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_tab1_layout, new MyAccountFragment());
                transaction.commit();*/
                FragmentManager fm = getFragmentManager();
                fm.popBackStack(null, fm.POP_BACK_STACK_INCLUSIVE);
            }
        });
    }
}
