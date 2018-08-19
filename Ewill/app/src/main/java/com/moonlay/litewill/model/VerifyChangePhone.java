package com.moonlay.litewill.model;

public class VerifyChangePhone {
    private String userName;
    private String token;
    private String newPhoneNumber;

    public VerifyChangePhone(String userName, String token, String newPhoneNumber) {
        this.userName = userName;
        this.token = token;
        this.newPhoneNumber = newPhoneNumber;
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

    public String getNewPhoneNumber() {
        return newPhoneNumber;
    }

    public void setNewPhoneNumber(String newPhoneNumber) {
        this.newPhoneNumber = newPhoneNumber;
    }
}
