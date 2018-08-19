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

import com.moonlay.litewill.DashboardActivity;
import com.moonlay.litewill.R;
import com.moonlay.litewill.api.ApiInterface;
import com.moonlay.litewill.api.RestProvider;
import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.model.ChangeEmail;
import com.moonlay.litewill.model.ChangeEmailResponse;
import com.moonlay.litewill.utility.SharedPrefManager;
import com.moonlay.litewill.utility.TextValidation;

import java.io.IOException;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeEmail1Fragment extends BaseFragment {
    private Button btnNext;
    private EditText etEmail;
    private TextInputLayout etlEmail;
    SharedPrefManager sharedPrefManager;

    public static final String TAG = "mydebug_changeemail";

    public ChangeEmail1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_email1, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etlEmail = mView.findViewById(R.id.etl_email);
        etEmail = mView.findViewById(R.id.et_email);
        btnNext = mView.findViewById(R.id.btn_next);
        sharedPrefManager = new SharedPrefManager(getContext());

        ((DashboardActivity) getActivity()).setActionBarTitle("Change Email Address");
        ((DashboardActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
    }

    private void initView() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String etEmailStr = etEmail.getText().toString();
                TextValidation textVal = new TextValidation();

                Bundle bundle = new Bundle();
                bundle.putString("email", etEmailStr);

                ChangeEmail2Fragment nextFrag = new ChangeEmail2Fragment();

                if (textVal.emailValidation(etEmailStr) == true) {
                    try {
                        if (requestChangeEmail(String.valueOf(sharedPrefManager.getUserLoggedIn()), etEmailStr).equals("200")) {
                            Log.d(TAG, "SUCCESS!");
                            fragmentTrans(R.id.frame_layout, nextFrag, bundle, "change_email2");

                        } else {
                            showErrorDialog("Cannot change email. Problem occured.");
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

    private String requestChangeEmail(final String userName, final String newEmail) throws InterruptedException {
        final String[] retVal = {null};

        Thread threadChangePass = new Thread(new Runnable() {
            String uniqueID = UUID.randomUUID().toString();
            String token = sharedPrefManager.getLoginToken();

            @Override
            public void run() {
                ApiInterface apiInterface = RestProvider.getClient().create(ApiInterface.class);
                Call<ChangeEmailResponse> call = apiInterface.changeEmail(uniqueID, Constants.HEADER_AGENT, Constants.HEADER_VERSION, token,
                        new ChangeEmail(userName, newEmail));

                try {
                    Response<ChangeEmailResponse> resp = call.execute();
                    Log.d(TAG, "token: " + token);
                    Log.d(TAG, "resp code: " + resp.code());
                    Log.d(TAG, "username: " + userName);
                    Log.d(TAG, "new email: " + newEmail);
                    retVal[0] = String.valueOf(resp.code());

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
