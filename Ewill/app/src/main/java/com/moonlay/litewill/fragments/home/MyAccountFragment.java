package com.moonlay.litewill.fragments.home;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.moonlay.litewill.DashboardActivity;
import com.moonlay.litewill.LoginActivity;
import com.moonlay.litewill.R;
import com.moonlay.litewill.api.ApiInterface;
import com.moonlay.litewill.api.RestProvider;
import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.dialogfragment.PinFragment;
import com.moonlay.litewill.fragments.change.ChangeEmail1Fragment;
import com.moonlay.litewill.fragments.change.ChangeMobileNum1Fragment;
import com.moonlay.litewill.fragments.change.ChangePIN1Fragment;
import com.moonlay.litewill.fragments.change.ChangePass1Fragment;
import com.moonlay.litewill.fragments.change.ChangePass3Fragment;
import com.moonlay.litewill.fragments.change.RecoverPin1Fragment;
import com.moonlay.litewill.model.Logout;
import com.moonlay.litewill.model.LogoutResponse;
import com.moonlay.litewill.utility.SharedPrefManager;

import java.io.IOException;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAccountFragment extends BaseFragment {
    LinearLayout ChangePass, ChangePin, ChangeEmail, ChangeMobileNum, RecoverPin, Logout;
    public static boolean verify = false;
    ApiInterface apiInterface;
    String loginToken, userLoggedIn;
    SharedPrefManager sharedPrefManager;

    public static MyAccountFragment newInstance(String token, String username, String newEmail) {
        MyAccountFragment fragment = new MyAccountFragment();
        Bundle args = new Bundle();
        args.putString("uriToken", token);
        args.putString("username", username);
        args.putString("newEmail", newEmail);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //((DashboardActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        //((DashboardActivity) getActivity()).setActionBarTitle("Home");
        return inflater.inflate(R.layout.fragment_my_account, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = new Bundle();
        sharedPrefManager = new SharedPrefManager(getContext());
        loginToken = sharedPrefManager.getLoginToken();
        userLoggedIn = sharedPrefManager.getUserLoggedIn();

        if (args != null && args.containsKey("uriToken")) {
            String getUriToken = getArguments().getString("uriToken");

            Log.d("mydebug", "uri token: " + getUriToken);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.frame_tab2_layout, new ChangePass3Fragment());
            ft.commit();

        } else {
            initView();
        }
    }

    private void showPinDialog(String title) {
        FragmentManager fm = getFragmentManager();
        PinFragment pinFrag = PinFragment.newInstance(title);
        pinFrag.show(fm, "fragment_edit_name");
    }

    private void initView() {
        ChangePass = mView.findViewById(R.id.chg_pass);
        ChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPinDialog("Change Email");
                fragmentTrans(R.id.frame_tab2_layout, new ChangePass1Fragment(), null, null);
            }
        });

        ChangePin = mView.findViewById(R.id.chg_pin);
        ChangePin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPinDialog("Change PIN");
                fragmentTrans(R.id.frame_tab2_layout, new ChangePIN1Fragment(), null, null);
            }
        });

        ChangeEmail = mView.findViewById(R.id.chg_email);
        ChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPinDialog("Change Email");
                fragmentTrans(R.id.frame_tab2_layout, new ChangeEmail1Fragment(), null, null);
            }
        });

        ChangeMobileNum = mView.findViewById(R.id.chg_mobilenum);
        ChangeMobileNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPinDialog("Change Mobile Number");
                //fragmentTrans(R.id.frame_tab2_layout, new ChangeMobileNum1Fragment(), null, null);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame_tab2_layout, new ChangeMobileNum1Fragment());
                ft.commit();
            }
        });

        RecoverPin = mView.findViewById(R.id.recover_pin);
        RecoverPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPinDialog("Recover PIN");
                fragmentTrans(R.id.frame_tab2_layout, new RecoverPin1Fragment(), null, null);
            }
        });

        Logout = mView.findViewById(R.id.logout);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (requestLogout().equals(Constants.SUCCESS)) {
                        Log.d("mydebug_home", "LOGOUT SUCCESS");
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String requestLogout() throws InterruptedException {
        final String[] retVal = {null};

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String uniqueID = UUID.randomUUID().toString();
                apiInterface = RestProvider.getClient().create(ApiInterface.class);
                Call<LogoutResponse> call = apiInterface.logout(uniqueID, Constants.HEADER_AGENT, Constants.HEADER_VERSION, loginToken,
                        new Logout(userLoggedIn));

                try {
                    Response<LogoutResponse> resp = call.execute();
                    Log.d("mydebug_home", "logout resp code: " + resp.code());

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
        thread.start();
        thread.join();

        return retVal[0];
    }
}
