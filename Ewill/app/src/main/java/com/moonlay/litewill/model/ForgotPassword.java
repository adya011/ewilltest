package com.moonlay.litewill.model;

/**
 * Created by nandana.samudera on 5/3/2018.
 */

public class ForgotPassword {
    private String email;

    public ForgotPassword(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
