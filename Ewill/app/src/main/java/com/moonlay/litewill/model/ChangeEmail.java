package com.moonlay.litewill.model;

/**
 * Created by nandana.samudera on 5/15/2018.
 */

public class ChangeEmail {
    private String userName;
    private String newEmail;

    public ChangeEmail(String userName, String newEmail) {
        this.userName = userName;
        this.newEmail = newEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }
}
