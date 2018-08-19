package com.moonlay.litewill.model;

/**
 * Created by nandana.samudera on 5/23/2018.
 */

public class VerifyEmailChange {
    private String userName;
    private String token;
    private boolean isChangeEmail;
    private String newEmail;

    public VerifyEmailChange(String userName, String token, boolean isChangeEmail, String newEmail) {
        this.userName = userName;
        this.token = token;
        this.isChangeEmail = isChangeEmail;
        this.newEmail = newEmail;
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

    public boolean isChangeEmail() {
        return isChangeEmail;
    }

    public void setChangeEmail(boolean changeEmail) {
        isChangeEmail = changeEmail;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }
}
