package com.moonlay.litewill;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.moonlay.litewill.api.ApiInterface;
import com.moonlay.litewill.api.GetToken;
import com.moonlay.litewill.api.RestProvider;
import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.model.VerifyEmail;
import com.moonlay.litewill.model.VerifyEmailChange;
import com.moonlay.litewill.model.VerifyEmailResponse;
import com.moonlay.litewill.utility.SharedPrefManager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    ApiInterface apiInterface;

    String uriHost;
    String uriToken;
    String uriUserName;
    String uriCommand;
    String uriNewEmail;
    String loginToken;

    public static boolean isUserLoggedIn = false;
    public static boolean isDeepLink = false;

    public static String TAG = "mydebug_start";
    public static String TAG_URI = "mydebug_uri";
    public static String TAG_VERIFY_EMAIL = "mydebug_verify";
    public static String TAG_VERIFY_EMAIL_CHANGE = "mydebug_verifychange";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        Intent intent = getIntent();
        String action = intent.getAction();
        Uri uriData = intent.getData();
        String scheme = intent.getScheme();

        sharedPrefManager = new SharedPrefManager(this);
        loginToken = sharedPrefManager.getLoginToken();

        sharedPrefManager.clearUserMemberType();
        Log.d(TAG, "member type: " + sharedPrefManager.getUserMemberType());

        String loggedInUsername = sharedPrefManager.getUserLoggedIn();
        String regUsername = sharedPrefManager.getRegUsername();
        String regStatus = sharedPrefManager.getRegStatus();

        Log.d(TAG, "logged in username: " + loggedInUsername);
        Log.d(TAG, "reg username: " + regUsername);
        Log.d(TAG, "reg status: " + String.valueOf(regStatus));

        if (uriData != null && action != null) {
            try {
                deepLink(uriData);
                isDeepLink = true;
                Log.d("mydebug", "DEEPLINK: " + isDeepLink + " | URI DATA: " + String.valueOf(uriData) + " | ACTION: " + String.valueOf(action) + " | SCHEME: " + scheme);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {
            Log.d("mydebug", "uridata null");
            switch (String.valueOf(regStatus)) {
                case Constants.REG_WAIT_VER_EMAIL:
                    showRegister(Constants.REG_WAIT_VER_EMAIL);
                    break;

                case Constants.REG_WAIT_VER_PHONE:
                    showRegister(Constants.REG_WAIT_VER_PHONE);
                    break;

                case "null":
                    showLogin();
                    break;
            }
        }
    }

    //Open app via deeplink email confirmation
    private void deepLink(Uri uriData) throws UnsupportedEncodingException {
        String uriDataStr = uriData.toString();
        String[] splitUriData = uriDataStr.split("ewill://|\\?token=|&userName=|&command=|&nemail=");

        uriHost = splitUriData[1];
        uriToken = URLDecoder.decode(splitUriData[2], "UTF-8");
        uriUserName = splitUriData[3];
        uriCommand = splitUriData[4];

        if (splitUriData.length > 5) {
            uriNewEmail = String.valueOf(splitUriData[5]).replace("%40", "@");
        }

        Log.d(TAG_URI, "split length: " + splitUriData.length);
        Log.d(TAG_URI, "raw: " + uriDataStr);
        Log.d(TAG_URI, "host: " + uriHost);
        Log.d(TAG_URI, "token: " + uriToken);
        Log.d(TAG_URI, "username: " + uriUserName);
        Log.d(TAG_URI, "command: " + uriCommand);
        Log.d(TAG_URI, "new email: " + String.valueOf(uriNewEmail));

        Class activity = null;
        Bundle bundle = new Bundle();

        switch (uriHost) {
            case "register":
                try {
                    if (requestVerifyEmailRegister().equals(Constants.SUCCESS)) {
                        Log.d(TAG_URI, "request verify email" + requestVerifyEmailRegister());
                        sharedPrefManager.setRegStatus(Constants.REG_WAIT_VER_PHONE);
                        Log.d(TAG_URI, "register status: " + sharedPrefManager.getRegStatus());

                        /*Intent intent = new Intent(this, RegMemberActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();*/

                        activity = RegMemberActivity.class;

                    } else {
                        Log.d(TAG_URI, "VERIFY EMAIL FAILED");
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;

            case "forgotpassword":
                activity = ForgotActivity.class;
                break;

            case "change-email":
                if (isUserLoggedIn) {
                    try {
                        if (requestVerifyEmailChangeEmail(loginToken, uriToken, uriUserName, uriNewEmail).equals(Constants.SUCCESS)) {
                            Log.d(TAG_URI, "request verify change email: " + Constants.SUCCESS);
                            activity = DashboardActivity.class;

                        } else {
                            Log.d(TAG_URI, "request verify change email: " + Constants.FAILED);
                            activity = LoginActivity.class;
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    activity = LoginActivity.class;
                }
                break;
        }

        Intent intent = new Intent(this, activity);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void showLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void showRegister(String action) {
        Intent intent = new Intent(this, RegMemberActivity.class);
        intent.putExtra("action", action);
        startActivity(intent);
        finish();
    }

    private String requestVerifyEmailRegister() throws InterruptedException {
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

        Thread threadVerifyEmail = new Thread(new Runnable() {
            @Override
            public void run() {
                String uniqueID = UUID.randomUUID().toString();
                apiInterface = RestProvider.getClient().create(ApiInterface.class);
                Call<VerifyEmailResponse> call = apiInterface.verifyEmail(uniqueID, Constants.HEADER_AGENT, Constants.HEADER_VERSION, token[0],
                        new VerifyEmail(uriUserName, uriToken));
                Log.d("mydebug_verifychg", "start");

                try {
                    Response<VerifyEmailResponse> resp = call.execute();
                    Log.d(TAG_VERIFY_EMAIL, "username: " + uriUserName);
                    Log.d(TAG_VERIFY_EMAIL, "email token: " + uriToken);
                    Log.d(TAG_VERIFY_EMAIL, "verify email resp code: " + resp.code());
                    Log.d(TAG_VERIFY_EMAIL, "verify email success: " + resp.isSuccessful());

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
        threadVerifyEmail.start();
        threadVerifyEmail.join();

        return retVal[0];
    }

    private String requestVerifyEmailChangeEmail(final String loginToken, final String uriToken, final String uriUserName, final String uriNewEmail) throws InterruptedException {
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
        Thread threadVerifyEmail = new Thread(new Runnable() {
            @Override
            public void run() {
                String uniqueID = UUID.randomUUID().toString();
                apiInterface = RestProvider.getClient().create(ApiInterface.class);
                Call<VerifyEmailResponse> call = apiInterface.verifyEmailChange(uniqueID, Constants.HEADER_AGENT, Constants.HEADER_VERSION, token[0],
                        new VerifyEmailChange(uriUserName, uriToken, true, uriNewEmail));

                try {
                    Response<VerifyEmailResponse> resp = call.execute();
                    Log.d(TAG_VERIFY_EMAIL_CHANGE, "username: " + uriUserName);
                    Log.d(TAG_VERIFY_EMAIL_CHANGE, "email token: " + uriToken);
                    Log.d(TAG_VERIFY_EMAIL_CHANGE, "verify email resp code: " + resp.code());
                    Log.d(TAG_VERIFY_EMAIL_CHANGE, "verify email success: " + resp.isSuccessful());

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
        threadVerifyEmail.start();
        threadVerifyEmail.join();

        return retVal[0];
    }
}
