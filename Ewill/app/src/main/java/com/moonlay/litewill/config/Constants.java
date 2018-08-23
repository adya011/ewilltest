package com.moonlay.litewill.config;

/**
 * Created by nandana.samudera on 3/16/2018.
 */

public class Constants {
    public static final String BASE_URL = "http://11.11.5.146:5541/api/";
    public static final String ID_URL = "http://11.11.5.146:5550/api/";
    public static final String LOGIN_URL = "http://11.11.5.146:5550/api/";
    public static final String TOKEN_URL = "http://11.11.5.146:5540/";
    public static final String STORAGE_URL = "http://ewilldevstorage.blob.core.windows.net/pictures/";

    public static final String IDENTITY_URL = "http://11.11.5.146:5550/api/";
    public static final String MEMBERSHIP_URL = "http://11/11.5.146:5541/api/";


    public static final String FORGOT_USERNAME = "forgot_username";
    public static final String FORGOT_PASSWORD = "forgotpassword";
    public static final String CHANGE_EMAIL = "change_email";

    public static final String TAG = "Learn2Crack";

    public static final String[] phoneCountry = {"Indonesia", "Malaysia", "Singapore", "Philippines", "Thailand"};
    public static final String[] gender = {"Male", "Female"};
    public static final String[] yesNo = {"Yes", "No"};
    public static final String[] phoneCode = {"+62", "+60", "+65", "+63", "+66"};
    public static final String[] secQuestion = {"What is your first pet name?", "What is your brother's name?", "What is your mother's name?", "What is your cousin's name?"};
    public static final String[] upgradeTo = {"Law Firm", "Individual", "Public Authority"};
    public static final String[] payPackage = {"2 Years", "5 Years"};

    public static final String MY_PREFERENCES = "MyPrefs";

    public static final String WILL_UPDATED = "Will Updated";
    public static final String WILL_DELETED = "Will Deleted";
    public static final String WILL_NOWILL = "Have No Will";

    // === API ===
    public static final String SUCCESS = "success";
    public static final String FAILED = "failed";
    public static final String DEFAULT = "default";


    // === REGISTRASI ===

    // Shared Pref
    public static final String LOGGEDIN_USERNAME = "loggedin_username";
    public static final String LOGGEDIN_TOKEN = "loggedin_token";
    public static final String LAST_LOGGEDIN_USERNAME = "last_loggedin_username";
    public static final String REG_STATUS = "reg_status";
    public static final String REG_USERNAME = "username_register";
    public static final String REG_WAIT_VER_EMAIL = "wait_verify_email";
    public static final String REG_WAIT_VER_PHONE = "wait_verify_phone";
    public static final String REG_VER_EMAIL = "verify_email";

    // API
    public static final String HEADER_CLIENT_ID = "mobile.droid";
    public static final String HEADER_CLIENT_SECURITY = "Xv5LpK8K15";
    public static final String HEADER_GRANT_TYPE = "client_credentials";
    public static final String HEADER_GRANT_TYPE_REFRESH = "refresh_token";
    public static final String HEADER_SCOPE = "ewill";
    public static final String HEADER_AGENT = "mobile";
    public static final String HEADER_VERSION = "1.0";

    public static final int MIN_AGE = 21;

    //KEY
    public static final String STORAGE_CONNECTION_STRING = "DefaultEndpointsProtocol=https;"
            + "AccountName=ewilldevstorage;"
            + "AccountKey=ttlmYNTs4C4t7YlqynkeZ5CYCeNsiPWsEnF9iXaGFPKybqK1SPfN84l7a8WrKLpz6RBzIpDZkFcSH5IgJ4auOA==;"
            + "EndpointSuffix=core.windows.net";


}