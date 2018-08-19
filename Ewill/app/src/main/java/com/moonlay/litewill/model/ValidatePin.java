package com.moonlay.litewill.model;

/**
 * Created by nandana.samudera on 5/24/2018.
 */

public class ValidatePin {
    private String pin;
    private String userName;

    public ValidatePin(String pin, String userName) {
        this.pin = pin;
        this.userName = userName;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
