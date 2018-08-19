package com.moonlay.litewill.fragments.reg_member;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.moonlay.litewill.R;
import com.moonlay.litewill.fragments.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class Reg9EmailVerifyFragment extends BaseFragment {
    TextView tvResend;
    Button btnNext;

    public Reg9EmailVerifyFragment() {
    }

    public static Fragment newInstance(String username) {
        Reg9EmailVerifyFragment fragment = new Reg9EmailVerifyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reg9_email_verify, container, false);
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
