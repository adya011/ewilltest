package com.moonlay.litewill.fragments.reg_member;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.moonlay.litewill.R;
import com.moonlay.litewill.RegMemberActivity;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.utility.TextValidation;

/**
 * A simple {@link Fragment} subclass.
 */
public class Reg3PassFragment extends BaseFragment {
    private EditText etPassword, etPasswordConf;
    private TextInputLayout etlPassword, etlPasswordConf;
    private Button btnNext;


    public static Reg3PassFragment newInstance(String email, String username) {
        Reg3PassFragment frag = new Reg3PassFragment();
        Bundle bundle = new Bundle();
        bundle.putString("email", email);
        bundle.putString("username", username);
        frag.setArguments(bundle);

        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reg3_pass, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        ((RegMemberActivity) getActivity()).setActionBarTitle("Password");
    }

    private void initView() {
        etPassword = mView.findViewById(R.id.et_password);
        etPasswordConf = mView.findViewById(R.id.et_password_conf);
        etlPassword = mView.findViewById(R.id.etl_password);
        etlPasswordConf = mView.findViewById(R.id.etl_password_conf);
        btnNext = mView.findViewById(R.id.btn_next);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passwordStr = etPassword.getText().toString();
                String passwordConfStr = etPasswordConf.getText().toString();
                String email = getArguments().getString("email");
                String username = getArguments().getString("username");

                if (TextValidation.passwordValidation(passwordStr)) {
                    if (passwordConfStr.equals(passwordStr)) {
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content_register, Reg4PhoneFragment.newInstance(email, username, passwordStr));
                        ft.addToBackStack("reg3");
                        ft.commit();

                    } else {
                        etlPasswordConf.setErrorEnabled(true);
                        etlPasswordConf.setError(getResources().getString(R.string.err_msg_pass_conf));
                    }
                } else {
                    etlPassword.setErrorEnabled(true);
                    etlPassword.setError(TextValidation.warning);
                }
            }
        });
    }

}
