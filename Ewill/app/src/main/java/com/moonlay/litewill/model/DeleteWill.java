package com.moonlay.litewill.model;

/**
 * Created by nandana.samudera on 7/4/2018.
 */

public class DeleteWill {
    private int willId;

    public DeleteWill(int willId) {
        this.willId = willId;
    }

    public int getWillId() {
        return willId;
    }

    public void setWillId(int willId) {
        this.willId = willId;
    }
}
