package com.moonlay.litewill.fragments.forgot;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moonlay.litewill.R;
import com.moonlay.litewill.fragments.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgotPass2Fragment extends BaseFragment {
    TextView tvResend;

    public ForgotPass2Fragment() {
        // Required empty public constructor
    }

    public static ForgotPass2Fragment newInstance(String userName, String token){
        ForgotPass2Fragment fragment = new ForgotPass2Fragment();
        Bundle args = new Bundle();
        args.putString("username", userName);
        args.putString("token", token);
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forgot_pass2, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        tvResend = mView.findViewById(R.id.tv_resend);
        textEnable(tvResend, true);
        tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textEnable(tvResend, false);
                textEnableTimer(tvResend);
            }
        });
    }
}
