package com.moonlay.litewill.model;

/**
 * Created by nandana.samudera on 7/6/2018.
 */

public class UpdateShared {
    private int sharedId;
    private String email;
    private boolean isDocShared;
    private boolean isLocShared;

    public UpdateShared(int sharedId, String email, boolean isDocShared, boolean isLocShared) {
        this.sharedId = sharedId;
        this.email = email;
        this.isDocShared = isDocShared;
        this.isLocShared = isLocShared;
    }

    public int getSharedId() {
        return sharedId;
    }

    public void setSharedId(int sharedId) {
        this.sharedId = sharedId;
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
