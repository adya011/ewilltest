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
public class Reg2NameFragment extends BaseFragment {
    private EditText etUsername, etUsernameConf;
    private Button btnNext;
    private String email;
    //public static String email;
    private TextInputLayout etlUsername, etlUsernameConf;

    public static Reg2NameFragment newInstance(String getEmail) {
        Reg2NameFragment frag = new Reg2NameFragment();
        Bundle bundle = new Bundle();
        bundle.putString("email", getEmail);
        frag.setArguments(bundle);

        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reg2_name, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        ((RegMemberActivity) getActivity()).setActionBarTitle("Username");
    }

    private void initView() {
        etUsername = mView.findViewById(R.id.et_username);
        etUsernameConf = mView.findViewById(R.id.et_username_conf);
        etlUsername = mView.findViewById(R.id.etl_username);
        etlUsernameConf = mView.findViewById(R.id.etl_username_conf);
        btnNext = mView.findViewById(R.id.btn_next);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userNameStr = etUsername.getText().toString();
                String userNameConfStr = etUsernameConf.getText().toString();
                String email = getArguments().getString("email");

                if (TextValidation.userNameValidation(userNameStr)) {
                    if (userNameConfStr.equals(userNameStr)) {
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content_register, Reg3PassFragment.newInstance(email, userNameStr));
                        ft.addToBackStack("reg2");
                        ft.commit();

                    } else {
                        etlUsernameConf.setErrorEnabled(true);
                        etlUsernameConf.setError(getResources().getString(R.string.err_msg_email_conf));
                    }
                } else {
                    etlUsername.setErrorEnabled(true);
                    etlUsername.setError(TextValidation.warning);
                }
            }
        });
    }
}
