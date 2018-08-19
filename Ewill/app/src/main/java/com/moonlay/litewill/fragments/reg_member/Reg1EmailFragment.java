package com.moonlay.litewill.fragments.reg_member;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
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
public class Reg1EmailFragment extends BaseFragment {
    private EditText etEmail, etEmailConf;
    private TextInputLayout etLayEmail, etLayEmailConf;
    private Button btnNext;
    String emailStr = "";
    String emailConfStr = "";


    public Reg1EmailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("mydebug_reg2", "on Attach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("mydebug_reg2", "on Created");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reg1_email, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("mydebug_reg2", "on View Created");
        initView();
        ((RegMemberActivity) getActivity()).setActionBarTitle("Email Address");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("mydebug_reg2", "on Pause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("mydebug_reg2", "on Resume");
    }

    public void initView() {
        etEmail = mView.findViewById(R.id.et_email);
        etLayEmail = mView.findViewById(R.id.etl_email);
        etEmailConf = mView.findViewById(R.id.et_email_conf);
        etLayEmailConf = mView.findViewById(R.id.etl_email_conf);
        btnNext = mView.findViewById(R.id.btn_next);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailStr = etEmail.getText().toString();
                emailConfStr = etEmailConf.getText().toString();

                if (TextValidation.emailValidation(emailStr)) {
                    if (emailConfStr.equals(emailStr)) {
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content_register, Reg2NameFragment.newInstance(emailStr));
                        ft.addToBackStack("reg1");
                        ft.commit();

                    } else {
                        etLayEmailConf.setErrorEnabled(true);
                        etLayEmailConf.setError(getResources().getString(R.string.err_msg_email_conf));

                    }
                } else {
                    etLayEmail.setErrorEnabled(true);
                    etLayEmail.setError(TextValidation.warning);

                }
            }
        });
    }
}
