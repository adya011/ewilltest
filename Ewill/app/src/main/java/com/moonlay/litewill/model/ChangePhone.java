package com.moonlay.litewill.model;

public class ChangePhone {
    private String userName;
    private String newPhoneNumber;

    public ChangePhone(String userName, String newPhoneNumber) {
        this.userName = userName;
        this.newPhoneNumber = newPhoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNewPhoneNumber() {
        return newPhoneNumber;
    }

    public void setNewPhoneNumber(String newPhoneNumber) {
        this.newPhoneNumber = newPhoneNumber;
    }
}
