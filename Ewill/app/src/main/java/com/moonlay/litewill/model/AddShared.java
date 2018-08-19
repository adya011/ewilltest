package com.moonlay.litewill.model;

/**
 * Created by nandana.samudera on 7/13/2018.
 */

public class AddShared {
    private int willId;
    private Shared[] addShareds;

    public AddShared(int willId, Shared[] addShareds) {
        this.willId = willId;
        this.addShareds = addShareds;
    }

    public int getWillId() {
        return willId;
    }

    public void setWillId(int willId) {
        this.willId = willId;
    }

    public Shared[] getAddShareds() {
        return addShareds;
    }

    public void setAddShareds(Shared[] addShareds) {
        this.addShareds = addShareds;
    }
}