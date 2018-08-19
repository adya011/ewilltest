package com.moonlay.litewill.fragments.change;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.moonlay.litewill.R;
import com.moonlay.litewill.api.ApiInterface;
import com.moonlay.litewill.api.GetToken;
import com.moonlay.litewill.api.RestProvider;
import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.model.ChangePassword;
import com.moonlay.litewill.model.ChangePasswordResponse;
import com.moonlay.litewill.utility.SharedPrefManager;

import java.io.IOException;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePass2Fragment extends BaseFragment {
    Button btnNext;
    EditText etNewPass, etNewPassConf;
    TextInputLayout etlNewPass, etlNewPassConf;
    SharedPrefManager sharedPrefManager;
    ApiInterface apiInterface;

    public ChangePass2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_pass2, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView = view;
        initView();
    }

    private void initView() {
        etNewPass = mView.findViewById(R.id.et_new_password);
        etNewPassConf = mView.findViewById(R.id.et_new_password_conf);
        etlNewPass = mView.findViewById(R.id.etl_new_password);
        etlNewPassConf = mView.findViewById(R.id.etl_new_password_conf);


        btnNext = mView.findViewById(R.id.btn_next);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String etNewPassStr = etNewPass.getText().toString();
                String etNewPassConfStr = etNewPassConf.getText().toString();

                Bundle b = getArguments();
                String oldPass = b.getString("oldPass");

                Log.d("mydebug_changepass", "old pass: " + oldPass);
                Log.d("mydebug_changepass", "new pass: " + etNewPassStr);


                if (etNewPassConfStr.equals(etNewPassStr)) {
                    try {
                        if (requestChangePassword(oldPass, etNewPassStr).equals("200")) {
                            Log.d("mydebug_changepass", "SUCCESS!");
                            fragmentTrans(R.id.frame_tab2_layout, new ChangePass3Fragment(), null, "change_pass3");

                        } else {
                            showErrorDialog("Cannot change password. Problem occured.");
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } else {
                    etlNewPassConf.setErrorEnabled(true);
                    etlNewPassConf.setError(getString(R.string.err_msg_email_conf));
                }
            }
        });
    }

    public String requestChangePassword(final String oldPass, final String newPass) throws InterruptedException {
        final String[] token = {null};
        final String[] retVal = {null};
        final GetToken getRest = new GetToken();
        sharedPrefManager = new SharedPrefManager(getContext());

        Thread threadToken = new Thread(new Runnable() {
            @Override
            public void run() {
                token[0] = getRest.requestToken();
            }
        });
        threadToken.start();
        threadToken.join();

        Thread threadChangePass = new Thread(new Runnable() {
            String uniqueID = UUID.randomUUID().toString();

            @Override
            public void run() {
                apiInterface = RestProvider.getClient().create(ApiInterface.class);
                Call<ChangePasswordResponse> call = apiInterface.changePassword(uniqueID, Constants.HEADER_AGENT, Constants.HEADER_VERSION, token[0],
                        new ChangePassword(sharedPrefManager.getUserLoggedIn(), oldPass, newPass));
                //token dari sharedpreferences

                try {
                    Response<ChangePasswordResponse> resp = call.execute();
                    Log.d("mydebug_changepass", "resp code: " + resp.code());
                    Log.d("mydebug_changepass", ": " + "old pass: " + oldPass);
                    Log.d("mydebug_changepass", ": " + "new pass: " + newPass);
                    retVal[0] = String.valueOf(resp.code());
                    //jika respon 401 panggil APi refresh token, set shared preference yang baru

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        threadChangePass.start();
        threadChangePass.join();

        return retVal[0];
    }
}
