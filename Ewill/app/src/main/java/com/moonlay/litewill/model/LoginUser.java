package com.moonlay.litewill.model;

/**
 * Created by nandana.samudera on 5/9/2018.
 */

public class LoginUser {
    private String userName;
    private String password;
    private String clientId;
    private String clientKey;

    public LoginUser(String userName, String password, String clientId, String clientKey) {
        this.userName = userName;
        this.password = password;
        this.clientId = clientId;
        this.clientKey = clientKey;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientKey() {
        return clientKey;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }
}
