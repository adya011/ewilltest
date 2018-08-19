package com.moonlay.litewill.fragments.forgot;


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
public class ForgotName2Fragment extends BaseFragment {
    Button btnNext;
    TextView tvResend;

    public ForgotName2Fragment() {
        // Required empty public constructor
    }

    public static ForgotName2Fragment newInstance(String email){
        ForgotName2Fragment fragment = new ForgotName2Fragment();
        Bundle args = new Bundle();
        args.putString("email", email);
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_name2, container, false); 
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        tvResend = mView.findViewById(R.id.tv_resend);
        btnNext = mView.findViewById(R.id.btn_next);

        textEnable(tvResend, true);

        tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textEnable(tvResend, false);
                textEnableTimer(tvResend);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }
}
