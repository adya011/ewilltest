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
import com.moonlay.litewill.model.NotificationDb;
import com.moonlay.litewill.model.VerifyEmail;
import com.moonlay.litewill.model.VerifyEmailChange;
import com.moonlay.litewill.model.VerifyEmailResponse;
import com.moonlay.litewill.utility.DatabaseHelper;
import com.moonlay.litewill.utility.MyService;
import com.moonlay.litewill.utility.SharedPrefManager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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

    public static boolean isDeepLinkRegister = false;
    public static boolean isDeepLinkChangeEmail = false;
    public static boolean isDeepLinkForgotPass = false;

    public static String TAG = "mydebug_start";
    public static String TAG_URI = "mydebug_uri";
    public static String TAG_VERIFY_EMAIL = "mydebug_verify";
    public static String TAG_VERIFY_EMAIL_CHANGE = "mydebug_verifychange";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        init();
        startMyService();
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

        //insertNotificationDb(223, "Will Updated");

        if (uriData != null && action != null) {
            try {
                deepLink(uriData);
                Log.d("mydebug", " | URI DATA: " + String.valueOf(uriData) + " | ACTION: " + String.valueOf(action) + " | SCHEME: " + scheme);

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

    private void startMyService() {
        Intent intent = new Intent(getApplicationContext(), MyService.class);
        startService(intent);
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
                isDeepLinkRegister = true;
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
                isDeepLinkForgotPass = true;
                activity = ForgotActivity.class;
                break;

            case "change-email":
                isDeepLinkChangeEmail = true;
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

    DatabaseHelper db;
    List<NotificationDb> notificationDbList = new ArrayList<>();

    /*private void createNotification() {
        db = new DatabaseHelper(this);

        long id = db.insertNotification("will name", "updated will",
                false, "will creator", "myemail@gmail.com");

        NotificationDb n = db.getNotification(id);

        if (n != null) {
            notificationDbList.add(0, n);
            Log.d(TAG, "n not null");
            Log.d(TAG, "notification.add creator name: " + n.getCreator_name());
            Log.d(TAG, "notification.add will name: " + n.getWill_name());
        }
    }*/

    private void insertNotificationDb(int willId, String willName, String flag) {
        db = new DatabaseHelper(this);
        sharedPrefManager = new SharedPrefManager(this);

        String userEmail = sharedPrefManager.getUserEmail();

        long id = db.insertNotification(willId, willName, flag, userEmail);

        NotificationDb n = db.getNotification(id);
        if (n != null) {
            notificationDbList.add(0, n);
            Log.d(TAG, "n not null");
            Log.d(TAG, "notification.add will id: " + n.getWill_id());
            Log.d(TAG, "notification.add flag: " + n.getFlag());
        }
    }

    private void showNotifications() {
        Log.d(TAG, "show notifications");
        db = new DatabaseHelper(this);

        int notificationCount = db.getNotificationCount();
        Log.d(TAG, "notification count: " + notificationCount);

        /*try {
            notificationDbList.add(db.getNotification(1));
            Log.d(TAG, "will name 0: " + notificationDbList.get(0).getWill_name());
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        try {
            notificationDbList.addAll(db.getAllNotification());
            String willName = notificationDbList.get(0).getWill_name();
            Log.d(TAG, "will name 0: " + willName);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "exception: " + e);
        }
    }
}
