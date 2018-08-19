package com.moonlay.litewill.model;

/**
 * Created by nandana.samudera on 6/26/2018.
 */

public class Shared {
    private int id;
    private String email;
    private boolean isDocShared;
    private boolean isLocShared;

    public Shared(String email, boolean isDocShared, boolean isLocShared) {
        this.email = email;
        this.isDocShared = isDocShared;
        this.isLocShared = isLocShared;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isDocShared() {
        return isDocShared;
    }

    public void setDocShared(boolean docShared) {
        isDocShared = docShared;
    }

    public boolean isLocShared() {
        return isLocShared;
    }

    public void setLocShared(boolean locShared) {
        isLocShared = locShared;
    }
}
