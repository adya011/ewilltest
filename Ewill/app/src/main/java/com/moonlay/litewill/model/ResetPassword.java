package com.moonlay.litewill.model;

/**
 * Created by nandana.samudera on 5/7/2018.
 */

public class ResetPassword {
    private String username;
    private String token;
    private String newPassword;

    public ResetPassword(String username, String token, String newPassword) {
        this.username = username;
        this.token = token;
        this.newPassword = newPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
