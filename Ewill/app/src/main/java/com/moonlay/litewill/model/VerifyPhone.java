package com.moonlay.litewill.model;

/**
 * Created by nandana.samudera on 4/19/2018.
 */

public class VerifyPhone {
    private String userName;
    private String token;

    public VerifyPhone(String userName, String token) {
        this.userName = userName;
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
