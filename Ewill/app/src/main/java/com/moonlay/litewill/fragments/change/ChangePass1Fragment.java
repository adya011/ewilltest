package com.moonlay.litewill.fragments.change;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.moonlay.litewill.DashboardActivity;
import com.moonlay.litewill.R;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.utility.TextValidation;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePass1Fragment extends BaseFragment {
    Button btnNext;
    EditText etOldPassword;
    TextInputLayout etlOldPassword;


    public ChangePass1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_pass1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //((DashboardActivity) getActivity()).setActionBarTitle("Change Password");
        //((DashboardActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
    }

    private void initView() {
        etOldPassword = mView.findViewById(R.id.et_old_password);
        etlOldPassword = mView.findViewById(R.id.etl_old_password);
        btnNext = mView.findViewById(R.id.btn_next);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String etOldpasswordStr = etOldPassword.getText().toString();
                TextValidation textVal = new TextValidation();

                Bundle bundle = new Bundle();
                bundle.putString("oldPass", etOldpasswordStr);

                if(textVal.passwordValidation(etOldpasswordStr) == true){
                    fragmentTrans(R.id.frame_tab2_layout, new ChangePass2Fragment(), bundle, null);

                }else {
                    etlOldPassword.setErrorEnabled(true);
                    etlOldPassword.setError(TextValidation.warning);
                }
            }
        });
    }
}
