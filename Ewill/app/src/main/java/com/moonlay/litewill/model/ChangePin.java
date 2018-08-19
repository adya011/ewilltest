package com.moonlay.litewill.model;

/**
 * Created by nandana.samudera on 5/11/2018.
 */

public class ChangePin {
    private String userName;
    private String newPin;

    public ChangePin(String userName, String newPin) {
        this.userName = userName;
        this.newPin = newPin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNewPin() {
        return newPin;
    }

    public void setNewPin(String newPin) {
        this.newPin = newPin;
    }
}
