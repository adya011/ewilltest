package com.moonlay.litewill.model;

/**
 * Created by nandana.samudera on 7/3/2018.
 */

public class MyWillDetailResponse {
    private boolean success;
    private MyWillDetail[] result;
    private String version;
    private String timestamp;

    public MyWillDetailResponse(boolean success, MyWillDetail[] result, String version, String timestamp) {
        this.success = success;
        this.result = result;
        this.version = version;
        this.timestamp = timestamp;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public MyWillDetail[] getResult() {
        return result;
    }

    public void setResult(MyWillDetail[] result) {
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
