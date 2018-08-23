package com.moonlay.litewill.fragments.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.moonlay.litewill.ForgotActivity;
import com.moonlay.litewill.DashboardActivity;
import com.moonlay.litewill.LoginActivity;
import com.moonlay.litewill.MainActivity;
import com.moonlay.litewill.R;
import com.moonlay.litewill.RegMemberActivity;
import com.moonlay.litewill.api.ApiInterface;
import com.moonlay.litewill.api.GetToken;
import com.moonlay.litewill.api.RestProvider;
import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.fragments.BaseFragment;
import com.moonlay.litewill.model.LoginUser;
import com.moonlay.litewill.model.LoginUserResponse;
import com.moonlay.litewill.model.MemberInfoResponse;
import com.moonlay.litewill.model.OAuthToken;
import com.moonlay.litewill.utility.SharedPrefManager;

import java.io.IOException;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment {
    private static String TAG = "mydebug_login";
    TextView tvRegister, tvForgotUsername, tvForgotPassword;
    EditText etUsername, etPassword;
    Button btnLogin;
    SharedPrefManager sharedPrefManager;
    ApiInterface apiInterfaceLogin, apiInterface;
    GetToken getRest;
    String loginToken = null;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apiInterface = RestProvider.getClient2().create(ApiInterface.class);

        init();
        sharedPrefManager.removeRegStatus();
        sharedPrefManager.removeRegUsername();
    }

    private void init() {
        etUsername = mView.findViewById(R.id.et_username);
        etPassword = mView.findViewById(R.id.et_password);
        btnLogin = mView.findViewById(R.id.button_login);
        getRest = new GetToken();
        sharedPrefManager = new SharedPrefManager(getContext());
        String lastLoggedInUsername = sharedPrefManager.getUserLastLoggedIn();

        /*sharedPrefManager.addNotificationCount(2);*/

        if (lastLoggedInUsername != null) {
            etUsername.setText(lastLoggedInUsername, TextView.BufferType.EDITABLE);
            etPassword.requestFocus();

        } else {
            etUsername.requestFocus();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String etUsernameStr = etUsername.getText().toString();
                String etPasswordstr = etPassword.getText().toString();

                /*DashboardActivity dashboardActivity = new DashboardActivity();
                dashboardActivity.updateNotificationBadgeCount(9);*/

                /*sharedPrefManager.addNotificationCount(1);*/

                try {
                    if (requestLogin(etUsernameStr, etPasswordstr).equals(Constants.SUCCESS)) {
                        sharedPrefManager.setLoginToken("Bearer " + loginToken);
                        sharedPrefManager.setUserLoggedIn(etUsernameStr);

                        Log.d(LoginActivity.TAG, "User logged in, Welcome, " + sharedPrefManager.getUserLoggedIn());
                        Log.d(LoginActivity.TAG, "Get login token " + sharedPrefManager.getLoginToken());

                        Thread threadMemberInfo = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    requestMemberInfo();
                                    startActivity(new Intent(getContext(), DashboardActivity.class));
                                    getActivity().finish();
                                    MainActivity.isUserLoggedIn = true;

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        threadMemberInfo.start();
                        threadMemberInfo.join();

                    } else {
                        showErrorDialog("Cannot login to appllication. Problem occured.");
                        Log.d(LoginActivity.TAG, "name: " + etUsernameStr + " pass: " + etPasswordstr);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        tvRegister = mView.findViewById(R.id.tv_register);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RegMemberActivity.class);
                startActivity(intent);
            }
        });

        tvForgotUsername = mView.findViewById(R.id.tv_forgot_username);
        tvForgotUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ForgotActivity.class);
                intent.putExtra("action", Constants.FORGOT_USERNAME);
                startActivity(intent);
            }
        });

        tvForgotPassword = mView.findViewById(R.id.tv_forgot_password);
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ForgotActivity.class);
                intent.putExtra("action", Constants.FORGOT_PASSWORD);
                startActivity(intent);
            }
        });
    }

    public String requestLogin(final String userName, final String password) throws InterruptedException {
        final String[] token = {null};
        final String[] retVal = {"none"};

        Thread threadToken = new Thread(new Runnable() {
            @Override
            public void run() {
                token[0] = getRest.requestToken();
            }
        });
        threadToken.start();
        threadToken.join();

        Thread threadLogin = new Thread(new Runnable() {
            String uniqueID = UUID.randomUUID().toString();

            @Override
            public void run() {
                apiInterfaceLogin = RestProvider.getRestLogin().create(ApiInterface.class);
                Call<LoginUserResponse> call = apiInterfaceLogin.loginUser(uniqueID, Constants.HEADER_AGENT, Constants.HEADER_VERSION, token[0],
                        new LoginUser(userName, password, Constants.HEADER_CLIENT_ID, Constants.HEADER_CLIENT_SECURITY));

                try {
                    Response<LoginUserResponse> resp = call.execute();
                    Log.d(LoginActivity.TAG, "login resp code: " + resp.code());
                    Log.d(LoginActivity.TAG, "username: " + userName + " | password: " + password);

                    if (resp.code() == 200 && resp.isSuccessful() == true) {
                        loginToken = resp.body().getResult().getAccessToken();

                        retVal[0] = Constants.SUCCESS;

                    } else {
                        retVal[0] = Constants.FAILED;
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        threadLogin.start();
        threadLogin.join();

        return retVal[0];
    }

    private void requestMemberInfo() throws IOException {
        String token = sharedPrefManager.getLoginToken();
        String uniqueID = UUID.randomUUID().toString();

        Call<MemberInfoResponse> call = apiInterface.memberInfo(uniqueID, Constants.HEADER_AGENT,
                Constants.HEADER_VERSION, token);
        Response<MemberInfoResponse> resp = call.execute();
        if (resp.code() == 200 && resp.isSuccessful()) {
            try {
                Log.d(TAG, "Get Member Info: Response code: " + resp.code());
                int memberType = resp.body().getResult().get(0).getMemberTypeId();
                Log.d(TAG, "member info: membertype: " + memberType);
                String userEmail = resp.body().getResult().get(0).getEmail();
                Log.d(TAG, "member info: email: " + userEmail);
                sharedPrefManager.setUserMemberType(memberType);
                sharedPrefManager.setUserEmail(userEmail);
                Log.d(TAG, "shared pref: get user email " + sharedPrefManager.getUserEmail());

            } catch (Exception e) {
                Log.d(TAG, "Exception: " + e);
                Toast.makeText(getContext(), "Can't get member info", Toast.LENGTH_SHORT);
            }
        }
    }
}
