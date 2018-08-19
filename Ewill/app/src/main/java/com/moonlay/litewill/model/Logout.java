package com.moonlay.litewill.model;

/**
 * Created by nandana.samudera on 5/24/2018.
 */

public class Logout {
    private String userName;

    public Logout(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
