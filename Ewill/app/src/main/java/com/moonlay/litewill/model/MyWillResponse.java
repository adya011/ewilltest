package com.moonlay.litewill.model;

import java.util.ArrayList;

/**
 * Created by nandana.samudera on 7/2/2018.
 */

public class MyWillResponse {
    private boolean success;
    private ArrayList<MyWillDetail> result;
    private String version;
    private String timestamp;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<MyWillDetail> getResult() {
        return result;
    }

    public void setResult(ArrayList<MyWillDetail> result) {
        this.result = result;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
