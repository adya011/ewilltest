package com.moonlay.litewill.fragments.forgot;


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

import com.moonlay.litewill.ForgotActivity;
import com.moonlay.litewill.R;
import com.moonlay.litewill.api.ApiInterface;
import com.moonlay.litewill.api.GetToken;
import com.moonlay.litewill.api.RestProvider;
import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.model.ForgotUsername;
import com.moonlay.litewill.model.ForgotUsernameResponse;
import com.moonlay.litewill.utility.TextValidation;

import java.io.IOException;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgotName1Fragment extends BaseFragment {
    Button btnNext;
    EditText etEmail;
    TextInputLayout etlEmail;

    String emailStr = null;
    final static String TAG = "mydebug_forgusername";

    ApiInterface apiInterface;

    public ForgotName1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_name1, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        ((ForgotActivity) getActivity()).setActionBarTitle("Forgot Username");
    }


    private void initView() {
        etEmail = mView.findViewById(R.id.et_email);
        etlEmail = mView.findViewById(R.id.etl_email);
        btnNext = mView.findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailStr = etEmail.getText().toString();

                if (TextValidation.emailValidation(emailStr)) {
                    try {
                        if (requestForgotUsername(emailStr).equals(Constants.SUCCESS)) {
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.frame_layout_forgot, new ForgotName2Fragment());
                            ft.addToBackStack(null);
                            ft.commit();

                        } else {
                            showErrorDialog("Error has occured");
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    etlEmail.setErrorEnabled(true);
                    etlEmail.setError(TextValidation.warning);

                }
            }
        });
    }

    public String requestForgotUsername(final String email) throws InterruptedException {
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

        Thread threadForgName = new Thread(new Runnable() {
            String uniqueID = UUID.randomUUID().toString();

            @Override
            public void run() {
                apiInterface = RestProvider.getClient().create(ApiInterface.class);
                Call<ForgotUsernameResponse> call = apiInterface.forgotUsername(uniqueID, Constants.HEADER_AGENT, Constants.HEADER_VERSION, token[0],
                        new ForgotUsername(email));

                try {
                    Response<ForgotUsernameResponse> resp = call.execute();
                    Log.d(TAG, "forgot username resp code: " + resp.code());
                    Log.d(TAG, "forgot username email: " + email);

                    if (resp.code() == 200 && resp.isSuccessful()) {
                        retVal[0] = Constants.SUCCESS;

                    } else {
                        retVal[0] = Constants.FAILED;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        threadForgName.start();
        threadForgName.join();

        return retVal[0];
    }
}
