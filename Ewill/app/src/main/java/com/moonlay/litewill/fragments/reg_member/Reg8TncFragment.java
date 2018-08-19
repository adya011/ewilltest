package com.moonlay.litewill.fragments.reg_member;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.moonlay.litewill.R;
import com.moonlay.litewill.RegMemberActivity;
import com.moonlay.litewill.api.ApiInterface;
import com.moonlay.litewill.api.GetToken;
import com.moonlay.litewill.api.RestProvider;
import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.model.RegisterUser;
import com.moonlay.litewill.model.RegisterUserResponse;
import com.moonlay.litewill.utility.SharedPrefManager;

import java.io.IOException;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Reg8TncFragment extends BaseFragment {
    Button btnNext;
    CheckBox cbAgree;
    ApiInterface apiInterface;
    SharedPrefManager sharedPrefManager;

    public Reg8TncFragment() {
        // Required empty public constructor
    }

    public static Reg8TncFragment newInstance(String email, String username, String password, String phonenumber, String pin, String question, String answer) {
        Reg8TncFragment frag = new Reg8TncFragment();
        Bundle bundle = new Bundle();
        bundle.putString("email", email);
        bundle.putString("username", username);
        bundle.putString("password", password);
        bundle.putString("phonenumber", phonenumber);
        bundle.putString("pin", pin);
        bundle.putString("question", question);
        bundle.putString("answer", answer);
        frag.setArguments(bundle);

        Log.d("mydebug_register", "reg8: " + email + " " + username + " " + password + " " + phonenumber + " " + pin);

        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_reg8_tnc, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView = view;

        initView();
        ((RegMemberActivity) getActivity())
                .setActionBarTitle("Account Opening Terms and Conditions");

        hideKeyboard(getActivity());
    }

    private void initView() {
        btnNext = mView.findViewById(R.id.button_next8);
        cbAgree = mView.findViewById(R.id.cb_agree);
        btnNext.setEnabled(false);

        final String email = getArguments().getString("email");
        final String username = getArguments().getString("username");
        final String password = getArguments().getString("password");
        final String phone = getArguments().getString("phonenumber");
        final String pin = getArguments().getString("pin");
        final String question = getArguments().getString("question");
        final String answer = getArguments().getString("answer");
        final String countrycode = "INA";

        cbAgree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    btnNext.setEnabled(true);
                } else {
                    btnNext.setEnabled(false);
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (requestRegister(pin, username, email, password, phone, question, answer, countrycode).equals(Constants.SUCCESS)) {
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content_register, Reg9EmailVerifyFragment.newInstance(username), "reg9");
                        ft.addToBackStack("reg8");
                        ft.commit();

                        String username = getArguments().getString("username");

                        sharedPrefManager = new SharedPrefManager(getContext());
                        sharedPrefManager.setRegStatus(Constants.REG_WAIT_VER_EMAIL);
                        sharedPrefManager.setRegUsername(username);

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Alert");
                        builder.setPositiveButton("OK", null);
                        builder.setMessage("Registration Failed. Problem has occured");

                        AlertDialog theAlertDialog = builder.create();
                        theAlertDialog.show();

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public String requestRegister(final String pin, final String username, final String email, final String password, final String phone,
                                  final String question, final String answer, final String countrycode) throws InterruptedException {
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

        Thread threadRegister = new Thread(new Runnable() {

            @Override
            public void run() {
                String uniqueID = UUID.randomUUID().toString();
                apiInterface = RestProvider.getClient().create(ApiInterface.class);
                Call<RegisterUserResponse> call = apiInterface.registerUser(uniqueID, Constants.HEADER_AGENT, Constants.HEADER_VERSION, token[0],
                        new RegisterUser(pin, username, email, password, phone, question, answer, countrycode, false));

                Log.d(RegMemberActivity.TAG, uniqueID + ", " + Constants.HEADER_AGENT + ", " + Constants.HEADER_VERSION + ", " + token[0]);

                Log.d(RegMemberActivity.TAG, "email: " + email + ", username: " + username + ", password: " + password
                        + ", phone: " + phone + ", pin: " + pin + ", question: " + question + ", answer: " + answer + ", countrCode: " + countrycode);

                try {
                    Response<RegisterUserResponse> resp = call.execute();
                    Log.d(RegMemberActivity.TAG, "register resp code: " + resp.code());

                    if (resp.code() == 200 && resp.isSuccessful() == true) {
                        retVal[0] = Constants.SUCCESS;


                    } else {
                        retVal[0] = Constants.FAILED;
                        Log.d(RegMemberActivity.TAG, "url: " + resp.raw().request().url());

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        threadRegister.start();
        threadRegister.join();

        return retVal[0];
    }
}
