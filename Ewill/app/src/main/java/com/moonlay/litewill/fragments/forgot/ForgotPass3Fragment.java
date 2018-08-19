package com.moonlay.litewill.fragments.forgot;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.moonlay.litewill.ForgotActivity;
import com.moonlay.litewill.R;
import com.moonlay.litewill.api.ApiInterface;
import com.moonlay.litewill.api.GetToken;
import com.moonlay.litewill.api.RestProvider;
import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.model.ResetPassword;
import com.moonlay.litewill.model.ResetPasswordResponse;
import com.moonlay.litewill.utility.SharedPrefManager;
import com.moonlay.litewill.utility.TextValidation;

import java.io.IOException;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgotPass3Fragment extends BaseFragment {
    private Button btnNext;
    private TextInputEditText etNewPassword, etNewPasswordConf;
    private TextInputLayout etlNewPassword, etlNewPasswordConf;

    String userName, fpToken, newPassStr, newPassConfStr;
    final static String TAG = "mydebug_forgpass";

    ApiInterface apiInterface;
    SharedPrefManager sharedPrefManager;

    public ForgotPass3Fragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(String uriUserName, String uriToken) {
        ForgotPass3Fragment fragment = new ForgotPass3Fragment();
        Bundle args = new Bundle();
        args.putString("username", uriUserName);
        args.putString("token", uriToken);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forgot_pass3, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((ForgotActivity) getActivity()).setActionBarTitle("Forgot Password");
        initView();
    }

    private void initView() {
        etNewPassword = mView.findViewById(R.id.et_new_password);
        etNewPasswordConf = mView.findViewById(R.id.et_new_password_conf);
        sharedPrefManager = new SharedPrefManager(getContext());
        btnNext = mView.findViewById(R.id.btn_next);

        userName = getArguments().getString("username");
        fpToken = getArguments().getString("token");

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newPassStr = etNewPassword.getText().toString();
                newPassConfStr = etNewPasswordConf.getText().toString();

                if (TextValidation.passwordValidation(newPassStr)) {
                    if (newPassConfStr.equals(newPassStr)) {
                        try {
                            if(requestResetPassword(newPassStr).equals(Constants.SUCCESS)){
                                Log.d(TAG, "FORGOT PASS SUCCESS");
                                FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.replace(R.id.frame_layout_forgot, new ForgotPass4Fragment());
                                ft.addToBackStack(null);
                                ft.commit();
                            }else {
                                Log.d(TAG, "FORGOT PASS FAILED");
                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    } else {
                        etlNewPasswordConf.setErrorEnabled(true);
                        etlNewPasswordConf.setError(getResources().getString(R.string.err_msg_pass_conf));
                    }
                } else {
                    etlNewPassword.setErrorEnabled(true);
                    etlNewPassword.setError(TextValidation.warning);
                }
            }
        });
    }

    public String requestResetPassword(final String newPassword) throws InterruptedException {
        final String[] retVal = {null};
        final String[] token = {null};

        Thread threadToken = new Thread(new Runnable() {
            @Override
            public void run() {
                token[0] = GetToken.requestToken();
            }
        });
        threadToken.start();
        threadToken.join();

        Thread threadForgPass = new Thread(new Runnable() {
            String uniqueID = UUID.randomUUID().toString();

            @Override
            public void run() {
                apiInterface = RestProvider.getClient().create(ApiInterface.class);
                Call<ResetPasswordResponse> call = apiInterface.resetPassword(uniqueID, Constants.HEADER_AGENT, Constants.HEADER_VERSION, token[0],
                        new ResetPassword(userName, fpToken, newPassStr));

                try {
                    Response<ResetPasswordResponse> resp = call.execute();
                    Log.d(TAG, "forgot password resp code: " + resp.code());

                    if (resp.code() == 200 && resp.isSuccessful() == true) {
                        retVal[0] = Constants.SUCCESS;

                    } else {
                        retVal[0] = Constants.FAILED;

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        threadForgPass.start();
        threadForgPass.join();

        return retVal[0];
    }
}
